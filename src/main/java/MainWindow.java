import controlpanel.Ui;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.*;
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
    public ScrollPane scrollPane2;
    @FXML
    public VBox graphContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    @FXML
    public VBox autoCompleteContainer;

    private Duke duke;
    private Ui mainWindowUi = new Ui();

    private static Image userImage;
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    /**
     * Initialises scroll bar and outputs Duke Welcome message on startup of GUI.
     */
    @FXML
    public void initialize() throws IOException {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        scrollPane2.vvalueProperty().bind(graphContainer.heightProperty());

        String welcomeDuke = mainWindowUi.showWelcome();
        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog("enter start to begin", dukeImage));

        FileReader fileReader = new FileReader("data/iconPath.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String iconPath = bufferedReader.readLine();
        userImage = new Image(this.getClass().getResourceAsStream(iconPath));
        bufferedReader.close();
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
        if (input.equals("change icon")) {
            FileWriter fileWriter = new FileWriter("data/iconPath.txt", false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            FileChooser chooser = new FileChooser();
            chooser.setTitle("Select a picture:");
            File defaultDirectory = new File("D:/");
            chooser.setInitialDirectory(defaultDirectory);
            File selectedFile = chooser.showOpenDialog(null);
            Path from = Paths.get(selectedFile.toURI());
            Path to = Paths.get("src/main/resources/images/" + selectedFile.getName());
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
            userImage = new Image(this.getClass().getResourceAsStream("/images/" + selectedFile.getName()));
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getDukeDialog("Done.", dukeImage)
            );
            bufferedWriter.write("/images/" + selectedFile.getName());
            bufferedWriter.close();
            userInput.clear();
        } else {
            String[] response = duke.getResponse(input);
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getDukeDialog(response[0], dukeImage)
            );
            if(!response[1].equals("")){
                graphContainer.getChildren().clear();
                graphContainer.getChildren().addAll(
                        DialogBox.getDukeDialog(response[1], dukeImage));
            }

            userInput.clear();
            if (input.startsWith("graph")) {
                graphContainer.getChildren().clear();
                float[] data = duke.getMonthlyData();
                graphContainer.getChildren().addAll(
                        Histogram.getHistogram("The Month Report", data[0], data[1])
                );
            }
        }
        userInput.clear();
    }
}