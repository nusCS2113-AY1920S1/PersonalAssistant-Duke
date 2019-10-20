package Commands;

import Farmio.Farmio;
import Farmio.Storage;
import Farmio.Farmer;
import Exceptions.FarmioException;
import FrontEnd.Simulation;
import FrontEnd.Ui;
import UserCode.Actions.Action;
import UserCode.Conditions.Condition;
import UserCode.Tasks.*;

//IN PROGRESS

public class CommandTaskCreate extends Command {
    private String taskType;
    private String condition;
    private String action;

    public CommandTaskCreate(String taskType, String condition, String action) {
        this.taskType = taskType;
        this.condition = condition;
        this.action = action;
    }

    @Override
    public void execute(Farmio farmio) throws FarmioException {
        Ui ui = farmio.getUi();
        Storage storage = farmio.getStorage();
        Farmer farmer = farmio.getFarmer();
        try {
            Task task;
            switch (taskType) {
                case "do":
                    task = new DoTask(Condition.toCondition(condition), Action.stringToAction(action));
                    break;
                case "if":
                    task = new IfTask(Condition.toCondition(condition), Action.stringToAction(action));
                    break;
                case "while":
                    task = new WhileTask(Condition.toCondition(condition), Action.stringToAction(action));
                    break;
                case "for":
                    task = new ForTask(Condition.toCondition(condition), Action.stringToAction(action));
                default:
                    throw new FarmioException("Error Creating Task!");
            }
            farmer.getTasks().addTask(task);
            Simulation.animate(ui, storage, farmer, "TaskCreate", 0);
            ui.showInfo("Task [" + task.toString() + "] added! \nYou now have " + farmer.getTasks().size() + " tasks!");
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
