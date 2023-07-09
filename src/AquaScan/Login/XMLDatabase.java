// package AquaScan.Login;

// import java.io.File;
// import java.io.IOException;
// import javax.xml.parsers.DocumentBuilder;
// import javax.xml.parsers.DocumentBuilderFactory;
// import javax.xml.parsers.ParserConfigurationException;
// import javax.xml.transform.Transformer;
// import javax.xml.transform.TransformerConfigurationException;
// import javax.xml.transform.TransformerException;
// import javax.xml.transform.TransformerFactory;
// import javax.xml.transform.dom.DOMSource;
// import javax.xml.transform.stream.StreamResult;
// import org.w3c.dom.Document;
// import org.w3c.dom.Element;
// import org.w3c.dom.Node;
// import org.w3c.dom.NodeList;
// import org.xml.sax.SAXException;

// public class XMLDatabase {
// private static final String DATABASE_PATH = "database.xml";

// // Create the XML database if it doesn't exist
// public static void initializeDatabase() {
// File databaseFile = new File(DATABASE_PATH);
// if (!databaseFile.exists()) {
// try {
// DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
// DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

// // Root element
// Document doc = docBuilder.newDocument();
// Element rootElement = doc.createElement("users");
// doc.appendChild(rootElement);

// // Write the XML content to the file
// TransformerFactory transformerFactory = TransformerFactory.newInstance();
// Transformer transformer = transformerFactory.newTransformer();
// DOMSource source = new DOMSource(doc);
// StreamResult result = new StreamResult(databaseFile);
// transformer.transform(source, result);
// } catch (ParserConfigurationException | TransformerException e) {
// e.printStackTrace();
// }
// }
// }

// // Add a new user to the XML database
// public static void addUser(String name, String address, String email, String
// password) {
// try {
// DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
// DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
// Document doc = docBuilder.parse(DATABASE_PATH);

// // Root element
// Element rootElement = doc.getDocumentElement();

// // User element
// Element userElement = doc.createElement("user");

// // Name element
// Element nameElement = doc.createElement("name");
// nameElement.setTextContent(name);
// userElement.appendChild(nameElement);

// // Address element
// Element addressElement = doc.createElement("address");
// addressElement.setTextContent(address);
// userElement.appendChild(addressElement);

// // Email element
// Element emailElement = doc.createElement("email");
// emailElement.setTextContent(email);
// userElement.appendChild(emailElement);

// // Password element
// Element passwordElement = doc.createElement("password");
// passwordElement.setTextContent(password);
// userElement.appendChild(passwordElement);

// // Append the user element to the root element
// rootElement.appendChild(userElement);

// // Write the XML content to the file
// TransformerFactory transformerFactory = TransformerFactory.newInstance();
// Transformer transformer = transformerFactory.newTransformer();
// DOMSource source = new DOMSource(doc);
// StreamResult result = new StreamResult(new File(DATABASE_PATH));
// transformer.transform(source, result);
// } catch (ParserConfigurationException | SAXException | IOException |
// TransformerException e) {
// e.printStackTrace();
// }
// }

// // Check if the provided email and password exist in the XML database
// public static boolean checkCredentials(String email, String password) {
// try {
// DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
// DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
// Document doc = docBuilder.parse(DATABASE_PATH);

// NodeList userList = doc.getElementsByTagName("user");

// for (int i = 0; i < userList.getLength(); i++) {
// Node userNode = userList.item(i);

// if (userNode.getNodeType() == Node.ELEMENT_NODE) {
// Element userElement = (Element) userNode;

// String userEmail =
// userElement.getElementsByTagName("email").item(0).getTextContent();
// String userPassword =
// userElement.getElementsByTagName("password").item(0).getTextContent();

// if (userEmail.equalsIgnoreCase(email) && userPassword.equals(password)) {
// return true;
// }
// }
// }
// } catch (ParserConfigurationException | SAXException | IOException e) {
// e.printStackTrace();
// }

// return false;
// }
// }
