package main.java.UserElements;

/**
 * Contains method to parse the user input and interpret it for Model_Class.Command class
 */
public class Parser {

    /**
     * Creates new Model_Class.Parser object.
     */
    public Parser() {
    }

    /**
     * Parses user input to extract command.
     *
     * @param userInput String containing user input
     * @return Model_Class.Command object after parsing input
     */
    public Command parseInput(String userInput) {
        String command;
        String continuation;
        if (userInput.contains(" ")) {
            int indexOfSpace = userInput.indexOf(' ');
            command = userInput.substring(0, indexOfSpace);
            continuation = userInput.substring(indexOfSpace + 1);
            return new Command(command, continuation);
        } else {
            return new Command(userInput);
        }
    }
}