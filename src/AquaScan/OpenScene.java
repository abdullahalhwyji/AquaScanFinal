package AquaScan;

import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class OpenScene {

    private Pane page;

    public Pane getPane(String filename) {
        try {
            URL pageFile = App.class.getResource("/aquaScan/" + filename + ".fxml");
            if (pageFile == null) {
                throw new java.io.FileNotFoundException("Cannot find the page");
            }
            page = FXMLLoader.load(pageFile);
        } catch (Exception e) {
            System.out.println("Cannot find the requested page");
        }
        return page;
    }

}
