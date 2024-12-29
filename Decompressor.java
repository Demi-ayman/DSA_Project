package com.example;

import java.io.*;
import java.util.*;

public class Decompressor {

    // LZW decompression method
    public static void decompress(String inputFilePath, String outputFilePath) throws IOException {
        try (DataInputStream compFile = new DataInputStream(new FileInputStream(inputFilePath))) {
            // Read compressed codes
            List<Integer> compressedCodes = new ArrayList<>();
            while (compFile.available() > 0) {
                compressedCodes.add(compFile.readInt());
            }

            if (compressedCodes.isEmpty()) {
                throw new IOException("Error: The compressed file is empty.");
            }

            // Initialize the string table with single character strings
            Map<Integer, String> stringTable = new HashMap<>();
            for (int i = 0; i < 256; i++) {
                stringTable.put(i, String.valueOf((char) i));
            }

            StringBuilder decompressed = new StringBuilder();
            int tableIndex = 256; // Next available code index
            String previous = stringTable.get(compressedCodes.get(0)); // First code
            decompressed.append(previous);

            for (int i = 1; i < compressedCodes.size(); i++) {
                String current;
                int currentCode = compressedCodes.get(i);
                if (stringTable.containsKey(currentCode)) {
                    current = stringTable.get(currentCode);
                } else if (currentCode == tableIndex) {
                    current = previous + previous.charAt(0); // Special case for new entries
                } else {
                    throw new IOException("Error: Invalid code encountered during decompression.");
                }

                decompressed.append(current);

                // Add new entry to the table
                stringTable.put(tableIndex++, previous + current.charAt(0));
                previous = current;
            }

            // Write the decompressed data to a file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
                writer.write(decompressed.toString());
            }

            System.out.println("Decompressed data written to " + outputFilePath);
        }
    }

    // This method is similar to the usage in other classes
    public static void formatAndSaveDecompressedFile(String inputFilePath, String outputFilePath) {
        try {
            decompress(inputFilePath, outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Main method with command-line argument handling
    public static void main(String[] args) {
        if (args.length < 4 || !args[0].equals("decompress")) {
            System.out.println("Usage: xml_editor decompress -i input_file.comp -o output_file.xml");
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

        // Step 1: Decompress the file and save it to the output file
        formatAndSaveDecompressedFile(inputFile, outputFile);
        System.out.println("Decompression complete. Decompressed file saved to: " + outputFile);
    }
}
