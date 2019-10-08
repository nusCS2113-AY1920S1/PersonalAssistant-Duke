package seedu.hustler;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import seedu.hustler.logic.CommandLineException;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

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
    private TextArea console;
    @FXML
    private PrintStream ps;

    private Hustler hustler;

    @FXML
    public void initialize() throws IOException {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        ps = new PrintStream(new Console(console));
        System.setOut(ps);
        System.setErr(ps);
        Hustler.initialize();
    }

    public void setHustler(Hustler h) {
        hustler = h;
    }

    public class Console extends OutputStream {
        private TextArea console;

        public Console(TextArea console) {
            this.console = console;
        }

        public void appendText(String valueOf) {
            Platform.runLater(() -> console.appendText(valueOf));
        }

        public void write(int b) {
            appendText(String.valueOf((char)b));
        }
    }

    @FXML
    public void handleUserInput() throws CommandLineException {
        String input = userInput.getText();
        Hustler.run(input);
        userInput.clear();
    }
}