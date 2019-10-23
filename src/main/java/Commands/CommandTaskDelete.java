package Commands;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import UserCode.Tasks.Task;

public class CommandTaskDelete extends Command {
    int taskID;

    public CommandTaskDelete (int taskID) {
        this.taskID = taskID;
    }

    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        if (taskID < 1 || taskID > farmio.getFarmer().getTasks().size()) {
            throw new FarmioException("Invalid TaskID!");
        }
        farmio.getUi().showInfo("You have deleted task: " + farmio.getFarmer().getTasks().removeTask(taskID));
        farmio.getSimulation().animate(farmio.getLevel().getPath(), farmio.getLevel().getNarratives().size());
    }
}
