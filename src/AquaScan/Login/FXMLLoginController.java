package AquaScan.Login;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import AquaScan.Main.MainController;
import javafx.animation.TranslateTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FXMLLoginController implements Initializable {
    @FXML
    private AnchorPane layer1;

    @FXML
    private Label ls1;

    @FXML
    private Label ls2;

    @FXML
    private Button ls3;

    @FXML
    private Label ll1;

    @FXML
    private Label ll2;

    @FXML
    private Button ll3;

    @FXML
    private AnchorPane layer2;

    @FXML
    private Label s1;

    @FXML
    private Label s2;

    @FXML
    private ImageView s3;

    @FXML
    private Button s4;

    @FXML
    private Label s5;

    @FXML
    private TextField s6;

    @FXML
    private TextField s7;

    @FXML
    private Label s8;

    @FXML
    private TextField s9;

    @FXML
    private PasswordField s10;

    @FXML
    private CheckBox s11;

    @FXML
    private PasswordField s12;

    @FXML
    private CheckBox s13;

    // @FXML
    // private Label s14;

    // @FXML
    // private ChoiceBox<String> s15;

    @FXML
    private Button s16;

    @FXML
    private Label l1;

    @FXML
    private TextField l2;

    @FXML
    private PasswordField l3;

    @FXML
    private CheckBox l4;

    @FXML
    private Button l5;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // s15.getItems().addAll("Environmental office", "Laboratory person", "Public");
        // s15.setValue("Environmental office");

        ll1.setVisible(false);
        ll2.setVisible(false);
        ll3.setVisible(false);
        l1.setVisible(false);
        l2.setVisible(false);
        l3.setVisible(false);
        l4.setVisible(false);
        l5.setVisible(false);

        ls1.setVisible(true);
        ls2.setVisible(true);
        ls3.setVisible(true);
        s1.setVisible(true);
        s2.setVisible(true);
        s3.setVisible(true);
        s4.setVisible(true);
        s5.setVisible(true);
        s6.setVisible(true);
        s7.setVisible(true);
        s8.setVisible(true);
        s9.setVisible(true);
        s10.setVisible(true);
        s11.setVisible(true);
        s12.setVisible(true);
        s13.setVisible(true);
        // s14.setVisible(true);
        // s15.setVisible(true);
        s16.setVisible(true);

    }

    @FXML
    private void login(MouseEvent event) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.6));
        slide.setNode(layer1);
        slide.setByX(571);
        slide.play();
        layer2.setTranslateX(-342);
        // layer2.setTranslateY(90);
        ll1.setVisible(true);
        ll2.setVisible(true);
        ll3.setVisible(true);
        l1.setVisible(true);
        l2.setVisible(true);
        l3.setVisible(true);
        l4.setVisible(true);
        l5.setVisible(true);

        ls1.setVisible(false);
        ls2.setVisible(false);
        ls3.setVisible(false);
        s1.setVisible(false);
        s2.setVisible(false);
        s3.setVisible(false);
        s4.setVisible(false);
        s5.setVisible(false);
        s6.setVisible(false);
        s7.setVisible(false);
        s8.setVisible(false);
        s9.setVisible(false);
        s10.setVisible(false);
        s11.setVisible(false);
        s12.setVisible(false);
        s13.setVisible(false);
        // s14.setVisible(false);
        // s15.setVisible(false);
        s16.setVisible(false);
        slide.setOnFinished((e -> {

        }));
    }

    @FXML
    private void signup(MouseEvent event) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.6));
        slide.setNode(layer1);
        slide.setByX(-571);
        slide.play();
        layer2.setTranslateX(0);
        ls1.setVisible(true);
        ls2.setVisible(true);
        ls3.setVisible(true);
        s1.setVisible(true);
        s2.setVisible(true);
        s3.setVisible(true);
        s4.setVisible(true);
        s5.setVisible(true);
        s6.setVisible(true);
        s7.setVisible(true);
        s8.setVisible(true);
        s9.setVisible(true);
        s10.setVisible(true);
        s11.setVisible(true);
        s12.setVisible(true);
        s13.setVisible(true);
        // s14.setVisible(true);
        // s15.setVisible(true);
        s16.setVisible(true);

        ll1.setVisible(false);
        ll2.setVisible(false);
        ll3.setVisible(false);
        l1.setVisible(false);
        l2.setVisible(false);
        l3.setVisible(false);
        l4.setVisible(false);
        l5.setVisible(false);

        slide.setOnFinished((e -> {

        }));

    }

    @FXML
    public void handleShowPassword(ActionEvent event) {
        if (l4.isSelected()) {
            l3.setPromptText(l3.getText());
            l3.setText("");
        } else {
            l3.setText(l3.getPromptText());
            l3.setPromptText("");
        }
    }

    @FXML
    public void handleShowPassword2(ActionEvent event) {
        if (s11.isSelected()) {
            s10.setPromptText(s10.getText());
            s10.setText("");
        } else {
            s10.setText(s10.getPromptText());
            s10.setPromptText("");
        }
    }

    @FXML
    public void handleShowPassword3(ActionEvent event) {
        if (s13.isSelected()) {
            s12.setPromptText(s12.getText());
            s12.setText("");
        } else {
            s12.setText(s12.getPromptText());
            s12.setPromptText("");
        }
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    // ...

    @FXML
    private void uploadpic(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            s3.setImage(image);
            photoPath = selectedFile.getAbsolutePath();
        }
    }

    private String photoPath;

    @FXML
    private void signupAction(MouseEvent event) {
        // Retrieve user input
        String name = s6.getText();
        String address = s7.getText();
        String email = s9.getText();
        String password = s10.getText();
        String password2 = s12.getText();

        if (name.isEmpty() || address.isEmpty() || email.isEmpty() || password.isEmpty() || password2.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all the required fields.");
            alert.showAndWait();
        } else if (!password.equals(password2)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Password Error");
            alert.setHeaderText(null);
            alert.setContentText("The password should be the same.");
            alert.showAndWait();
        } else if (s3.getImage() == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select an image.");
            alert.showAndWait();
        } else {
            // Save user data to XML
            XMLDataStorage.saveUserData(name, address, email, password, photoPath);

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("The user data has been saved.");
            alert.showAndWait();

            s6.clear();
            s7.clear();
            s9.clear();
            s10.clear();
            s12.clear();
            s3.setImage(null);
            login(event);
        }
    }

    @FXML
    private void loginAction(MouseEvent event) {
        // Retrieve user input
        String email = l2.getText();
        String password = l3.getText();

        // Check if the email and password match the stored values
        boolean credentialsValid = XMLDataStorage.checkCredentials(email, password);

        if (credentialsValid) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AquaScan/Main/MainFxml.fxml"));
                Parent root = loader.load();
                MainController mainController = loader.getController();

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

                // Pass the email to the MainController to set user credentials
                mainController.setUserCredentials(email);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Create an alert dialog
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter the correct Email and Password");
            alert.showAndWait();
        }
    }

}
// ...

// @FXML
// public void loginAction(ActionEvent event) throws IOException {
// String email = l2.getText();
// String password = l3.getText();

// if (email.equalsIgnoreCase("222")
// && password.equals("A2")) {

// FXMLLoader loader = new FXMLLoader(getClass().getResource(
// "/AquaScan/Main/MainFxml.fxml"));
// root = loader.load();
// // Parent root =
// FXMLLoader.load(getClass().getResource("LoginPageHello.fxml"));
// stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
// scene = new Scene(root);
// stage.setScene(scene);
// stage.show();
// } else {
// // Create an alert dialog
// Alert alert = new Alert(AlertType.ERROR);
// alert.setTitle("Login Error");
// alert.setHeaderText(null);
// alert.setContentText("Please enter the correct Email and Password");
// alert.showAndWait();
// }
// }

// @FXML
// private void uploadpic(MouseEvent event) {

// }

// @FXML
// private void gosignup(MouseEvent event) {

// }
