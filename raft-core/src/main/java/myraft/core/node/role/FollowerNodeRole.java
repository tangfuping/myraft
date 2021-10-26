package myraft.core.node.role;

import myraft.core.node.NodeId;
import myraft.core.node.schedule.ElectionTimeout;

public class FollowerNodeRole extends AbstractNodeRole {
    private final NodeId votedFor; // 投过票的节点，有可能为空
    private final NodeId leaderId; // 当前leader节点ID，有可能为空
    private final ElectionTimeout electionTimeout; // 选举超时

    // 构造函数
    public FollowerNodeRole(int term, NodeId votedFor, NodeId leaderId, ElectionTimeout electionTimeout) {
        super(RoleName.FOLLOWER, term);
        this.votedFor = votedFor;
        this.leaderId = leaderId;
        this.electionTimeout = electionTimeout;
    }

    // 获取投过票的节点
    public NodeId getVotedFor() {
        return votedFor;
    }

    // 获取当前leader节点ID
    public NodeId getLeaderId() {
        return leaderId;
    }

    @Override
    public void cancelTimeoutOrTask() {
        electionTimeout.cancel();
    }

    @Override
    public String toString() {
        return "FollowerNodeRole{" +
                "term=" + term +
                ", leaderId=" + leaderId +
                ", votedFor=" + votedFor +
                ", electionTimeout=" + electionTimeout +
                '}';
    }

}
