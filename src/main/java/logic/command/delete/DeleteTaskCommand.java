package logic.command.delete;

import logic.command.Command;
import logic.command.CommandOutput;
import model.Model;
import common.DukeException;

public class DeleteTaskCommand extends Command {
    private static final String SUCCESS_MSSAGE = "You have removed a task: ";

    private int[] taskIndexInList;
    private String taskName;

    public DeleteTaskCommand(int[] taskIndexInList) {
        this.taskIndexInList = taskIndexInList;
    }

    //@@author yuyanglin28

    /**
     * This method is to delete tasks with task index in list
     * @param model Model interface
     * @return successful message or index doesn't in task list message
     */
    @Override
    public CommandOutput execute(Model model) {
        String output = "";
        for (int i = 0; i < taskIndexInList.length; i++) {
            if (checkTaskIndex(taskIndexInList[i], model)) {
                taskName = model.deleteTask(taskIndexInList[i] - 1);
                model.save();
                output += SUCCESS_MSSAGE + taskIndexInList[i] + ". " + taskName + "\n";
            } else {
                output += taskIndexInList[i] + Command.INDEX_NOT_IN_TASK_MESSAGE + "\n";
            }

        }
        return new CommandOutput(output);


    }

}
