package myraft.core.rpc.message;

import myraft.core.node.NodeId;
import myraft.core.node.NodeImpl;
import myraft.core.node.role.AbstractNodeRole;

import java.nio.channels.Channel;

/**
 * @author fptang
 * @date 2021/10/28
 */
public abstract class AbstractRpcMessage<T> {

    private final T rpc;
    private final NodeId sourceNodeId;
    private final Channel channel;

    AbstractRpcMessage(T rpc, NodeId sourceNodeId, Channel channel) {
        this.rpc = rpc;
        this.sourceNodeId = sourceNodeId;
        this.channel = channel;
    }

    public T get() {
        return this.rpc;
    }

    public NodeId getSourceNodeId() {
        return sourceNodeId;
    }

    public Channel getChannel() {
        return channel;
    }
}
