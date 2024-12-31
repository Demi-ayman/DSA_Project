package com.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TreeToJSONConverter {

    public static void main(String[] args) {
        if (args.length < 4 || !args[0].equals("json")) {
            System.out.println("Usage: xml_editor json -i input_file.xml -o output_file.json");
            return;
        }

        String inputFile = null;
        String outputFile = null;

        // Parse command-line arguments
        for (int i = 1; i < args.length; i++) {
            switch (args[i]) {
                case "-i":
                    inputFile = args[++i];
                    break;
                case "-o":
                    outputFile = args[++i];
                    break;
                default:
                    System.out.println("Invalid argument: " + args[i]);
                    return;
            }
        }

        if (inputFile == null || outputFile == null) {
            System.out.println("Both input and output files are required.");
            return;
        }

        try {
            // Read XML content from file
            String xmlContent = new String(Files.readAllBytes(Paths.get(inputFile)));
            if (xmlContent.isEmpty()) {
                System.out.println("Input XML file is empty or could not be read.");
                return;
            }

            // Convert XML to NodeJson structure
            NodeJson root = XMLToTreeConverter.parseXMLFromString(xmlContent);

            // Convert NodeJson structure to JSON string
            String json = convertToJSON(root);

            // Write the JSON string to the output file
            writeToFile(json, outputFile);

            System.out.println("Successfully converted XML to JSON and saved to " + outputFile);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static String convertToJSON(NodeJson root) {
        return convertNodeToJSON(root, 0); // Start at indentation level 0
    }

    private static String convertNodeToJSON(NodeJson node, int indentLevel) {
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
            json.append(indent).append("]"); // Close the array
        }

        json.append("\n").append(indent).append("}"); // Close the object
        return json.toString();
    }

    public static void writeToFile(String json, String outputFilePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            writer.write(json);
        }
    }
}
