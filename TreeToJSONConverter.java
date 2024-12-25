package com.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TreeToJSONConverter {

    public static String convertToJSON(Node root) {
        return convertNodeToJSON(root, 0); // Start at indentation level 0
    }

    private static String convertNodeToJSON(Node node, int indentLevel) {
        StringBuilder json = new StringBuilder();
        String indent = "  ".repeat(indentLevel); // Two spaces per level

        json.append(indent).append("{\n");
        json.append(indent).append("  \"").append(node.tagName).append("\": ");

        if (node.children.isEmpty()) {
            // If there are no children, add the value
            json.append("\"").append(node.tagValue).append("\"\n");
        } else {
            // If there are children, add them as an array
            json.append("[\n");
            for (int i = 0; i < node.children.size(); i++) {
                json.append(convertNodeToJSON(node.children.get(i), indentLevel + 2));
                if (i < node.children.size() - 1) {
                    json.append(",");
                }
                json.append("\n");
            }
            json.append(indent).append("]");
        }

        json.append("\n").append(indent).append("}");
        return json.toString();
    }

    public static void writeToFile(String json, String outputFilePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            writer.write(json);
        }
    }
}