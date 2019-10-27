package ui;

import booking.Booking;
import booking.BookingList;
import control.Duke;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for ui.MainWindow. Provides the layout for the other controls.
 */
public class Ui extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ScrollPane scrollPane2;
    @FXML
    private VBox dialogContainer;
    @FXML
    private VBox listContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    @FXML
    private AnchorPane flexiblePane;

    private String output;
    private Duke duke;
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DukeCat.png"));

    /**
     * Do on application start. Prints a welcome message.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        scrollPane2.vvalueProperty().bind(listContainer.heightProperty());

        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog(showWelcome(), dukeImage)
        );
    }

    public void setDuke(Duke d) {
        duke = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing control.Duke's reply and then
     * appends them to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = duke.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, dukeImage)
        );
        userInput.clear();
        if (input.equals("list")) {
            BookingList bookingList = duke.getBookingList();
            showList(bookingList);
        }
    }

    private void showList(BookingList bookingList) {
        for (Booking i : bookingList) {
            addToList(new ListBox(i.getName(), i.getVenue(), i.getDateTimeStart().toString(),
                    i.getTimeEnd().toString(), i.getStatus()));
        }
    }

    private void addToList(ListBox listBox) {
        listContainer.getChildren().addAll(listBox);
    }

    public void showLoadingError() {
        setOutput(":( OOPS!!! File path not found. Creating directory /data/data.txt");
    }

    public void showError(String error) {
        System.out.println(error);
    }

    public void addToOutput(String string) {
        output += string + "\n";
    }

    /**
     * Prints user input to GUI.
     * @param string string to display
     */
    public void showUserInput(String string) {
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(string, userImage)
        );
    }

    /**
     * Show Welcome message on programme start.
     */
    public String showWelcome() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        return "Hello! I'm Duke\n"
                + "What can I do for you?";
    }

    public void showLine() {
        System.out.println("    ____________________________________________________________");
    }

    public void showBye() {
        setOutput("Bye. Hope to see you again soon!");
    }

    public void indent() {
        System.out.print("    ");
    }

    public void setOutput(String text) {
        output = text;
    }

    public String getOutput() {
        return output;
    }
}