package commands;

import members.Member;
import tasks.Task;
import utils.DukeException;
import utils.Storage;
import core.Ui;

import java.util.ArrayList;

/**
 * This class is to handle "find" command
 */
public class FindCommand extends Command {
    private String line;

    /**
     * This is a class for command FIND, which search a keyword from the task list and print all results.
     * @param line the keyword to be searched
     */
    public FindCommand(String line) {
        this.line = line.trim();
    }

    @Override
    public void execute(ArrayList<Task> tasks, ArrayList<Member> members, Storage storage) throws DukeException {
        String output = "Here are the matching tasks in your list:";
        int resultCount = 1;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getDescription().contains(line)) {
                output += "\n" + (resultCount++) + "." + tasks.get(i);
            }
        }
        Ui.print(output);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
