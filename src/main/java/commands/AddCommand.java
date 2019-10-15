package commands;

import members.Member;
import core.Ui;
import tasks.Task;
import utils.DukeException;
import utils.Parser;
import utils.Storage;

import java.util.ArrayList;

/**
 * This class is to handle "add" command
 */
public class AddCommand extends Command {
    private String content;

    /**
     * This is a class for command of ADD, which add task to the task list.
     *
     * @param content the command line String without the first ADD command
     */
    public AddCommand(String content) {
        this.content = content;
    }

    @Override
    public void execute(ArrayList<Task> tasks, ArrayList<Member> members, Storage storage) throws DukeException {
        try {
            Task temp = Parser.addCommand(content);
            if (content.contains("/after")) {
                String preconditionString = content.split("/after", 2)[1].trim();
                temp.setDescription(content.split("/after", 2)[0].trim().split(" ")[1]);
                temp.addPrecondition(preconditionString);
            }
            tasks.add(temp);
            storage.storeTaskList(tasks);
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
