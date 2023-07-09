package AquaScan.EditProfile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import AquaScan.Login.User;
import AquaScan.Login.XMLDataStorage;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class EditProfileController implements Initializable {

    @FXML
    private ImageView IVPictureFile;

    @FXML
    private Button BTUploadPic;

    @FXML
    private TextField TFChangeName;

    @FXML
    private TextField TFChangeAddress;

    @FXML
    private TextField TFChangeEmail;

    @FXML
    private PasswordField PFChangePassword;

    @FXML
    private PasswordField PFConfirmPassword;

    @FXML
    private CheckBox CBShowPassword;

    private String photoPath;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Load the user data and display it in the text fields and image view
        User userData = loadUserDataFromXML();
        if (userData != null) {
            TFChangeName.setText(userData.getName());
            TFChangeAddress.setText(userData.getAddress());
            TFChangeEmail.setText(userData.getEmail());
            IVPictureFile.setImage(new Image(new File(userData.getPhotoPath()).toURI().toString()));
            photoPath = userData.getPhotoPath();
        }
    }

    @FXML
    private void handleUploadPic(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg",
                        "*.jpeg"));

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            IVPictureFile.setImage(image);
            photoPath = selectedFile.getAbsolutePath();
        }
    }

    @FXML
    private void handleShowPassword(ActionEvent event) {
        if (CBShowPassword.isSelected()) {
            PFChangePassword.setPromptText(PFChangePassword.getText());
            PFChangePassword.setText("");
        } else {
            PFChangePassword.setText(PFChangePassword.getPromptText());
            PFChangePassword.setPromptText("");
        }
    }

    @FXML
    private void handleSaveChanges(ActionEvent event) {
        String name = TFChangeName.getText();
        String address = TFChangeAddress.getText();
        String email = TFChangeEmail.getText();
        String password = PFChangePassword.getText();
        String password2 = PFConfirmPassword.getText();

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
        } else if (IVPictureFile.getImage() == null) {
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
        }
    }

    private boolean saveUserDataToXML(User user) throws IOException {
        XMLDataStorage dataStorage = new XMLDataStorage();
        dataStorage.saveUserData(user.getName(), user.getAddress(), user.getEmail(), user.getPassword(),
                user.getPhotoPath());
        return true;
    }

    private User loadUserDataFromXML() {
        try {
            // Load user data from XML file using XMLDataStorage class
            XMLDataStorage dataStorage = new XMLDataStorage();
            return dataStorage.loadUserData();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setUser(User user) {
        TFChangeName.setText(user.getName());
        TFChangeAddress.setText(user.getAddress());
        TFChangeEmail.setText(user.getEmail());
        IVPictureFile.setImage(new Image(new File(user.getPhotoPath()).toURI().toString()));
        photoPath = user.getPhotoPath();
    }
}
