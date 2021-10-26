package myraft.core.node.role;

import myraft.core.node.schedule.ElectionTimeout;

public class CandidateNodeRole extends AbstractNodeRole {
    private final int votesCount; // 票数
    private final ElectionTimeout electionTimeout; // 选举超时时间

    public CandidateNodeRole(int term, ElectionTimeout electionTimeout) {
        this(term, 1, electionTimeout);
    }

    public CandidateNodeRole(int term, int votesCount, ElectionTimeout electionTimeout) {
        super(RoleName.CANDIDATE, term);
        this.votesCount = votesCount;
        this.electionTimeout = electionTimeout;
    }

    // 获得投票数
    public int getVotesCount() {
        return votesCount;
    }

    @Override
    public void cancelTimeoutOrTask() {
        electionTimeout.cancel();
    }

    @Override
    public String toString() {
        return "CandidateNodeRole{" +
                "term=" + term +
                ", votesCount=" + votesCount +
                ", electionTimeout=" + electionTimeout +
                '}';
    }
}
