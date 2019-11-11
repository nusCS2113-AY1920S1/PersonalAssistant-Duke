package com.algosenpai.app;

import com.algosenpai.app.exceptions.FileParsingException;
import com.algosenpai.app.stats.UserStats;
import com.algosenpai.app.storage.Storage;
import com.algosenpai.app.ui.Ui;
import com.algosenpai.app.ui.controller.MusicController;
import com.algosenpai.app.utility.LogCenter;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.algosenpai.app.logic.Logic;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Logger;

/**
 * A one scene chatbot GUI.
 * There are two fxml files, MainWindow and DialogBox.
 */
public class MainApp extends Application {

    private static final String APPLICATION_TITLE = "AlgoSenpai Adventures";
    private static int MAINWINDOW_WIDTH = 500;
    private static int MAINWINDOW_HEIGHT = 650;
    private static int SPLASHSCREEN_WIDTH = 600;
    private static int SPLASHSCREEN_HEIGHT = 400;

    //Initialise the different components here
    private Logic logic;
    private static MusicController musicController;
    private UserStats stats;
    private static final Logger logger = LogCenter.getLogger(MainApp.class);

    private boolean wasDatafileCorrupted = false;

    static {
        try {
            logger.info("Starting music controller.....");
            musicController = MusicController.getMusicController();
            musicController.playMusic();
        } catch (URISyntaxException e) {
            logger.info("Failed to start music controller.");
            e.printStackTrace();
        }
    }

    private void initialize() {
        // Ignore the parsing error here, as it is properly dealt with later in the Ui.initialize
        // SetupCommand.
        logger.info("==========================[ Initializing AlgoSenpai Adventures ]========================");
        try {
            stats = new UserStats("./UserData.txt");
            logger.info("User Stats have been successfully loaded.");
        } catch (FileParsingException ignored) {
            stats = UserStats.getDefaultUserStats();
            Storage.saveData("UserData.txt",stats.toString());
            wasDatafileCorrupted = true;
            logger.info("User Stats data could not be loaded due to a parsing error.");
        }
        logic = new Logic(stats);
    }

    @Override
    public void start(Stage stage) throws IOException {
        initialize();
        startSplashScreen(stage);
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> {
            try {
                stage.close();
                startMain(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        pause.play();
    }

    private void startSplashScreen(Stage stage) throws IOException {
        FXMLLoader fxmlSplashScreen = new FXMLLoader(MainApp.class.getResource("/view/SplashScreen.fxml"));
        AnchorPane ap = fxmlSplashScreen.load();
        ap.setStyle("-fx-background-image: url('/images/cute-anime.png'); -fx-background-size: cover;");
        Scene splashScreen = new Scene(ap, SPLASHSCREEN_WIDTH, SPLASHSCREEN_HEIGHT);
        stage.setScene(splashScreen);
        stage.setResizable(false);
        stage.setTitle(APPLICATION_TITLE);
        stage.show();
    }

    private void startMain(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/view/MainWindow.fxml"));
        AnchorPane ap = fxmlLoader.load();
        Scene scene = new Scene(ap, MAINWINDOW_WIDTH, MAINWINDOW_HEIGHT);
        stage.setScene(scene);
        fxmlLoader.<Ui>getController().setLogic(logic, stats, wasDatafileCorrupted);
        stage.setResizable(false);
        stage.setTitle(APPLICATION_TITLE);
        stage.show();
        logger.info("Main GUI screen successfully rendered.");
    }
}
