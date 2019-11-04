//@@author yueyuu
package gazeeebo.commands.help;

import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.exception.DukeException;
import gazeeebo.commands.Command;
import gazeeebo.help.HelpText;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class HelpCommand extends Command {

    private static final String TASKS_PAGE = "tasks page";
    private static final String TASKS_TODO = "tasks todo";
    private static final String TASKS_DEADLINE = "tasks deadline";
    private static final String TASKS_EVENT = "tasks event";
    private static final String TASKS_LIST = "tasks list";
    private static final String TASKS_DELETE = "tasks delete";
    private static final String TASKS_DONE = "tasks done";

    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<ArrayList<Task>> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws DukeException, ParseException, IOException, NullPointerException {
        //gazeeebo.help COMMAND or just gazeeebo.help
        //description of a gazeeebo.help can be empty
        HelpText help = new HelpText();
        String description;
        String[] command = ui.fullCommand.split(" ", 2);
        assert command.length != 0 : "Bug in parser that affects HelpCommand";
        if (command.length == 1) {
            description = HelpText.commandFormat + System.lineSeparator() + System.lineSeparator() +
                    HelpText.commandsHeader + HelpText.pageSeparator +
                    HelpText.tasksPageHeader + HelpText.commandSeparator +
                    HelpText.todo + HelpText.commandSeparator +
                    HelpText.deadline + HelpText.commandSeparator +
                    HelpText.event + HelpText.commandSeparator +
                    HelpText.list + HelpText.commandSeparator +
                    HelpText.delete + HelpText.commandSeparator +
                    HelpText.done + HelpText.commandSeparator +
                    HelpText.tasksFind + HelpText.commandSeparator +
                    HelpText.scheduleDaily + HelpText.commandSeparator +
                    HelpText.scheduleWeekly + HelpText.commandSeparator +
                    HelpText.scheduleMonthly + HelpText.commandSeparator +
                    HelpText.doWithinPeriod + HelpText.commandSeparator +
                    HelpText.addTentativeEvent + HelpText.commandSeparator +
                    HelpText.confirmTentativeEvent + HelpText.commandSeparator +
                    HelpText.tasksEdit + HelpText.commandSeparator +
                    HelpText.fixedDurationTask + HelpText.commandSeparator +
                    HelpText.categorizeTasks + HelpText.commandSeparator +
                    HelpText.monthlyCalendarView + HelpText.commandSeparator +
                    HelpText.annualCalendarView + HelpText.commandSeparator +
                    HelpText.undone + HelpText.commandSeparator +
                    HelpText.doneList + HelpText.commandSeparator +
                    HelpText.doAfterTask + HelpText.commandSeparator +
                    HelpText.snooze + HelpText.commandSeparator +
                    HelpText.reschedule + HelpText.commandSeparator +
                    HelpText.recurringTask + HelpText.commandSeparator +
                    HelpText.undoneList + HelpText.commandSeparator +
                    HelpText.tagging + HelpText.commandSeparator +
                    HelpText.undoCommands + HelpText.commandSeparator;
        } else {
            switch (command[1]) {
            case TASKS_PAGE: description = help.tasksPageHeader;
                break;
            case TASKS_TODO: description = help.todo;
                break;
            case TASKS_DEADLINE: description = help.deadline;
                break;
            case TASKS_EVENT: description = help.event;
                break;
            case TASKS_LIST: description = help.list;
                break;
            case TASKS_DELETE: description = help.delete;
                break;
            case TASKS_DONE: description = help.done;
                break;
            default:
                description = "OOPS!!! There is no such command.";
                break;
            }
        }
        System.out.println(description);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
