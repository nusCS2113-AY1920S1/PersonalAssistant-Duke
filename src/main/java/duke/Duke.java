package duke;

import duke.command.Command;
import duke.core.CommandManager;
import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.PatientManager;
import duke.relation.PatientTaskList;
import duke.statistic.Counter;
import duke.storage.StorageManager;
import duke.task.TaskManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * Represents Duke, a Personal Assistant to help
 * users tracking their progress.
 */
public class Duke extends Application {
    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;
    // ...
    private Image user = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image duke = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));
    // ...

    public static void main(String[] args) {
        // ...
    }

    @Override
    public void start(Stage stage) {
        //Step 1. Setting up required components

        //The container for the content of the chat to scroll.
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout);

        stage.setScene(scene);
        stage.show();

        //Step 2. Formatting the window to look as expected
        stage.setTitle("Duke");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        // You will need to import `javafx.scene.layout.Region` for this.
        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);


        userInput.setPrefWidth(325.0);

        sendButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);

        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);

        AnchorPane.setLeftAnchor(userInput , 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        //Step 3. Add functionality to handle user input.
//        sendButton.setOnMouseClicked((event) -> {
//            dialogContainer.getChildren().add(getDialogLabel(userInput.getText()));
//            userInput.clear();
//        });

//        userInput.setOnAction((event) -> {
//            dialogContainer.getChildren().add(getDialogLabel(userInput.getText()));
//            userInput.clear();
//        });

        // more code to be added here later

        //Scroll down to the end every time dialogContainer's height changes.
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));

        //Part 3. Add functionality to handle user input.
        sendButton.setOnMouseClicked((event) -> {
            handleUserInput();
        });

        userInput.setOnAction((event) -> {
            handleUserInput();
        });


    }

    /**
     * Iteration 1:
     * Creates a label with the specified text and adds it to the dialog container.
     * @param text String containing text to add
     * @return a label with the specified text that has word wrap enabled.
     */
    private Label getDialogLabel(String text) {
        // You will need to import `javafx.scene.control.Label`.
        Label textToAdd = new Label(text);
        textToAdd.setWrapText(true);

        return textToAdd;
    }

    /**
     * Iteration 2:
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    private void handleUserInput() {
        Label userText = new Label(userInput.getText());
        Label dukeText = new Label(getResponse(userInput.getText()));
        dialogContainer.getChildren().addAll(
            new DialogBox(userText, new ImageView(user)),
            new DialogBox(dukeText, new ImageView(duke))
        );
        userInput.clear();
    }

    /**
     * You should have your own function to generate a response to user input.
     * Replace this stub with your completed method.
     */
    private String getResponse(String input) {
        return "Duke heard: " + input;
    }
}


//
//
//    /**
//     * A Storage object that handles reading tasks from a local
//     * file and saving them to the same file.
//     */
//    private StorageManager storageManager;
//    /**
//     * A TaskList object that deals with add, delete, mark as done,
//     * find functions of a list of tasks.
//     */
//    private PatientTaskList patientTaskList;
//    private TaskManager taskManager;
//    private PatientManager patientManager;
//    private Counter counter;
//
//
//    /**
//     * A Ui object that deals with interactions with the user.
//     */
//
//    private static final Ui ui = Ui.getUi();
//
//
//    /**
//     * Constructs a Duke object with a relative file path.
//     * Initialize the user interface and reads tasks from the specific text file.
//     *
//     * @param filePath A string that represents the path of the local file
//     *                 used for storing tasks.
//     */
//    public Duke(String filePath) {
//        storageManager = new StorageManager(filePath);
//
//        try {
//            patientTaskList = new PatientTaskList(storageManager.loadAssignedTasks());
//            taskManager = new TaskManager(storageManager.loadTasks());
//            patientManager = new PatientManager(storageManager.loadPatients());
//            counter = new Counter(storageManager.loadCommandFrequency());
//
//        } catch (DukeException e) {
//            ui.showLoadingError();
//            System.out.println(e.getMessage());
//            taskManager = new TaskManager();
//        }
//    }
//
//    /**
//     * Runs the Duke program.
//     * Reads user input until a "bye" message is received.
//     */
//    public void run() {
//        ui.showWelcome();
//        boolean isExit = false;
//        while (!isExit) {
//            try {
//                String fullCommand = ui.readCommand();
//                ui.showLine();
//                Command c = CommandManager.manageCommand(fullCommand);
//                c.execute(patientTaskList, taskManager, patientManager,
//                    ui, storageManager);
//                counter.runCommandCounter(c, storageManager, counter);
//                isExit = c.isExit();
//            } catch (DukeException e) {
//                ui.showError(e.getMessage());
//            } finally {
//                ui.showLine();
//            }
//
//        }
//        System.exit(0);
//    }
//
//    /**
//     * Starts the Duke thread and Reminder thread concurrently
//     * by passing a filepath to duke and a global ui object&
//     * task list to Reminder.
//     *
//     * @param args The command line arguments.
//     */
//    public static void main(String[] args) {
//        new Duke("./data").run();
//    }
//
//}
