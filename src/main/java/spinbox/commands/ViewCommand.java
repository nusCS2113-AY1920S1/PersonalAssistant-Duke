package spinbox.commands;

import spinbox.DateTime;
import spinbox.containers.ModuleContainer;
import spinbox.entities.Module;
import spinbox.Ui;
import spinbox.exceptions.DateFormatException;
import spinbox.exceptions.InputException;
import spinbox.exceptions.SpinBoxException;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(ViewCommand.class.getName());
    private static final String LOG_INVALID_VIEW_COMMAND = "Invalid format for view command";
    private static final String LOG_PAGE = "Page is ";
    private static final String LOG_MODULE = "Module is ";
    private static final String LOG_TAB = "Tab is ";
    private static final String LOG_NON_EXISTENT_PAGE = "Page does not exist.";
    private static final String LOG_NON_EXISTENT_MODULE = "Module does not exist.";
    private static final String LOG_NON_EXISTENT_TAB = "Tab does not exist.";
    private static final String LOG_ADDED_PAGE = "Added page to page trace.";
    private static final String LOG_ADDED_MODULE_CODE = "Added module cod to page trace.";
    private static final String LOG_ADDED_TAB = "Added tab to page trace.";

    private static final String MISSING_PAGE_INPUT = "Please input the page you want to change to.";
    private static final String SPECIFY_PAGE = "Please specify module before tab.\n"
            + "E.g. 'view / <moduleCode> <tab>'";
    private static final String INCORRECT_VIEW_FORMAT = "Please input correct format for view command.";
    private static final String NON_EXISTENT_PAGE = "Sorry, that page does not exist."
            + " Please choose 'main', 'calendar', or 'modules'.";
    private static final String NON_EXISTENT_MODULE = "Sorry, that module or module tab does not exist. "
            + "These are the current modules:";
    private static final String NON_EXISTENT_TAB = "Sorry, that tab does not exist."
            + " Please choose 'tasks', 'files', 'notes' or 'grades'.";

    private static final int NO_PAGE_INPUT = 0;
    private static final int ONE_PAGE_INPUT = 1;
    private static final int TWO_PAGE_INPUTS = 2;
    private static final int THREE_PAGE_INPUTS = 3;

    private static final String INVALID_MONTH_YEAR = "Sorry, please input valid month and year in the format of"
            + " MM/yyyy.\n"
            + "E.g. 'view / calendar 12/2019'";
    private static final String MONTH_YEAR_REGEX = "\\d{2}/\\d{4}";

    private String page;
    private String moduleCode;
    private String tab;

    /**
     * Constructs by splitting the input and pageTrace and storing it in private variables.
     * @param pageDataComponents The page trace from parser.
     * @param content The content of input.
     * @throws InputException if invalid view command.
     */
    public ViewCommand(String[] pageDataComponents, String content) throws InputException {
        LOGGER.setLevel(Level.INFO);
        LOGGER.setUseParentHandlers(true);
        LOGGER.entering(getClass().getName(), "Constructor");

        String[] contentComponents = content.toLowerCase().split(" ");

        if (contentComponents.length == NO_PAGE_INPUT) {
            LOGGER.severe(MISSING_PAGE_INPUT);
            throw new InputException(MISSING_PAGE_INPUT);
        } else if (contentComponents.length == ONE_PAGE_INPUT) {
            switch (contentComponents[0]) {
            case "main":
                page = "main";
                break;
            case "calendar":
                page = "calendar";
                break;
            case "modules":
                page = "modules";
                break;
            case "tasks":
            case "files":
            case "grades":
                try {
                    moduleCode = pageDataComponents[1];
                } catch (ArrayIndexOutOfBoundsException e) {
                    LOGGER.severe(SPECIFY_PAGE);
                    throw new InputException(SPECIFY_PAGE);
                }

                assert moduleCode.length() > 0 : SPECIFY_PAGE;

                page = "modules";
                moduleCode = pageDataComponents[1];
                tab = contentComponents[0];
                break;
            default:
                page = "modules";
                moduleCode = contentComponents[0];
                tab = "tasks";
            }
        } else if (contentComponents.length == TWO_PAGE_INPUTS) {
            boolean isValidTab = contentComponents[1].equals("tasks") || contentComponents[1].equals("files")
                    || contentComponents[1].equals("grades");
            if (contentComponents[0].equals("modules")) {
                page = "modules";
                moduleCode = contentComponents[1];
                tab = "tasks";
            } else if (isValidTab) {
                page = "modules";
                moduleCode = contentComponents[0];
                tab = contentComponents[1];
            } else if (contentComponents[0].equals("calendar")) {
                page = "calendar";
                tab = contentComponents[1];
            } else {
                LOGGER.severe(LOG_INVALID_VIEW_COMMAND);
                throw new InputException(INCORRECT_VIEW_FORMAT);
            }
        } else if (contentComponents.length == THREE_PAGE_INPUTS) {
            if (contentComponents[0].equals("modules")) {
                page = "modules";
                moduleCode = contentComponents[1];
                tab = contentComponents[2];
            }
        } else {
            LOGGER.severe(LOG_INVALID_VIEW_COMMAND);
            throw new InputException(INCORRECT_VIEW_FORMAT);
        }

        if (moduleCode != null) {
            moduleCode = moduleCode.toUpperCase();
        }

        LOGGER.fine(LOG_PAGE + page);
        LOGGER.fine(LOG_MODULE + moduleCode);
        LOGGER.fine(LOG_TAB + tab);

        LOGGER.exiting(getClass().getName(), "Constructor");
    }

    /**
     * Replace pageTrace with the new pageTrace.
     * @param moduleContainer The container where the modules stored.
     * @param pageTrace The current pageTrace.
     * @param ui The Ui instance.
     * @param guiMode Boolean to check if in gui mode.
     * @return The display once it has been changed.
     * @throws SpinBoxException if page, module, or tab does not exist.
     */
    @Override
    public String execute(ModuleContainer moduleContainer, ArrayDeque<String> pageTrace, Ui ui, boolean guiMode)
            throws SpinBoxException {
        LOGGER.entering(getClass().getName(), "execute");

        ArrayDeque<String> tempPageTrace = pageTrace.clone();
        String oldTrace = "";
        while (tempPageTrace.size() > 0) {
            oldTrace = oldTrace.concat("/" + tempPageTrace.getLast());
            tempPageTrace.removeLast();
        }

        ArrayDeque<String> newPageTrace = new ArrayDeque<>();
        boolean isValidPage = page.equals("main") || page.equals("calendar") || page.equals("modules");
        if (isValidPage) {
            newPageTrace.addFirst(page);
            LOGGER.fine(LOG_ADDED_PAGE);
        } else {
            LOGGER.severe(LOG_NON_EXISTENT_PAGE);
            throw new InputException(NON_EXISTENT_PAGE);
        }

        if (page.equals("modules") && moduleCode != null) {
            if (moduleContainer.checkModuleExists(moduleCode)) {
                newPageTrace.addFirst(moduleCode);
                LOGGER.fine(LOG_ADDED_MODULE_CODE);
            } else {
                String currentModules = "";
                for (HashMap.Entry<String, Module> entry : moduleContainer.getModules().entrySet()) {
                    currentModules = currentModules.concat(entry.getKey() + "\n");
                }
                LOGGER.severe(LOG_NON_EXISTENT_MODULE);
                throw new InputException(NON_EXISTENT_MODULE + "\n" + currentModules);
            }
        }

        List<String> outputList = new ArrayList<>();
        outputList.add("");
        if (page.equals("modules") && tab != null) {
            HashMap<String, Module> modules = moduleContainer.getModules();
            Module module = modules.get(moduleCode);
            switch (tab) {
            case "tasks":
                newPageTrace.addFirst(tab);
                outputList = module.getTasks().viewList();
                break;
            case "files":
                newPageTrace.addFirst(tab);
                outputList = module.getFiles().viewList();
                break;
            case "grades":
                newPageTrace.addFirst(tab);
                outputList = module.getGrades().viewList();
                break;
            case "notes":
                newPageTrace.addFirst(tab);
                outputList = module.getNotepad().viewList();
                break;
            default:
                LOGGER.severe(LOG_NON_EXISTENT_TAB);
                throw new InputException(NON_EXISTENT_TAB);
            }
        }
        LOGGER.fine(LOG_ADDED_TAB);

        if (page.equals("calendar") && tab != null) {
            newPageTrace.addFirst(tab);
            if (!tab.matches(MONTH_YEAR_REGEX)) {
                throw new InputException(INVALID_MONTH_YEAR);
            }
            try {
                String[] monthYear = tab.split("/");
                new DateTime(monthYear[0] + "/01/" + monthYear[1] + " 00:00");
            } catch (DateFormatException e) {
                throw new InputException(INVALID_MONTH_YEAR);
            }
        }

        pageTrace.clear();

        String newTrace = "";
        tempPageTrace = newPageTrace.clone();
        while (tempPageTrace.size() > 0) {
            newTrace = newTrace.concat("/" + tempPageTrace.getLast());
            pageTrace.addFirst(tempPageTrace.getLast());
            tempPageTrace.removeLast();
        }

        if (guiMode) {
            outputList.set(0, newTrace);
            return outputList.get(0);
        } else {
            outputList.set(0, "Changed from page "
                    + oldTrace + " to " + newTrace);
        }

        LOGGER.exiting(getClass().getName(), "execute");
        return ui.showFormatted(outputList);
    }
}