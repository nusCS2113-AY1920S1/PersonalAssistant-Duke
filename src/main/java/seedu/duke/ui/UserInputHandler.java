package seedu.duke.ui;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import seedu.duke.Duke;

import java.util.function.UnaryOperator;

class UserInputHandler {
    private TextField userInput;
    private Button sendButton;

    UserInputHandler(TextField userInput, Button sendButton) {
        this.userInput = userInput;
        this.sendButton = sendButton;
    }

    /**
     * Set text in userInput.
     *
     * @param text to set in userInput.
     */
    public void setUserInputText(String text) {
        removeFilter(text);
        applyFilter();

        int pos = userInput.getText().length();
        if (pos < Duke.getUI().getPrefix().length()) {
            userInput.positionCaret(Duke.getUI().getPrefix().length());
        } else {
            userInput.positionCaret(pos);
        }
    }

    private void removeFilter(String text) {
        // To apply a noFilter to userInput to remove the effect of the previous filter so that clear()
        // can work properly.
        UnaryOperator<TextFormatter.Change> noFilter = c -> {
            return c;
        };
        userInput.setTextFormatter(new TextFormatter<String>(noFilter));
        userInput.clear();
        userInput.setText(text);
    }

    private void applyFilter() {
        // To apply a filter to any changes in userInput text field so that the prefix is non-deletable text.
        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (c.getCaretPosition() < Duke.getUI().getPrefix().length()) {
                return null;
            } else {
                return c;
            }
        };
        userInput.setTextFormatter(new TextFormatter<String>(filter));
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
        int pos = userInput.getCaretPosition();
        if (pos <= Duke.getUI().getPrefix().length()) {
            return;
        }
        String text = userInput.getText();
        String newText = text.substring(0, pos - 1) + text.substring(pos);
        setUserInputText(newText);
        userInput.positionCaret(pos - 1);
    }

    /**
     * Moves the position of caret to the right by 1
     */
    public void moveCaretRight() {
        int pos = userInput.getCaretPosition();
        userInput.positionCaret(pos + 1);
    }

    /**
     * Moves the position of caret to the left by 1
     */
    public void moveCaretLeft() {
        int pos = userInput.getCaretPosition();
        userInput.positionCaret(pos - 1);
    }
}
