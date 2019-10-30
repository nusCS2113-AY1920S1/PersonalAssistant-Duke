package logic.command;

import model.Model;
import common.DukeException;

public class DeleteTaskCommand extends Command {
    private static final String SUCCESS_MSSAGE = "you have removed a task: ";
    private static final String INVALID_MSSAGE = "invalid task index";
    private static final String FAIL_MSSAGE = "fail to delete task";
    private int taskIndexInList;
    private String taskName;

    public DeleteTaskCommand(int index) {
        this.taskIndexInList = index;
    }

    @Override
    public CommandOutput execute(Model model) throws DukeException {
        try {
            if (taskIndexInList > model.getTaskListSize() || taskIndexInList < 1) {
                return new CommandOutput(INVALID_MSSAGE);
            } else {
                taskName = model.deleteTask(taskIndexInList);
                return new CommandOutput(SUCCESS_MSSAGE + taskName);
            }
        } catch (Exception e) {
            throw new DukeException(FAIL_MSSAGE);
        }

    }

}
