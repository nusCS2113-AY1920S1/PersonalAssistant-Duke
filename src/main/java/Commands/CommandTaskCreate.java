package Commands;

import Farmio.Farmio;
import FarmioExceptions.FarmioException;
import UserCode.Conditions.Condition;
import UserCode.Tasks.Task;
import UserCode.Tasks.TaskList;

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
        //do nothing for now
    }
}
