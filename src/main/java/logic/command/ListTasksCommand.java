package logic.command;

import model.Model;
import model.task.Task;

import java.util.ArrayList;

public class ListTasksCommand extends Command{
    public static final String COMMAND_WORD = "tasks";
    private String arguments;

    public ListTasksCommand(String arguments){
        this.arguments = arguments;
    }

    @Override
    public CommandOutput execute(Model model) {
        return new CommandOutput(convertArrayListToText(model.getTaskList()));
    }

    public String convertArrayListToText(ArrayList<Task> tasks){
        return "Hi";
    }

}
