/**
 * Contains method to parse the user input and interpret it for Command class
 */
public class Parser {

    /**
     * Creates new Parser object.
     */
    public Parser(){}

    /**
     * Parses user input to extract command.
     * @param userInput String containing user input
     * @return Command object after parsing input
     */
    public Command parseInput(String userInput){
        String command;
        String continuation;
        if (userInput.contains(" ")){
            int indexOfSpace = userInput.indexOf(' ');
            command = userInput.substring(0, indexOfSpace);
            continuation = userInput.substring(indexOfSpace + 1);
            return new Command(command, continuation);
        } else {
            return new Command(userInput);
        }
    }
}
