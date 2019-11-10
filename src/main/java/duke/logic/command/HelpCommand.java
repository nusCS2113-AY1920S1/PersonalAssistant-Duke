package duke.logic.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.storage.UndoStack;
import duke.tasklist.TaskList;
import duke.ui.Ui;

public class HelpCommand extends Command {
    public HelpCommand() {
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        System.out.println("Here is a summary of what you can do in El Duque. For a more detailed explanation, please" +
                " read the User Guide\n");

        System.out.println("El Duque commands follow a general format: \n");
        System.out.println("[-FILTER] COMMAND [-MODIFICATIONS] \n");

        System.out.println("Commands Available:");

        System.out.println("   help        | Shows a summary of commands that are available and how they can be used");
        System.out.println("   task        | Creates a task");
        System.out.println("                 e.g. task finish spanish tutorial -t 031120");
        System.out.println("   event       | Creates an event");
        System.out.println("                 e.g. event spanish lesson -t 031119");
        System.out.println("   list        | Lists existing tasks and events");
        System.out.println("                 e.g. list day");
        System.out.println("                 e.g. list week");
        System.out.println("   edit        | Edits an existing task or event");
        System.out.println("                 e.g. edit 3 -description bring ahma for checkup");
        System.out.println("   delete      | Delete an existing task or event");
        System.out.println("                 e.g. delete 3");
        System.out.println("   find        | Finds existing task(s) and event(s) that contain the query");
        System.out.println("                 e.g. find homework");
        System.out.println("   undo        | undos undoable commands, task, event, done, delete, edit");
        System.out.println("                 e.g. undo");
        System.out.println("   autoassign  | Suggest filters to assign a task/event");
        System.out.println("                 e.g. autoassign 3");
        System.out.println("   pomo        | pomodoro timer feature");
        System.out.println("                 e.g. pomo start");
        System.out.println("   bye         | Exits the program\n");

        System.out.println("Filter");
        System.out.println("They are similar to sublist, they work with all the commands that references a certain tasks and" +
                " the index indicated is \nrespective to the filtered list.");
        System.out.println("e.g. -cs2113 list\n");

        System.out.println("Modifications Available:");
        System.out.println("task, event and edit");
        System.out.println("   -r       | reccurence, indicates the period for recurrence either daily or weekly");
        System.out.println("   -d       | duration in hours assigned to a task");
        System.out.println("   -p       | priority tag, l, m or h");
        System.out.println("   -t       | date and time, follows ddMMyy, ddMMyy HHmm, today/tomorrow, today/tomorrow " +
                "HHmm\n");
        System.out.println("edit ");
        System.out.println("   -desc    | edits the description");
        System.out.println("   -f       | edits the filter\n");

        System.out.println("list");
        System.out.println("   day      | list task within today");
        System.out.println("   week     | list task within this week");
        System.out.println("   priority | list tasks based on priority");
        System.out.println("   undone   | list undone tasks\n");

        System.out.println("pomo");
        System.out.println("   start    | starts the timer");
        System.out.println("   stop     | stops the timer");
        System.out.println("   reset    | resets to the previous cycle");
        System.out.println("   restart  | restarts the entire pomodoro");
        System.out.println("   status   | prints the status");
        System.out.println("   add      | adds tasks to the pomodoro");
        System.out.println("   list     | lists the tasks added");
        System.out.println("   done     | marks the task as done");
        System.out.println("   answer   | answer to the brain teaser");
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
