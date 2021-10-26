package myraft.core.node.rpc.message;

public class RequestVoteResult {
    private final int term; // 选举term
    private final boolean voteGranted; // 是否投票

    public RequestVoteResult(int term, boolean voteGranted) {
        this.term = term;
        this.voteGranted = voteGranted;
    }

    public int getTerm() {
        return term;
    }

    public boolean isVoteGranted() {
        return voteGranted;
    }

    @Override
    public String toString() {
        return "RequestVoteResult{" +
                "term=" + term +
                ", voteGranted=" + voteGranted +
                '}';
    }
}
