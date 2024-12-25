package com.example;

import java.io.*;
import java.util.*;

public class Compressor {

    // Method to read the file and convert it to a string
    public static String parseFileToString(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        return stringBuilder.toString();
    }

    // LZW compression method
    public static void compress(String inputFilePath, String outputFilePath) {
        try {
            String input = parseFileToString(inputFilePath);
            Map<String, Integer> stringTable = new HashMap<>();
            int code = 256; // Start with the first available code after ASCII

            // Initialize string table with single characters
            for (int i = 0; i < 256; i++) {
                stringTable.put(String.valueOf((char) i), i);
            }

            String P = "";
            List<Integer> outputCodes = new ArrayList<>();

            // Compression logic
            for (int i = 0; i < input.length(); i++) {
                char C = input.charAt(i); // Next input character
                if (stringTable.containsKey(P + C)) {
                    P = P + C; // P + C is in the table
                } else {
                    outputCodes.add(stringTable.get(P)); // Output the code for P
                    stringTable.put(P + C, code++); // Add P + C to the table
                    P = String.valueOf(C); // Set P to C
                }
            }

            // Output the code for the last P
            outputCodes.add(stringTable.get(P));

            // Write the compressed data to a .comp file
            try (DataOutputStream compFile = new DataOutputStream(new FileOutputStream(outputFilePath))) {
                for (int codeValue : outputCodes) {
                    compFile.writeInt(codeValue);
                }
            }

            System.out.println("Compressed data written to " + outputFilePath);

        } catch (IOException e) {
            System.err.println("Error during compression: " + e.getMessage());
        }
    }

    // This method is similar to XMLFormatter's usage
    public static void formatAndSaveCompressedFile(String inputFilePath, String outputFilePath) {
        // Compress the file content and save to the output
        compress(inputFilePath, outputFilePath);
    }
}
