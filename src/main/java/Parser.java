import Commands.Command;
import Commands.ExitCommand;
import Commands.StartCommand;
import Commands.TestCommand;
import Places.ChickenFarm;
import Places.CowFarm;
import Places.WheatFarm;
import UserInterfaces.Ui;

public class Parser {
    Ui ui;
    WheatFarm wheatFarm;
    ChickenFarm chickenFarm;
    CowFarm cowFarm;

    public Parser(Ui ui, WheatFarm wheatFarm, ChickenFarm chickenFarm, CowFarm cowFarm) {
        this.ui = ui;
        this.wheatFarm = wheatFarm;
        this.chickenFarm = chickenFarm;
        this.cowFarm = cowFarm;
    }

    public Command parse(String fullCommand) {
        System.out.println("Received command " + fullCommand);
        if (fullCommand.equals("exit")) {
            return new ExitCommand();
        }
        if (fullCommand.equals("start")) {
            return new StartCommand();
        }
        if (fullCommand.equals("test")) {
            return new TestCommand(ui, wheatFarm, chickenFarm, cowFarm);
        }
        return new ExitCommand();
    }
}
