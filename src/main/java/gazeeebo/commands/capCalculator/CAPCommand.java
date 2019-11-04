package gazeeebo.commands.capCalculator;

import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Stack;

/**
 * Deals with the user in the main CAP page.
 */
public class CAPCommand extends Command {
    /** module name of the module. */
    protected String moduleCode;
    /** Modular Credit of the module. */
    protected int moduleCredit;
    /** Alphabetical score for the module. */
    protected String grade;
    /**
     * Constructor for CAPCommand.
     * @param moduleCode name of the module
     * @param moduleCredit about of Modular Credit of the module
     * @param grade Alphabetical score attained
     */
    public CAPCommand(final String moduleCode,
                      final int moduleCredit, final String grade) {
        this.moduleCode = moduleCode;
        this.moduleCredit = moduleCredit;
        this.grade = grade;
    }

    /** Decodes the command input in the CAP page. */
    @Override
    public void execute(final ArrayList<Task> list,
                        final  Ui ui, final Storage storage,
                        final Stack<ArrayList<Task>> commandStack,
                        final ArrayList<Task> deletedTask,
                        final TriviaManager triviaManager)
            throws DukeException, ParseException,
            IOException, NullPointerException {
        String helpCAP = "__________________"
                + "________________________________________\n"
                + "1. Add module: add\n"
                + "2. Find module: find moduleCode\n"
                + "3. Delete a module: delete module\n"
                + "4. See your CAP list: list\n"
                + "5. Help Command: help\n"
                + "6. Exit CAP page: esc\n"
                + "_________________"
                + "_________________________________________\n\n";
        System.out.print("Welcome to your CAP Calculator page! "
                + "What would you like to do?\n\n");
        System.out.print(helpCAP);
        HashMap<String, ArrayList<CAPCommand>> map
                = storage.readFromCAPFile(); //Read the file
        Map<String, ArrayList<CAPCommand>> caplist = new TreeMap<>(map);
        String lineBreak = "------------------------------\n";
        ui.readCommand();
        while (!(ui.fullCommand.equals("esc") || ui.fullCommand.equals("6"))) {
            try {
                double cap = new CalculateCAPCommand().calculateCAP(caplist);
                if (ui.fullCommand.split(" ")[0].equals("add")
                        || ui.fullCommand.equals("1")) {
                    new AddCAPCommand(ui, caplist);
                } else if (ui.fullCommand.split(" ")[0].equals("find")
                        || ui.fullCommand.equals("2")) {
                    new FindCAPCommand(ui, caplist, lineBreak);
                } else if (ui.fullCommand.split(" ")[0].equals("list")
                        || ui.fullCommand.equals("4")) {
                    new ListCAPCommand(ui, caplist, lineBreak);
                } else if (ui.fullCommand.split(" ")[0].equals("delete")
                        || ui.fullCommand.equals("3")) {
                    new DeleteCAPCommand(ui, caplist);
                } else if (ui.fullCommand.equals("help")
                        || ui.fullCommand.equals("5")) {
                    System.out.println(helpCAP);
                } else {
                    throw new ArrayIndexOutOfBoundsException();
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Command not found:\n" + helpCAP);
            } finally {
                String toStore = "";
                for (String key : caplist.keySet()) {
                    for (int i = 0; i < caplist.get(key).size(); i++) {
                        toStore = toStore.concat(key + "|"
                                + caplist.get(key).get(i).moduleCode
                                + "|" + caplist.get(key).get(i).moduleCredit
                                + "|" + caplist.get(key).get(i).grade + "\n");

                    }

                }
                storage.writeToCAPFile(toStore);
                System.out.println("What do you want to do next ?");
                ui.readCommand();
            }
        }
        System.out.print("Go back to Main Menu...\n"
                + "Content Page:\n"
                + "------------------ \n"
                + "1. help\n"
                + "2. contacts\n"
                + "3. expenses\n"
                + "4. places\n"
                + "5. tasks\n"
                + "6. cap\n"
                + "7. spec\n"
                + "8. moduleplanner\n"
                + "9. notes\n");
    }

    /** */
    @Override
    public boolean isExit() {
        return false;
    }
}
