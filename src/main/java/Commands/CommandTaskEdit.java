package Commands;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import FrontEnd.Ui;
import UserCode.Tasks.Task;
import UserCode.Tasks.TemplateTask;
import Farmio.Storage;
import Farmio.Farmer;

public class CommandTaskEdit extends Command {
    private TemplateTask templateTask;
    private int taskID;


    public CommandTaskEdit(int taskID, TemplateTask templateTask) {
        this.taskID = taskID;
        this.templateTask = templateTask;
    }

    /**
     * Edit a Task in the tasklist
     * @param farmio the game which contains the tasklist to be editted
     * @throws FarmioFatalException if the simulation file cannot be found
     */
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        Ui ui = farmio.getUi();
        Task task = templateTask.toTask(farmio);
        Storage storage = farmio.getStorage();
        Farmer farmer = farmio.getFarmer();
        if (taskID < 1 || taskID > farmer.getTasks().size()) {
            throw new FarmioException("Invalid Task ID!");
        }
        farmer.getTasks().editTask(taskID, task);
        farmio.getSimulation().simulate(farmio.getLevel().getPath(), farmio.getLevel().getNarratives().size() - 1);
        ui.showInfo("Successfully edited task!");
    }
}
