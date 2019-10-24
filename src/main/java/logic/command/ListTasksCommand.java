package logic.command;

import model.Model;
import model.task.Task;

import java.util.ArrayList;

public class ListTasksCommand extends Command {
    public static final String COMMAND_WORD = "tasks";
    public static final String EMPTY_TASKS_LIST = "There are currently no tasks in project manager";
    private String arguments;

    public ListTasksCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public CommandOutput execute(Model model) {
        return new CommandOutput(convertArrayListToText(model.getTaskList()));
    }

    //@@author JustinChia1997
    public String convertArrayListToText(ArrayList<Task> tasks) {
        String finalOutput = "";
        if (tasks.size() > 0) {
            for (int i = 0; i < tasks.size(); i += 1) {
                finalOutput += String.valueOf(i+1) + " : " + "Task Name:"
                        + tasks.get(i).getName().toString() + "\n";
            }
        } else {
            finalOutput = EMPTY_TASKS_LIST;
        }
        return finalOutput;
    }

}
