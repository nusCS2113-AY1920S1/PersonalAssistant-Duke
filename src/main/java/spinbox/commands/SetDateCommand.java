package spinbox.commands;

import spinbox.DateTime;
import spinbox.containers.ModuleContainer;
import spinbox.containers.lists.TaskList;
import spinbox.entities.Module;
import spinbox.entities.items.tasks.Deadline;
import spinbox.entities.items.tasks.Event;
import spinbox.entities.items.tasks.Exam;
import spinbox.entities.items.tasks.Lab;
import spinbox.entities.items.tasks.Lecture;
import spinbox.entities.items.tasks.Tutorial;
import spinbox.entities.items.tasks.Task;
import spinbox.exceptions.SpinBoxException;
import spinbox.exceptions.InputException;
import spinbox.Ui;

import java.util.ArrayDeque;
import java.util.HashMap;

public class SetDateCommand extends Command {
    private static final String TASK_SET = "Task date and time details successfully changed.\n";
    private static final String INVALID_INDEX = "Please enter a valid index to be set.";
    private static final String NON_EXISTENT_MODULE = "This module does not exist.";
    private static final String INVALID_SET_DATE_FORMAT = "Please use the valid set-date format:\n"
            + "set-date <pageContent> / task <index> to: <new date and time details>";
    private static final String INVALID_DATETIME_FORMAT = "Please use the valid set-date format:\n"
            + "1. Event/Exam/Lab/Lecture/Tutorial: set-date <pageContent> / task <index> to: <new start datetime> to"
            + " <new end datetime>\n" + "2. Deadline: set-date <pageContent> / task <index> to: <new deadline "
            + "datetime>";
    private static final String SET_DATE_UNAVAILABLE_OTHERS = "Sorry, set-date is not available for the selected tab.";
    private static final String SET_DATE_UNAVAILABLE_TODO = "Sorry, set-date is not available for a To-Do task.\n"
            + "Set-date is only available for a Deadline/Event/Exam/Lab/Lecture/Tutorial.";
    private static final String DATETIME_ERROR = "Datetime value provided is incorrect. Please follow the format "
            + "below:\n" + "1. Event/Exam/Lab/Lecture/Tutorial: 1 start datetime value and 1 end datetime value\n"
            + "2. Deadline: 1 deadline datetime value";
    private static final String CHANGE_FROM = " changed from: ";
    private static final String TO = "\n\t\t\t\t to: ";

    private String type;

    private String moduleCode;
    private String content;

    /**
     * Constructor for initialization of variables to support the set date command.
     * @param pageDataComponents page data components.
     * @param content A string containing the content of the processed user input.
     */
    public SetDateCommand(String[] pageDataComponents, String content) {
        if (pageDataComponents.length > 1) {
            this.moduleCode = pageDataComponents[1];
        }
        this.content = content;
        this.type = content.split(" ")[0].toLowerCase();
    }

    @Override
    public String execute(ModuleContainer moduleContainer, ArrayDeque<String> pageTrace, Ui ui, boolean guiMode) throws
            SpinBoxException {
        Task taskChanged;
        int doneStatus;
        String taskDescription;
        String replaceDateTime;
        DateTime replaceStart;
        DateTime replaceEnd;

        if (!content.contains("to: ")) {
            throw new InputException(INVALID_SET_DATE_FORMAT);
        }
        switch (type) {
        case "task":
            checkIfOnModulePage(moduleCode);
            if (moduleContainer.checkModuleExists(moduleCode)) {
                try {
                    HashMap<String, Module> modules = moduleContainer.getModules();
                    Module module = modules.get(moduleCode);
                    TaskList tasks = module.getTasks();
                    int index = Integer.parseInt(content.split(" ")[1]) - 1;
                    Task taskSelected = tasks.get(index);
                    String fullTaskDescription = taskSelected.toString();
                    String taskType = taskSelected.getTaskType().name();
                    replaceDateTime = content.split("to: ")[1].trim();
                    int dateTimeLength = replaceDateTime.split(" ").length;
                    if (taskSelected.getDone()) {
                        doneStatus = 1;
                    } else {
                        doneStatus = 0;
                    }
                    if ((taskType.equals("EVENT") || taskType.equals("EXAM") || taskType.equals("LAB")
                            || taskType.equals("LECTURE") || taskType.equals("TUTORIAL")) && (dateTimeLength >= 3)) {
                        taskDescription = fullTaskDescription.substring(fullTaskDescription.lastIndexOf("] ")
                                + 2, fullTaskDescription.lastIndexOf("(at: ") - 1);
                        replaceStart = new DateTime(replaceDateTime.substring(0,
                                replaceDateTime.lastIndexOf(" to")));
                        replaceEnd = new DateTime(replaceDateTime.substring(
                                replaceDateTime.lastIndexOf("to ") + 3));
                    } else if (taskType.equals("DEADLINE") && dateTimeLength <= 2) {
                        taskDescription = fullTaskDescription.substring(fullTaskDescription.lastIndexOf("] ")
                                + 2, fullTaskDescription.lastIndexOf("(by: ") - 1);
                        replaceStart = new DateTime(replaceDateTime);
                        replaceEnd = null;
                    } else if (taskType.equals("TODO")) {
                        throw new InputException(SET_DATE_UNAVAILABLE_TODO);
                    } else {
                        throw new InputException(DATETIME_ERROR);
                    }
                    switch (taskType) {
                    case "DEADLINE":
                        taskChanged = new Deadline(doneStatus, taskDescription, replaceStart);
                        break;
                    case "EVENT":
                        taskChanged = new Event(doneStatus, taskDescription, replaceStart, replaceEnd);
                        break;
                    case "EXAM":
                        taskChanged = new Exam(doneStatus, taskDescription, replaceStart, replaceEnd);
                        break;
                    case "LAB":
                        taskChanged = new Lab(doneStatus, taskDescription, replaceStart, replaceEnd);
                        break;
                    case "LECTURE":
                        taskChanged = new Lecture(doneStatus, taskDescription, replaceStart, replaceEnd);
                        break;
                    default:
                        taskChanged = new Tutorial(doneStatus, taskDescription, replaceStart, replaceEnd);
                        break;
                    }
                    tasks.remove(index);
                    taskChanged = tasks.add(taskChanged);
                    return HORIZONTAL_LINE + "\n" + TASK_SET + "Task " + (index + 1) + CHANGE_FROM
                            + taskSelected.toString() + TO + taskChanged.toString() + "\n" + HORIZONTAL_LINE;
                } catch (NumberFormatException e) {
                    throw new InputException(INVALID_INDEX);
                } catch (IndexOutOfBoundsException e) {
                    throw new InputException(INVALID_DATETIME_FORMAT);
                }
            } else {
                return NON_EXISTENT_MODULE;
            }

        case "file":
        case "note":
        case "grade":
            throw new InputException(SET_DATE_UNAVAILABLE_OTHERS);

        default:
            throw new InputException(INVALID_SET_DATE_FORMAT);
        }
    }
}