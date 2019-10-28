package Commands;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;

public class CommandTaskDelete extends Command {
    int taskID;

    public CommandTaskDelete (int taskID) {
        this.taskID = taskID;
    }

    /**
     * Delete a specific task from the task list
     * @param farmio the game with the tasklist to be editted
     * @throws FarmioException if TaskID is invalid
     * @throws FarmioFatalException if simulation file cannot be found
     */
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        if (taskID < 1 || taskID > farmio.getFarmer().getTasks().size()) {
            throw new FarmioException("Invalid TaskID!");
        }
        String taskToString = farmio.getFarmer().getTasks().removeTask(taskID);
        farmio.getSimulation().simulate(farmio.getLevel().getPath(), farmio.getLevel().getNarratives().size());
        farmio.getUi().showInfo("You have deleted task: " + taskToString);
    }
}
