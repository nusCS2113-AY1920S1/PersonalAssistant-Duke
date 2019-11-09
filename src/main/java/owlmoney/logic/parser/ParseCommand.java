package owlmoney.logic.parser;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.ExitCommand;
import owlmoney.logic.command.UpdateCommand;
import owlmoney.logic.command.help.HelpCommand;
import owlmoney.logic.parser.exception.ParserException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Represents the first instance of parsing user input.
 * This determines what type of command the user has entered.
 */
public class ParseCommand extends Parser {
    private ParseType parseType = new ParseType();
    private final Scanner scanner = new Scanner(System.in);
    private static final String HELP_COMMAND = "/help";
    private static final String UNDO_COMMAND = "/undo";
    private static final String ADD_COMMAND = "/add";
    private static final String EDIT_COMMAND = "/edit";
    private static final String DELETE_COMMAND = "/delete";
    private static final String LIST_COMMAND = "/list";
    private static final String FIND_COMMAND = "/find";
    private static final String TRANSFER_COMMAND = "/transfer";
    private static final String EXIT_COMMAND = "/exit";
    private static final String UPDATE_COMMAND = "/update";
    private static final String[] COMMAND_KEYWORDS = new String[] {
        HELP_COMMAND, UNDO_COMMAND, ADD_COMMAND,
        EDIT_COMMAND, DELETE_COMMAND, LIST_COMMAND,
        FIND_COMMAND, TRANSFER_COMMAND, EXIT_COMMAND, UPDATE_COMMAND};
    private static final List<String> COMMAND_KEYWORD_LISTS = Arrays.asList(COMMAND_KEYWORDS);

    /**
     * Checks if there are any more user input if using I/O redirection.
     *
     * @return a boolean true when there are more inputs and false when no more input is detected.
     */
    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    /**
     * Takes in the user input and checks if it is blank first before extracting the command.
     * The command extracted is then check against a whitelist before removing it from the input.
     * The command then determines which command to execute.
     *
     * @return a Command object that is required to be executed.
     * @throws ParserException if command is not in the whitelist.
     */
    public Command parseLine() throws ParserException {
        String input = scanner.nextLine();
        parseIsBlank(input);
        String command = parseFirstField(input);
        if (!COMMAND_KEYWORD_LISTS.contains(command)) {
            throw new ParserException(command + " is an invalid command");
        }
        String data = removeFirstField(input, command);
        return parseCommandMenu(command, data);
    }

    /**
     * Checks if the user input is full of spaces or is empty.
     *
     * @param input The user input.
     * @throws ParserException if it is blank or full of spaces.
     */
    private void parseIsBlank(String input) throws ParserException {
        if (input == null || input.isBlank()) {
            throw new ParserException("Input cannot be blank or space-bar only");
        }
    }

    /**
     * The command menu determines what type of command to execute and pass to parseType.
     *
     * @param command The command extracted with parseFirstField.
     * @param data    The data that has command removed from the first field.
     * @return The Command object that is required to be executed.
     * @throws ParserException When an invalid command is detected.
     */
    private Command parseCommandMenu(String command, String data) throws ParserException {
        switch (command) {
        case ADD_COMMAND:
            // Fallthrough
        case DELETE_COMMAND:
            // Fallthrough
        case EDIT_COMMAND:
            // Fallthrough
        case TRANSFER_COMMAND:
            // Fallthrough
        case FIND_COMMAND:
            // Fallthrough
        case LIST_COMMAND:
            return parseType.parseData(command, data);
        case EXIT_COMMAND:
            if (!data.isBlank()) {
                throw new ParserException("/exit cannot have trailing arguments");
            }
            return new ExitCommand();
        case UPDATE_COMMAND:
            if (!data.isBlank()) {
                throw new ParserException("/update cannot have trailing arguments");
            }
            return new UpdateCommand(true);
        case HELP_COMMAND:
            if (!data.isBlank()) {
                throw new ParserException("/help cannot have trailing arguments");
            }
            return new HelpCommand();
        default:
            throw new ParserException("You entered an invalid command");
        }
    }
}
