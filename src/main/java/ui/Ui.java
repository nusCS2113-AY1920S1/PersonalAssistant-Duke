package ui;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

import java.util.Scanner;

/**
 * To deal with user interactions.
 */
public class Ui {
    private Scanner scanner;
    @FXML
    private VBox dialogContainer;
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));
    private String output;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public void showLoadingError() {
        setOutput(":( OOPS!!! File path not found. Creating directory /data/data.txt");
    }

    public void showError(String error) {
        System.out.println(error);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void addToOutput(String string) {
        output += string + "\n";
    }

    public void showUserInput(String string) {
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(string, userImage)
        );
    }

    public void showDukeOutput(String string) {
        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog(string, dukeImage)
        );
    }

    /**
     * Show Welcome message on programme start.
     */
    public void showWelcome() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";

        //System.out.println("Hello from\n" + logo);
        setOutput("Hello! I'm Duke\n"
                + "What can I do for you?");
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
