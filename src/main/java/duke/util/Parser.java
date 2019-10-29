//@@lmtaek

package duke.util;

import duke.exceptions.DukeException;

public class Parser {

    String userInput;
    String[] parsedInput;

    /**
     * Constructor for the Parser class.
     *
     * @param userInput Takes in user's raw input and stores it to use in its methods, parsing it
     *                  into a format that is appropriate for the command it invokes.
     */
    public Parser(String userInput) throws DukeException {
        this.userInput = userInput.trim();
        try {
            parsedInput = userInput.split(":");
        } catch (Exception e) {
            throw new DukeException("Could not parse user input!");
        }
    }

    /**
     * Parses any instance of user input and attempts to pass the output to a Command class.
     *
     * @return The user input without the command keyword.
     */
    public String[] parseUserInput() {
        int outputLength = parsedInput.length - 1;
        String[] formattedInput = new String[outputLength];

        for (int i = 1; i <= formattedInput.length; i++) {
            formattedInput[i - 1] = parsedInput[i].trim();
        }
        return formattedInput;
    }
}