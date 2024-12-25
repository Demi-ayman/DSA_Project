package com.example;

import java.io.*;

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
}
