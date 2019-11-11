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
import spinbox.entities.items.tasks.TaskType;
import spinbox.exceptions.SpinBoxException;
import spinbox.exceptions.InputException;
import spinbox.Ui;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SetDateCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(SetDateCommand.class.getName());
    private static final String LOG_MODULE_CODE = "Module code is ";
    private static final String LOG_NO_MODULE_CODE = "No module code indicated.";
    private static final String LOG_NON_EXISTENT_MODULE = "Module does not exist.";
    private static final String LOG_SET_DATE_UNAVAILABLE = "Set-date is unavailable.";
    private static final String LOG_INVALID_SET_DATE_FORMAT = "Set-date format is invalid.";
    private static final String LOG_INVALID_INDEX = "Index is invalid.";
    private static final String LOG_INVALID_DATETIME_FORMAT = "DataTime format is invalid.";
    private static final String LOG_TASK_CHANGED = "Task name has been changed.";
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
    private static final String NO_MODULE_CODE = "No module code indicated.";
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
    public SetDateCommand(String[] pageDataComponents, String content) throws InputException {
        LOGGER.setLevel(Level.INFO);
        LOGGER.setUseParentHandlers(true);
        LOGGER.entering(getClass().getName(), "Constructor");

        if (pageDataComponents.length > 1) {
            this.moduleCode = pageDataComponents[1];
            LOGGER.fine(LOG_MODULE_CODE + moduleCode);
        } else {
            LOGGER.severe(LOG_NO_MODULE_CODE);
            throw new InputException(INVALID_SET_DATE_FORMAT);
        }

        assert !moduleCode.isEmpty();

        this.content = content;
        this.type = content.split(" ")[0].toLowerCase();

        LOGGER.exiting(getClass().getName(), "Constructor");
    }

    @Override
    public String execute(ModuleContainer moduleContainer, ArrayDeque<String> pageTrace, Ui ui, boolean guiMode) throws
            SpinBoxException {
        LOGGER.entering(getClass().getName(), "execute");

        checkIfOnModulePage(moduleCode);
        assert checkIfOnModulePage(moduleCode) : NO_MODULE_CODE;

        Module module;
        HashMap<String, Module> modules;

        if (moduleContainer.checkModuleExists(moduleCode)) {
            modules = moduleContainer.getModules();
            module = modules.get(moduleCode);
            switch (type) {
            case "task":
                try {
                    String taskDescription;
                    DateTime replaceStart;
                    DateTime replaceEnd;

                    int index = Integer.parseInt(content.split(" ")[1]) - 1;
                    TaskList tasks = module.getTasks();
                    Task taskSelected = tasks.get(index);

                    String fullTaskDescription = taskSelected.toString();
                    TaskType taskType = taskSelected.getTaskType();
                    String taskTypeString = taskType.name();

                    String replaceDateTime = content.split("to: ")[1].trim();
                    int dateTimeLength = replaceDateTime.split(" ").length;

                    if ((taskSelected.isSchedulable() && !taskTypeString.equals("DEADLINE")) && (dateTimeLength >= 3)) {
                        taskDescription = fullTaskDescription.substring(fullTaskDescription.lastIndexOf("] ")
                                + 2, fullTaskDescription.lastIndexOf("(at: ") - 1);
                        replaceStart = new DateTime(replaceDateTime.substring(0,
                                replaceDateTime.lastIndexOf(" to")));
                        replaceEnd = new DateTime(replaceDateTime.substring(
                                replaceDateTime.lastIndexOf("to ") + 3));
                    } else if (taskTypeString.equals("DEADLINE") && dateTimeLength <= 2) {
                        taskDescription = fullTaskDescription.substring(fullTaskDescription.lastIndexOf("] ")
                                + 2, fullTaskDescription.lastIndexOf("(by: ") - 1);
                        replaceStart = new DateTime(replaceDateTime);
                        replaceEnd = null;
                    } else if (taskTypeString.equals("TODO")) {
                        throw new InputException(SET_DATE_UNAVAILABLE_TODO);
                    } else {
                        throw new InputException(DATETIME_ERROR);
                    }

                    Task taskChanged;
                    int doneStatus = (taskSelected.getDone()) ? 1 : 0;
                    switch (taskTypeString) {
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
                    LOGGER.fine(LOG_TASK_CHANGED);

                    return HORIZONTAL_LINE + "\n" + TASK_SET + "Task " + (index + 1) + CHANGE_FROM
                            + taskSelected.toString() + TO + taskChanged.toString() + "\n" + HORIZONTAL_LINE;
                } catch (NumberFormatException e) {
                    LOGGER.severe(LOG_INVALID_INDEX);
                    throw new InputException(INVALID_INDEX);
                } catch (IndexOutOfBoundsException e) {
                    LOGGER.severe(LOG_INVALID_DATETIME_FORMAT);
                    throw new InputException(INVALID_DATETIME_FORMAT);
                }

            case "file":
            case "note":
            case "grade":
                LOGGER.severe(LOG_SET_DATE_UNAVAILABLE);
                throw new InputException(SET_DATE_UNAVAILABLE_OTHERS);

            default:
                LOGGER.severe(LOG_INVALID_SET_DATE_FORMAT);
                throw new InputException(INVALID_SET_DATE_FORMAT);
            }
        } else {
            LOGGER.severe(LOG_NON_EXISTENT_MODULE);
            return NON_EXISTENT_MODULE;
        }
    }
}