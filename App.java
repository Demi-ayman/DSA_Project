/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package DSAlevel1;
import java.io.*;
import java.util.*;
public class App { 
    public String getGreeting() {
        return "Hello World!";
    }

    // Function to validate XML string
    public static boolean validation(String xml) {
        Stack<String> st = new Stack<>();
        String str = "";
        String check = "";
        String str_to_fix="";
        boolean valid=true;
        int i;
        for (i = 0; i < xml.length(); i++) {

            if (xml.charAt(i) == '<' && xml.charAt(i + 1) != '/') {
                str = "";
                for (int j = i + 1; j < xml.length(); j++) {
                    if (xml.charAt(j) == '>' || xml.charAt(j) == ' ') {
                        break;
                    } else {
                        str += xml.charAt(j);
                    }
                }
                st.push(str);
            } else if (xml.charAt(i) == '<' && xml.charAt(i + 1) == '/') {

                check = "";
                for (int k = i + 2; k < xml.length(); k++) {
                    if (xml.charAt(k) == '>') {
                        break;
                    } else {
                        check += xml.charAt(k);
                    }
                }
                if (!st.empty()&&check.equals(st.peek())) {
                    st.pop();
                } 
                else 
                {
                    valid=false;
                    
                    if(!st.empty()&&st.peek()!=null){
                    str_to_fix="</"+st.peek()+">";
                    st.pop();
                    }  
                    else if(check!=null)
                    {
                    str_to_fix="<"+check+">";
                    st.push(check);
                    }else if(!st.empty()&&check!=""&&st.peek()!=null)
                    str_to_fix="</"+st.peek()+">"+"<"+check+">";
                    xml = new StringBuilder(xml).insert(i, str_to_fix).toString();
                    validation(xml);
                    
                }
            } else {
                continue;
            }
        }
        if(i == xml.length()&&!st.empty())
        {
            str_to_fix="</"+st.peek()+">";
                    xml = new StringBuilder(xml).insert(i, str_to_fix).toString();
        }
        
        return (st.empty()&&valid);
    }

    // Function to read the content of an XML file
    public static String readXMLFile(String filename) {
        StringBuilder xmlContent = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                xmlContent.append(line);  // Concatenate each line to the content string
            }
        } catch (IOException e) {
            System.err.println("Failed to open file: " + filename);
            return "";  // Return an empty string if the file can't be opened
        }
        return xmlContent.toString();
    }

    public static void main(String[] args) {
        String filename = "C:\\Users\\Johnathan\\Desktop\\New Text Document.txt";  
        String xmlContent = readXMLFile(filename);

        if (!xmlContent.isEmpty()) {
            if (validation(xmlContent)) {
                System.out.println("Valid XML.");
            } else {
                System.out.println("Invalid XML.");
                //System.out.println((xmlContent));
            }
        } else {
            System.out.println("Error: Could not read the file.");
        }
    }
}