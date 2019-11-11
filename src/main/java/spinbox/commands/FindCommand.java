package spinbox.commands;

import spinbox.containers.ModuleContainer;
import spinbox.containers.lists.FileList;
import spinbox.containers.lists.GradeList;
import spinbox.entities.Module;
import spinbox.exceptions.InputException;
import spinbox.containers.lists.TaskList;
import spinbox.Ui;
import spinbox.exceptions.SpinBoxException;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayDeque;
import java.util.HashMap;

public class FindCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(FindCommand.class.getName());
    private static final String LOG_MODULE_CODE = "Module code is ";
    private static final String LOG_NO_MODULE_CODE = "No module code indicated.";
    private static final String LOG_EMPTY_KEYWORD = "Keyword is empty.";
    private static final String LOG_NON_EXISTENT_MODULE = "Module does not exist.";
    private static final String LOG_UNKNOWN_ITEM_TYPE = "Unknown item type.";

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
        LOGGER.setLevel(Level.INFO);
        LOGGER.setUseParentHandlers(true);
        LOGGER.entering(getClass().getName(), "Constructor");

        if (pageDataComponents.length > 1) {
            this.moduleCode = pageDataComponents[1];
            LOGGER.fine(LOG_MODULE_CODE + moduleCode);
        } else {
            LOGGER.severe(LOG_NO_MODULE_CODE);
            throw new InputException(FIND_ERROR_MESSAGE + FIND_FORMAT);
        }

        assert !moduleCode.isEmpty();

        this.content = content;
        this.type = content.split(" ")[0].toLowerCase();

        LOGGER.exiting(getClass().getName(), "Constructor");
    }

    /**
     * Finds the items in the list containing keyword in the name.
     * @param moduleContainer Container of all the modules.
     * @param pageTrace Contains information on the current page.
     * @param ui Instance of UI.
     * @param guiMode Boolean to check if in gui mode.
     * @return The display once it has been changed.
     * @throws SpinBoxException If the item type is unknown.
     */
    @Override
    public String execute(ModuleContainer moduleContainer, ArrayDeque<String> pageTrace, Ui ui, boolean guiMode) throws
            SpinBoxException {
        LOGGER.entering(getClass().getName(), "execute");

        String[] contentComponents = content.split(" ", 2);
        try {
            keyword = contentComponents[1].trim();
            assert !keyword.isEmpty();
        } catch (IndexOutOfBoundsException e) {
            LOGGER.severe(LOG_EMPTY_KEYWORD);
            return FIND_ERROR_MESSAGE + FIND_FORMAT;
        }

        switch (type) {
        case "file":
            checkIfOnModulePage(moduleCode);
            if (moduleContainer.checkModuleExists(moduleCode)) {
                HashMap<String, Module> modules = moduleContainer.getModules();
                Module module = modules.get(moduleCode);
                FileList files = module.getFiles();
                LOGGER.exiting(getClass().getName(), "execute");
                return ui.showFormatted(files.containsKeyword(keyword));
            } else {
                LOGGER.severe(LOG_NON_EXISTENT_MODULE);
                return NON_EXISTENT_MODULE;
            }

        case "task":
            checkIfOnModulePage(moduleCode);
            if (moduleContainer.checkModuleExists(moduleCode)) {
                HashMap<String, Module> modules = moduleContainer.getModules();
                Module module = modules.get(moduleCode);
                TaskList tasks = module.getTasks();
                LOGGER.exiting(getClass().getName(), "execute");
                return ui.showFormatted(tasks.containsKeyword(keyword));
            } else {
                LOGGER.severe(LOG_NON_EXISTENT_MODULE);
                return NON_EXISTENT_MODULE;
            }

        case "grade":
            checkIfOnModulePage(moduleCode);
            if (moduleContainer.checkModuleExists(moduleCode)) {
                HashMap<String, Module> modules = moduleContainer.getModules();
                Module module = modules.get(moduleCode);
                GradeList grades = module.getGrades();
                LOGGER.exiting(getClass().getName(), "execute");
                return ui.showFormatted(grades.containsKeyword(keyword));
            } else {
                LOGGER.severe(LOG_NON_EXISTENT_MODULE);
                return NON_EXISTENT_MODULE;
            }

        default:
            LOGGER.severe(LOG_UNKNOWN_ITEM_TYPE);
            throw new InputException(UNKNOWN_ITEM_TYPE);
        }
    }
}
