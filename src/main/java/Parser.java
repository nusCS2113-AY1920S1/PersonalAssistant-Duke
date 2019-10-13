import Commands.Command;
import Commands.ExitCommand;
import Commands.StartCommand;
import Commands.TestCommand;
import Places.ChickenFarm;
import Places.CowFarm;
import Places.Market;
import Places.WheatFarm;
import UserCode.Conditions.ConditionChecker;
import UserCode.Tasks.TaskList;
import UserInterfaces.Ui;

public class Parser {
    Ui ui;
    TaskList tasks;
    Farmer farmer;
    WheatFarm wheatFarm;
    ChickenFarm chickenFarm;
    CowFarm cowFarm;
    Market market;
    ConditionChecker conditionChecker;

    public Parser(Ui ui, Farmer farmer, ConditionChecker conditionChecker) {
        this.ui = ui;
        this.tasks = farmer.tasks;
        this.farmer = farmer;
        this.wheatFarm = farmer.wheatFarm;
        this.chickenFarm = farmer.chickenFarm;
        this.cowFarm = farmer.cowFarm;
        this.market = farmer.market;
        this.conditionChecker = conditionChecker;
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
            return new TestCommand(ui, tasks, wheatFarm, chickenFarm, cowFarm, market, conditionChecker);
        }
        return new ExitCommand();
    }
}
