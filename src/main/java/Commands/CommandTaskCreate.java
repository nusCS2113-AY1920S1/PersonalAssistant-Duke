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
    private String taskType;
    private String condition;
    private String action;

    public CommandTaskCreate(String taskType, String condition, String action) {
        this.taskType = taskType;
        this.condition = condition;
        this.action = action;
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
        Storage storage = farmio.getStorage();
        Farmer farmer = farmio.getFarmer();
        try {
            Task task;
            switch (taskType) {
                case "do":
                    task = new DoTask(Condition.toCondition(condition), Action.toAction(action));
                    break;
                case "if":
                    task = new IfTask(Condition.toCondition(condition), Action.toAction(action));
                    break;
                case "while":
                    task = new WhileTask(Condition.toCondition(condition), Action.toAction(action));
                    break;
                case "for":
                    task = new ForTask(Condition.toCondition(condition), Action.toAction(action));
                default:
                    throw new FarmioException("Error Creating Task!");
            }
            farmer.getTasks().addTask(task);
            farmio.getSimulation().simulate(farmio.getLevel().getPath(), farmio.getLevel().getNarratives().size() - 1);
            ui.showInfo("Task [" + task.toString() + "] added! \nYou now have " + farmer.getTasks().size() + " tasks!");
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
