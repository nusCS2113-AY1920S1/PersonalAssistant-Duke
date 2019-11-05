package commands;

import exceptions.FarmioFatalException;
import farmio.Farmio;
import farmio.Farmer;
import exceptions.FarmioException;
import frontend.Ui;
import usercode.tasks.Task;

public class CommandTaskCreate extends CommandChangeTask {
    private Task task;

    public CommandTaskCreate(Task task) {
        this.task = task;
    }

    /**
     * Creating a Task based on the interpretation of the parser.
     * @param farmio the game that contains the tasklist that is being changed.
     * @throws FarmioException if action is executed although its criteria is not met.
     * @throws FarmioFatalException if simulation file cannot be found.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        Ui ui = farmio.getUi();
        Farmer farmer = farmio.getFarmer();
        farmer.getTasks().addTask(task);
        super.saveTaskandResetScreen(farmio);
        ui.showInfo("Task [" + task.toString() + "] added! \nYou now have " + farmer.getTasks().size() + " tasks!");
    }
}
