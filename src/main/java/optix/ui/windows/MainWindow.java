package optix.ui.windows;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import optix.Optix;

import java.io.IOException;

public class MainWindow extends AnchorPane {
    //// All the elements on the right side of the Bot.
    @FXML
    private VBox chatBox;
    @FXML
    private JFXTextField userInput;
    @FXML
    private ImageView icon;

    private Image optixImage = new Image(this.getClass().getResourceAsStream("/img/optixImage.png"));
    private Image optixIcon = new Image(this.getClass().getResourceAsStream("/img/optixIcon.png"));

    private Optix optix;

    public MainWindow(Optix optix) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        icon.setImage(optixIcon);
        this.optix = optix;
    }

    @FXML
    private void handleResponse() {
        String input = userInput.getText();
        String response = userInput.getText();
        chatBox.getChildren().addAll(
                DialogBox.getUserDialog(input, optixImage),
                DialogBox.getDukeDialog(response, optixImage)
        );

        userInput.clear();
    }
}
