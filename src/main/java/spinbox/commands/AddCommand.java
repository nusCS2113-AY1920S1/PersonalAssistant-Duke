package spinbox.commands;

import spinbox.DateTime;
import spinbox.exceptions.SpinBoxException;
import spinbox.exceptions.InputException;
import spinbox.Storage;
import spinbox.Ui;
import spinbox.items.tasks.Deadline;
import spinbox.items.tasks.Event;
import spinbox.items.tasks.Task;
import spinbox.items.tasks.Todo;
import spinbox.items.tasks.Schedulable;
import spinbox.lists.TaskList;

import java.util.ArrayList;
import java.util.List;

public class AddCommand extends Command {
    private String type;
    private String fullCommand;

    /**
     * Constructor for creation of Task objects, does some input checking.
     * @param components Components of the full command
     * @param fullCommand the full command
     * @throws SpinBoxException Can throw from invalid input or storage errors
     */
    public AddCommand(String[] components, String fullCommand) throws SpinBoxException {
        this.type = components[0];
        this.fullCommand = fullCommand;

        if (components.length == 1) {
            throw new InputException("☹ OOPS!!! The description of a task cannot be empty.");
        } else if (this.type.equals("deadline") && components[1].equals("/by")) {
            throw new InputException("☹ OOPS!!! The description of a deadline cannot be empty.");
        } else if (this.type.equals("event") && components[1].equals("/by")) {
            throw new InputException("☹ OOPS!!! The description of an event cannot be empty.");
        } else if (this.type.equals("exam") && components[1].equals("/by")) {
            throw new InputException("☹ OOPS!!! The description of an exam cannot be empty.");
        } else if (this.type.equals("tutorial") && components[1].equals("/by")) {
            throw new InputException("☹ OOPS!!! The description of an tutorial cannot be empty.");
        } else if (this.type.equals("lab") && components[1].equals("/by")) {
            throw new InputException("☹ OOPS!!! The description of an lab cannot be empty.");
        } else if (this.type.equals("lecture") && components[1].equals("/by")) {
            throw new InputException("☹ OOPS!!! The description of an lecture cannot be empty.");
        }
    }

    /**
     * Add Task to task list.
     * @param taskList TaskList instance.
     * @param storage Storage instance.
     * @param ui Ui instance.
     * @throws SpinBoxException invalid input or storage error.
     */
    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui) throws SpinBoxException {
        List<String> formattedOutput = new ArrayList<>();
        Task added;
        List dates;
        String fixedDuration;
        String doAfter;
        DateTime start;
        DateTime end;

        try {
            switch (this.type) {
            case "todo":
                added = taskList.add(new Todo(fullCommand.replaceFirst("todo ", "")));
                formattedOutput.add("Got it. I've added this todo:");
                formattedOutput.add(added.toString());
                break;

            case "deadline":
                start = new DateTime(fullCommand.split("/by ")[1]);
                added = taskList.add(new Deadline(fullCommand.substring(0, fullCommand.lastIndexOf(" /by"))
                        .replaceFirst("deadline ", ""),
                        start));
                formattedOutput.add("Got it. I've added this deadline:");
                formattedOutput.add(added.toString());
                break;
            case "exam":
            case "tutorial":
            case "lecture":
            case "lab":
            default:
                start = new DateTime(fullCommand.split("/at ")[1], 0);
                end = new DateTime(fullCommand.split("/at ")[1], 1);

                List<Task> tasks = taskList.getList();
                for (int i = 0; i < tasks.size(); i++) {
                    Task currentTask = tasks.get(i);
                    if (currentTask.isSchedulable()) {
                        if (((Schedulable) currentTask).isOverlapping(start, end)) {
                            throw new InputException("Time conflicting with:\n"
                                    + "    " + (i + 1) + "." + currentTask.toString() + "\n"
                                    + "Please choose another time interval.");
                        }
                    }
                }
                added = taskList.add(new Event(fullCommand.substring(0, fullCommand.lastIndexOf(" /at"))
                        .replaceFirst("event ", ""),
                        start, end));
                formattedOutput.add("Got it. I've added this event:");
                formattedOutput.add(added.toString());
            }
        } catch (IndexOutOfBoundsException e) {
            throw new InputException("Please ensure that you enter the full command.\n"
                    + "SpinBox.Tasks.Deadline: deadline <task name> /by <MM/DD/YYYY HH:MM>\n"
                    + "SpinBox.Tasks.Do-After: do-after <task name> /after <do-after event or time>\n"
                    + "SpinBox.Tasks.Event: event <task name> /at <start as MM/DD/YYYY HH:MM> "
                    + "to <end as MM/DD/YYYY HH:MM>\n"
                    + "SpinBox.Tasks.Fixed: fixed <task name> /needs <fixed task duration>\n"
                    + "SpinBox.Tasks.Tentative: tentative <task name> /around <start as MM/DD/YYYY HH:MM> "
                    + "to <end as MM/DD/YYYY HH:MM>\n"
                    + "SpinBox.Tasks.Within: do-within <task name> /between <start as MM/DD/YYYY HH:MM> "
                    + "and <end as MM/DD/YYYY HH:MM>\n"
                    + "SpinBox.Tasks.Recurring: recurring <task name> /at <start as MM/DD/YYYY HH:MM> "
                    + "to <end as MM/DD/YYYY HH:MM> /every DD:HH:MM"
            );
        }
        formattedOutput.add("You currently have " + taskList.getList().size()
                + ((taskList.getList().size() == 1) ? " task in the list." : " tasks in the list."));
        taskList.saveData();
        taskList.sort();
        return ui.showFormatted(formattedOutput);
    }
}