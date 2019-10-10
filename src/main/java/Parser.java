import Commands.Command;
import Commands.ExitCommand;
import Commands.StartCommand;
import Commands.TestCommand;
import Places.ChickenFarm;
import Places.CowFarm;
import Places.WheatFarm;
import Task.Task;
import Task.TaskList;
import UserInterfaces.Ui;

public class Parser {
    Ui ui;
    TaskList tasks;
    WheatFarm wheatFarm;
    ChickenFarm chickenFarm;
    CowFarm cowFarm;

    public Parser(Ui ui, TaskList tasks, WheatFarm wheatFarm, ChickenFarm chickenFarm, CowFarm cowFarm) {
        this.ui = ui;
        this.tasks = tasks;
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
            return new TestCommand(ui, tasks, wheatFarm, chickenFarm, cowFarm);
        }

        return new ExitCommand();
    }
}
