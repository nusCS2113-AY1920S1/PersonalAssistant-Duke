package eggventory.ui;

import eggventory.logic.commands.CommandDictionary;
import java.util.ArrayList;

//@@author Raghav-B
public class InputPredictor {

    private CommandDictionary commandDictionary; // Contains all valid commands+arguments that can be input.
    private static ArrayList<String> curSearch; // Stores all results that match the current input.
    private int curSearchIndex = 0; // Used to iterate through curSearch
    public boolean isCommandFound = false; // Used for differentiating between command and argument search.
    private String foundCommand = ""; // Stores valid command when searching for corresponding arguments.

    /**
     * Initialize current prediction for user's input String. Initializes the
     * curSearch ArrayList to store all matching results so that user can
     * iterate through the results.
     */
    public InputPredictor() {
        this.commandDictionary = new CommandDictionary();
        reset();
    }

    /**
     * Resets search parameters. Is useful when need to begin a new search
     * with completely different query String.
     */
    public void reset() {
        curSearch = new ArrayList<>();
        curSearchIndex = 0;
        isCommandFound = false;
        foundCommand = "";
    }

    /**
     * Returns rest of String that matches prediction. Has two states:
     *  1. Searching for command, e.g. add stock
     *  2. Searching for arguments for said command, e.g. < Stock Type > < Stock Code > etc..
     * @param query User's input so far that will be used for prediction.
     * @param direction -1 = previous result.
     *                  0 = current result.
     *                  1 = next result.
     * @return Returns requested String that matches prediction.
     */
    public String getPrediction(String query, int direction) {
        String remainString = "";

        if (query.equals("")) {
            return remainString;
        }

        // Conditional to control aforementioned states.
        if (isCommandFound) {
            // At this point the user will have input a valid command, and will be
            // inputting the arguments for said command. We just check if this is
            // the case.
            if (query.length() > foundCommand.length()) {
                remainString = argumentPredictionHandler(query, direction);
            } else {
                // Else, the user input is invalid, or the user is inputting a longer
                // command. E.g. from `add stock` to `add stocktype`
                reset(); // Reset because user could be inputting another command.
                remainString = commandPredictionHandler(query, direction); // Try to predict new input.
                // remainString will be "" if user input is invalid and no prediction could be found.
            }
        } else {
            // This runs when user is still inputting the command part of the input. Therefore, all
            // predictions will be searching for valid commands, rather than corresponding arguments.
            remainString = commandPredictionHandler(query, direction);
        }

        return remainString;
    }

    /**
     * Handles searching of commands.
     * @param query User input to base command search on.
     * @param direction Used for iterating through array of multiple possible
     *                  commands:
     *                  -1 = previous result.
     *                  0 = current result.
     *                  1 = next result.
     * @return Remaining String required to complete user input to get nearest valid command.
     */
    private String commandPredictionHandler(String query, int direction) {
        String returnString = "";
        // Get ArrayList of possible matching commands.
        curSearch = commandDictionary.searchDictCommands(query);

        // Checking if no result is found from search. If so, we return
        // a blank String, "".
        if (curSearch.isEmpty()) {
            return "";
        }

        curSearchIndex = moduloIndex(direction); // Ensuring index is within array bounds.
        /* Checks if the user has completed a command entry by comparing
        the input length with the returned command length. This works because it is already
        guaranteed that curSearch has a valid Command available because of the curSearch.empty()
        check above.
        */
        if (query.length() == curSearch.get(curSearchIndex).length()) {
            /* Once we're at this point, we will only be interested in the remaining arguments
            corresponding to the entered command.
            */

            // Storing query so that we can search for it to get the corresponding arguments.
            foundCommand = query;
            isCommandFound = true;
            curSearchIndex = 0; // Reset search index because searching for arguments is a new search.

            // Appends blank space to returned arguments so UX is more seamless.
            returnString = " " + getArgumentPrediction(query, direction);
        } else {
            // At this point, user input for command is still incomplete.
            returnString = getRemainCommand(query, curSearch.get(curSearchIndex));
        }

        return returnString;
    }

    /**
     * Handles searching of arguments corresponding to a full command. Hence precondition
     * is that param command is a completely valid command.
     * @param command Valid command entered by user.
     * @param direction Used for iterating through array of multiple possible
     *                  arguments corresponding to a command:
     *                  -1 = previous result.
     *                  0 = current result.
     *                  1 = next result.
     * @return Remaining template for command's expected arguments not entered yet by user.
     */
    private String argumentPredictionHandler(String command, int direction) {
        String returnString = "";
        // Getting arguments entered by user so far by removing the valid command that has been
        // previously entered (stores in foundCommand).
        String inputArgs = command.substring(foundCommand.length());
        // Check if user has not properly entered a space after typing command.
        if (inputArgs.charAt(0) != ' ') {
            reset();
            return commandPredictionHandler(command, direction);
        }

        // Remove " " character from front of user argument input.
        inputArgs = inputArgs.substring(1);

        returnString = getArgumentPrediction(foundCommand, direction);
        // Concatenate argument format based on user inputted arguments
        returnString = getRemainArgument(inputArgs, returnString);

        return returnString;
    }

    /**
     * Simply returns selected argument format from a list of available argument formats
     * corresponding to the valid command already input by the user.
     * @param command A valid command that has already been input by the user.
     * @param direction Direction to traverse ArrayList of matching argument results in.
     * @return Chosen argument format.
     */
    private String getArgumentPrediction(String command, int direction) {
        String returnString = "";
        curSearch = commandDictionary.searchDictArguments(command);

        curSearchIndex = moduloIndex(direction);

        returnString = curSearch.get(curSearchIndex);
        // Some arguments are null for commands like `list stock`, we do
        // not want null to be printed out.
        if (returnString == null) {
            return "";
        }

        return returnString;
    }

    /**
     * Ensures that the curSearchIndex for the current ArrayList of returned
     * possible search results stays within the bounds of the array.
     * @param direction Direction to traverse ArrayList in.
     * @return Modulo-ed index during traversal.
     */
    private int moduloIndex(int direction) {
        if (direction == -1) {
            curSearchIndex -= 1;
            if (curSearchIndex < 0) {
                curSearchIndex = curSearch.size() - 1;
            }
        } else if (direction == 1) {
            curSearchIndex += 1;
            if (curSearchIndex >= curSearch.size()) {
                curSearchIndex = 0;
            }
        }

        return curSearchIndex;
    }

    /**
     * Concatenates returned search results by the input that the user has
     * entered so far. This is used to give the illusion of the predictive search
     * by showing the text required to complete the command in grey.
     * @param query Text input by user.
     * @param match Full String result of what user's input is predicted as.
     * @return Concatenated String for showing prediction in grey.
     */
    private String getRemainCommand(String query, String match) {
        return match.substring(query.length());
    }

    /**
     * Concatenates returned search results for required arguments to be entered by user
     * based on the arguments that have already been input by the user. Once again, used
     * to give illusion of predictive search by showing required text in grey.
     * @param argument Arguments that have entered by the user so far.
     * @param match Required full argument format for currently entered command.
     * @return Format of remaining arguments yet to be entered by user.
     */
    private String getRemainArgument(String argument, String match) {
        String[] remainStringArr = match.split("(?<=>) (?=<)");
        String[] inputArgsArr = argument.split(" ");
        StringBuilder sb = new StringBuilder();

        int argsToExclude = inputArgsArr.length;
        if (inputArgsArr[0].equals("")) {
            // Special case when user is yet to input any arguments.
            argsToExclude = 0;
        } else {
            // Handles appending of space to end of user argument input
            // for seamless UX.
            if (argument.charAt(argument.length() - 1) != ' ') {
                sb.append(" ");
            }
        }

        // Create String to return with remaining argument format yet to be
        // entered by the user.
        for (int i = argsToExclude; i < remainStringArr.length; i++) {
            sb.append(remainStringArr[i]);
            sb.append(" ");
        }
        match = sb.toString();

        return match;
    }
}
