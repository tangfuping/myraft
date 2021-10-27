package myraft.core.node.store;

import myraft.core.node.NodeId;

import javax.annotation.Nullable;

/**
 * @author fptang
 * @date 2021/10/27
 */
public class MemoryNodeStore implements NodeStore {
    private int term;
    private NodeId voteFor;

    public MemoryNodeStore() {
        this(0, null);
    }

    public MemoryNodeStore(int term, NodeId voteFor) {
        this.term = term;
        this.voteFor = voteFor;
    }

    @Override
    public int getTerm() {
        return term;
    }

    @Override
    public void setTerm(int term) {
        this.term = term;
    }

    @Nullable
    @Override
    public NodeId getVotedFor() {
        return voteFor;
    }

    @Override
    public void setVotedFor(@Nullable NodeId votedFor) {
        this.voteFor = votedFor;
    }

    @Override
    public void close() {

    }
}
