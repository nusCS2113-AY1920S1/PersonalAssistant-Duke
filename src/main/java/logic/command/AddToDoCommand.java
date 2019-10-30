package logic.command;

import model.Model;
import model.task.ToDo;

public class AddToDoCommand extends Command {
    public static final String COMMAND_WORD = "todo";
    private String arguments;

    public AddToDoCommand(String arguments) {
        this.arguments = arguments;
    }


    @Override
    public CommandOutput execute(Model model) {
        ToDo todo = new ToDo(arguments);
        model.addToDo(todo);
        return new CommandOutput("you have added a todo");
    }
}
