package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.logic.autocorrect.Autocorrect;
import duke.logic.commands.Command;
import duke.logic.commands.ExitCommand;
import duke.logic.commands.HistoryCommand;
import duke.logic.commands.UpdateWeightCommand;

import static duke.commons.definitions.CommandDefinitions.PARSER_ADD_COMMAND;
import static duke.commons.definitions.CommandDefinitions.PARSER_BREAKFAST_COMMAND;
import static duke.commons.definitions.CommandDefinitions.PARSER_CLEAR_COMMAND;
import static duke.commons.definitions.CommandDefinitions.PARSER_DELETE_COMMAND;
import static duke.commons.definitions.CommandDefinitions.PARSER_DINNER_COMMAND;
import static duke.commons.definitions.CommandDefinitions.PARSER_DONE_COMMAND;
import static duke.commons.definitions.CommandDefinitions.PARSER_EDIT_COMMAND;
import static duke.commons.definitions.CommandDefinitions.PARSER_EXIT_COMMAND;
import static duke.commons.definitions.CommandDefinitions.PARSER_FIND_COMMAND;
import static duke.commons.definitions.CommandDefinitions.PARSER_HELP_COMMAND;
import static duke.commons.definitions.CommandDefinitions.PARSER_HISTORY_COMMAND;
import static duke.commons.definitions.CommandDefinitions.PARSER_LIST_COMMAND;
import static duke.commons.definitions.CommandDefinitions.PARSER_LUNCH_COMMAND;
import static duke.commons.definitions.CommandDefinitions.PARSER_SET_GOAL_COMMAND;
import static duke.commons.definitions.CommandDefinitions.PARSER_SUGGEST_COMMAND;
import static duke.commons.definitions.CommandDefinitions.PARSER_UPDATE_WEIGHT_COMMAND;
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
            case PARSER_EXIT_COMMAND:
                return new ExitCommand();
            case PARSER_BREAKFAST_COMMAND:
                return new AddBreakfastCommandParser().parse(userInput);
            case PARSER_LUNCH_COMMAND:
                return new AddLunchCommandParser().parse(userInput);
            case PARSER_DINNER_COMMAND:
                return new AddDinnerCommandParser().parse(userInput);
            case PARSER_ADD_COMMAND:
                return new AddItemCommandParser().parse(userInput);
            case PARSER_LIST_COMMAND:
                return new ListCommandParser().parse(userInput);
            case PARSER_DONE_COMMAND:
                return new DoneCommandParser().parse(userInput);
            case PARSER_FIND_COMMAND:
                return new FindCommandParser().parse(userInput);
            case PARSER_DELETE_COMMAND:
                return new DeleteCommandParser().parse(userInput);
            case PARSER_UPDATE_WEIGHT_COMMAND:
                return new UpdateWeightCommand(userInput);
            case PARSER_CLEAR_COMMAND:
                return new ClearCommandParser().parse(userInput);
            case PARSER_EDIT_COMMAND:
                return new EditCommandParser().parse(userInput);
            case PARSER_SET_GOAL_COMMAND:
                return new SetgoalCommandParser().parse(userInput);
            case PARSER_HELP_COMMAND:
                return new HelpCommandParser().parse(userInput);
            case PARSER_HISTORY_COMMAND:
                // clear history if requested
                if (!userInput.isEmpty() && userInput.equals("clear")) {
                    historyCommand.clearHistory();
                }
                return historyCommand;
            case PARSER_SUGGEST_COMMAND:
                return new SuggestCommandParser().parse(userInput);
            default:
                throw new DukeException(UNKNOWN_COMMAND);
        }
    }
}
