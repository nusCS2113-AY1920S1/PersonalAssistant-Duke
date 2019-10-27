package seedu.hustler.logic.parser.anomaly;

/**
 * Detects timer anomalies in user input.
 */
public class TimerAnomaly {

    /**
     * Detects anomalies for input.
     *
     * @param userInput input for which anomaly is detected
     * @return true or false for any anomaly detected
     */
    public boolean detect(String[] userInput) {

        String[] timeParts = userInput[1].split(" ");

        //detects whether the number of arguments are exactly three (hours, minutes and
	//seconds). For example, 'timer 1' and 'timer 1 2 3 4' are invalid inputs.
        if (timeParts.length != 3) {
            System.out.println("The number of arguments should be exactly three! Timer format should be: 'timer <integer> <integer> <integer>'");
            return true;
        }

	//detects whether the relevant arguments are non-integers. For example, 'timer winter cheese sofa' is a invalid input.
	for (int i = 0; i < 3; i += 1) {
            try {
                int numberOfCommandsToUndo = Integer.parseInt(timeParts[i]);
            } catch (NumberFormatException e) {
                System.out.println("Timer arguments specified are not integers! Timer format should be: 'timer <integer> <integer> <integer>'");
                return true;
            }
        }

	//if no error has been detected, the method returns a false to indicate that.
	return false;
    }
}
