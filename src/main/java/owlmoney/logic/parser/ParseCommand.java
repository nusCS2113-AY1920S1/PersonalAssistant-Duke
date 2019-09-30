package owlmoney.logic.parser;

import java.util.Scanner;

import owlmoney.logic.parser.exception.ParserException;
import owlmoney.model.profile.Profile;

public class ParseCommand extends Parser {
    ParseType parseType = new ParseType();
    private final Scanner scanner = new Scanner(System.in);

    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    public void parseLine(Profile profile) throws ParserException {
        String input = scanner.nextLine();
        parseIsBlank(input);
        String command = parseFirstField(input);
        String data = removeFirstField(input,command);
        parseCommandMenu(command, data, profile);
    }

    private void parseIsBlank(String input) throws ParserException {
        if (input.isBlank() || input.isEmpty()) {
            throw new ParserException("Input cannot be blank or space-bar only");
        }
    }

    //for now is pass profile all the way in. Double check if is correct structure
    private void parseCommandMenu(String command, String data, Profile profile) throws ParserException{
        switch (command) {
        case "/add":
            System.out.println("You added");
            parseType.parseData(command, data, profile);
            break;
        case "/delete":
            System.out.println("You deleted");
            parseType.parseData(command, data, profile);
            break;
        case "/edit":
            System.out.println("You edited");
            parseType.parseData(command, data, profile);
            break;
       /* case "/test": //for testing of output
            profile.listBanks();
            break;
        */
        /*case "/test": //for testing of output
            profile.listMyExpenditure();
            break;
         */
        case "/exit":
            System.exit(0);
            break;
        default:
            System.out.println("You entered an invalid command");
            break;
        }
    }
}
