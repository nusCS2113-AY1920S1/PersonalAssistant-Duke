import Commands.Command;
import Commands.ExitCommand;
import Commands.StartCommand;
import Commands.TestCommand;

public class Parser {
    public Command parse(String fullCommand) {
        System.out.println("Received command " + fullCommand);
        if (fullCommand.equals("exit")) {
            return new ExitCommand();
        }
        if (fullCommand.equals("start")) {
            return new StartCommand();
        }
        if (fullCommand.equals("test")) {
            return new TestCommand();
        }
        return new ExitCommand();
    }
}
