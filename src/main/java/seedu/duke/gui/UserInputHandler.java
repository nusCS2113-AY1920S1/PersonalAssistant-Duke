package seedu.duke.gui;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import seedu.duke.CommandParser;

import java.util.function.UnaryOperator;

public class UserInputHandler {
    TextField userInput;
    Button sendButton;

    public UserInputHandler(TextField userInput, Button sendButton) {
        this.userInput = userInput;
        this.sendButton = sendButton;
    }

    /**
     * Set text in userInput.
     *
     * @param text to set in userInput.
     */
    public void setUserInputText(String text) {
        // To apply a noFilter to userInput to remove the effect of the previous filter so that clear()
        // can work properly.
        UnaryOperator<TextFormatter.Change> noFilter = c -> {
            return c;
        };
        userInput.setTextFormatter(new TextFormatter<String>(noFilter));
        userInput.clear();
        userInput.setText(text);

        // To apply a filter to any changes in userInput text field so that the prefix is non-deletable text.
        String prefix = CommandParser.getInputPrefix();
        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (c.getCaretPosition() < prefix.length()) {
                return null;
            } else {
                return c;
            }
        };
        userInput.setTextFormatter(new TextFormatter<String>(filter));
        int pos = userInput.getText().length();
        if (pos < prefix.length()) {
            userInput.positionCaret(prefix.length());
        } else {
            userInput.positionCaret(pos);
        }
    }

    /**
     * Update text in userInput when DELETE is pressed.
     */
    public void setTextDelete() {
        int pos = userInput.getCaretPosition();
        String text = userInput.getText();
        if (pos >= text.length()) {
            return;
        }
        String newText = text.substring(0, pos) + text.substring(pos + 1);
        setUserInputText(newText);
        userInput.positionCaret(pos);
    }


    /**
     * Update text in userInput when BACKSPACE is pressed.
     */
    public void setTextBackSpace() {
        String prefix = CommandParser.getInputPrefix();
        int pos = userInput.getCaretPosition();
        if (pos <= prefix.length()) {
            return;
        }
        String text = userInput.getText();
        String newText = text.substring(0, pos - 1) + text.substring(pos);
        setUserInputText(newText);
        userInput.positionCaret(pos - 1);
    }

    public void moveCaretRight() {
        int pos = userInput.getCaretPosition();
        userInput.positionCaret(pos + 1);
    }

    public void moveCaretLeft() {
        int pos = userInput.getCaretPosition();
        userInput.positionCaret(pos - 1);
    }

}
