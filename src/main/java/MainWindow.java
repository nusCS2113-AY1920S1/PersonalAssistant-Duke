import Money.Account;
import controlpanel.Ui;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Duke duke;
    private Ui mainWindowUi;

    private static Image userImage;
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    /**
     * Initialises scroll bar and outputs Duke Welcome message on startup of GUI.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        mainWindowUi = new Ui();
        String welcomeDuke = mainWindowUi.showWelcome();
        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog("enter start to begin", dukeImage));
        userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    }

    public void setDuke(Duke d) {
        duke = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to.
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() throws IOException {
        String input = userInput.getText();
        if (input.startsWith("finance")) {
            Parent root = new FXMLLoader(getClass().getResource("/view/NewWindow.fxml")).load();
            Stage stage = new Stage();
            stage.setTitle("New Window");
            stage.setScene(new Scene(root, 600, 600));
            stage.show();
        } else if (input.equals("change icon")) {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Select a picture:");
            File defaultDirectory = new File("D:/");
            chooser.setInitialDirectory(defaultDirectory);
            File selectedFile = chooser.showOpenDialog(null);
            Path from = Paths.get(selectedFile.toURI());
            Path to = Paths.get("src/main/resources/images/" + selectedFile.getName());
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
            userImage = new Image(this.getClass().getResourceAsStream("/images/" + selectedFile.getName()));
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getDukeDialog("Done.", dukeImage)
            );
            userInput.clear();
        } else {
            String response = duke.getResponse(input);
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getDukeDialog(response, dukeImage)
            );
        }
        userInput.clear();
    }
}