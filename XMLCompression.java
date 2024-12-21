import java.io.*;
import java.util.Stack;

public class XMLCompression {
    static class StackWrapper {
        private Stack<String> stack;

        public StackWrapper() {
            stack = new Stack<>();
        }

        public void push(String tag) {
            stack.push(tag);
        }

        public void pop() {
            stack.pop();
        }

        public String top() {
            return stack.peek();
        }

        public boolean isEmpty() {
            return stack.isEmpty();
        }
    }

    // Function to read XML data from a file
    public static String readXML(String filename) throws IOException {
        StringBuilder buffer = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        }
        return buffer.toString();
    }

    // Compression and decompression functions
    public static String compressXML(String xml) {
        StringBuilder compressed = new StringBuilder();
        int n = xml.length();

        for (int i = 0; i < n; i++) {
            // Count occurrences of the current character
            int count = 1;
            while (i < n - 1 && xml.charAt(i) == xml.charAt(i + 1)) {
                count++;
                i++;
            }
            // Append the character and its count to the result
            compressed.append(xml.charAt(i));
            compressed.append(count);
        }

        return compressed.toString();
    }

    public static String decompressXML(String compressed) {
        StringBuilder decompressed = new StringBuilder();
        int n = compressed.length();

        for (int i = 0; i < n; i++) {
            char currentChar = compressed.charAt(i);
            i++;
            int count = 0;
            while (i < n && Character.isDigit(compressed.charAt(i))) {
                count = count * 10 + (compressed.charAt(i) - '0');
                i++;
            }
            i--;  // Adjust for the loop increment
            for (int j = 0; j < count; j++) {
                decompressed.append(currentChar);
            }
        }

        return decompressed.toString();
    }

    public static void main(String[] args) {
        String filename = "D:\\CSE-Senior 1\\Fall 25\\DSA\\Project\\TestFx\\src\\sample.xml";
        try {
            String xmlData = readXML(filename);

            String compressedData = compressXML(xmlData);
            System.out.println("Compressed Data: " + compressedData);

            String decompressedData = decompressXML(compressedData);
            System.out.println("Decompressed Data: " + decompressedData);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
