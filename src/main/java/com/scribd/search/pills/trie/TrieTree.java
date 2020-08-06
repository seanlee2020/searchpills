package com.scribd.search.pills.trie;

import java.util.*;

public class TrieTree {

    private TrieNode root = new TrieNode();

    public void insertQuery(String query, int nu, int ns, int nh) {

        String[] tokens = query.toLowerCase().split("\\s+");

        TrieNode curNode = root;

        for (int i =0; i < tokens.length; i ++) {

            String token = tokens[i];
            if ( curNode.getChildren() == null)
                curNode.setChildren(new HashMap<>());

            Map<String, TrieNode> children = curNode.getChildren();
            if (!children.containsKey(token)) {
                TrieNode trieNode = new TrieNode();
                trieNode.setToken(token);

                children.put(token, trieNode);
            }

            curNode = children.get(token);

            if (i == tokens.length -1) {
                curNode.setEndQ(true);
                curNode.setNumUsers(nu);
                curNode.setNumSessions(ns);
                curNode.setNumHits(nh);
            }
        }
    }

    public Map<String, TrieNode> getChildren(String query) {

        String[] tokens = query.toLowerCase().split("\\s+");

        Map<String, TrieNode> trieNodeMap = null;
        TrieNode curNode = root;

        for (int i =0; i < tokens.length; i ++) {
            String token = tokens[i];

            if (!(curNode.getChildren() == null)) {
                curNode = curNode.getChildren().get(token);
                if ( curNode == null)
                    break;
            }

            if ( i == tokens.length -1) {
                trieNodeMap = curNode.getChildren();

            }
        }

        return trieNodeMap;
    }
}
