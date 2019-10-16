package spinbox.commands;

import spinbox.entities.Module;
import spinbox.Ui;
import spinbox.exceptions.InputException;
import spinbox.exceptions.SpinBoxException;

import java.util.ArrayDeque;
import java.util.HashMap;

public class ViewCommand extends Command {
    private String page;
    private String moduleCode;
    private String tab;

    /**
     * Constructs by splitting the input and pageTrace and storing it in private variables.
     * @param pageData the page trace from parser.
     * @param content the content of input.
     * @throws InputException if invalid view command.
     */
    public ViewCommand(String pageData, String content) throws InputException {
        String[] pageDataComponents = pageData.split(" ");
        String[] contentComponents = content.split(" ");

        if (contentComponents.length == 0) {
            throw new InputException("Please input the page you want to change to.");
        // can be page, module, or tab
        } else if (contentComponents.length == 1) {
            switch (contentComponents[0]) {
            // content is page
            case "main":
                page = "main";
                break;
            case "calendar":
                page = "calendar";
                break;
            case "modules":
                page = "modules";
                break;
            // content is tab
            case "tasks":
            case "files":
            case "grades":
                // check if on a module page first
                try {
                    moduleCode = pageDataComponents[1];
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new InputException("Please specify module before tab.\n"
                        + "E.g. 'view : <moduleCode> <tab>'");
                }
                tab = contentComponents[0];
                break;
            // content is module code
            default:
                // check that you are on modules page first
                if (pageDataComponents[0].equals("modules")) {
                    moduleCode = contentComponents[0];
                } else {
                    throw new InputException("Please be on modules page first.\n"
                        + "E.g. 'view : modules <moduleCode>'");
                }
            }
        // can be 'modules <moduleCode>' or '<moduleCode> tab'
        } else if (contentComponents.length == 2) {
            if (contentComponents[0].equals("modules")) {
                page = "modules";
                moduleCode = contentComponents[1];
            } else if (contentComponents[1].equals("tasks") || contentComponents[1].equals("files")
                    || contentComponents[1].equals("grades")) {
                page = "modules";
                moduleCode = contentComponents[0];
                tab = contentComponents[1];
            } else {
                throw new InputException("Please input correct format for view command.");
            }
        }
    }

    /**
     * Replace pageTrace with the new pageTrace.
     * @param modules the modules stored.
     * @param pageTrace the current pageTrace.
     * @param ui the Ui instance.
     * @return the display once been changed..
     * @throws SpinBoxException if page, module, or tab does not exist.
     */
    public String execute(HashMap<String, Module> modules, ArrayDeque<String> pageTrace, Ui ui)
            throws SpinBoxException {
        StringBuilder oldTrace = new StringBuilder();
        while (pageTrace.size() > 0) {
            oldTrace.append("/").append(pageTrace.getLast());
            pageTrace.removeLast();
        }

        // add page
        if (page.equals("main") || page.equals("calendar") || page.equals("modules")) {
            pageTrace.addFirst(page);
        } else {
            throw new InputException("Sorry, that page does not exist."
                    + " Please choose 'main', 'calendar', or 'modules'.");
        }

        // add module if exists
        if (page.equals("modules") && moduleCode != null) {
            // check if module exists
            if (modules.containsKey(moduleCode)) {
                pageTrace.addFirst(moduleCode);
            } else {
                String currentModules = "";
                for (HashMap.Entry<String, Module> entry : modules.entrySet()) {
                    currentModules = currentModules.concat(entry.getKey() + "\n");
                }
                throw new InputException("Sorry, that module does not exist. These are the current modules\n"
                    + currentModules);
            }
        }

        // add tab
        if (page.equals("modules") && tab != null) {
            if (tab.equals("tasks") || tab.equals("files") || tab.equals("grades")) {
                pageTrace.addFirst(tab);
            } else {
                throw new InputException("Sorry, that tab does not exist."
                        + " Please choose 'tasks', 'files', or 'grades'.");
            }
        }

        StringBuilder newTrace = new StringBuilder();
        ArrayDeque<String> tempPageTrace = pageTrace.clone();
        while (tempPageTrace.size() > 0) {
            newTrace.append("/").append(tempPageTrace.getLast());
            tempPageTrace.removeLast();
        }

        return ui.showFormatted("Changed from page "
                + oldTrace.toString() + " to " + newTrace.toString());
    }
}