package owlmoney.logic.parser;

import java.util.Scanner;

import owlmoney.logic.parser.exception.ParserException;

public class ParseCommand extends Parser {
    ParseType parseType = new ParseType();
    private final Scanner scanner = new Scanner(System.in);

    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    public void parseLine() throws ParserException {
        String input = scanner.nextLine();
        parseIsBlank(input);
        String command = parseFirstField(input);
        String data = removeFirstField(input,command);
        parseCommandMenu(command, data);
    }

    private void parseIsBlank(String input) throws ParserException {
        if (input.isBlank() || input.isEmpty()) {
            throw new ParserException("Input cannot be blank or space-bar only");
        }
    }

    private void parseCommandMenu(String command, String data) throws ParserException{
        switch (command) {
        case "/add":
            System.out.println("You added");
            parseType.parseData(command, data);
            break;
        case "/delete":
            System.out.println("You deleted");
            parseType.parseData(command, data);
            break;
        case "/edit":
            System.out.println("You edited");
            break;
        case "/exit":
            System.exit(0);
            break;
        default:
            System.out.println("You entered an invalid command");
            break;
        }
    }
}
