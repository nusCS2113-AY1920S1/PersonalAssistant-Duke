package Commands;

import Farmio.Farmio;
import FarmioExceptions.FarmioException;
import Places.Farm;
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
        try {
            Task task;
            switch (taskType) {
                case "do":
                    task = new DoTask(Condition.toCondition(condition), Action.stringToAction(action, farmio));
                    break;
                case "if":
                    task = new IfTask(Condition.toCondition(condition), Action.stringToAction(action, farmio));
                    break;
                case "while":
                    task = new WhileTask(Condition.toCondition(condition), Action.stringToAction(action, farmio));
                    break;
                case "for":
                    task = new ForTask(Condition.toCondition(condition), Action.stringToAction(action, farmio));
                default:
                    throw new FarmioException("Error Creating Task!");
            }
            farmio.getFarmer().getTasks().addTask(task);
            farmio.getUi().showInfo("You now have " + farmio.getFarmer().getTasks().size() + " tasks!");
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
