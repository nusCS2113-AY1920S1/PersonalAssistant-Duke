package seedu.hustler.logic.parser.anomaly;

/**
 * Detects undo anomalies in user input.
 */
public class UndoAnomaly extends DetectAnomaly {

    /**
     * Detects anomalies for the undo command..
     *
     * @param userInput input for which anomaly is detected
     * @return true or false for any anomaly detected
     */
    public boolean detect(String[] userInput) {

        String[] parsedInput = userInput[1].split(" ");

        //detects whether they are more arguments than expected. For example,
	//'undo 1 2 3' and 'undo 3 fried rice' are invalid inputs.
        if (parsedInput.length != 1) {
            System.out.println("There should be exactly one argument inside the undo command! Undo commands should follow this format: 'undo <integer>'");
            return true;
	}

	//detects whether the argument is a non-integer. For example, 'undo
	//tacobell' is a invalid input.
        try {
            int numberOfCommandsToUndo = Integer.parseInt(parsedInput[0]);
        } catch (NumberFormatException e) {
            System.out.println("The specified argument of the undo commnad is not an integer! Undo commands should follow this format: 'undo <integer>'");
            return true;
        }

	//if no error has been detected, the method returns a false to
	//indicate that.
        return false;
    }
}
