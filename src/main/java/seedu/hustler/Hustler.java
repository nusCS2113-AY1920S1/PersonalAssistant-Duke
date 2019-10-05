package seedu.hustler;

import java.io.IOException;

import seedu.hustler.game.avatar.Avatar;
import seedu.hustler.command.Command;
import java.util.Scanner;

import seedu.hustler.data.AvatarStorage;
import seedu.hustler.data.Schedule;
import seedu.hustler.logic.CommandLineException;
import seedu.hustler.task.Reminders;
import seedu.hustler.ui.Ui;
import seedu.hustler.data.Storage;
import seedu.hustler.data.Folder;
import seedu.hustler.task.TaskList;
import seedu.hustler.parser.CommandParser;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A personal assitant that takes in user input and gives and performs
 * an operation that can help the user
 * in his day to day needs. Has a tasklist with multiple features.
 */
public class Hustler extends Application {

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;

    private Image user = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image duke = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    /**
     * TaskList instance that  stores all the tasks added by the
     * user and from storage.
     */
    public static TaskList list;

    /**
     * Storage instance that stores and loads tasks to and from
     * disk.
     */
    private static Storage storage = new Storage("data/duke.txt");
    /**
     * UI instance that is used to take input from console
     * and display errors and responses. Handles user interaction.
     */
    private static Ui ui = new Ui(new Scanner(System.in));
    /**
     * Parser instance that makes sense of user input and
     * performs some operation on list.
     */
    private static CommandParser parser = new CommandParser();

    /**
     * Avatar instance that keeps track of the User's progress.
     */
    public static Avatar avatar;

    /**
     * Runs Duke which commences the user to machine
     * feedback loop until the user enters "bye".
     * Loads existing tasklist and avatar, and performs operations
     * like list, find, delete and add on the tasklist. Adds
     * the Tasks in the TreeMap.
     * Saves the list to disk for next duke session inside
     * data/duke.txt.
     * @see Storage
     * @see TaskList
     * @see CommandParser
     * @see Ui
     * @see Schedule
     */
    public static void run() throws IOException {
        ui.show_opening_string();
        Folder.checkDirectory();
        list = new TaskList(storage.load());

        // Display reminders at the start
        Reminders.runAll(list);
        Reminders.displayReminders();
        System.out.println();
        avatar = AvatarStorage.load();
        AvatarStorage.save(avatar);

        // Taking the the first raw input
        String rawInput = ui.take_input();

        // Taking input and printing till user input is bye
        while (!rawInput.equals("bye")) {
            try {
                Command command = parser.parse(rawInput);
                command.execute();
                try {
                    storage.save(list.return_list());
                } catch (IOException e) {
                    ui.show_save_error();
                }
                rawInput = ui.take_input();

                System.out.println();
            } catch (CommandLineException e) {
                e.getErrorMsg();
                rawInput = ui.take_input();
            }
        }
        ui.show_bye_message();
    }

    /**
     * Main function run by java.
     *
     * @param args the command line parameters
     */
    public static void main(final String[] args) throws IOException {
        Hustler.run();
    }

    @Override
    public void start(Stage stage) {
        // The container for the content of the chat to scroll.
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout);

        stage.setScene(scene); // Setting the stage to show our screen
        stage.show(); // Render the stage.

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
        sendButton.setOnMouseClicked((event) -> {
            handleUserInput();
        });

        userInput.setOnAction((event) -> {
            handleUserInput();
        });

        //Scroll down to the end every time dialogContainer's height changes.
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));
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
                //DialogBox.getUserDialog(userText, new ImageView(user)),
                //DialogBox.getDukeDialog(dukeText, new ImageView(duke))
                DialogBox.getUserDialog(userInput.getText(), user),
                DialogBox.getDukeDialog(getResponse(userInput.getText()), duke)
        );
        userInput.clear();
    }

    /**
     * You should have your own function to generate a response to user input.
     * Replace this stub with your completed method.
     */
    public String getResponse(String input) {
        return "Duke heard: " + input;
    }
}