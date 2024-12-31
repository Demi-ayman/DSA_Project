package com.example;

import java.io.*;
import java.util.*;

public class XMLMinifier {
    private String inputFile;
    private String outputFile;

    // Constructor
    public XMLMinifier(String input, String output) {
        this.inputFile = input;
        this.outputFile = output;
    }

    // Function to perform the minification
    public void minify() throws IOException {
        try (BufferedReader inFile = new BufferedReader(new FileReader(inputFile));
             BufferedWriter outFile = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            boolean insideTag = false;

            while ((line = inFile.readLine()) != null) {
                // Process the line character by character
                for (char c : line.toCharArray()) {
                    // Skip whitespace outside of tags
                    if (Character.isWhitespace(c) && !insideTag) {
                        continue;
                    }

                    // If entering a tag, set the flag
                    if (c == '<') {
                        insideTag = true;
                    }

                    // Write the character to the output file
                    outFile.write(c);

                    // If exiting a tag, reset the flag
                    if (c == '>') {
                        insideTag = false;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        if (args.length < 4 || !args[0].equals("mini")) {
            System.out.println("Usage: xml_editor mini -i input_file.xml -o output_file.xml");
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
            // Create an XMLMinifier instance and perform minification
            XMLMinifier minifier = new XMLMinifier(inputFile, outputFile);
            minifier.minify();
            System.out.println("Minified XML saved to " + outputFile);
        } catch (IOException e) {
            System.out.println("Error during minification: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
