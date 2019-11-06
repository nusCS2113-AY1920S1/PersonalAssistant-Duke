package commands;

import exceptions.FarmioException;
import exceptions.FarmioFatalException;
import farmio.Farmio;

public class CommandTaskDelete extends CommandChangeTask {
    int taskID;

    public CommandTaskDelete(int taskID) {
        this.taskID = taskID;
    }

    /**
     * Delete a specific task from the task list.
     * @param farmio the game with the tasklist to be editted.
     * @throws FarmioException if TaskID is invalid, or if there is error deleting task.
     * @throws FarmioFatalException if simulation file cannot be found.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        if (taskID < 1 || taskID > farmio.getFarmer().getTasks().size()) {
            throw new FarmioException("Invalid TaskID!");
        }
        try {
            String taskToString = farmio.getFarmer().getTasks().deleteTask(taskID);
            super.saveTaskandResetScreen(farmio);
            farmio.getUi().showInfo("You have deleted task: " + taskToString);
        } catch (Exception e) {
            throw new FarmioException("Error deleting task!");
        }
    }
}
