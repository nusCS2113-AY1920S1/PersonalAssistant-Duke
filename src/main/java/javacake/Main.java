package javacake;

import java.io.IOException;

import javacake.ui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private JavaCake javaCake = new JavaCake();


    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            GridPane ap = fxmlLoader.load();
            System.out.println("Yeet");
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setJavaCake(javaCake);
            fxmlLoader.<MainWindow>getController().setStage(stage);
            //stage.setResizable(false);
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setMinHeight(600);
            stage.setMinWidth(880);
            stage.showingProperty().addListener((observable, oldValue, showing) -> {
                if (showing) {
                    stage.setMinHeight(stage.getMinHeight());
                    stage.setMinWidth(stage.getMinWidth());
                }
            });
            stage.show();
            stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/images/app_icon.jpg")));
            stage.setTitle("JavaCake");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}