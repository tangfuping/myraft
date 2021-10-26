package myraft.core.rpc.message;

import myraft.core.node.NodeId;


public class RequestVoteRpc {
    private int term; // 选举term
    private NodeId candidateId; // 候选者节点ID，一般都是发送者自己
    private int lastLogIndex = 0; // 候选者最后一条日志的索引
    private int lastLogTerm = 0; // 候选者最后一条日志的term

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public NodeId getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(NodeId candidateId) {
        this.candidateId = candidateId;
    }

    public int getLastLogIndex() {
        return lastLogIndex;
    }

    public void setLastLogIndex(int lastLogIndex) {
        this.lastLogIndex = lastLogIndex;
    }

    public int getLastLogTerm() {
        return lastLogTerm;
    }

    public void setLastLogTerm(int lastLogTerm) {
        this.lastLogTerm = lastLogTerm;
    }

    @Override
    public String toString() {
        return "RequestVoteRpc{" +
                "candidateId=" + candidateId +
                ", lastLogIndex=" + lastLogIndex +
                ", lastLogTerm=" + lastLogTerm +
                ", term=" + term +
                '}';
    }
}
