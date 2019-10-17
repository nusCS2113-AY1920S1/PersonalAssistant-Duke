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

public class RemoveCommand extends Command {
    private static final String MODULE_NOT_EXISTS = "This module does not exist.";
    private String type;
    private String fullCommand;

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
        // remove files
        case "file":
            if (moduleContainer.checkModuleExists(moduleCode)) {
                try {
                    HashMap<String, Module> modules = moduleContainer.getModules();
                    Module module = modules.get(moduleCode);
                    FileList files = module.getFiles();
                    int index = Integer.parseInt(content.split(" ")[1]) - 1;
                    File fileRemoved = files.get(index);
                    files.remove(index);
                    return "Removed file: " + fileRemoved.toString();
                } catch (NumberFormatException e) {
                    throw new InputException("Please enter a valid index.");
                }
            } else {
                return MODULE_NOT_EXISTS;
            }
        default:
            throw new InputException("Please use valid remove format:\n"
                + "remove <pageContent> : <type> <index>");
        }
    }
}