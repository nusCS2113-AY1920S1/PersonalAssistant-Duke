package owlmoney.logic.parser;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.ExitCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Represents the first instance of parsing user input.
 * This determines what type of command the user has entered.
 */

public class ParseCommand extends Parser {
    private ParseType parseType = new ParseType();
    private final Scanner scanner = new Scanner(System.in);
    private static final String[] COMMAND_KEYWORDS = new String[] {
        "/help", "/undo", "/add", "/edit", "/delete", "/list", "/find", "/transfer", "/exit"
    };
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
        if (input.isBlank() || input.isEmpty()) {
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
        case "/add":
            // Fallthrough
        case "/delete":
            // Fallthrough
        case "/edit":
            // Fallthrough
        case "/list":
            return parseType.parseData(command, data);
        case "/exit":
            return new ExitCommand();
        default:
            throw new ParserException("You entered an invalid command");
        }
    }
}
