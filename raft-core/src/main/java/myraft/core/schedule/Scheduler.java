package myraft.core.schedule;

public interface Scheduler {
    // 创建日志复制定时任务
    LogReplicationTask scheduleLogReplicationTask(Runnable task);

    // 创建选举超时器
    ElectionTimeout scheduleElectionTimeout(Runnable task);

    // 关闭定时器
    void stop() throws InterruptedException;
}
