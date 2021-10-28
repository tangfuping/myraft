package myraft.core.rpc.message;

import myraft.core.node.NodeId;

import java.nio.channels.Channel;

/**
 * @author fptang
 * @date 2021/10/28
 */
public class RequestVoteRpcMessage extends AbstractRpcMessage<RequestVoteRpc>{


    RequestVoteRpcMessage(RequestVoteRpc rpc, NodeId sourceNodeId, Channel channel) {
        super(rpc, sourceNodeId, channel);
    }
}
