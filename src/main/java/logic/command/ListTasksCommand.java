package logic.command;

import model.Model;
import model.Task;

import java.util.ArrayList;

import gui.Window;

public class  ListTasksCommand extends Command {
    public static final String COMMAND_WORD = "tasks";
    public static final String EMPTY_TASKS_LIST = "There are currently no tasks in project manager";
    //private String arguments;

    //public ListTasksCommand(String arguments) {
        //this.arguments = arguments;
    //}

    @Override
    public CommandOutput execute(Model model) {
        Window.instance.showTaskView(true);
        return new CommandOutput(convertArrayListToText(model.getTaskList()));
    }

    //@@author chenyuheng

    /**
     * Convert to displayable text format
     */
    public String convertArrayListToText(ArrayList<Task> tasks) {
        String finalOutput = "";
        if (tasks.size() > 0) {
            for (int i = 0; i < tasks.size(); i += 1) {
                Task task = tasks.get(i);
                finalOutput += (task.isDone() ? "[\u2713] " : "[\u2715] ")
                        + (i + 1) + ": "
                        + task.getName() + "\n"
                        + (task.getTime() != null ? "Time: " + task.getTime() + "\n" : "")
                        + (task.getMemberList().size() != 0
                        ? "Assigned to: " + task.getMemberList().toString() + "\n" : "")
                        + "\n";
            }
        } else {
            finalOutput = EMPTY_TASKS_LIST;
        }
        return finalOutput;
    }

}
