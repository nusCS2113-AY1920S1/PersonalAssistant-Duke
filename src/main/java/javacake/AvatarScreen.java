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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AvatarScreen extends VBox {
    @FXML
    private ImageView avatarImage;

    public enum AvatarMode {
        HAPPY, SAD, POUT
    }

    private List<Image> imageList = new ArrayList<>();
    private int timeFrame = 0;
    public static AvatarMode avatarMode;

    private void initialiseList() throws DukeException {
        try {
            String mainDir = "images/avatar/";
            InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(mainDir);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            List<String> listOfImages = new ArrayList<>();
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                listOfImages.add(currentLine);
                System.out.println(currentLine);
            }
            for (String filePath : listOfImages) {
                String outputFile = "/" + mainDir + filePath;
                System.out.println(outputFile);
                Image img = new Image(this.getClass().getResourceAsStream(outputFile));
                imageList.add(img);
            }
        } catch (IOException e) {
            throw new DukeException("Cannot load image sprites!");
        }
    }

    /**
     * Constructor for setting Avatar's face.
     * @param type Type of face Avatar makes
     */
    public AvatarScreen(AvatarMode type) throws DukeException {
        initialiseList();
        avatarMode = type;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/AvatarScreen.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            if (avatarMode == AvatarMode.HAPPY) {
                if (timeFrame % 4 <= 2) {
                    avatarImage.setImage(imageList.get(3));
                } else {
                    avatarImage.setImage(imageList.get(2));
                }
            } else if (avatarMode == AvatarMode.SAD) {
                if (timeFrame % 4 <= 2) {
                    avatarImage.setImage(imageList.get(1));
                } else {
                    avatarImage.setImage(imageList.get(0));
                }
            } else {
                if (timeFrame % 4 <= 2) {
                    avatarImage.setImage(imageList.get(5));
                } else {
                    avatarImage.setImage(imageList.get(4));
                }
            }
            timeFrame++;
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Method to set Avatar.
     * @param type Type of face Avatar makes
     * @return AvatarScreen object
     */
    public static AvatarScreen setAvatar(AvatarMode type) throws DukeException {
        return new AvatarScreen(type);
    }
}
