package Commands;

import FarmioExceptions.FarmioException;
import Places.ChickenFarm;
import Places.CowFarm;
import Places.Market;
import Places.WheatFarm;
import UserCode.Conditions.ConditionChecker;
import UserCode.Tasks.TaskList;
import UserInterfaces.Ui;

public abstract class Command {
    protected boolean isExit = false;
    protected boolean isStart = false;
    Ui ui;
    TaskList tasks;
    WheatFarm wheatFarm;
    ChickenFarm chickenFarm;
    CowFarm cowFarm;
    Market market;
    ConditionChecker conditionChecker;
    public boolean getIsExit() {
        return isExit;
    }
    public boolean getIsStart() { return isStart; }
    public abstract void execute() throws FarmioException;
}
