import command.Command;
import exception.DukeException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import parser.ParserFactory;
import storage.Storage;
import task.Task;
import task.TaskList;
import ui.Ui;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private VBox todayTaskContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    /**
     * Allocation of the images for the chat bot.
     */
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));


    private static String filePath = System.getProperty("user.dir") + "/src/DukeDatabase/ArrayList";
    private static Storage storage;
    private static TaskList tasks;
    private static File file = new File(filePath);
    private static ArrayList<Task> holdTodayTasks;

    /**
     * This method is utilised to initialize the required aspects of Duke such as the storage and the rendering of
     * the TaskList.
     */
    public static void initializeDukeElements() {
        try {
            storage = new Storage(file);
            tasks = new TaskList(storage.loadFile(file));
        } catch (DukeException e) {
            tasks = new TaskList(new ArrayList<>());
            Ui.printMessage(e.getMessage());
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        holdTodayTasks = tasks.schedule(dtf.format(now));
    }

    /**
     * This @FXML initialize() is a special function where static members of the GUI can be initialised.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        todayTaskContainer.getChildren().add(TodayTaskBox.getUserTaskForToday(holdTodayTasks));
    }

    /**
     * This @FXML handleUserInput() is provides the logic for the text field, whenever the user provides an input
     * this function handles it by passing it to the Duke logic.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        try {
            Command command = ParserFactory.parse(input);
            command.execute(tasks, storage);
        } catch (DukeException e) {
            Ui.printMessage(e.getMessage());
        }
        dialogContainer.getChildren().addAll(
            DialogBox.getUserDialog(input, userImage),
            DialogBox.getDukeDialog(Ui.userOutputForUI, dukeImage)
        );
        userInput.clear();
    }

}
