import Commands.Command;
import Commands.ExitCommand;
import Commands.StartCommand;

public class Parser {
    public Command parse(String fullCommand) {
        System.out.println("Received command " + fullCommand);
        if (fullCommand.equals("exit")) {
            return new ExitCommand();
        }
        if (fullCommand.equals("start")) {
            return new StartCommand();
        }
        return new ExitCommand();
    }
}
