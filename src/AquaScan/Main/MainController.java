package AquaScan.Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import AquaScan.OpenScene;
import AquaScan.Login.User;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainController implements Initializable {

    @FXML
    private ImageView userPhoto;

    @FXML
    private Label titleLabel;

    @FXML
    private Label userEmail;

    @FXML
    private Label userName;

    OpenScene openScene = new OpenScene();
    @FXML
    private AnchorPane centerScenePane;
    @FXML
    private BorderPane myBorderPane;

    public void setUserCredentials(String email) {
        try {
            File xmlFile = new File("userdata.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            NodeList userNodes = document.getElementsByTagName("user");
            for (int i = 0; i < userNodes.getLength(); i++) {
                Element userElement = (Element) userNodes.item(i);
                String storedEmail = userElement.getElementsByTagName("email").item(0).getTextContent();
                if (email.equals(storedEmail)) {

                    String photoPath = userElement.getElementsByTagName("photo").item(0).getTextContent();
                    String name = userElement.getElementsByTagName("name").item(0).getTextContent();

                    // Set the email and name in the respective labels
                    userEmail.setText(email);
                    userName.setText("Hello, " + name);

                    if (photoPath.isEmpty()) {
                        // Set a default or placeholder image
                        Image defaultImage = new Image("/AquaScan/Media/user.png");
                        userPhoto.setImage(defaultImage);
                    } else {
                        // Load the image from the file path and set it in the ImageView
                        Image image = new Image(new File(photoPath).toURI().toString());
                        userPhoto.setImage(image);
                    }

                    break; // Exit the loop after finding the matching user
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goLab(ActionEvent event) {
        Node source = (Node) event.getSource();
        Pane page = openScene.getPane("Laboratory/LaboratoryFxml");
        myBorderPane.setCenter(page);
        titleLabel.setText("Laboratory Submission Data");
        System.out.println("You clicked this page");
    }

    @FXML
    private void goPieChart(ActionEvent event) {
        Node source = (Node) event.getSource();

        Pane page = openScene.getPane("PieChart/PieChart");
        myBorderPane.setCenter(page);
        titleLabel.setText("River Conditions");
        System.out.println("You clicked this page");
    }

    // @FXML
    // private void goLineChart(MouseEvent event) {
    // Node source = (Node) event.getSource();

    // if (source instanceof ImageView || source.getId().equals("t4")) {

    // Pane page = openScene.getPane("LineChart/LineChart");
    // myBorderPane.setCenter(page);
    // // titleLabel.setText("Lab Submission Data");
    // System.out.println("You clicked this page");
    // }
    // }

    @FXML
    private void goReports(ActionEvent event) {
        Node source = (Node) event.getSource();

        Pane page = openScene.getPane("Reports/Reports");
        myBorderPane.setCenter(page);
        titleLabel.setText("Pollution Management");
        System.out.println("You clicked this page");
    }

    @FXML
    private void goBarChart(ActionEvent event) {
        Node source = (Node) event.getSource();

        Pane page = openScene.getPane("BarChart/BarChart");
        myBorderPane.setCenter(page);
        titleLabel.setText("Displaying Parameters");
        System.out.println("You clicked this page");
    }

    @FXML
    private void goHomePage(ActionEvent event) {
        Node source = (Node) event.getSource();

        Pane page = openScene.getPane("HomePage/FXMLHomePage");
        myBorderPane.setCenter(page);
        titleLabel.setText("Home Page");
        System.out.println("You clicked this page");
    }

    @FXML
    private void goDisplay(ActionEvent event) {
        Node source = (Node) event.getSource();

        Pane page = openScene.getPane("DisplayingReport/DisplayingReport");
        myBorderPane.setCenter(page);
        titleLabel.setText("Pollution Distribution");
        System.out.println("You clicked this page");
    }

    Stage stage;

    public void logoutAction(MouseEvent event) {
        Node source = (Node) event.getSource();

        if (source instanceof ImageView || source.getId().equals("mm")) {

            System.out.println("You clicked this page");
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Logout");
            alert.setHeaderText("You're about to logout!");
            alert.setContentText("Do you want to save before exiting?: ");
            if (alert.showAndWait().get() == ButtonType.OK) {
                // Create a new instance of the login page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AquaScan/Login/FXMLLogin.fxml"));
                Parent loginRoot;
                try {
                    loginRoot = loader.load();
                    Scene loginScene = new Scene(loginRoot);

                    // Get the stage of the current scene
                    Stage currentStage = (Stage) myBorderPane.getScene().getWindow();

                    // Set the login scene on the stage
                    currentStage.setScene(loginScene);

                    System.out.println("You successfully logged out!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Set the radius for userPhoto
        double radius = 50.0;
        Shape clipShape = new Circle(radius);
        userPhoto.setClip(clipShape);
        addPopUpEffect(userPhoto);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AquaScan/HomePage/FXMLHomePage.fxml"));
        try {
            Pane page = loader.load();
            myBorderPane.setCenter(page);
            titleLabel.setText("Home Page");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void addPopUpEffect(ImageView imageView) {
        // Set the rounded corner rectangle as the clip shape
        double radius = 100;
        double width = imageView.getFitWidth();
        double height = imageView.getFitHeight();

        Shape clipShape = new Rectangle(width, height);
        ((Rectangle) clipShape).setArcWidth(radius * 2);
        ((Rectangle) clipShape).setArcHeight(radius * 2);
        imageView.setClip(clipShape);

        // // Add any other effects or event handlers you need
        // imageView.setOnMouseEntered(event -> {
        // // Apply your desired effects when the mouse enters
        // // For example, increase the image size or change opacity
        // imageView.setScaleX(1.2);
        // imageView.setScaleY(1.2);
        // });

        // imageView.setOnMouseExited(event -> {
        // // Apply your desired effects when the mouse exits
        // // For example, revert the image size or opacity
        // imageView.setScaleX(1.0);
        // imageView.setScaleY(1.0);
        // });
    }

}