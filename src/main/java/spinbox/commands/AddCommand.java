package spinbox.commands;

import spinbox.DateTime;
import spinbox.containers.ModuleContainer;
import spinbox.containers.lists.FileList;
import spinbox.entities.items.File;
import spinbox.entities.Module;
import spinbox.exceptions.SpinBoxException;
import spinbox.exceptions.InputException;
import spinbox.Storage;
import spinbox.Ui;
import spinbox.entities.items.tasks.Deadline;
import spinbox.entities.items.tasks.Event;
import spinbox.entities.items.tasks.Task;
import spinbox.entities.items.tasks.Todo;
import spinbox.entities.items.tasks.Schedulable;
import spinbox.containers.lists.TaskList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddCommand extends Command {
    private static final String MODULE_ADDED = "The following module has been added to SpinBox: ";
    private static final String MODULE_NOT_ADDED = "A module with this code already exists in SpinBox.";
    private static final String MODULE_NOT_EXISTS = "This module does not exist.";
    private String type;
    private String fullCommand;

    private String moduleCode;
    private String content;

    /**
     * Constructor for initialization of variables to support addition of entities.
     * @param moduleCode A String denoting the module code.
     * @param content A string containing the content of the processed user input.
     */
    public AddCommand(String moduleCode, String content) {
        this.moduleCode = moduleCode;
        this.content = content;
        this.type = content.split(" ")[0];
    }

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

    @Override
    public String execute(ModuleContainer moduleContainer, ArrayDeque<String> pageTrace, Ui ui) throws
            SpinBoxException {

        switch (type) {
        // add files
        case "file":
            if (moduleContainer.checkModuleExists(moduleCode)) {
                HashMap<String, Module> modules = moduleContainer.getModules();
                Module module = modules.get(moduleCode);
                FileList files = module.getFiles();
                String fileName = content.replace(type.concat(" "), "");
                files.add(new File(0, fileName));
                return "Added into " + module.toString() + " file: " + fileName;
            } else {
                return MODULE_NOT_EXISTS;
            }
        default:
            if (!moduleContainer.checkModuleExists(moduleCode)) {
                String moduleName = this.content;
                Module module = new Module(this.moduleCode, moduleName);
                moduleContainer.addModule(module);
                return MODULE_ADDED + module.toString();
            } else {
                return MODULE_NOT_ADDED;
            }
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