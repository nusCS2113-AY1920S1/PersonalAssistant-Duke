package commands;

import exceptions.FarmioException;
import exceptions.FarmioFatalException;
import farmio.Farmio;
import frontend.Ui;
import usercode.tasks.Task;
import farmio.Farmer;

public class CommandTaskEdit extends CommandChangeTask {
    private Task task;
    private int taskID;


    public CommandTaskEdit(int taskID, Task task) {
        this.taskID = taskID;
        this.task = task;
    }

    /**
     * Edit a Task in the tasklist.
     * @param farmio the game which contains the tasklist to be editted.
     * @throws FarmioFatalException if the simulation file cannot be found.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        Farmer farmer = farmio.getFarmer();
        if (taskID < 1 || taskID > farmer.getTasks().size()) {
            throw new FarmioException("Invalid Task ID!");
        }
        farmer.getTasks().editTask(taskID, task);
        super.saveTaskandResetScreen(farmio);
        Ui ui = farmio.getUi();
        ui.showInfo("Successfully edited task!");
    }
}
