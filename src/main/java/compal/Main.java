package compal;

import compal.ui.DailyCal;
import compal.ui.MainWindow;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import compal.commons.Compal;
import compal.ui.Ui;
import compal.ui.UiManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Initializes GUI.
 */
public class Main extends Application {
    //Class Properties/Variables

    private Compal compal = new Compal();
    private Ui ui;

    /**
     * Initializes and sets up the GUI.
     * Pulls layout from file MainWindow.fxml.
     *
     * @param primaryStage The stage for GUI.
     * @throws Exception If there is GUI problems.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        ui = new UiManager(compal);
        ui.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
