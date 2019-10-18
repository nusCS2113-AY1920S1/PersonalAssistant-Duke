package spinbox.commands;

import spinbox.containers.ModuleContainer;
import spinbox.containers.lists.FileList;
import spinbox.containers.lists.TaskList;
import spinbox.entities.items.File;
import spinbox.entities.Module;
import spinbox.entities.items.tasks.Task;
import spinbox.exceptions.SpinBoxException;
import spinbox.exceptions.InputException;
import spinbox.Ui;

import java.util.ArrayDeque;
import java.util.HashMap;

public class MarkCommand extends Command {
    private static final String NON_EXISTENT_MODULE = "This module does not exist.";
    private static final String FILE_MARKED = "Marked file: ";
    private static final String PROVIDE_INDEX = "Please provide an index to be removed.";
    private static final String INVALID_MARK_FORMAT = "Please use valid remove format:\n"
            + "mark <pageContent> : <type> <index>";
    private static final String INVALID_INDEX = "Please enter a valid index.";
    private String type;

    private String moduleCode;
    private String content;

    /**
     * Constructor for initialization of variables to support marking of entities.
     * @param moduleCode A String denoting the module code.
     * @param content A string containing the content of the processed user input.
     */
    public MarkCommand(String moduleCode, String content) {
        this.moduleCode = moduleCode;
        this.content = content;
        this.type = content.split(" ")[0];
    }

    @Override
    public String execute(ModuleContainer moduleContainer, ArrayDeque<String> pageTrace, Ui ui) throws
            SpinBoxException {
        StringBuilder outputMessage = new StringBuilder();
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
                    File fileMarked = files.get(index);
                    files.mark(index);
                    return FILE_MARKED + fileMarked.toString();
                } catch (NumberFormatException e) {
                    throw new InputException(INVALID_INDEX);
                }
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
                    Task taskMarked = tasks.get(index);
                    tasks.mark(index);
                    outputMessage.append(FILE_MARKED).append(taskMarked.toString()).append("\n");
                    tasks.remove(index);
                    outputMessage.append("This task has been removed from the list.\n");
                    outputMessage.append("You currently have ").append(tasks.getList().size()).append((
                            tasks.getList().size() == 1) ? " task in the list." : " tasks in the list.");
                    return outputMessage.toString();
                } catch (NumberFormatException e) {
                    throw new InputException(INVALID_INDEX);
                }
            } else {
                return NON_EXISTENT_MODULE;
            }
        default:
            throw new InputException(INVALID_MARK_FORMAT);
        }
    }
}