package com.scribd.search.pills.controller;


import com.scribd.search.pills.PillObject;
import com.scribd.search.pills.PillSuggestions;
import com.scribd.search.pills.trie.TrieNode;
import com.scribd.search.pills.trie.TrieTree;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class PillsController {

    private final static Logger logger = LoggerFactory.getLogger(PillsController.class);

    private TrieTree  trieTree = new TrieTree();

    public PillsController() {
        setupTree();

    }

    private void setupTree() {

        String queryFile = "/Users/seanl/data/search_browse/queries_nu_ns_nuser_2_nsession_2.csv";
        try {
            LineIterator lineIterator = FileUtils.lineIterator(new File(queryFile), "UTF-8");

            boolean head = true;
            int count = 0;
            while (lineIterator.hasNext()) {
                String line = lineIterator.nextLine();
                if (head) {
                    head = false;
                    continue;
                }
                count++;

                String[] fields = line.split(",");

                String query = fields[0];
                int nUsers = Integer.parseInt(fields[1]);
                int nSessions = Integer.parseInt(fields[2]);
                int nHits = 100;
                trieTree.insertQuery(query, nUsers, nSessions, nHits);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(path = "/pills", produces = MediaType.APPLICATION_JSON_VALUE)
    public PillSuggestions lookup(@RequestParam(name = "q") String qstr,
                         @RequestParam(defaultValue = "1") Integer version,
                         @RequestParam(defaultValue = "10") Integer size,
                         @RequestParam(defaultValue = "false") String explain) {


        Map<String, TrieNode> children = trieTree.getChildren(qstr);

        List<TrieNode> trieNodeList = new ArrayList<>();

        for (String token: children.keySet()) {

            TrieNode trieNode = children.get(token);

            if (trieNode.isEndQ()) {
                trieNodeList.add(trieNode);
                String childQ = qstr + " " + trieNode.getToken();
               // System.out.println("q="  + childQ + ",  nu=" + trieNode.getNumUsers() + ", ns=" + trieNode.getNumSessions() );
            }
        }


        //System.out.println("\nsorted:\n " + children);
        Collections.sort(trieNodeList, Collections.reverseOrder());

        List<PillObject> pillObjectList = new ArrayList<>();

        for (TrieNode trieNode: trieNodeList) {
            String childQ = qstr + " " + trieNode.getToken();
            PillObject pillObject = new PillObject();
            pillObject.setQuery(childQ);
            pillObject.setToken(trieNode.getToken());
            //pillObject.setNumHits(trieNode.getNumHits());
            if ( explain .equalsIgnoreCase("true")) {
                pillObject.setNumUsers(trieNode.getNumUsers());
                pillObject.setNumSessions(trieNode.getNumSessions());
            }
            pillObjectList.add(pillObject);

            System.out.println("q="  + childQ + ",  nu=" + trieNode.getNumUsers() + ", ns=" + trieNode.getNumSessions() );

        }

        PillSuggestions pillSuggestions = new PillSuggestions();
        pillSuggestions.setPillObjects(pillObjectList);
        pillSuggestions.setStatus(200);

        return pillSuggestions;
    }


}
