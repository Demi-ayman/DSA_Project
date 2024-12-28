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
}