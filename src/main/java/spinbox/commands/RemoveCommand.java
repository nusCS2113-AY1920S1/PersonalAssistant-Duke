package spinbox.commands;

import spinbox.containers.ModuleContainer;
import spinbox.containers.lists.FileList;
import spinbox.containers.lists.TaskList;
import spinbox.entities.Notepad;
import spinbox.entities.items.File;
import spinbox.entities.Module;
import spinbox.entities.items.tasks.Task;
import spinbox.exceptions.SpinBoxException;
import spinbox.exceptions.InputException;
import spinbox.Ui;

import java.util.ArrayDeque;
import java.util.HashMap;

public class RemoveCommand extends Command {
    private static final String NON_EXISTENT_MODULE = "This module does not exist.";
    private static final String NOTE_REMOVED = "A note has been successfully removed from ";
    private static final String PROVIDE_INDEX = "Please provide an index to be removed.";
    private static final String INVALID_REMOVE_FORMAT = "Please use valid remove format:\n"
            + "remove <pageContent> : <type> <index>";
    private static final String INVALID_INDEX = "Please enter a valid index.";
    private String type;

    private String moduleCode;
    private String content;

    /**
     * Constructor for initialization of variables to support removal of entities.
     * @param moduleCode A String denoting the module code.
     * @param content A string containing the content of the processed user input.
     */
    public RemoveCommand(String moduleCode, String content) {
        this.moduleCode = moduleCode;
        this.content = content;
        this.type = content.split(" ")[0];
    }

    @Override
    public String execute(ModuleContainer moduleContainer, ArrayDeque<String> pageTrace, Ui ui) throws
            SpinBoxException {
        switch (type) {
        case "file":
            if (moduleContainer.checkModuleExists(moduleCode)) {
                try {
                    HashMap<String, Module> modules = moduleContainer.getModules();
                    Module module = modules.get(moduleCode);
                    FileList files = module.getFiles();
                    if (content.split(" ").length == 1) {
                        throw new InputException(PROVIDE_INDEX);
                    }
                    int index = Integer.parseInt(content.split(" ")[1]) - 1;
                    File fileRemoved = files.get(index);
                    files.remove(index);
                    return "Removed file: " + fileRemoved.toString() + "\n"
                            + "You currently have " + files.getList().size()
                            + ((files.getList().size() == 1) ? " file in the list." : " files in the list.");
                } catch (NumberFormatException e) {
                    throw new InputException(INVALID_INDEX);
                }
            } else {
                return NON_EXISTENT_MODULE;
            }

        case "note":
            if (moduleContainer.checkModuleExists(moduleCode)) {
                HashMap<String, Module> modules = moduleContainer.getModules();
                Module module = modules.get(moduleCode);
                Notepad notepad = module.getNotepad();
                if (content.split(" ").length == 1) {
                    throw new InputException(PROVIDE_INDEX);
                }
                int index = Integer.parseInt(content.split(" ")[1]) - 1;
                notepad.removeLine(index);
                return NOTE_REMOVED + moduleCode;
            } else {
                return NON_EXISTENT_MODULE;
            }

        case "task":
            if (moduleContainer.checkModuleExists(moduleCode)) {
                try {
                    HashMap<String, Module> modules = moduleContainer.getModules();
                    Module module = modules.get(moduleCode);
                    TaskList tasks = module.getTasks();
                    if (content.split(" ").length == 1) {
                        throw new InputException(PROVIDE_INDEX);
                    }
                    int index = Integer.parseInt(content.split(" ")[1]) - 1;
                    Task taskRemoved = tasks.get(index);
                    tasks.remove(index);
                    return "Removed task: " + taskRemoved.toString() + "\n"
                            + "You currently have " + tasks.getList().size()
                            + ((tasks.getList().size() == 1) ? " task in the list." : " tasks in the list.");
                } catch (NumberFormatException e) {
                    throw new InputException(INVALID_INDEX);
                }
            } else {
                return NON_EXISTENT_MODULE;
            }

        default:
            throw new InputException(INVALID_REMOVE_FORMAT);
        }
    }
}