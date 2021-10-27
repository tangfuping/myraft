package myraft.core.node;

import myraft.core.node.role.AbstractNodeRole;
import myraft.core.node.role.FollowerNodeRole;
import myraft.core.node.role.RoleName;
import myraft.core.node.store.NodeStore;
import myraft.core.schedule.ElectionTimeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        // TODO
    }


}
