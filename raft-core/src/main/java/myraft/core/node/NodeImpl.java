package myraft.core.node;

import com.google.common.eventbus.Subscribe;
import myraft.core.node.role.AbstractNodeRole;
import myraft.core.node.role.CandidateNodeRole;
import myraft.core.node.role.FollowerNodeRole;
import myraft.core.node.role.RoleName;
import myraft.core.node.store.NodeStore;
import myraft.core.rpc.message.RequestVoteResult;
import myraft.core.rpc.message.RequestVoteRpc;
import myraft.core.rpc.message.RequestVoteRpcMessage;
import myraft.core.schedule.ElectionTimeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.relation.Role;
import java.util.Objects;

/**
 * @author fptang
 * @date 2021/10/27
 */
public class NodeImpl implements Node {
    private static final Logger logger = LoggerFactory.getLogger(NodeImpl.class);
    private final NodeContext context;  // 核心组件上下文
    private boolean started; // 是否已启动
    private AbstractNodeRole role; // 当前的角色及信息

    // 构造函数
    NodeImpl(NodeContext context) {
        this.context = context;
    }


    @Override
    public synchronized void start() {
        // 如果已经启动，则直接跳过
        if (started) {
            return;
        }
        // 注册自己到EventBus
        context.eventBus().register(this);
        // 初始化连接器
        context.connector().initialize();
        // 启动时为Follower角色
        NodeStore store = context.store();
        changeToRole(new FollowerNodeRole(
                store.getTerm(), store.getVotedFor(), null, scheduleElectionTimeout()
        ));
        started = true;
    }

    @Override
    public synchronized void stop() throws InterruptedException {
        // 不允许没有启动时关闭
        if (!started) {
            throw new IllegalStateException("node not started");
        }
        // 关闭定时器
        context.scheduler().stop();
        // 关闭连接器
        context.connector().close();
        // 关闭任务执行器
        context.taskExecutor().shutdown();
        started = false;
    }

    private void changeToRole(AbstractNodeRole newRole) {
        logger.debug("node {}, role state changed -> {}", context.selfId(), newRole);
        NodeStore store = context.store();
        store.setTerm(newRole.getTerm());
        if (newRole.getName() == RoleName.FOLLOWER) {
            store.setVotedFor(((FollowerNodeRole) newRole).getVotedFor());
        }
        role = newRole;
    }

    private ElectionTimeout scheduleElectionTimeout() {
        return context.scheduler().scheduleElectionTimeout(this::electionTimeout);
    }

    /**
     * Election timeout
     * <p>
     * Source: scheduler
     * </p>
     */
    void electionTimeout() {
        context.taskExecutor().submit(this::doProcessElectionTimeout);
    }

    private void doProcessElectionTimeout() {
        // Leader角色下不可能有选举超时
        if (role.getName() == RoleName.LEADER) {
            logger.warn("node {}, current role is leader, ignore election timeout", context.selfId());
            return;
        }
        // 对于follower节点来说是发起选举
        // 对于candidate节点来说是再次发起选举
        // 选举term加2
        int newTerm = role.getTerm() + 1;
        role.cancelTimeoutOrTask();
        logger.info("start election");
        // 变成Candidate角色
        changeToRole(new CandidateNodeRole(newTerm, scheduleElectionTimeout()));

        // 发送RequestVote消息
        RequestVoteRpc rpc = new RequestVoteRpc();
        rpc.setTerm(newTerm);
        rpc.setCandidateId(context.selfId());
        rpc.setLastLogIndex(0);
        rpc.setLastLogTerm(0);
        context.connector().sendRequestVote(rpc, context.group().listEndpointOfMajorExceptSelf());
    }

    @Subscribe
    public void onReceiveRequestVoteRpc(RequestVoteRpcMessage rpcMessage) {
        context.taskExecutor().submit(
                () -> context.connector().replyRequestVote(
                        doProcessRequestVoteRpc(rpcMessage),
                        // 发送消息的节点
                        context.findMember(rpcMessage.getSourceNodeId())
                )
        );
    }

    private RequestVoteResult doProcessRequestVoteRpc(RequestVoteRpcMessage rpcMessage) {
        // 如果对方的term比自己小，则不投票并且返回自己的term给对象
        RequestVoteRpc rpc = rpcMessage.get();
        if (rpc.getTerm() < role.getTerm()) {
            logger.debug("term from rpc < cunrent term, don't vote ({} < {})",
                    rpc.getTerm(), role.getTerm());
            return new RequestVoteResult(role.getTerm(), false);
        }
        // 此次无条件投票
        boolean voteForCandidate = true;

        // 如果对象的term比自己大，则切换为Folower角色
        if (rpc.getTerm() > role.getTerm()) {
            becomeFollower(rpc.getTerm(), (voteForCandidate ? rpc.getCandidateId() : null), null, true);
            return new RequestVoteResult(rpc.getTerm(), voteForCandidate);
        }

        // 本地的term与消息的term一致
        switch (role.getName()) {
            case FOLLOWER:
                FollowerNodeRole follower = (FollowerNodeRole) role;
                NodeId votedFor = follower.getVotedFor();
                // 一下两种情况下投票
                // case 1. 自己尚未投过票，并且对方的日志比自己新
                // case 2. 自己已经给对方投过票
                // 投票后需要切换为Follower角色
                if ((votedFor == null && voteForCandidate) || // 情况1
                        Objects.equals(votedFor, rpc.getCandidateId())) {    // 情况2
                    becomeFollower(role.getTerm(), rpc.getCandidateId(), null, true);
                    return new RequestVoteResult(role.getTerm(), false);
                }
            case CANDIDATE: // 已经给自己投过票， 所以不会给其他节点投票
            case LEADER:
                return new RequestVoteResult(role.getTerm(), false);
            default:
                throw new IllegalStateException("unexpected node role {" + role.getName() + "}");
        }
    }

    private void becomeFollower(int term, NodeId nodeId, Object o, boolean b) {
    }


}
