package myraft.core;

/**
 * @author fptang
 * @date 2021/10/28
 */
public class ReplicatingState {
    private int nextIndex;
    private int matchIndex;
    private boolean replicating = false;
    private long lastReplicatedAt = 0;

    public ReplicatingState(int nextIndex) {
        this(nextIndex, 0);
    }

    public ReplicatingState(int nextIndex, int matchIndex) {
        this.nextIndex = nextIndex;
        this.matchIndex = matchIndex;
    }

    /**
     * Get next index.
     *
     * @return next index
     */
    public int getNextIndex() {
        return nextIndex;
    }

    /**
     * Get match index.
     *
     * @return match index
     */
    public int getMatchIndex() {
        return matchIndex;
    }

    /**
     * Back off next index, in other word, decrease.
     *
     * @return true if decrease successfully, false if next index is less than or equal to {@code 1}
     */
    public boolean backOffNextIndex() {
        if (nextIndex > 1) {
            nextIndex--;
            return true;
        }
        return false;
    }

    /**
     * Advance next index and match index by last entry index.
     *
     * @param lastEntryIndex last entry index
     * @return true if advanced, false if no change
     */
    public boolean advance(int lastEntryIndex) {
        // changed
        boolean result = (matchIndex != lastEntryIndex || nextIndex != (lastEntryIndex + 1));

        matchIndex = lastEntryIndex;
        nextIndex = lastEntryIndex + 1;

        return result;
    }

    /**
     * Test if replicating.
     *
     * @return true if replicating, otherwise false
     */
    public boolean isReplicating() {
        return replicating;
    }

    /**
     * Set replicating.
     *
     * @param replicating replicating
     */
    public void setReplicating(boolean replicating) {
        this.replicating = replicating;
    }

    /**
     * Get last replicated timestamp.
     *
     * @return last replicated timestamp
     */
    public long getLastReplicatedAt() {
        return lastReplicatedAt;
    }

    /**
     * Set last replicated timestamp.
     *
     * @param lastReplicatedAt last replicated timestamp
     */
    public void setLastReplicatedAt(long lastReplicatedAt) {
        this.lastReplicatedAt = lastReplicatedAt;
    }

    @Override
    public String toString() {
        return "ReplicatingState{" +
                "nextIndex=" + nextIndex +
                ", matchIndex=" + matchIndex +
                ", replicating=" + replicating +
                ", lastReplicatedAt=" + lastReplicatedAt +
                '}';
    }
}
