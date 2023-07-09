package AquaScan.HomePage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController1 implements Initializable {

    @FXML
    private ImageView myImageView1;
    // @FXML
    // private ImageView myImageView2;
    // @FXML
    // private ImageView myImageView3;

    @FXML
    private Hyperlink myLink;

    @FXML
    private Button previousButton;
    @FXML
    private Button nextButton;

    @FXML
    private HBox myParentContainer;

    @FXML
    private MediaView mediaView;
    @FXML
    private CheckBox muteCheckBox;
    @FXML
    private Button playButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Label currentTimeLabel;
    @FXML
    private Label totalTimeLabel;
    @FXML
    private Slider videoSlider;

    private MediaPlayer mediaPlayer;
    private Media media;
    private boolean isSliderDragging = false;

    private Image[] images;
    // private String[] imageLinks;
    private int currentIndex;
    private javafx.animation.Timeline imageTimeline;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // for the photos
        images = new Image[] {
                new Image("/AquaScan/Media/1river.jpg"),
                new Image("/AquaScan/Media/2river.jpg"),
                new Image("/AquaScan/Media/3river.jpg")
        };

        setImageViewRadius(myImageView1, 10);
        // setImageViewRadius(myImageView2, 10);
        // setImageViewRadius(myImageView3, 10);

        currentIndex = 0;
        myImageView1.setImage(images[currentIndex]);
        // myImageView2.setImage(images[currentIndex + 1]);
        // myImageView3.setImage(images[currentIndex + 2]);
        myImageView1.setPreserveRatio(false);
        // myLink.setText(imageLinks[currentIndex]);

        // previousButton.setOnAction(event -> {
        // currentIndex = (currentIndex - 1 + images.length) % images.length;
        // updateImageViews();
        // });

        // nextButton.setOnAction(event -> {
        // currentIndex = (currentIndex + 1) % images.length;
        // updateImageViews();
        // });
        startImageTimeline();

        // for the video
        String videoPath = "/AquaScan/Media/vedio.mp4";
        media = new Media(getClass().getResource(videoPath).toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setAutoPlay(true);

        volumeSlider.setValue(mediaPlayer.getVolume() * 100);
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            mediaPlayer.setVolume(newValue.doubleValue() / 100);
        });

        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            if (!isSliderDragging) {
                Duration currentTime = newValue;
                currentTimeLabel.setText(formatTime(currentTime));

                double progress = currentTime.toSeconds() / mediaPlayer.getTotalDuration().toSeconds();
                videoSlider.setValue(progress * 100);
            }
        });

        videoSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (!isSliderDragging) {
                double progress = newValue.doubleValue() / 100;
                Duration seekTime = mediaPlayer.getTotalDuration().multiply(progress);
                mediaPlayer.seek(seekTime);
            }
        });

        muteCheckBox.setOnAction(event -> {
            mediaPlayer.setMute(muteCheckBox.isSelected());
        });

        mediaPlayer.setOnReady(() -> {
            Duration totalDuration = mediaPlayer.getMedia().getDuration();
            totalTimeLabel.setText(formatTime(totalDuration));
        });

    }

    private void setImageViewRadius(ImageView imageView, double radius) {
        Rectangle clip = new Rectangle(imageView.getFitWidth(), imageView.getFitHeight());
        clip.setArcWidth(radius);
        clip.setArcHeight(radius);
        clip.setStrokeType(StrokeType.CENTERED); // Updated stroke type
        clip.setStroke(Color.BLACK); // Added stroke color
        imageView.setClip(clip);
    }

    private void updateImageViews() {
        myImageView1.setImage(images[(currentIndex) % images.length]);
        // myImageView2.setImage(images[(currentIndex + 1) % images.length]);
        // myImageView3.setImage(images[(currentIndex + 2) % images.length]);
        // myLink.setText(imageLinks[currentIndex]);
    }

    @FXML
    void playButtonClicked() {
        mediaPlayer.play();
    }

    @FXML
    void pauseButtonClicked() {
        mediaPlayer.pause();
    }

    @FXML
    void videoSliderPressed() {
        isSliderDragging = true;
    }

    @FXML
    void videoSliderReleased() {
        isSliderDragging = false;
        double progress = videoSlider.getValue() / 100;
        Duration seekTime = mediaPlayer.getTotalDuration().multiply(progress);
        mediaPlayer.seek(seekTime);
    }

    private void startImageTimeline() {
        Duration interval = Duration.seconds(7);
        javafx.animation.KeyFrame keyFrame = new javafx.animation.KeyFrame(interval, event -> {
            currentIndex = (currentIndex + 1) % images.length;
            animateImageChange();
        });

        imageTimeline = new javafx.animation.Timeline(keyFrame);
        imageTimeline.setCycleCount(javafx.animation.Animation.INDEFINITE);

        imageTimeline.play();
    }

    private void animateImageChange() {
        // Fade out transition
        javafx.animation.FadeTransition fadeOut = new javafx.animation.FadeTransition(Duration.seconds(1),
                myImageView1);
        fadeOut.setFromValue(1.0);

        fadeOut.setToValue(0.0);

        fadeOut.setOnFinished(event -> {
            // Update image views
            // myImageView1.setImage(myImageView2.getImage());
            // myImageView2.setImage(myImageView3.getImage());
            myImageView1.setImage(images[(currentIndex + 1) % images.length]);

            // Crossfade transition
            javafx.animation.FadeTransition crossFade = new javafx.animation.FadeTransition(Duration.seconds(1),
                    myImageView1);
            crossFade.setFromValue(0.0);

            crossFade.setToValue(1.0);

            // Scale transition
            javafx.animation.ScaleTransition scaleTransition = new javafx.animation.ScaleTransition(
                    Duration.seconds(0.5), myImageView1);
            scaleTransition.setFromX(0.8);

            scaleTransition.setFromY(0.8);

            scaleTransition.setToY(1.0);

            // Rotate transition
            // javafx.animation.RotateTransition rotateTransition = new
            // javafx.animation.RotateTransition(Duration.seconds(1), myImageView1);
            // rotateTransition.setByAngle(360);
            //
            //
            // rotateTransition.setCycleCount(1);
            //

            // Parallel transition
            javafx.animation.ParallelTransition parallelTransition = new javafx.animation.ParallelTransition(
                    scaleTransition, crossFade);// , rotateTransition);
            parallelTransition.setOnFinished(e -> {

                currentIndex = (currentIndex + 1);

                // myLink.setText(imageLinks[currentIndex]); // Update the hyperlink
            });
            parallelTransition.play();

            // Set background color
            // Assuming you have a reference to the parent container or scene
            myParentContainer.setStyle("-fx-background-color: #ffffff;"); // Set to blue
        });

        fadeOut.play();
    }

    private String formatTime(Duration duration) {
        int seconds = (int) Math.floor(duration.toSeconds()) % 60;
        int minutes = (int) Math.floor(duration.toMinutes()) % 60;
        int hours = (int) Math.floor(duration.toHours());
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
