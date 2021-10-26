package myraft.core.rpc.message;

import myraft.core.node.NodeId;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AppendEntriesRpc {
    private int term;   // 选举term
    private NodeId leaderId; // leader节点ID
    private int prevLogIndex = 0; // 前一条日志的索引
    private int prevLogTerm; // 前一条日志的term
    private List<Map.Entry> entries = Collections.emptyList(); // 复制的日志条目
    private int leaderCommit; // leader的commitIndex

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public NodeId getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(NodeId leaderId) {
        this.leaderId = leaderId;
    }

    public int getPrevLogIndex() {
        return prevLogIndex;
    }

    public void setPrevLogIndex(int prevLogIndex) {
        this.prevLogIndex = prevLogIndex;
    }

    public int getPrevLogTerm() {
        return prevLogTerm;
    }

    public void setPrevLogTerm(int prevLogTerm) {
        this.prevLogTerm = prevLogTerm;
    }

    public List<Map.Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Map.Entry> entries) {
        this.entries = entries;
    }

    public int getLeaderCommit() {
        return leaderCommit;
    }

    public void setLeaderCommit(int leaderCommit) {
        this.leaderCommit = leaderCommit;
    }

    @Override
    public String toString() {
        return "AppendEntriesRpc{" +
                "term=" + term +
                ", leaderId=" + leaderId +
                ", prevLogIndex=" + prevLogIndex +
                ", prevLogTerm=" + prevLogTerm +
                ", entries=" + entries +
                ", leaderCommit=" + leaderCommit +
                '}';
    }
}
