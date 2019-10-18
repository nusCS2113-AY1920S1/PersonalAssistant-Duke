package javacake;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.print.attribute.standard.Media;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private static String savedDataPath = "data/";
    private Duke duke = new Duke(savedDataPath);


    @Override
    public void start(Stage stage) {
        try {
            //            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
            //                    getClass().getResourceAsStream("/sound/kahootOst.wav"));
            //            Clip clip = AudioSystem.getClip();
            //            clip.open(audioInputStream);
            //            clip.start();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setDuke(duke);
            fxmlLoader.<MainWindow>getController().setStage(stage);
            stage.show();
            stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/images/imgay.jpg")));
            stage.setTitle("JavaCake");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}