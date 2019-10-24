package duke.logic.parsers;

import duke.logic.autocorrect.Autocorrect;
import duke.logic.commands.*;
import duke.commons.exceptions.DukeException;

import static duke.commons.definitions.CommandDefinitions.*;
import static duke.commons.exceptions.ExceptionMessages.UNKNOWN_COMMAND;

/**
 * Parser is a public class that help to parse the command that is inputted from the user.
 * And generate the appropriate command with their appropriate arguments
 */
public class Parser {
    private static HistoryCommand historyCommand = new HistoryCommand();
    private static Autocorrect autocorrect;

    public Parser(Autocorrect autocorrect) {
        this.autocorrect = autocorrect;
    }

    /**
     * This is the main function that parse the command inputted by the user.
     * @param fullCommand the string the user input in the CLI
     * @return <code>new ExitCommand()</code>
     *         if the user input "bye"
     *         <code>new AddCommand(new Breakfast())</code> if the user input
     *         "breakfast" followed by the description of the meal
     *         <code>new AddCommand(new Lunch()</code> if the user input
     *         "lunch" followed by the description of the meal
     *         <code>new AddCommand(new Dinner()</code> if the user input
     *         "dinner" followed by the description of the meal
     *         <code>new ListCommand()</code> if the user input
     *         list
     *         <code>new MarkDoneCommand(index)</code> if the user input
     *         "done" followed by the index of the meal to be marked completed
     *         <code>new FindCommand(description)</code> if the user input
     *         "find" followed by the string that needs to be added
     *         <code>new DeleteCommand(index) </code> if the sure input
     *         "delete" followed by the index of the task to be deleted
     * @throws DukeException when the command is not recognized or command syntax is invalid
     */
    public Command parse(String fullCommand) throws DukeException {
        String userInput = "";
        String[] splitCommand = fullCommand.split(" ", 2);
        if (splitCommand.length != 2) {
            splitCommand = new String[] {splitCommand[0], ""};
        }
        String command = splitCommand[0];
        command = autocorrect.runOnCommand(command);
        userInput = autocorrect.runOnArgument(splitCommand[1]);
        historyCommand.addCommand(command);

        switch (command) {
            case EXIT_COMMAND:
                return new ExitCommand();
            case BREAKFAST_COMMAND:
                return new AddBreakfastCommandParser().parse(userInput);
            case LUNCH_COMMAND:
                return new AddLunchCommandParser().parse(userInput);
            case DINNER_COMMAND:
                return new AddDinnerCommandParser().parse(userInput);
            case ADD_COMMAND:
                return new AddItemCommandParser().parse(userInput);
            case LIST_COMMAND:
                return new ListCommandParser().parse(userInput);
            case DONE_COMMAND:
                return new DoneCommandParser().parse(userInput);
            case FIND_COMMAND:
                return new FindCommandParser().parse(userInput);
            case DELETE_COMMAND:
                return new DeleteCommandParser().parse(userInput);
            case UPDATE_WEIGHT_COMMAND:
                return new UpdateWeightCommand(userInput);
            case CLEAR_COMMAND:
                return new ClearCommandParser().parse(userInput);
            case EDIT_COMMAND:
                return new EditCommandParser().parse(userInput);
            case SET_GOAL_COMMAND:
                return new SetgoalCommandParser().parse(userInput);
            case HELP_COMMAND:
                return new HelpCommandParser().parse(userInput);
            case DEPOSIT_COMMAND:
                return new DepositCommandParser().parse(userInput);
            case PAYMENT_COMMAND:
                return new PaymentCommandParser().parse(userInput);
            case HISTORY_COMMAND:
                // clear history if requested
                if (!userInput.isEmpty() && userInput.equals("clear")) {
                    historyCommand.clearHistory();
                }
                return historyCommand;
            case SUGGEST_COMMAND:
                return new SuggestCommandParser().parse(userInput);
            default:
                throw new DukeException(UNKNOWN_COMMAND);
        }
    }
}
