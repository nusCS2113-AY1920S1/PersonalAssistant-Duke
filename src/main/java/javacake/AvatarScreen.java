package javacake;

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
    List<Image> images = new ArrayList<>();
    private int imageIndex = 0;

    /**
     * Constructor for setting Avatar's face.
     * @param type Type of face Avatar makes
     */
    public AvatarScreen(AvatarMode type) {
        images.add(avatarHappy1);
        images.add(avatarHappy2);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/AvatarScreen.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //avatarImage.setImage(images.get(0));
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            avatarImage.setImage(images.get(imageIndex++ % 2));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Method to set Avatar.
     * @param type Type of face Avatar makes
     * @return AvatarScreen object
     */
    public static AvatarScreen setAvatar(AvatarMode type) {
        return new AvatarScreen(type);
    }
}
