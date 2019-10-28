package Commands;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import UserCode.Tasks.TemplateTask;

public class CommandTaskEdit extends Command {
    private int taskID;
    private String taskType;
    private String condition;
    private String action;


    public CommandTaskEdit(int taskID, TemplateTask templateTask) {
        this.taskID = taskID;
        this.taskType = templateTask.getTaskType();
        this.condition = templateTask.getCondition();
        this.action = templateTask.getAction();
    }

    /**
     * Edit a Task in the tasklist
     * @param farmio the game which contains the tasklist to be editted
     * @throws FarmioFatalException if the simulation file cannot be found
     */
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        System.out.println("test\r");
        farmio.getUi().getInput();
        System.out.println("exiting");
    }
}
