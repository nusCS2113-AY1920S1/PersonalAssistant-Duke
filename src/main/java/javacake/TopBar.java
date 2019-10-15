package javacake;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import javafx.scene.control.Label;
import java.io.IOException;

public class TopBar extends HBox {
    @FXML
    private ImageView cakeLeft;
    @FXML
    private Label title;
    @FXML
    private ImageView cakeRight;

    public TopBar() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/TopBar.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static TopBar setTitle() {
        return new TopBar();
    }
}
