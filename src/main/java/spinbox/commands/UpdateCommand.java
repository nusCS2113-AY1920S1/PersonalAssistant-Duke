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

public class UpdateCommand extends Command {
    private static final String HORIZONTAL_LINE = "____________________________________________________________";
    private static final String NON_EXISTENT_MODULE = "This module does not exist.";
    private static final String FILE_MARKED = "Updated file: ";
    private static final String TASK_MARKED = "Updated task: ";
    private static final String PROVIDE_INDEX = "Please provide an index of item to be updated.";
    private static final String INVALID_MARK_FORMAT = "Please use valid update format:\n"
            + "update <pageContent> / <type> <index> <booleanValue>";
    private static final String INVALID_INDEX = "Please enter a valid index.";
    private static final String INVALID_VALUE = "PLease enter a valid boolean value.";
    private String type;

    private String moduleCode;
    private String content;

    /**
     * Constructor for initialization of variables to support marking of entities.
     * @param pageDataComponents page data components.
     * @param content A string containing the content of the processed user input.
     */
    public UpdateCommand(String[] pageDataComponents, String content) {
        if (pageDataComponents.length > 1) {
            this.moduleCode = pageDataComponents[1];
        }
        this.content = content;
        this.type = content.split(" ")[0].toLowerCase();
    }

    @Override
    public String execute(ModuleContainer moduleContainer, ArrayDeque<String> pageTrace, Ui ui, boolean guiMode) throws
            SpinBoxException {
        String outputMessage = "";
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
                    File fileMarked = files.get(index);
                    if (contentComponents[2].toLowerCase().equals("true")) {
                        files.update(index, true);
                    } else if (contentComponents[2].toLowerCase().equals("false")) {
                        files.update(index, false);
                    } else {
                        throw new InputException(INVALID_VALUE);
                    }
                    return HORIZONTAL_LINE + "\n" + FILE_MARKED + fileMarked.toString() + "\n" + HORIZONTAL_LINE;
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
                    Task taskMarked = tasks.get(index);
                    String[] contentComponents = content.split(" ");
                    if (contentComponents[2].toLowerCase().equals("true")) {
                        tasks.update(index, true);
                    } else if (contentComponents[2].toLowerCase().equals("false")) {
                        tasks.update(index, false);
                    } else {
                        throw new InputException(INVALID_VALUE);
                    }
                    outputMessage = outputMessage.concat(HORIZONTAL_LINE + "\n" + TASK_MARKED + taskMarked.toString()
                            + "\n" + HORIZONTAL_LINE);
                    return outputMessage;
                } catch (NumberFormatException e) {
                    throw new InputException(INVALID_INDEX);
                } catch (IndexOutOfBoundsException e) {
                    throw new InputException(PROVIDE_INDEX);
                }
            } else {
                return NON_EXISTENT_MODULE;
            }
        default:
            throw new InputException(INVALID_MARK_FORMAT);
        }
    }
}