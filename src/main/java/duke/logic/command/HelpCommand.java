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
        ui.showLine("Here is a summary of what you can do in El Duque. For a more detailed explanation, please"
                + " read the User Guide\n");

        ui.showLine("El Duque commands follow a general format: \n");
        ui.showLine("[-FILTER] COMMAND [-MODIFICATIONS] \n");

        ui.showLine("Commands Available:");

        ui.showLine("   help        | Shows a summary of commands that are available and how they can be used");
        ui.showLine("   task        | Creates a task");
        ui.showLine("                 e.g. task finish spanish tutorial -t 031120");
        ui.showLine("   event       | Creates an event");
        ui.showLine("                 e.g. event spanish lesson -t 031119");
        ui.showLine("   list        | Lists existing tasks and events");
        ui.showLine("                 e.g. list day");
        ui.showLine("                 e.g. list week");
        ui.showLine("   edit        | Edits an existing task or event");
        ui.showLine("                 e.g. edit 3 -description bring ahma for checkup");
        ui.showLine("   delete      | Delete an existing task or event");
        ui.showLine("                 e.g. delete 3");
        ui.showLine("   find        | Finds existing task(s) and event(s) that contain the query");
        ui.showLine("                 e.g. find homework");
        ui.showLine("   undo        | undos undoable commands, task, event, done, delete, edit");
        ui.showLine("                 e.g. undo");
        ui.showLine("   autoassign  | Suggest filters to assign a task/event");
        ui.showLine("                 e.g. autoassign 3");
        ui.showLine("   pomo        | pomodoro timer feature");
        ui.showLine("                 e.g. pomo start");
        ui.showLine("   bye         | Exits the program\n");

        ui.showLine("Filter");
        ui.showLine("They are similar to sublist, they work with all the commands that references a certain tasks and"
                + " the index indicated is \nrespective to the filtered list.");
        ui.showLine("e.g. -cs2113 list\n");

        ui.showLine("Modifications Available:");
        ui.showLine("task, event and edit");
        ui.showLine("   -r       | reccurence, indicates the period for recurrence either daily or weekly");
        ui.showLine("   -d       | duration in hours assigned to a task");
        ui.showLine("   -p       | priority tag, l, m or h");
        ui.showLine("   -t       | date and time, follows ddMMyy, ddMMyy HHmm, today/tomorrow, today/tomorrow "
                + "HHmm\n");
        ui.showLine("edit ");
        ui.showLine("   -desc    | edits the description");
        ui.showLine("   -f       | edits the filter\n");

        ui.showLine("list");
        ui.showLine("   day      | list task within today");
        ui.showLine("   week     | list task within this week");
        ui.showLine("   priority | list tasks based on priority");
        ui.showLine("   undone   | list undone tasks\n");

        ui.showLine("pomo");
        ui.showLine("   start    | starts the timer");
        ui.showLine("   stop     | stops the timer");
        ui.showLine("   reset    | resets to the previous cycle");
        ui.showLine("   restart  | restarts the entire pomodoro");
        ui.showLine("   status   | prints the status");
        ui.showLine("   add      | adds tasks to the pomodoro");
        ui.showLine("   list     | lists the tasks added");
        ui.showLine("   done     | marks the task as done");
        ui.showLine("   answer   | answer to the brain teaser");
    }

    /**
     * Not applicable for this Command.
     *
     * @param tasks     NA
     * @param undoStack NA
     * @throws DukeException NA
     */
    @Override
    public void savePrevState(TaskList tasks, UndoStack undoStack) {

    }

}
