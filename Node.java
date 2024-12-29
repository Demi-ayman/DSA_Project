package com.example;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public String tagName;
    public String tagValue;
    public List<Node> children;

    public Node(String tagName) {
        this.tagName = tagName;
        this.tagValue = "";
        this.children = new ArrayList<>();
    }

    public void addChild(Node child) {
        this.children.add(child);
    }

    @Override
    public String toString() {
        return "Node{" +
                "tagName='" + tagName + '\'' +
                ", tagValue='" + tagValue + '\'' +
                ", childrenCount=" + (children != null ? children.size() : 0) +
                '}';
    }
}