package eggventory.ui;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

//@@author Raghav-B
public class InputTextBox {

    private TextFlow textFlow;
    private Text normalText; // User's raw input
    private Text caretText; // Used to emulate a caret for ease of input
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

        normalText = new Text("");
        caretText = new Text("|");
        caretText.setFill(Color.BLUE);
        searchText = new Text("");
        searchText.setFill(Color.LIGHTGRAY);

        textFlow.getChildren().setAll(normalText, caretText, searchText);
    }

    /**
     * Appends a character to normalText when a key is pressed.
     * @param appendChar Character to append to text.
     * @param searchDirection Only used when method is used for cycling through
     *                        command search results. Default value to be passed
     *                        in is 0.
     */
    public void appendText(String appendChar, int searchDirection) {
        String finalText = normalText.getText() + appendChar;
        // Getting updated search result for new text.
        String searchResultText = inputPredictor.getPrediction(finalText, searchDirection);
        searchText.setText(searchResultText);
        normalText.setText(finalText);

        // Update inputField
        textFlow.getChildren().setAll(normalText, caretText, searchText);
    }

    /**
     * Deletes tail character from normalText when backspace is pressed.
     */
    public void removeFromWord() {
        String curText = normalText.getText();

        // Additional check in case the current deletion will make
        // inputField blank.
        if (curText.length() - 1 <= 0) {
            clearAllText();
            return;
        }

        // Removing last character from text.
        String newText = curText.substring(0, curText.length() - 1);
        // Getting updated search result for new text.
        String searchResultText = inputPredictor.getPrediction(newText, 0);
        normalText.setText(newText);
        searchText.setText(searchResultText);

        // Update inputField
        textFlow.getChildren().setAll(normalText, caretText, searchText);
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

        String newText = normalText.getText() + searchText.getText();
        normalText.setText(newText);
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
        textFlow.getChildren().setAll(normalText, caretText, searchText);
    }

    /**
     * Gets all normalText. Is used right before user input is passed to parser.
     * @return all normalText as a String.
     */
    public String getAllText() {
        return normalText.getText();
    }

    /**
     * Clears both normalText and searchText.
     */
    public void clearAllText() {
        // Resets internal search parameters because user input is to be cleared
        // and so user can input all possible inputs.
        inputPredictor.reset();
        normalText.setText("");
        searchText.setText("");

        // Update inputField
        textFlow.getChildren().setAll(normalText, caretText, searchText);
    }
}
