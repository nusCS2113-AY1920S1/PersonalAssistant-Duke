package duke.logic.command;

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
                " read the User Guide\n");

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

        System.out.println("POMODORO");
        System.out.println("El Duque supports a pomodoro timer workflow option. If you use this method in your usual " +
                "workflow, you can use this to complement your work. You can use it purely as a timer or add tasks[co" +
                "ming in v1.4] to a particular pomodoro that you are completing. Pomorodo commands begin with a pomo." +
                " Pomodoro has 3 states, a work timer, short break timer and a long break timer with 25, 5 and 15 min" +
                "utes periods respectively. The first instance of the timer start will be the work cycle and it will " +
                "automatically update to the short break once the timer has ended. Pomodoro timing reminders are prin" +
                "ted out every 5 minutes.");
        System.out.println("Format: pomo start");
        System.out.println("Other commands for the pomodoro are:");
        System.out.println("Status - This status command prints out time remaining and current state");
        System.out.println("Reset - Resets the current state to the previous state");
        System.out.println("Restart - Restarts the pomodoro back to first work cycle\n");

        System.out.println("Understanding how filters work:");
        System.out.println("A filter is like a sublist. Most command can begin with a filter. E.g. -cs2113 .....");
        System.out.println("When you use a filter, the index in the command is RELATIVE to the filtered list!");
        System.out.println("-cs2113 delete 1    CAN BE EQUAL TO    delete 3\n");

        System.out.println("Advanced Modifications:");

        System.out.println("PRIORITY: There are 3 values: 1, 2, 3. Priority tags are added at the end of the " +
                "command with a -p prefix followed by priority level");
        System.out.println("Format: -[FILTER] task tutorial -p [PRIORITY LEVEL]\n");

        System.out.println("RECURRING TASKS: modification is denoted by -r followed by the recurrence period either " +
                "'daily' or 'weekly'");
        System.out.println("Format: -[FILTER] task tutorial -r [PERIOD]\n");

        System.out.println("DURATION: modification is denoted by -d followed by the duration in hours");
        System.out.println("Format: -[FILTER] task tutorial -d [HOURS]\n");

        System.out.println("TIME: modification is denoted by -t followed by the date. Dates can be input in the " +
                "following ways: ddMMyy or ddMMyy HHmm or today or tomorrow");
        System.out.println("Format: -[FILTER] task tutorial -t [DATETIME]\n");

        System.out.println("Description: you should only use this modifier with edit command.");
        System.out.println("Format: -[FILTER] edit 1 -description [DESCRIPTION]\n");


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
