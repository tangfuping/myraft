package myraft.core.node;

import com.google.common.eventbus.EventBus;
import myraft.core.node.store.NodeStore;
import myraft.core.rpc.Connector;
import myraft.core.schedule.Scheduler;
import myraft.core.support.TaskExecutor;


/**
 * @author fptang
 * @date 2021/10/26
 */
public class NodeContext {
    private NodeId selfId; // 当前节点ID
    private NodeGroup group; // 成员列表
    // private Log log; // 日志
    private Connector connector; // RPC组件
    private NodeStore store;    // 部分角色状态数据存储
    private Scheduler scheduler;

    private EventBus eventBus;
    private TaskExecutor taskExecutor; // 主线程执行器

    public NodeId selfId() {
        return selfId;
    }

    public void setSelfId(NodeId selfId) {
        this.selfId = selfId;
    }

    public NodeId getSelfId() {
        return selfId;
    }

    public NodeGroup getGroup() {
        return group;
    }

    public void setGroup(NodeGroup group) {
        this.group = group;
    }

    public Connector getConnector() {
        return connector;
    }

    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    public NodeStore getStore() {
        return store;
    }

    public void setStore(NodeStore store) {
        this.store = store;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public TaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }
}
