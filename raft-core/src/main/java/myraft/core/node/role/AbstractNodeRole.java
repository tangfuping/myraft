package myraft.core.node.role;

public abstract class AbstractNodeRole {
    private final RoleName name;
    protected final int term;

    // 构造函数
    AbstractNodeRole(RoleName name, int term) {
        this.name = name;
        this.term = term;
    }

    // 获取当前的角色名
    public RoleName getName() {
        return name;
    }

    // 取消超时或者定时任务
    public abstract void cancelTimeoutOrTask();

    // 获取当前的term
    public int getTerm() {
        return term;
    }
}
