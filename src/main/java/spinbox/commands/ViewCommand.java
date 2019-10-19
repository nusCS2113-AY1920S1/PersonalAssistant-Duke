package spinbox.commands;

import spinbox.containers.ModuleContainer;
import spinbox.entities.Module;
import spinbox.Ui;
import spinbox.exceptions.InputException;
import spinbox.exceptions.SpinBoxException;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewCommand extends Command {
    private String page;
    private String moduleCode;
    private String tab;

    /**
     * Constructs by splitting the input and pageTrace and storing it in private variables.
     * @param pageDataComponents the page trace from parser.
     * @param content the content of input.
     * @throws InputException if invalid view command.
     */
    public ViewCommand(String[] pageDataComponents, String content) throws InputException {
        String[] contentComponents = content.toLowerCase().split(" ");

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
            case "notes":
                // check if on a module page first
                try {
                    moduleCode = pageDataComponents[1];
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new InputException("Please specify module before tab.\n"
                        + "E.g. 'view / <moduleCode> <tab>'");
                }
                page = "modules";
                moduleCode = pageDataComponents[1];
                tab = contentComponents[0];
                break;
            // content is module code
            default:
                page = "modules";
                moduleCode = contentComponents[0];
            }
        // can be 'modules <moduleCode>' or '<moduleCode> tab'
        } else if (contentComponents.length == 2) {
            if (contentComponents[0].equals("modules")) {
                page = "modules";
                moduleCode = contentComponents[1];
            } else if (contentComponents[1].equals("tasks") || contentComponents[1].equals("files")
                    || contentComponents[1].equals("grades") || contentComponents[1].equals("notes")) {
                page = "modules";
                moduleCode = contentComponents[0];
                tab = contentComponents[1];
            } else {
                throw new InputException("Please input correct format for view command.");
            }
        // modules <moduleCode> <tab>
        } else if (contentComponents.length == 3) {
            if (contentComponents[0].equals("modules")) {
                page = "modules";
                moduleCode = contentComponents[1];
                tab = contentComponents[2];
            }
        } else {
            throw new InputException("Please input correct format for view command.");
        }
    }

    /**
     * Replace pageTrace with the new pageTrace.
     * @param moduleContainer the modules stored.
     * @param pageTrace the current pageTrace.
     * @param ui the Ui instance.
     * @return the display once been changed..
     * @throws SpinBoxException if page, module, or tab does not exist.
     */
    @Override
    public String execute(ModuleContainer moduleContainer, ArrayDeque<String> pageTrace, Ui ui)
            throws SpinBoxException {
        ArrayDeque<String> tempPageTrace = pageTrace.clone();
        StringBuilder oldTrace = new StringBuilder();
        while (tempPageTrace.size() > 0) {
            oldTrace.append("/").append(tempPageTrace.getLast());
            tempPageTrace.removeLast();
        }

        ArrayDeque<String> newPageTrace = new ArrayDeque<>();
        // add page
        if (page.equals("main") || page.equals("calendar") || page.equals("modules")) {
            newPageTrace.addFirst(page);
        } else {
            throw new InputException("Sorry, that page does not exist."
                    + " Please choose 'main', 'calendar', or 'modules'.");
        }

        // add module if exists
        if (page.equals("modules") && moduleCode != null) {
            // check if module exists
            if (moduleContainer.checkModuleExists(moduleCode)) {
                newPageTrace.addFirst(moduleCode);
            } else {
                String currentModules = "";
                for (HashMap.Entry<String, Module> entry : moduleContainer.getModules().entrySet()) {
                    currentModules = currentModules.concat(entry.getKey() + "\n");
                }
                throw new InputException("Sorry, that module does not exist. These are the current modules:\n"
                    + currentModules);
            }
        }

        List<String> outputList = new ArrayList<>();
        outputList.add("First line");
        // add tab
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
                throw new InputException("Sorry, that tab does not exist."
                        + " Please choose 'tasks', 'files', 'notes' or 'grades'.");
            }
        }

        pageTrace.clear();

        StringBuilder newTrace = new StringBuilder();
        tempPageTrace = newPageTrace.clone();
        while (tempPageTrace.size() > 0) {
            newTrace.append("/").append(tempPageTrace.getLast());
            pageTrace.addFirst(tempPageTrace.getLast());
            tempPageTrace.removeLast();
        }

        outputList.set(0, "Changed from page "
                + oldTrace.toString() + " to " + newTrace.toString());

        return ui.showFormatted(outputList);
    }
}