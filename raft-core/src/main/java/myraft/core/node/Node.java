package myraft.core.node;

/**
 * @author fptang
 * @date 2021/10/27
 */
public interface Node {
    // 启动
    void start();

    // 关闭
    void stop() throws InterruptedException;
}
