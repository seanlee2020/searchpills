package com.scribd.search;

import com.scribd.search.pills.trie.TrieNode;
import com.scribd.search.pills.trie.TrieTree;
import org.apache.commons.collections4.trie.PatriciaTrie;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TrieTest {

    @Test
    public void testTrie() {

        String q1 = "harry";
        String q2 = "harry potter";

        String q3 = "harry potter movie";
        String q4 = "harry potter book";

        String q5 = "harry potter book list";

        TrieTree trieTree = new TrieTree();

        trieTree.insertQuery(q1,1,2,3);
        trieTree.insertQuery(q2,1,2,3);
        trieTree.insertQuery(q3,1,2,3);
        trieTree.insertQuery(q4,1,2,3);
        trieTree.insertQuery(q5,1,2,3);

        Map<String, TrieNode> children = trieTree.getChildren(q2);
        System.out.println("size of children " + children.size());

    }


    @Test
    public void testQueryTrie() throws Exception {

        TrieTree trieTree = new TrieTree();
        String queryFile = "/Users/seanl/data/search_browse/queries_nu_ns_nuser_2_nsession_2.csv";
        LineIterator lineIterator = FileUtils.lineIterator(new File(queryFile), "UTF-8");

        boolean head = true;
        int count = 0;
        while (lineIterator.hasNext()) {
            String line = lineIterator.nextLine();
            if ( head ) {
                head = false;
                continue;
            }
            count ++;

            String[] fields = line.split(",");

            String query = fields[0];
            int nUsers = Integer.parseInt(fields[1]);
            int nSessions = Integer.parseInt(fields[2]);
            int nHits = 100;
            trieTree.insertQuery(query, nUsers, nSessions, nHits);
            //System.out.println("line=" + line);
        }

        System.out.println("count=" + count);

        String q = "iphone";

        Map<String, TrieNode> children = trieTree.getChildren(q);
        System.out.println("size of children " + children.size());

        System.out.println("children:\n " + children);

        List<TrieNode> trieNodeList = new ArrayList<>();

        for (String token: children.keySet()) {

            TrieNode trieNode = children.get(token);

            if (trieNode.isEndQ()) {
                trieNodeList.add(trieNode);
                String childQ = q + " " + trieNode.getToken();
                System.out.println("q="  + childQ + ",  nu=" + trieNode.getNumUsers() + ", ns=" + trieNode.getNumSessions() );
            }
        }


        System.out.println("\nsorted:\n " + children);
         Collections.sort(trieNodeList, Collections.reverseOrder());

        for (TrieNode trieNode: trieNodeList) {

            String childQ = q + " " + trieNode.getToken();
            System.out.println("q="  + childQ + ",  nu=" + trieNode.getNumUsers() + ", ns=" + trieNode.getNumSessions() );


        }
    }

}
