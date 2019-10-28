package UserCode.Tasks;

import Exceptions.FarmioException;
import Farmio.Farmio;
import FrontEnd.Ui;
import UserCode.Actions.Action;
import UserCode.Conditions.Condition;
import Farmio.Storage;
import Farmio.Farmer;

public class TemplateTask {
    private String taskType;
    private String condition;
    private String action;

    public TemplateTask(String t, String c, String a) {
        this.taskType = t;
        this.condition = c;
        this.action = a;
    }

    public Task toTask(Farmio farmio) throws FarmioException {
        Ui ui = farmio.getUi();
        Storage storage = farmio.getStorage();
        Farmer farmer = farmio.getFarmer();
        Task task;
        try {
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
        } catch (Exception e) {
            throw new FarmioException("error creating task!");
        }
        return task;
    }

    public String getTaskType() { return this.taskType; }

    public String getCondition() { return this.condition; }

    public String getAction() { return this.action; }
}
