package com.algosenpai.app;


import com.algosenpai.app.stats.UserStats;
import com.algosenpai.app.ui.Ui;
import com.algosenpai.app.ui.controller.MusicController;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.algosenpai.app.logic.Logic;
import com.algosenpai.app.logic.parser.Parser;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * A one scene chatbot GUI.
 * There are two fxml files, MainWindow and DialogBox.
 */
public class MainApp extends Application {
    //Initialise the different components here
    private Parser parser = new Parser();
    private Logic logic;
    private UserStats userStats;
    private static MusicController musicController;

    static {
        try {
            musicController = new MusicController();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        initialize();
        startSplashScreen(stage);
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
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

    private void initialize() {
        try {
            userStats = new UserStats();
            logic = new Logic(parser, userStats);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void startSplashScreen(Stage stage) throws IOException {
        FXMLLoader fxmlSplashScreen = new FXMLLoader(MainApp.class.getResource("/view/SplashScreen.fxml"));
        AnchorPane ap = fxmlSplashScreen.load();
        ap.setStyle("-fx-background-image: url('/images/cute-anime.png'); -fx-background-size: cover;");
        Scene splashScreen = new Scene(ap, 600, 400);
        stage.setScene(splashScreen);
        stage.setResizable(false);
        stage.setTitle("AlgoSenpai Adventures");
        stage.show();
    }

    private void startMain(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/view/MainWindow.fxml"));
        AnchorPane ap = fxmlLoader.load();
        Scene scene = new Scene(ap, 400, 600);
        stage.setScene(scene);
        fxmlLoader.<Ui>getController().setLogic(logic);
        stage.setResizable(false);
        stage.setTitle("AlgoSenpai Adventures");
        stage.show();
    }
}
