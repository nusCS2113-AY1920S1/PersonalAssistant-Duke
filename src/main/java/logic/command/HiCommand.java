package logic.command;

import model.Model;
import tasks.Task;

public class HiCommand extends Command {
    private Task task;
    public static final String COMMAND_WORD = "hi";

    /**
     * This is a sample command to give a sensing how the new structure works
     * */
    @Override
    public CommandOutput execute(Model model) {
        return new CommandOutput("This is the hello message");
    }
}
