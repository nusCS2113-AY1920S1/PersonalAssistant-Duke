package ducats;

import ducats.components.SongList;
import ducats.components.UndoRedoStack;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class DucatsGui extends Application {

    Stage window;

    private Storage storage;
    private SongList songs;
    private Ui ui;
    private UndoRedoStack undoRedoStack;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/ducatswindow.fxml"));
        stage.setTitle("Ducats v1.4");
        stage.setScene(new Scene(root, 900, 600));
        stage.setMinHeight(550);
        stage.setMinWidth(700);
        stage.show();
    }

    private void closeProgram() {
        //Logic to execute before closing the program
        System.out.println("Program about to close");
        window.close();
    }
}