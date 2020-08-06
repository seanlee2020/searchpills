package com.scribd.search.pills.trie;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class TrieNode implements Comparable<TrieNode>{

    private String token;

    private Map<String, TrieNode> children;

    private boolean isLeaf;

    private boolean endQ;

    private int numUsers;

    private int numSessions;

    private int numHits;

    public String getToken() {

        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Map<String, TrieNode> getChildren() {
        return children;
    }

    public void setChildren(Map<String, TrieNode> children) {
        this.children = children;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public boolean isEndQ() {
        return endQ;
    }

    public void setEndQ(boolean endQ) {
        this.endQ = endQ;
    }

    public int getNumUsers() {
        return numUsers;
    }

    public void setNumUsers(int numUsers) {
        this.numUsers = numUsers;
    }

    public int getNumSessions() {
        return numSessions;
    }

    public void setNumSessions(int numSessions) {
        this.numSessions = numSessions;
    }

    public int getNumHits() {
        return numHits;
    }

    public void setNumHits(int numHits) {
        this.numHits = numHits;
    }

    @Override
    public int compareTo(TrieNode other) {

        Integer weightThis = this.numUsers * 10 + this.numSessions *3 + this.numHits;

        Integer weightOther = other.numUsers * 10 + other.numSessions *3 + other.numHits;

        return weightThis.compareTo(weightOther);

    }


    @Override
    public String toString() {
        return "TrieNode{" +
                "token='" + token + '\'' +
                ", children=" + children +
                ", isLeaf=" + isLeaf +
                ", endQ=" + endQ +
                ", numUsers=" + numUsers +
                ", numSessions=" + numSessions +
                ", numHits=" + numHits +
                '}';
    }

    /*
    @Override
    public String toString() {
        return "TrieNode{" +
                "token='" + token + '\'' +
                "\n \t\tchildren=" + children +
                "\nisLeaf=" + isLeaf +
                "\n endQ=" + endQ +
                '}' + "\n";
    }*/

}
