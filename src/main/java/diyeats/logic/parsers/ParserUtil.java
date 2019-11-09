package diyeats.logic.parsers;

import diyeats.commons.exceptions.ProgramException;
import diyeats.logic.autocorrect.Autocorrect;
import diyeats.logic.commands.HistoryCommand;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

//@@author Fractalisk
/**
 * Utility class to handle pre-parsing of user inputs.
 */
public class ParserUtil {
    private Autocorrect autocorrect;
    private HistoryCommand history = new HistoryCommand();
    private String command;
    private String argument;

    /**
     * Constructor for ParserUtil.
     * @param autocorrect the autocorrect object to be set
     */
    public ParserUtil(Autocorrect autocorrect) {
        this.autocorrect = autocorrect;
    }

    /**
     * Parse fullCommand into command and arguments.
     * @param fullCommand the full command entered by the user
     * @throws ProgramException if the full command cannot be parsed
     */
    public void parse(String fullCommand) throws ProgramException {
        argument = "";
        try {
            String[] splitCommand = fullCommand.split(" ", 2);
            if (splitCommand.length != 2) {
                splitCommand = new String[]{splitCommand[0], ""};
            }
            command = splitCommand[0];
            argument = splitCommand[1];
        } catch (Exception e) {
            throw new ProgramException("A parser error has been encountered.");
        }
        command = autocorrect.runOnCommand(command);
        argument = autocorrect.runOnArgument(argument);
        history.addCommand(command);
    }

    /**
     * Getter for command.
     * @return command The string containing the command made by the user.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Getter for argument.
     * @return argument The string containing the arguments made by the user.
     */
    public String getArgument() {
        return argument;
    }

    /**
     * Getter for history.
     * @param userInput the user input to be parsed
     * @return <code>history</code> the object containing records of all the past commands taken
     */
    public HistoryCommand getHistory(String userInput) {
        // clear history if requested
        if (!userInput.isEmpty() && userInput.equals("clear")) {
            history.clearHistory();
        }
        return history;
    }
}
