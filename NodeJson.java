package com.example;

import java.util.ArrayList;
import java.util.List;

public class NodeJson {
    public String tagName;
    public String tagValue;
    public List<NodeJson> children;

    public NodeJson(String tagName) {
        this.tagName = tagName;
        this.tagValue = "";
        this.children = new ArrayList<>();
    }

    public void addChild(NodeJson child) {
        this.children.add(child);
    }
}