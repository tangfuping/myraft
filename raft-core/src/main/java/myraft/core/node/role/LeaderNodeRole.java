package myraft.core.node.role;

import myraft.core.schedule.LogReplicationTask;

public class LeaderNodeRole extends AbstractNodeRole {

    private final LogReplicationTask logReplicationTask; // 日志复制定时器

    LeaderNodeRole(int term, LogReplicationTask logReplicationTask) {
        super(RoleName.LEADER, term);
        this.logReplicationTask = logReplicationTask;
    }

    @Override
    public void cancelTimeoutOrTask() {
        logReplicationTask.cancel();
    }

    @Override
    public String toString() {
        return "LeaderNodeRole{term=" + term + ", logReplicationTask=" + logReplicationTask + '}';
    }
}
