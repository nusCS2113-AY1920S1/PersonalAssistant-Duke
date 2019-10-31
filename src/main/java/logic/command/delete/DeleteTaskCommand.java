package logic.command.delete;

import logic.command.Command;
import logic.command.CommandOutput;
import model.Model;
import common.DukeException;

public class DeleteTaskCommand extends Command {
    private static final String SUCCESS_MSSAGE = "you have removed a task: ";
    private static final String INVALID_MSSAGE = " is an invalid task index";
    private static final String FAIL_MESSAGE = "fail to delete task";
    private int[] taskIndexInList;
    private String taskName;

    public DeleteTaskCommand(int[] taskIndexInList) {
        this.taskIndexInList = taskIndexInList;
    }

    @Override
    public CommandOutput execute(Model model) throws DukeException {
        String output = "";
        try {
            for (int i = 0; i < taskIndexInList.length; i++) {
                if (checkTaskIndex(taskIndexInList[i], model)) {
                    taskName = model.deleteTask(taskIndexInList[i] - 1);
                    model.save();
                    output += SUCCESS_MSSAGE + taskName + "\n";
                } else {
                    output += taskIndexInList[i] + INVALID_MSSAGE + "\n";
                }

            }
            return new CommandOutput(output);

        } catch (Exception e) {
            throw new DukeException(FAIL_MESSAGE);
        }

    }

}
