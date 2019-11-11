package javacake;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javacake.exceptions.CakeException;
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
    private static final Logger LOGGER = Logger.getLogger(Main.class.getPackageName());

    @Override
    public void start(Stage stage) {
        try {
            File logFile = new File("cakeLog");
            logFile.mkdirs();
            FileHandler fileHandler = new FileHandler("cakeLog/logFiles.txt", true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);
            LOGGER.setUseParentHandlers(false);
            LOGGER.addHandler(fileHandler);
        } catch (IOException e) {
            LOGGER.warning("Unable to save log file");
        }
        LOGGER.setLevel(Level.WARNING);
        LOGGER.entering(getClass().getName(), "start");
        JavaCake javaCake;
        boolean hasCrashed = false;
        try {
            FXMLLoader fxmlLoaderInitial = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            GridPane ap = fxmlLoaderInitial.load();
            System.out.println("Yeet");
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            try {
                javaCake = new JavaCake();
                fxmlLoaderInitial.<MainWindow>getController().setJavaCake(javaCake);
            } catch (CakeException e) {
                System.out.println(e.getMessage());
                System.out.println("CRASHED FK");
                hasCrashed = true;
            }
            if (!hasCrashed) {
                LOGGER.fine("JavaCake successfully started");
                fxmlLoaderInitial.<MainWindow>getController().setStage(stage);
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

            } else {
                LOGGER.warning("Error in loading primary window! Showing Error Screen");
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/Crash.fxml"));
                    ap = fxmlLoader.load();
                    scene = new Scene(ap);
                    stage.setScene(scene);
                    stage.setResizable(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            stage.show();
            stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/images/app_icon.jpg")));
            stage.setTitle("JavaCake");
            LOGGER.exiting(getClass().getName(), "start");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}