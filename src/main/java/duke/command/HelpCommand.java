package duke.command;

import java.io.IOException;
import java.text.ParseException;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.storage.UndoStack;
import duke.tasklist.TaskList;
import duke.ui.Ui;

public class HelpCommand extends Command {
    public HelpCommand() {
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, ParseException, DukeException {
        System.out.println("Here is a summary of what you can do in El Duque. For a more detailed explanation, please" +
                "read the User Guide\n");

        System.out.println("help | Shows a summary of commands that are available and how they can be used\n");

        System.out.println("task | Creates a task");
        System.out.println("e.g. task finish spanish tutorial -t 031120\n");

        System.out.println("event | Creates an event");
        System.out.println("e.g. event spanish lesson -t 031119\n");

        System.out.println("list | Lists existing tasks and events");
        System.out.println("e.g. list");
        System.out.println("e.g. list day");
        System.out.println("e.g. list week\n");

        System.out.println("edit | Edits an existing task or event");
        System.out.println("e.g. edit 3 -description bring ahma for checkup\n");

        System.out.println("delete | Delete an existing task or event");
        System.out.println("e.g. delete 3\n");

        System.out.println("find | Finds existing task(s) and event(s) that contain the query");
        System.out.println("e.g. find homework\n");

        System.out.println("autoassign | Suggest filters to assign a task/event");
        System.out.println("e.g. autoassign 3");

        System.out.println("bye | Exits the program\n");

        System.out.println("Understanding how filters work:");
        System.out.println("A filter is like a sublist. Most command can begin with a filter. E.g. -cs2113 .....");
        System.out.println("When you use a filter, the index in the command is RELATIVE to the filtered list!");
        System.out.println("-cs2113 delete 1    CAN BE EQUAL TO    delete 3");
    }

    /**
     * Not applicable for this Command.
     * @param tasks NA
     * @param undoStack NA
     * @throws DukeException NA
     */
    @Override
    public void savePrevState(TaskList tasks, UndoStack undoStack) {

    }

}
