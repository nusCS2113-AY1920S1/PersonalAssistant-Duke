package owlmoney.logic.parser;

import java.util.Scanner;

import owlmoney.logic.command.Command;
import owlmoney.logic.parser.exception.ParserException;

public class ParseCommand extends Parser {
    ParseType parseType = new ParseType();
    private final Scanner scanner = new Scanner(System.in);

    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    public Command parseLine() throws ParserException {
        String input = scanner.nextLine();
        parseIsBlank(input);
        String command = parseFirstField(input);
        String data = removeFirstField(input,command);
        return parseCommandMenu(command, data);
    }

    private void parseIsBlank(String input) throws ParserException {
        if (input.isBlank() || input.isEmpty()) {
            throw new ParserException("Input cannot be blank or space-bar only");
        }
    }

    //for now is pass profile all the way in. Double check if is correct structure
    private Command parseCommandMenu(String command, String data) throws ParserException{
        switch (command) {
        case "/add":
            System.out.println("You added");
            return parseType.parseData(command, data);
        case "/delete":
            System.out.println("You deleted");
            return parseType.parseData(command, data);
        case "/edit":
            System.out.println("You edited");
            return parseType.parseData(command, data);
        case "/list":
            System.out.println("You listed");
            return parseType.parseData(command, data);
        /*case "/test": //for testing of output
            profile.listMyExpenditure();
            break;
         */
        case "/exit":
            System.exit(0);
        default:
            System.out.println("You entered an invalid command");
            return parseType.parseData(command, data);
        }
    }
}
