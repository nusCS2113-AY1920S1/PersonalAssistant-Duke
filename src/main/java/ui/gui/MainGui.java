package ui.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainGui extends Application {
    private String taskPath = "savedTask.txt";
    private String walletPath = "savedWallet.txt";
    private MainWindow mainWindowController;

    public void initialize(String[] args) {
        Application.launch(MainGui.class, args);
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loaderMain = new FXMLLoader(MainGui.class
                    .getResource("/view/MainWindow.fxml"));
            AnchorPane anchorPane = loaderMain.load();
            this.mainWindowController = loaderMain.<MainWindow>getController();
            this.mainWindowController.initialize(stage, this.taskPath, this.walletPath);
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
            stage.setTitle("Duke$$$");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        this.mainWindowController.saveAllData();
    }
}
