package eggventory.ui;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

//@@author Raghav-B
public class InputTextBox {

    private TextFlow textFlow;
    private Text leftText; // User's raw input left of caret
    private Text caretText; // Used to emulate a caret for ease of input
    private Text rightText; // User's raw input right of caret
    private Text searchText; // Displays predictive text in an autocomplete fashion.

    private InputPredictor inputPredictor;

    /**
     * This controls the format of the text to be displayed in the user's
     * input textbox.
     * @param textFlow Reference to TextFlow object from GUI controller. This
     *                 object will be modified based on the user's input and the
     *                 search being performed.
     */
    public InputTextBox(TextFlow textFlow) {
        this.textFlow = textFlow;
        this.inputPredictor = new InputPredictor();

        leftText = new Text("");
        caretText = new Text("|");
        caretText.setFill(Color.BLUE);
        rightText = new Text("");
        rightText.setFill(Color.LIGHTGRAY);
        searchText = new Text("");
        searchText.setFill(Color.LIGHTGRAY);

        textFlow.getChildren().setAll(leftText, caretText, rightText, searchText);
    }

    /**
     * Appends text to normalText.
     * @param appendString Character to append to text.
     * @param searchDirection Only used when method is used for cycling through
     *                        command search results. Default value to be passed
     *                        in is 0.
     */
    public void appendText(String appendString, int searchDirection) {
        String newLeftString = leftText.getText() + appendString;
        String finalString = newLeftString + rightText.getText();

        // Getting updated search result for new text.
        String searchResultText = inputPredictor.getPrediction(finalString, searchDirection);
        searchText.setText(searchResultText);
        leftText.setText(newLeftString);

        // Update inputField
        textFlow.getChildren().setAll(leftText, caretText, rightText, searchText);
    }

    /**
     * Deletes character on left of caret when backspace is pressed.
     */
    public void removeTextBackspace() {
        String curLeftString = leftText.getText();
        String curRightString = rightText.getText();

        // Additional check in case the current deletion will make
        // inputField blank.
        if (curLeftString.length() - 1 < 0) {
            // Handling case where rightText is not empty yet. In this case, we do not want to
            // empty the entire text input.
            if (!curRightString.equals("")) {
                return;
            }
            clearAllText();
            return;
        }

        // Needed to ensure colour of text is displayed properly.
        if (rightText.getText().equals("")) {
            rightText.setFill(Color.LIGHTGRAY);
        }

        // Removing last character from text.
        String newLeftString = curLeftString.substring(0, curLeftString.length() - 1);
        String finalString = newLeftString + curRightString;

        // Getting updated search result for new text.
        String searchResultText = inputPredictor.getPrediction(finalString, 0);
        leftText.setText(newLeftString);
        searchText.setText(searchResultText);

        // Update inputField
        textFlow.getChildren().setAll(leftText, caretText, rightText, searchText);
    }

    /**
     * Deletes character on right of caret when delete key is pressed.
     */
    public void removeWordDelete() {
        String curLeftString = leftText.getText();
        String curRightString = rightText.getText();

        // Additional check in case the current deletion is occurring
        // at the rightmost side of the text input, therefore not deleting
        // anything.
        if (curRightString.length() - 1 < 0) {
            rightText.setFill(Color.LIGHTGRAY);
            return;
        }

        // Removing last character from text.
        String newRightString = curRightString.substring(1);
        String finalString = curLeftString + newRightString;

        rightText.setText(newRightString);
        // Needed to ensure colour of text is displayed properly.
        if (rightText.getText().equals("")) {
            rightText.setFill(Color.LIGHTGRAY);
        }

        // Getting updated search result for new text.
        String searchResultText = inputPredictor.getPrediction(finalString, 0);
        searchText.setText(searchResultText);

        // Update inputField
        textFlow.getChildren().setAll(leftText, caretText, rightText, searchText);
    }

    /**
     * Moved the caret postion so that user can edit text with more flexibility.
     * @param direction -1: Move caret towards left.
     *                  1: Move caret towards right.
     */
    public void moveCaret(int direction) {
        String leftString = leftText.getText();
        String rightString = rightText.getText();

        if (direction == -1 && leftString.length() > 0) { // left caret movement
            rightString = leftString.charAt(leftString.length() - 1) + rightString;
            leftString = leftString.substring(0, leftString.length() - 1);
        } else if (direction == 1 && rightString.length() > 0) { // right caret movement
            leftString += rightString.charAt(0);
            rightString = rightString.substring(1);
        }

        leftText.setText(leftString);
        rightText.setText(rightString);

        // Needed to ensure colour of text is displayed properly.
        if (!rightText.getText().equals("")) {
            rightText.setFill(Color.BLACK);
        }

        // Update inputField
        textFlow.getChildren().setAll(leftText, caretText, rightText, searchText);
    }

    /**
     * Appends the searchText to the normalText immediately. This means that
     * the user has accepted the suggested/predicted searchText.
     */
    public void acceptSearchText() {
        // Accepting search text is only possible if currently searching through
        // commands alone. Once command has been accepted, cannot accept arguments
        // because these will be replaced by custom values from the user.
        if (inputPredictor.isCommandFound) {
            return;
        }

        String newText = leftText.getText() + rightText.getText() + searchText.getText();
        leftText.setText(newText);
        rightText.setText("");
        // Empty searchText since it has been with normalText.
        searchText.setText("");

        // Reset internal search parameters since the search will now move onto
        // searching for arguments for the input command.
        inputPredictor.reset();

        // This will now search for the possible arguments for the currently accepted
        // full command.
        String searchResultText = inputPredictor.getPrediction(newText, 0);
        searchText.setText(searchResultText);

        // Update inputField
        textFlow.getChildren().setAll(leftText, caretText, rightText, searchText);
    }

    /**
     * Gets all normalText. Is used right before user input is passed to parser.
     * @return all normalText as a String.
     */
    public String getAllText() {
        return leftText.getText() + rightText.getText();
    }

    /**
     * Clears both normalText and searchText.
     */
    public void clearAllText() {
        // Resets internal search parameters because user input is to be cleared
        // and so user can input all possible inputs.
        inputPredictor.reset();
        leftText.setText("");
        rightText.setText("");
        searchText.setText("");

        // Update inputField
        textFlow.getChildren().setAll(leftText, caretText, rightText, searchText);
    }
}
