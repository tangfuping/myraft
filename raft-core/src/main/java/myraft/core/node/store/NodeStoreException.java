package myraft.core.node.store;

/**
 * @author fptang
 * @date 2021/10/27
 * Thrown when failed to store state into store.
 */
public class NodeStoreException extends RuntimeException {
    /**
     * Create.
     *
     * @param cause cause
     */
    public NodeStoreException(Throwable cause) {
        super(cause);
    }

    /**
     * Create.
     *
     * @param message message
     * @param cause   cause
     */
    public NodeStoreException(String message, Throwable cause) {
        super(message, cause);
    }

}
