package spinbox.commands;

import spinbox.DateTime;
import spinbox.containers.ModuleContainer;
import spinbox.containers.lists.FileList;
import spinbox.containers.lists.TaskList;
import spinbox.entities.items.File;
import spinbox.entities.Notepad;
import spinbox.entities.Module;
import spinbox.entities.items.tasks.Deadline;
import spinbox.entities.items.tasks.Event;
import spinbox.entities.items.tasks.Exam;
import spinbox.entities.items.tasks.Lab;
import spinbox.entities.items.tasks.Lecture;
import spinbox.entities.items.tasks.Tutorial;
import spinbox.entities.items.tasks.Task;
import spinbox.entities.items.tasks.Todo;
import spinbox.exceptions.SpinBoxException;
import spinbox.exceptions.InputException;
import spinbox.Ui;

import java.util.ArrayDeque;
import java.util.HashMap;

public class SetNameCommand extends Command {
    private static final String HORIZONTAL_LINE = "____________________________________________________________";
    private static final String FILE_SET = "File name successfully changed.\n";
    private static final String NOTE_SET = "Note name successfully changed.\n";
    private static final String TASK_SET = "Task name successfully changed.\n";
    private static final String INVALID_INDEX = "Please enter a valid index.";
    private static final String PROVIDE_INDEX = "Please provide an index to be set.";
    private static final String NON_EXISTENT_MODULE = "This module does not exist.";
    private static final String INVALID_SET_NAME_FORMAT = "Please use the valid set name format:\n"
            + "set-name <pageContent> / <type> <index> to: <description>";
    private static final String CHANGE_FROM = " changed from: ";
    private static final String TO = "\n\t\t\t\t to: ";

    private String type;

    private String moduleCode;
    private String content;

    /**
     * Constructor for initialization of variables to support marking of entities.
     * @param pageDataComponents page data components.
     * @param content A string containing the content of the processed user input.
     */
    public SetNameCommand(String[] pageDataComponents, String content) {
        if (pageDataComponents.length > 1) {
            this.moduleCode = pageDataComponents[1];
        }
        this.content = content;
        this.type = content.split(" ")[0].toLowerCase();
    }

    @Override
    public String execute(ModuleContainer moduleContainer, ArrayDeque<String> pageTrace, Ui ui, boolean guiMode) throws
            SpinBoxException {
        File fileChanged;
        Task taskChanged;
        Notepad noteChanged;
        int doneStatus;
        String replaceName;
        DateTime start;
        DateTime end;

        if (!content.contains("to: ")) {
            throw new InputException(INVALID_SET_NAME_FORMAT);
        }
        switch (type) {
        case "file":
            checkIfOnModulePage(moduleCode);
            if (moduleContainer.checkModuleExists(moduleCode)) {
                try {
                    HashMap<String, Module> modules = moduleContainer.getModules();
                    Module module = modules.get(moduleCode);
                    FileList files = module.getFiles();
                    String[] contentComponents = content.split(" ");
                    int index = Integer.parseInt(contentComponents[1]) - 1;
                    File fileSelected = files.get(index);
                    replaceName = content.split("to: ")[1].trim();
                    if (fileSelected.getDone()) {
                        doneStatus = 1;
                    } else {
                        doneStatus = 0;
                    }
                    files.remove(index);
                    fileChanged = files.add(new File(doneStatus, replaceName));
                    return HORIZONTAL_LINE + "\n" + FILE_SET + "File " + (index + 1) + CHANGE_FROM
                            + fileSelected.toString() + TO + fileChanged.toString() + "\n" + HORIZONTAL_LINE;
                } catch (NumberFormatException e) {
                    throw new InputException(INVALID_INDEX);
                } catch (IndexOutOfBoundsException e) {
                    throw new InputException(PROVIDE_INDEX);
                }
            } else {
                return NON_EXISTENT_MODULE;
            }

        case "note":
            checkIfOnModulePage(moduleCode);
            if (moduleContainer.checkModuleExists(moduleCode)) {
                try {
                    HashMap<String, Module> modules = moduleContainer.getModules();
                    Module module = modules.get(moduleCode);
                    Notepad notepad = module.getNotepad();
                    int index = Integer.parseInt(content.split(" ")[1]) - 1;
                    replaceName = content.split("to: ")[1].trim();
                    String noteSelected = notepad.getLine(index);
                    notepad.removeLine(index);
                    notepad.addLine(replaceName);
                    return HORIZONTAL_LINE + "\n" + NOTE_SET + "Note " + (index + 1) + CHANGE_FROM
                            + noteSelected + TO + replaceName + "\n" + HORIZONTAL_LINE;
                } catch (NumberFormatException e) {
                    throw new InputException(INVALID_INDEX);
                } catch (IndexOutOfBoundsException e) {
                    throw new InputException(PROVIDE_INDEX);
                }
            } else {
                return NON_EXISTENT_MODULE;
            }

        case "task":
            checkIfOnModulePage(moduleCode);
            if (moduleContainer.checkModuleExists(moduleCode)) {
                try {
                    HashMap<String, Module> modules = moduleContainer.getModules();
                    Module module = modules.get(moduleCode);
                    TaskList tasks = module.getTasks();
                    int index = Integer.parseInt(content.split(" ")[1]) - 1;
                    Task taskSelected = tasks.get(index);
                    String taskDescription = taskSelected.toString();
                    String taskType = taskSelected.getTaskType().name();
                    replaceName = content.split("to: ")[1].trim();
                    if (taskSelected.getDone()) {
                        doneStatus = 1;
                    } else {
                        doneStatus = 0;
                    }
                    if (taskType.equals("EVENT") || taskType.equals("EXAM") || taskType.equals("LAB")
                            || taskType.equals("LECTURE") || taskType.equals("TUTORIAL")) {
                        start = new DateTime(taskDescription.substring(taskDescription.lastIndexOf("(at: "),
                                taskDescription.lastIndexOf(" to")));
                        end = new DateTime(taskDescription.substring(taskDescription.lastIndexOf("to ")));
                    } else if (taskType.equals("DEADLINE")) {
                        start = new DateTime(taskDescription.substring(taskDescription.lastIndexOf("(by: ")));
                        end = null;
                    } else {
                        start = null;
                        end = null;
                    }
                    tasks.remove(index);
                    if (taskType.equals("TODO")) {
                        taskChanged = tasks.add(new Todo(doneStatus, replaceName));
                    } else if (taskType.equals("DEADLINE")) {
                        taskChanged = tasks.add(new Deadline(doneStatus, replaceName, start));
                    } else if (taskType.equals("EVENT")) {
                        taskChanged = tasks.add(new Event(doneStatus, replaceName, start, end));
                    } else if (taskType.equals("EXAM")) {
                        taskChanged = tasks.add(new Exam(doneStatus, replaceName, start, end));
                    } else if (taskType.equals("LAB")) {
                        taskChanged = tasks.add(new Lab(doneStatus, replaceName, start, end));
                    } else if (taskType.equals("LECTURE")) {
                        taskChanged = tasks.add(new Lecture(doneStatus, replaceName, start, end));
                    } else {
                        taskChanged = tasks.add(new Tutorial(doneStatus, replaceName, start, end));
                    }
                    return HORIZONTAL_LINE + "\n" + TASK_SET + "Task " + (index + 1) + CHANGE_FROM
                            + taskSelected.toString() + TO + taskChanged.toString() + "\n" + HORIZONTAL_LINE;
                } catch (NumberFormatException e) {
                    throw new InputException(INVALID_INDEX);
                } catch (IndexOutOfBoundsException e) {
                    throw new InputException(PROVIDE_INDEX);
                }
            } else {
                return NON_EXISTENT_MODULE;
            }
        default:
            throw new InputException(INVALID_SET_NAME_FORMAT);
        }
    }
}