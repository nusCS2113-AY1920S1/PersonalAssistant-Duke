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

//        window = stage;
//        window.setTitle("Ducats");
//
//        CommandLine commandLine = new CommandLine("", "Enter a command...");
//        commandLine.setId("commandLine");
//        commandLine.requestFocus();
//        commandLine.setOnKeyPressed(e -> {
//            if (e.getCode().equals(KeyCode.ENTER)) {
//                System.out.println(commandLine.getText());
//                commandLine.clear();
//                commandLine.setPromptText("Enter command here...");
//                commandLine.setFocusTraversable(false);
//            }
//        });
//
//
//
//        //VBox layout = new VBox(10);
//        GridPane layout = new GridPane();
//        layout.setPadding(new Insets(10,10,10,10));
//        layout.setVgap(8);
//        layout.setHgap(10);
//
//        GridPane.setConstraints(commandLine, 0, 0);
//        layout.getChildren().add(commandLine);
//
//        VBox vBox = new VBox();
//
//        scene = new Scene(layout, 300, 250);
//
//        //applying stylesheet to the scene
//        scene.getStylesheets().add("style/ducats.css");
//        scene.getStylesheets().add("style/persistent-prompt.css");
//        window.setScene(scene);
//        window.setMinWidth(1024);
//        window.setMinHeight(760);
//
//        window.setOnCloseRequest(e -> {
//            e.consume();
//            closeProgram();
//        });
//        window.show();

    }

    private void closeProgram() {
        //Logic to execute before closing the program
        System.out.println("Program about to close");
        window.close();
    }
}