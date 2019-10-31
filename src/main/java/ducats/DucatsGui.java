package ducats;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DucatsGui extends Application {

    private Stage stage;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/ducatswindow.fxml"));
        stage.setTitle("Ducats v1.4");
        stage.setScene(new Scene(root, 900, 600));
        stage.setMinHeight(550);
        stage.setMinWidth(700);
        stage.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        stage.show();
    }

    private void closeProgram() {
        //Logic to execute before closing the program
        System.out.println("Program about to close");
        stage.close();
    }
}