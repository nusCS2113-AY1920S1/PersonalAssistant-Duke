import Commands.Command;
import Commands.ExitCommand;

public class Parser {
    public Command parse(String fullCommand) {
        if (fullCommand.equals("dummysring")) {
        }
        return new ExitCommand();
    }
}
