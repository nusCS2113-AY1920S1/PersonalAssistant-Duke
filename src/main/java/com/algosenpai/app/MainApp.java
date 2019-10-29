package com.algosenpai.app;


import com.algosenpai.app.stats.UserStats;
import com.algosenpai.app.storage.Storage;
import com.algosenpai.app.ui.Ui;
import com.algosenpai.app.ui.controller.MusicController;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.algosenpai.app.logic.Logic;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

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
    private static String USERSTATS_FILE_PATH = "UserData.txt";

    //Initialise the different components here
    private Logic logic;
    private static MusicController musicController;
    private UserStats stats = new UserStats(USERSTATS_FILE_PATH);

    static {
        try {
            musicController = new MusicController();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public MainApp() throws IOException {
    }

    private void initialize() throws IOException {
        try {
            logic = new Logic(stats);
            Storage.loadData(USERSTATS_FILE_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
        fxmlLoader.<Ui>getController().setLogic(logic, stats);
        stage.setResizable(false);
        stage.setTitle(APPLICATION_TITLE);
        stage.show();
    }
}
