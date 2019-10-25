package spinbox.commands;

import spinbox.containers.ModuleContainer;
import spinbox.containers.lists.FileList;
import spinbox.entities.Module;
import spinbox.exceptions.InputException;
import spinbox.storage.Storage;
import spinbox.containers.lists.TaskList;
import spinbox.entities.items.tasks.Task;
import spinbox.Ui;
import spinbox.exceptions.SpinBoxException;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FindCommand extends Command {
    private static final String UNKNOWN_ITEM_TYPE = "Sorry, unknown item type to add.";
    private static final String FIND_ERROR_MESSAGE = "Please ensure that you enter "
            + "the full command for find commands:\n";
    private static final String FIND_FORMAT = "find <moduleCode> / <type> <keyword>\n";
    private static final String NON_EXISTENT_MODULE = "This module does not exist.";

    private String type;
    private String moduleCode;
    private String content;
    private String keyword;

    /**
     * Constructor for finding tasks using a keyword.
     * @param pageDataComponents pageDataComponents.
     * @param content A string containing the content of the processed user input.
     * @throws InputException missing keyword
     */
    public FindCommand(String[] pageDataComponents, String content) throws InputException {
        if (pageDataComponents.length > 1) {
            this.moduleCode = pageDataComponents[1];
        }
        this.content = content;
        this.type = content.split(" ")[0].toLowerCase();
    }

    /**
     * Searches for tasks using a keyword.
     * @param taskList TaskList instance
     * @param storage Storage instance
     * @param ui Ui instance
     * @param guiMode boolean to check if in gui mode.
     */
    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui, boolean guiMode) {
        int resultCount = 0;
        ArrayList<String> formattedOutput = new ArrayList<>();
        formattedOutput.add("Here are the matching tasks in your list:");
        List<Task> tasks = taskList.getList();

        for (Task task : tasks) {
            if (task.getName().contains(keyword)) {
                formattedOutput.add((resultCount++ + 1) + ". " + task.toString());
            }
        }
        return ui.showFormatted(formattedOutput);
    }

    @Override
    public String execute(ModuleContainer moduleContainer, ArrayDeque<String> pageTrace, Ui ui, boolean guiMode) throws
            SpinBoxException {
        String[] contentComponents = content.split(" ", 2);
        try {
            keyword = contentComponents[1];
        } catch (IndexOutOfBoundsException e) {
            return FIND_ERROR_MESSAGE + FIND_FORMAT;
        }

        switch (type) {
        case "file":
            checkIfOnModulePage(moduleCode);
            if (moduleContainer.checkModuleExists(moduleCode)) {
                HashMap<String, Module> modules = moduleContainer.getModules();
                Module module = modules.get(moduleCode);
                FileList files = module.getFiles();
                return ui.showFormatted(files.containsKeyword(keyword));
            } else {
                return NON_EXISTENT_MODULE;
            }

        case "task":
            checkIfOnModulePage(moduleCode);
            if (moduleContainer.checkModuleExists(moduleCode)) {
                HashMap<String, Module> modules = moduleContainer.getModules();
                Module module = modules.get(moduleCode);
                TaskList tasks = module.getTasks();
                return ui.showFormatted(tasks.containsKeyword(keyword));
            } else {
                return NON_EXISTENT_MODULE;
            }

        default:
            throw new InputException(UNKNOWN_ITEM_TYPE);
        }
    }
}
