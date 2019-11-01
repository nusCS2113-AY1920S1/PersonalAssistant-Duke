package javafx;

import exception.DukeException;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import main.Duke;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import task.Task;
import task.TaskList;

import java.util.*;


/**
 * Controller for JavaFX.MainWindow. Provides the layout for the other controls.
 *
 * @author Lee Zhen Yu
 * @version %I%
 * @since 1.0
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

    @FXML
    private TableView<TaskFX> tableView;

    private Duke duke;
    private TaskList taskList;

    public Set<String> autoSuggestion = new HashSet<>(Arrays.asList("list", "detail", "help", "todo", "delete", "clear",
            "add", "swap", "bye", "replace", "undo", "redo", "sort"));
    private SuggestionProvider<String> provider = SuggestionProvider.create(autoSuggestion);



    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Method that runs when duke GUI is started up.
     * It does not take any input from the terminal yet, and does not output to the terminal either.
     *
     * @param d The duke helper that is going to be initialized
     */
    //This method initializes duke
    public void setDuke(Duke d) throws DukeException {

        duke = d;


        new AutoCompletionTextFieldBinding<>(this.userInput, provider);
        ObservableList<TaskFX> data = tableView.getItems();


        /*
        //Initialize autocompletion field
        TextFields.bindAutoCompletion(
                this.userInput,
                autoSuggestion);

         */

        String logo = "  _____  ______ _____ _____  ______ ______  _____  _   _ _    _  _____ \n"
                + " |  __ \\|  ____/ ____|  __ \\|  ____|  ____|/ ____|| \\ | | |  | |/ ____|\n"
                + " | |  | | |__ | |  __| |__) | |__  | |__  | (___  |  \\| | |  | | (___  \n"
                + " | |  | |  __|| | |_ |  _  /|  __| |  __|  \\___ \\ | . ` | |  | |\\___ \\ \n"
                + " | |__| | |___| |__| | | \\ \\| |____| |____ ____) || |\\  | |__| |____) |\n"
                + " |_____/|______\\_____|_|  \\_\\______|______|_____(_)_| \\_|\\____/|_____/ \n";

        String welcome = logo +"\n";

        String greeting;

        if (duke.reminder().isEmpty()) {
            greeting = "What would you like to do?\n\n";
        } else {
            greeting = duke.reminder() + "\n\n";
        }

        welcome += greeting;

        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog(welcome)
        );

        this.taskList = duke.getTaskList();

        for (int i = 0; i < this.taskList.size(); i++) {
            Task newTask = this.taskList.get(i);
            data.add(new TaskFX(Integer.toString(i + 1), newTask.getStatusIcon(), newTask.getType(),
                    newTask.getDescription(), newTask.getDueDate()));
        }

    }


    /**
     * Creates two dialog boxes, one echoing user input and the other containing JavaFX.Main.Duke's
     * reply and then appends them to the dialog container.
     * Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() throws DukeException {
        String input = userInput.getText();
        String response = duke.run(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input),
                DialogBox.getDukeDialog(response)
        );

        //If user wants to end program, create a separate thread with a timer to shutdown
        if (input.equals("bye")) {
            // delay & exit on other thread
            new Thread(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                }
                System.exit(0);
            }).start();
        }

        //Learn new auto suggestions based on user inputs
        if (!autoSuggestion.contains(input)) {
            autoSuggestion.add(input);
            /*
            TextFields.bindAutoCompletion(
                    this.userInput,
                    autoSuggestion);

             */
            provider.clearSuggestions();
            provider.addPossibleSuggestions(autoSuggestion);
        }
        userInput.clear();

        ObservableList<TaskFX> data = tableView.getItems();

        this.taskList = duke.getTaskList();
        data.clear();

        for (int i = 0; i < this.taskList.size(); i++) {
            Task newTask = this.taskList.get(i);
            data.add(new TaskFX(Integer.toString(i + 1), newTask.getStatusIcon(), newTask.getType(),
                    newTask.getDescription(), newTask.getDueDate()));
        }


    }
}
