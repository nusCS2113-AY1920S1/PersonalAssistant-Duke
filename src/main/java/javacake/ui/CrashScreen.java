package javacake.ui;

import javacake.JavaCake;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


public class CrashScreen extends GridPane {
    @FXML
    private Label crashText;
    @FXML
    private ImageView crashFace;

    private Image avatarPout1 = new Image(this.getClass().getResourceAsStream(
            "/images/avatar/h_poutopen.png"));
    private Image avatarPout2 = new Image(this.getClass().getResourceAsStream(
            "/images/avatar/h_poutclose.png"));
    private List<Image> images = new ArrayList<>();
    private int timeFrame = 0;

    /**
     * Initialise the Main Window launched.
     */
    @FXML
    public void initialize() {
        crashText.setText("Your waifu is disappointed with you for cheating on her!\n"
                + "Please delete your 'data' directory to not upset her again...");
        images.add(avatarPout1);
        images.add(avatarPout2);
        funWithWaifu();
    }

    private void funWithWaifu() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200), ev -> {
            if (timeFrame <= 14) {
                crashFace.setImage(images.get(0));
            } else {
                crashFace.setImage(images.get(1));
            }
            timeFrame = (timeFrame + 1) % 16;
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        crashFace.setOnMouseClicked(event -> {
            if (event.getClickCount() >= 1) {
                timeFrame = 15;
                JavaCake.logger.log(Level.INFO, "Touched waifu");
            }
        });
    }
}
