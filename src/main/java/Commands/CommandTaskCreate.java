package Commands;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Farmio.Storage;
import Farmio.Farmer;
import Exceptions.FarmioException;
import FrontEnd.Ui;
import UserCode.Actions.Action;
import UserCode.Conditions.Condition;
import UserCode.Tasks.*;

public class CommandTaskCreate extends Command {
    private TemplateTask templateTask;

    public CommandTaskCreate(TemplateTask templateTask) {
        this.templateTask = templateTask;
    }

    /**
     * Creating a Task based on the interpretation of the parser
     * @param farmio the game that contains the tasklist that is being changed
     * @throws FarmioException if action is executed although its criteria is not met
     * @throws FarmioFatalException if simulation file cannot be found
     */
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        Ui ui = farmio.getUi();
        Task task = templateTask.toTask(farmio);
        Storage storage = farmio.getStorage();
        Farmer farmer = farmio.getFarmer();
        farmer.getTasks().addTask(task);
        farmio.getSimulation().simulate(farmio.getLevel().getPath(), farmio.getLevel().getNarratives().size() - 1);
        ui.showInfo("Task [" + task.toString() + "] added! \nYou now have " + farmer.getTasks().size() + " tasks!");
    }
}
