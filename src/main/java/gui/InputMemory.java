package gui;

import java.util.ArrayList;

public class InputMemory {
    ArrayList<String> inputHistory = new ArrayList<String>();
    int currentPointer = 0;
    String tempInput = "";

    /**
     * Creates a new InputMemory Class Object
     */
    public InputMemory() {
    }

    /**
     * Looks for previous commands when user presses up key
     *
     * @param currentInput the text currently in the inputField
     * @return new text to replace inputField
     */
    public String moveUp(String currentInput) {
        if (inputHistory.size() == 0)
            return currentInput;
        else {
            if (currentPointer == inputHistory.size()) {
                tempInput = currentInput;
            }

            if (currentPointer == 0) {
                return currentInput;
            }

            currentPointer -= 1;
            return inputHistory.get(currentPointer);
        }
    }

    /**
     * Looks for previous commands when user presses down key
     *
     * @param currentInput the text currently in the inputField
     * @return new text to replace inputField
     */
    public String moveDown(String currentInput) {
        if (inputHistory.size() == 0) {
            return currentInput;
        } else {
            if (currentPointer == inputHistory.size() - 1) {
                currentPointer = inputHistory.size();
                return tempInput;
            }
            if (currentPointer == inputHistory.size()) {
                return currentInput;
            }

            currentPointer += 1;
            return inputHistory.get(currentPointer);
        }
    }

    /**
     * Adds new inputted command to history
     *
     * @param input the newly executed input
     */
    public void addToHistory(String input) {
        inputHistory.add(input);
        currentPointer = inputHistory.size();
    }
}
