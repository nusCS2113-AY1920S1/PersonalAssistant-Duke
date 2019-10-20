package javacake.ui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AvatarScreen extends VBox {
    @FXML
    private ImageView avatarImage;

    public enum AvatarMode {
        HAPPY, SAD, POUT
    }

    private Image avatarHappy1 = new Image(this.getClass().getResourceAsStream("/images/avatar/hapop.png"));
    private Image avatarHappy2 = new Image(this.getClass().getResourceAsStream("/images/avatar/hapclos.png"));
    private Image avatarSad1 = new Image(this.getClass().getResourceAsStream("/images/avatar/cryop.png"));
    private Image avatarSad2 = new Image(this.getClass().getResourceAsStream("/images/avatar/cryclos.png"));
    private Image avatarPout1 = new Image(this.getClass().getResourceAsStream("/images/avatar/poutop.png"));
    private Image avatarPout2 = new Image(this.getClass().getResourceAsStream("/images/avatar/poutclos.png"));
    List<Image> images = new ArrayList<>();
    private int imageIndex = 0;
    private int timeFrame = 0;
    public static AvatarMode avatarMode;

    private void initialiseList() {
        images.add(avatarSad2);
        images.add(avatarSad1);
        images.add(avatarHappy2);
        images.add(avatarHappy1);
        images.add(avatarPout2);
        images.add(avatarPout1);
    }

    /**
     * Constructor for setting Avatar's face.
     * @param type Type of face Avatar makes
     */
    public AvatarScreen(AvatarMode type) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/AvatarScreen.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initialiseList();
        avatarMode = type;
        setStyleLoop();
    }

    /**
     * Method to set Avatar.
     * @param type Type of face Avatar makes
     * @return AvatarScreen object
     */
    public static AvatarScreen setAvatar(AvatarMode type) {
        return new AvatarScreen(type);
    }

    private void setStyleLoop() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200), ev -> {
            if (avatarMode == AvatarMode.HAPPY) {
                if (timeFrame % 16 <= 14) {
                    avatarImage.setImage(images.get(3));
                } else {
                    avatarImage.setImage(images.get(2));
                }
            } else if (avatarMode == AvatarMode.SAD) {
                if (timeFrame % 16 <= 14) {
                    avatarImage.setImage(images.get(1));
                } else {
                    avatarImage.setImage(images.get(0));
                }
            } else {
                if (timeFrame % 16 <= 14) {
                    avatarImage.setImage(images.get(5));
                } else {
                    avatarImage.setImage(images.get(4));
                }
            }
            timeFrame = (timeFrame + 1) % 16;
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
