package command;

import exception.DukeException;
import parser.CommandParams;
import storage.Storage;
import task.Deadline;
import task.Event;
import task.Task;
import task.TaskList;
import task.ToDo;
import ui.Ui;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a specified command as AddCommand by extending the {@code Command} class.
 * Adds various specified type of tasks into the taskList. e.g event
 * Responses with the result.
 */
public class AddCommand extends Command {
    private static final String name = "add";
    private static final String description = "adds a task";
    private static final String usage = "add $description "
            + "/[after $time|recurring $interval|tentative|start $time|end $time|by $time|needs $[time|task]";

    private enum SecondaryParam {
        AFTER("after", "make the task only doable after another task or time"),
        RECURRING("recurring", "make the task recur at a set interval"),
        TENTATIVE("tentative", "make the task tentative, to be confirmed at a later time"),
        START("start", "set the starting time of the task"),
        END("end", "set the ending time of the task"),
        BY("by", "set the task as required to be done by a certain time"),
        NEEDS("needs", "set the amount of time required to do the task");

        private String name;
        private String description;

        SecondaryParam(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }

    /**
     * Constructs an {@code AddCommand} object.
     */
    public AddCommand() {
        super(
                name,
                description,
                usage,
                Stream.of(SecondaryParam.values()).collect(Collectors.toMap(s -> s.name, s -> s.description))
        );
    }

    /**
     * {@inheritDoc}
     * @throws DukeException If exceptions occur when adding tasks or updating storage.
     */
    @Override
    public void execute(CommandParams commandParams, TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (commandParams.getMainParam() == null) {
            throw new DukeException("☹ OOPS!!! The description of a task cannot be empty.");
        }

        Task taskToAdd;

        switch (commandParams.getCommandName()) {
        case "todo":
            taskToAdd = new ToDo(commandParams.getMainParam());
            if (commandParams.containsParams(SecondaryParam.NEEDS.name)) {
                ((ToDo) taskToAdd).setTimeTaken(commandParams.getParam(SecondaryParam.NEEDS.name));
            }
            break;
        case "deadline":
            taskToAdd = new Deadline(commandParams.getMainParam(), commandParams.getParam(SecondaryParam.BY.name));
            break;
        case "event":
            taskToAdd = new Event(commandParams.getMainParam(),
                    commandParams.getParam(SecondaryParam.START.name),
                    commandParams.getParam(SecondaryParam.END.name),
                    tasks);
            break;
        default:
            throw new DukeException("☹ OOPS!!! Your command type is unknown!");
        }

        if (commandParams.containsParams(SecondaryParam.AFTER.name)) {
            taskToAdd.setDoAfterDate(commandParams.getParam(SecondaryParam.AFTER.name));
        }

        if (commandParams.containsParams(SecondaryParam.RECURRING.name)) {
            String recurringType = commandParams.getParam(SecondaryParam.RECURRING.name);
            taskToAdd.setRecurringType(commandParams.getParam(SecondaryParam.RECURRING.name));
        }
        if (commandParams.containsParams(SecondaryParam.TENTATIVE.name)) {
            taskToAdd.setTentative(true);
        } else {
            taskToAdd.setRecurringType("NONE");
        }


        tasks.addTask(taskToAdd);

        storage.update(tasks.toStorageStrings());

        ui.println("Got it. I've added this task:");
        ui.println(tasks.getTaskInfo(tasks.getSize() - 1));
        ui.println("Now you have " + tasks.getSize() + " tasks in the list.");
    }


}
