package spinbox.commands;

import spinbox.containers.ModuleContainer;
import spinbox.containers.lists.FileList;
import spinbox.entities.items.File;
import spinbox.entities.Module;
import spinbox.exceptions.SpinBoxException;
import spinbox.exceptions.InputException;
import spinbox.Ui;

import java.util.ArrayDeque;
import java.util.HashMap;

public class MarkCommand extends Command {
    private static final String NON_EXISTENT_MODULE = "This module does not exist.";
    private static final String FILE_MARKED = "Marked file: ";
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

        switch (type) {
        case "file":
            if (moduleContainer.checkModuleExists(moduleCode)) {
                try {
                    HashMap<String, Module> modules = moduleContainer.getModules();
                    Module module = modules.get(moduleCode);
                    FileList files = module.getFiles();
                    int index = Integer.parseInt(content.split(" ")[1]) - 1;
                    File fileMarked = files.get(index);
                    files.mark(index);
                    return FILE_MARKED + fileMarked.toString();
                } catch (NumberFormatException e) {
                    throw new InputException("Please enter a valid index.");
                }
            } else {
                return NON_EXISTENT_MODULE;
            }
        default:
            throw new InputException("Please use valid remove format:\n"
                    + "remove <pageContent> : <type> <index>");
        }
    }
}