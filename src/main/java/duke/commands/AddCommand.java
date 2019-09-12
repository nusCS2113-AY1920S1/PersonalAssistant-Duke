package duke.commands;

import duke.exceptions.DukeException;
import duke.exceptions.InputException;
import duke.Storage;
import duke.TaskList;
import duke.tasks.Deadline;
import duke.tasks.Event;
import duke.tasks.Task;
import duke.tasks.Todo;
import duke.Ui;

import java.util.ArrayList;
import java.util.List;

public class AddCommand extends Command {
    private String type;
    private String fullCommand;

    /**
     * Constructor for creation of Task objects, does some input checking.
     * @param components Components of the full command
     * @param fullCommand the full command
     * @throws DukeException Can throw from invalid input or storage errors
     */
    public AddCommand(String[] components, String fullCommand) throws DukeException {
        this.type = components[0];
        this.fullCommand = fullCommand;

        if (components.length == 1) {
            throw new InputException("☹ OOPS!!! The description of a task cannot be empty.");
        } else if (this.type.equals("deadline") && components[1].equals("/by")) {
            throw new InputException("☹ OOPS!!! The description of a deadline cannot be empty.");
        } else if (this.type.equals("event") && components[1].equals("/by")) {
            throw new InputException("☹ OOPS!!! The description of an event cannot be empty.");
        }
    }

    /**
     * Add Task to task list.
     * @param taskList TaskList instance.
     * @param storage Storage instance.
     * @param ui Ui instance.
     * @throws DukeException invalid input or storage error.
     */
    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui) throws DukeException {
        List<String> formattedOutput = new ArrayList<>();
        Task added;

        try {
            switch (this.type) {
            case "todo":
                added = taskList.addTask(new Todo(fullCommand.replaceFirst("todo ", "")));
                formattedOutput.add("Got it. I've added this todo:");
                formattedOutput.add(added.toString());
                break;

            case "deadline":
                added = taskList.addTask(new Deadline(fullCommand.substring(0, fullCommand.lastIndexOf(" /by"))
                        .replaceFirst("deadline ", ""),
                        fullCommand.split("/by ")[1]));
                formattedOutput.add("Got it. I've added this deadline:");
                formattedOutput.add(added.toString());
                break;

            default:
                added = taskList.addTask(new Event(fullCommand.substring(0, fullCommand.lastIndexOf(" /at"))
                        .replaceFirst("event ", ""),
                        fullCommand.split("/at ")[1]));
                formattedOutput.add("Got it. I've added this event:");
                formattedOutput.add(added.toString());
            }
        } catch (IndexOutOfBoundsException e) {
            throw new InputException("Please ensure that you enter the full command.\n"
                    + "Duke.Tasks.Deadline: deadline <task name> /by <DD/MM/YYYY HHMM>\n"
                    + "Duke.Tasks.Event: event <task name> /at <start as DD/MM/YYYY HHMM>_<end as DD/MM/YYYY HHMM>");
        }
        formattedOutput.add("You currently have " + taskList.getTasks().size()
                + ((taskList.getTasks().size() == 1) ? " task in the list." : " tasks in the list."));
        storage.setData(taskList.getTasks());
        return ui.showFormatted(formattedOutput);
    }
}
