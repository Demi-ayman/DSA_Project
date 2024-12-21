import org.w3c.dom.*;
import javax.xml.parsers.*;
import org.xml.sax.InputSource;
import java.io.*;

public class XMLToTreeConverter {

    // Method to parse XML from a file
    public static Node parseXML(String filePath) {
        try {
            File file = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            document.getDocumentElement().normalize();

            return buildTree(document.getDocumentElement());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to parse XML from a string
    public static Node parseXMLFromString(String xmlContent) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlContent)));
            document.getDocumentElement().normalize();

            return buildTree(document.getDocumentElement());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Node buildTree(Element element) {
        Node node = new Node(element.getTagName());

        // If the element has a text value, add it as the node's value
        if (element.getTextContent() != null && !element.getTextContent().trim().isEmpty()) {
            node.tagValue = element.getTextContent().trim();
        }

        // Add child nodes
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) { // Fully qualify ELEMENT_NODE
                Element childElement = (Element) children.item(i);
                node.addChild(buildTree(childElement));
            }
        }

        return node;
    }
}
