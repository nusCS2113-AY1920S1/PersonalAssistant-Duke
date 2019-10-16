package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.io.IOException;
import java.text.ParseException;

public class HelpCommand extends Command{
    public HelpCommand() {}
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, ParseException, DukeException {
        System.out.println("The format of the commands are as followed:\n");
        System.out.println("Words in UPPER_CASE are the parameters to be supplied by the user e.g. in todo DESCRIPTION, DESCRIPTION is a parameter which can be used as todo read book.");
        System.out.println("Items in square brackets are optional e.g DESCRIPTION [-FLAG] can be used as read book -r or as read book.");
        System.out.println("Items with ... after them can be used multiple times including zero times e.g. [-FLAG]... can be used as   (i.e. 0 times), -r, -f, etc.\n");
        System.out.println("Here are some commands you can use:\n");
        System.out.println("Adding a todo task: todo DESCRIPTION");
        System.out.println("Adding a event task:  event DESCRIPTION /at DATE");
        System.out.println("Adding a deadline task: deadline DESCRIPTION /by DATE");
        System.out.println("You may use a recurring flag -r weekly or -r daily when creating a task: e.g. todo spanish homework -r weekly");
        System.out.println("Listing all tasks: list");
        System.out.println("Editing a task: edit INDEX DESCRIPTION [/at DATE]");
        System.out.println("Locating task by name: find KEYWORD [MORE_KEYWORDS]");
        System.out.println("Deleting a task: delete INDEX");
        System.out.println("CLearing all entries: clear");
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
