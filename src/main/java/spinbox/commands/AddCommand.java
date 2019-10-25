package spinbox.commands;

import spinbox.DateTime;
import spinbox.containers.ModuleContainer;
import spinbox.containers.lists.FileList;
import spinbox.containers.Notepad;
import spinbox.entities.items.File;
import spinbox.entities.Module;
import spinbox.exceptions.SpinBoxException;
import spinbox.exceptions.InputException;
import spinbox.Ui;
import spinbox.entities.items.tasks.Deadline;
import spinbox.entities.items.tasks.Event;
import spinbox.entities.items.tasks.Exam;
import spinbox.entities.items.tasks.Lab;
import spinbox.entities.items.tasks.Lecture;
import spinbox.entities.items.tasks.Tutorial;
import spinbox.entities.items.tasks.Task;
import spinbox.entities.items.tasks.Todo;
import spinbox.entities.items.tasks.Schedulable;
import spinbox.containers.lists.TaskList;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;

public class AddCommand extends Command {
    private static final String HORIZONTAL_LINE = "____________________________________________________________";
    private static final String MODULE_ADDED = "The following module has been added to SpinBox: ";
    private static final String MODULE_NOT_ADDED = "A module with this code already exists in SpinBox.";
    private static final String NON_EXISTENT_MODULE = "This module does not exist.";
    private static final String NOTE_ADDED = "A new note has been successfully added to ";
    private static final String UNKNOWN_ITEM_TYPE = "Sorry, unknown item type to add.";
    private static final String FILE_ERROR_MESSAGE = "Please ensure that you enter "
            + "the full command for adding files:\n";
    private static final String NOTE_ERROR_MESSAGE = "Please ensure that you enter "
            + "the full command for adding notes:\n";
    private static final String TODO_ERROR_MESSAGE = "Please ensure that you enter "
            + "the full command for adding todo:\n";
    private static final String DEADLINE_ERROR_MESSAGE = "Please ensure that you enter "
            + "the full command for adding deadlines:\n";
    private static final String EVENT_ERROR_MESSAGE = "Please ensure that you enter "
            + "the full command for adding this event type task:\n";
    private static final String MODULE_ERROR_MESSAGE = "Please ensure that you enter "
            + "the full command for adding modules:\n";
    private static final String FILE_FORMAT = "add <moduleCode> / file <fileName>";
    private static final String NOTE_FORMAT = "add <moduleCode> / note <fileName>";
    private static final String TODO_FORMAT = "add <moduleCode> / todo <fileName>";
    private static final String DEADLINE_FORMAT = "add <moduleCode> / deadline <taskName> by: <MM/DD/YYYY HH:MM>";
    private static final String EVENT_FORMAT = "add <moduleCode> / <eventType> <taskName> at: "
        + "<start as MM/DD/YYYY HH:MM> to <end as MM/DD/YYYY HH:MM>";
    private static final String MODULE_FORMAT = "add / module <moduleCode> <moduleName>";
    private static final String EMPTY_TODO_DESCRIPTION = "☹ OOPS!!! The description of a task cannot be empty.";
    private static final String EMPTY_DEADLINE_DESCRIPTION = "☹ OOPS!!! The description of a deadline cannot be empty.";
    private static final String EMPTY_EVENT_DESCRIPTION = "☹ OOPS!!! The description of an event cannot be empty.";
    private static final String EMPTY_EXAM_DESCRIPTION = "☹ OOPS!!! The description of an exam cannot be empty.";
    private static final String EMPTY_LAB_DESCRIPTION = "☹ OOPS!!! The description of a lab session cannot be empty.";
    private static final String EMPTY_LECTURE_DESCRIPTION = "☹ OOPS!!! The description of a lecture cannot be empty.";
    private static final String EMPTY_TUTORIAL_DESCRIPTION = "☹ OOPS!!! The description of a tutorial "
            + "session cannot be empty.";

    private String type;
    private String moduleCode;
    private String content;

    /**
     * Constructor for initialization of variables to support addition of entities.
     * @param pageDataComponents page data components.
     * @param content A string containing the content of the processed user input.
     */
    public AddCommand(String[] pageDataComponents, String content) throws InputException {
        if (pageDataComponents.length > 1) {
            this.moduleCode = pageDataComponents[1];
        }
        this.content = content;
        this.type = content.split(" ")[0].toLowerCase();
    }

    @Override
    public String execute(ModuleContainer moduleContainer, ArrayDeque<String> pageTrace, Ui ui, boolean guiMode) throws
            SpinBoxException {
        File fileAdded;
        Task taskAdded;
        DateTime start;
        DateTime end;
        switch (type) {
        case "file":
            try {
                checkIfOnModulePage(moduleCode);
                if (moduleContainer.checkModuleExists(moduleCode)) {
                    HashMap<String, Module> modules = moduleContainer.getModules();
                    Module module = modules.get(moduleCode);
                    FileList files = module.getFiles();
                    String fileName = content.replace(type, "").trim();
                    fileAdded = files.add(new File(0, fileName));
                    return HORIZONTAL_LINE + "\nAdded into " + module.toString() + " file: " + fileAdded.toString()
                            + "\nYou currently have " + files.getList().size()
                            + ((files.getList().size() == 1) ? " file in the list." : " files in the list.") + "\n"
                            + HORIZONTAL_LINE;
                } else {
                    return NON_EXISTENT_MODULE;
                }
            } catch (IndexOutOfBoundsException e) {
                throw new InputException(FILE_ERROR_MESSAGE + FILE_FORMAT);
            }

        case "note":
            try {
                checkIfOnModulePage(moduleCode);
                if (moduleContainer.checkModuleExists(moduleCode)) {
                    HashMap<String, Module> modules = moduleContainer.getModules();
                    Module module = modules.get(moduleCode);
                    Notepad notepad = module.getNotepad();
                    String noteContent = content.replace(type, "").trim();
                    notepad.addLine(noteContent);
                    return HORIZONTAL_LINE + "\n" + NOTE_ADDED + moduleCode + "\n" + HORIZONTAL_LINE;
                } else {
                    return NON_EXISTENT_MODULE;
                }
            } catch (IndexOutOfBoundsException e) {
                throw new InputException(NOTE_ERROR_MESSAGE + NOTE_FORMAT);
            }

        case "todo":
            try {
                checkIfOnModulePage(moduleCode);
                if (moduleContainer.checkModuleExists(moduleCode)) {
                    HashMap<String, Module> modules = moduleContainer.getModules();
                    Module module = modules.get(moduleCode);
                    TaskList tasks = module.getTasks();
                    String taskDescription = content.replace(type, "").trim();
                    if (taskDescription.equals("")) {
                        throw new InputException(EMPTY_TODO_DESCRIPTION);
                    }
                    taskAdded = tasks.add(new Todo(taskDescription));
                    return HORIZONTAL_LINE + "\nAdded into " + module.toString() + " task: " + taskAdded.toString()
                            + "\nYou currently have " + tasks.getList().size()
                            + ((tasks.getList().size() == 1) ? " task in the list." : " tasks in the list.") + "\n"
                            + HORIZONTAL_LINE;
                } else {
                    return NON_EXISTENT_MODULE;
                }
            } catch (IndexOutOfBoundsException e) {
                throw new InputException(TODO_ERROR_MESSAGE + TODO_FORMAT);
            }

        case "deadline":
            try {
                checkIfOnModulePage(moduleCode);
                if (moduleContainer.checkModuleExists(moduleCode)) {
                    HashMap<String, Module> modules = moduleContainer.getModules();
                    Module module = modules.get(moduleCode);
                    TaskList tasks = module.getTasks();
                    String taskDescription = content.replace(type, "").trim();
                    if (taskDescription.split(" ")[0].equals("by:")) {
                        throw new InputException(EMPTY_DEADLINE_DESCRIPTION);
                    }
                    start = new DateTime(taskDescription.split("by: ")[1]);
                    taskAdded = tasks.add(new Deadline(taskDescription.substring(0,
                            taskDescription.lastIndexOf(" by:")), start));
                    return HORIZONTAL_LINE + "\nAdded into " + module.toString() + " task: " + taskAdded.toString()
                            + "\nYou currently have " + tasks.getList().size()
                            + ((tasks.getList().size() == 1) ? " task in the list." : " tasks in the list.") + "\n"
                            + HORIZONTAL_LINE;
                } else {
                    return NON_EXISTENT_MODULE;
                }
            } catch (IndexOutOfBoundsException e) {
                throw new InputException(DEADLINE_ERROR_MESSAGE + DEADLINE_FORMAT);
            }

        case "event":
        case "exam":
        case "lab":
        case "lecture":
        case "tutorial":
            try {
                checkIfOnModulePage(moduleCode);
                if (moduleContainer.checkModuleExists(moduleCode)) {
                    HashMap<String, Module> modules = moduleContainer.getModules();
                    Module module = modules.get(moduleCode);
                    String taskDescription = content.replace(type, "").trim();
                    if (taskDescription.split(" ")[0].equals("at:")) {
                        if (this.type.equals("event")) {
                            throw new InputException(EMPTY_EVENT_DESCRIPTION);
                        } else if (this.type.equals("exam")) {
                            throw new InputException(EMPTY_EXAM_DESCRIPTION);
                        } else if (this.type.equals("tutorial")) {
                            throw new InputException(EMPTY_TUTORIAL_DESCRIPTION);
                        } else if (this.type.equals("lab")) {
                            throw new InputException(EMPTY_LAB_DESCRIPTION);
                        } else if (this.type.equals("lecture")) {
                            throw new InputException(EMPTY_LECTURE_DESCRIPTION);
                        }
                    }
                    TaskList tasks = module.getTasks();
                    start = new DateTime(taskDescription.split("at: ")[1], 0);
                    end = new DateTime(taskDescription.split("at: ")[1], 1);
                    List<Task> tasksList = tasks.getList();
                    for (int i = 0; i < tasksList.size(); i++) {
                        Task currentTask = tasksList.get(i);
                        if (currentTask.isSchedulable()) {
                            if (((Schedulable) currentTask).isOverlapping(start, end)) {
                                throw new InputException("Time conflicting with:\n"
                                        + "    " + (i + 1) + "." + currentTask.toString() + "\n"
                                        + "Please choose another time interval.");
                            }
                        }
                    }
                    if (this.type.equals("exam")) {
                        taskAdded = tasks.add(new Exam(taskDescription.substring(0,
                                taskDescription.lastIndexOf(" at:")), start, end));
                    } else if (this.type.equals("lab")) {
                        taskAdded = tasks.add(new Lab(taskDescription.substring(0,
                                taskDescription.lastIndexOf(" at:")), start, end));
                    } else if (this.type.equals("lecture")) {
                        taskAdded = tasks.add(new Lecture(taskDescription.substring(0,
                                taskDescription.lastIndexOf(" at:")), start, end));
                    } else if (this.type.equals("tutorial")) {
                        taskAdded = tasks.add(new Tutorial(taskDescription.substring(0,
                                taskDescription.lastIndexOf(" at:")), start, end));
                    } else {
                        taskAdded = tasks.add(new Event(taskDescription.substring(0,
                                taskDescription.lastIndexOf(" at:")), start, end));
                    }
                    return HORIZONTAL_LINE + "\nAdded into " + module.toString() + " task: " + taskAdded.toString()
                            + "\nYou currently have " + tasks.getList().size()
                            + ((tasks.getList().size() == 1) ? " task in the list." : " tasks in the list.") + "\n"
                            + HORIZONTAL_LINE;
                } else {
                    return NON_EXISTENT_MODULE;
                }
            } catch (IndexOutOfBoundsException e) {
                throw new InputException(EVENT_ERROR_MESSAGE + EVENT_FORMAT);
            }

        case "module":
            try {
                String[] contentComponents = content.split(" ", 3);
                moduleCode = contentComponents[1].toUpperCase();
                String moduleName = contentComponents[2];
                if (!moduleContainer.checkModuleExists(moduleCode)) {
                    Module module = new Module(this.moduleCode, moduleName);
                    moduleContainer.addModule(module);
                    return HORIZONTAL_LINE + "\n" + MODULE_ADDED + module.toString() + "\n" + HORIZONTAL_LINE;
                } else {
                    return MODULE_NOT_ADDED;
                }
            } catch (IndexOutOfBoundsException e) {
                throw new InputException(MODULE_ERROR_MESSAGE + MODULE_FORMAT);
            }

        default:
            throw new InputException(UNKNOWN_ITEM_TYPE);
        }
    }
}