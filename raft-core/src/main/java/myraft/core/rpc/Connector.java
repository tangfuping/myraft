package myraft.core.rpc;

import myraft.core.node.NodeEndpoint;
import myraft.core.rpc.message.AppendEntriesResult;
import myraft.core.rpc.message.AppendEntriesRpc;
import myraft.core.rpc.message.RequestVoteResult;
import myraft.core.rpc.message.RequestVoteRpc;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * Connector.
 */
public interface Connector {

    /**
     * Initialize connector.
     * <p>
     * SHOULD NOT call more than one.
     * </p>
     */
    void initialize();

    /**
     * Send request vote rpc.
     * <p>
     * Remember to exclude self node before sending.
     * </p>
     * <p>
     * Do nothing if destination endpoints is empty.
     * </p>
     *
     * @param rpc                  rpc
     * @param destinationEndpoints destination endpoints
     */
    void sendRequestVote(@Nonnull RequestVoteRpc rpc, @Nonnull Collection<NodeEndpoint> destinationEndpoints);

    /**
     * Reply request vote result.
     *
     * @param result     result
     * @param destinationEndpoint rpc message
     */
    void replyRequestVote(@Nonnull RequestVoteResult result, @Nonnull NodeEndpoint destinationEndpoint);

    /**
     * Send append entries rpc.
     *
     * @param rpc                 rpc
     * @param destinationEndpoint destination endpoint
     */
    void sendAppendEntries(@Nonnull AppendEntriesRpc rpc, @Nonnull NodeEndpoint destinationEndpoint);

    /**
     * Reply append entries result.
     *
     * @param result result
     * @param destinationEndpoints rpc message
     */
    void replyAppendEntries(@Nonnull AppendEntriesResult result, @Nonnull NodeEndpoint destinationEndpoints);


    /**
     * Close connector.
     */
    void close();

}
