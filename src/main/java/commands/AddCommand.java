package commands;

import tasks.Task;
import utils.DukeException;
import utils.Parser;
import utils.Storage;
import core.Ui;


import java.util.ArrayList;

public class AddCommand extends Command {
    private String content;

    /**
     * This is a class for command of ADD, which add task to the task list.
     * @param content the command line String without the first ADD command
     */
    public AddCommand(String content) {
        this.content = content;
    }

    @Override
    public void execute(ArrayList<Task> tasks, Storage storage) throws DukeException {
        try {
            tasks.add(Parser.addCommand(content));
            storage.store(tasks);
            Ui.print("Got it. I've added this task: \n" + tasks.get(tasks.size() - 1)
                    + "\nNow you have " + tasks.size() + " tasks in the list.");
        } catch (DukeException e) {
            throw e;
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
