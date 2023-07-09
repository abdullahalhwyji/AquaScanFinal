package AquaScan.Login;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XMLDataStorage {

    public static boolean checkCredentials(String email, String password) {
        // Load the XML file and retrieve the stored credentials
        // Compare the email and password with the stored values
        // Return true if the credentials match, false otherwise

        // Example implementation:
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File("userdata.xml")); // Replace with your XML file path

            NodeList userNodes = document.getElementsByTagName("user");
            for (int i = 0; i < userNodes.getLength(); i++) {
                Element userElement = (Element) userNodes.item(i);
                String storedEmail = userElement.getElementsByTagName("email").item(0).getTextContent();
                String storedPassword = userElement.getElementsByTagName("password").item(0).getTextContent();

                if (email.equals(storedEmail) && password.equals(storedPassword)) {
                    return true; // Credentials match
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false; // Credentials do not match
    }

    public static void saveUserData(String name, String address, String email, String password, String photoPath) {
        try {
            File xmlFile = new File("userdata.xml");
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc;
            Element rootElement;

            // Check if XML file exists
            if (xmlFile.exists()) {
                // Parse the existing XML file
                doc = docBuilder.parse(xmlFile);
                rootElement = doc.getDocumentElement();
            } else {
                // Create a new XML file
                doc = docBuilder.newDocument();
                rootElement = doc.createElement("users");
                doc.appendChild(rootElement);
            }

            // Create user element
            Element userElement = doc.createElement("user");
            rootElement.appendChild(userElement);

            // Create name element
            Element nameElement = doc.createElement("name");
            nameElement.appendChild(doc.createTextNode(name));
            userElement.appendChild(nameElement);

            // Create address element
            Element addressElement = doc.createElement("address");
            addressElement.appendChild(doc.createTextNode(address));
            userElement.appendChild(addressElement);

            // Create email element
            Element emailElement = doc.createElement("email");
            emailElement.appendChild(doc.createTextNode(email));
            userElement.appendChild(emailElement);

            // Create password element
            Element passwordElement = doc.createElement("password");
            passwordElement.appendChild(doc.createTextNode(password));
            userElement.appendChild(passwordElement);

            // Create photo element
            Element photoElement = doc.createElement("photo");
            photoElement.appendChild(doc.createTextNode(photoPath));
            userElement.appendChild(photoElement);

            // Write the content into the XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // Disable indentation

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);

            System.out.println("User data saved to XML file.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static User loadUserData() throws IOException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File("userdata.xml")); // Replace with your XML file path

            NodeList userNodes = document.getElementsByTagName("user");
            if (userNodes.getLength() > 0) {
                Element userElement = (Element) userNodes.item(0);
                String name = userElement.getElementsByTagName("name").item(0).getTextContent();
                String address = userElement.getElementsByTagName("address").item(0).getTextContent();
                String email = userElement.getElementsByTagName("email").item(0).getTextContent();
                String password = userElement.getElementsByTagName("password").item(0).getTextContent();
                String photoPath = userElement.getElementsByTagName("photo").item(0).getTextContent();

                return new User(name, address, email, password, photoPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
