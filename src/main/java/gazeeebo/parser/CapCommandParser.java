//@@author JasonLeeWeiHern

package gazeeebo.parser;

import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;
import gazeeebo.commands.capCalculator.CalculateCapCommand;
import gazeeebo.commands.capCalculator.AddCapCommand;
import gazeeebo.commands.capCalculator.DeleteCapCommand;
import gazeeebo.commands.capCalculator.ListCapCommand;
import gazeeebo.commands.capCalculator.FindCapCommand;
import gazeeebo.commands.help.HelpCommand;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.CapPageStorage;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Deals with the user in the main CAP page.
 */
public class CapCommandParser extends Command {
    private static final Logger LOGGER = Logger.getLogger(CapCommandParser.class.getName());
    /**
     * module name of the module.
     */
    public String moduleCode;
    /**
     * Modular Credit of the module.
     */
    public int moduleCredit;
    /**
     * Alphabetical score for the module.
     */
    public String grade;

    /** Back to main menu message.*/
    private static final String MAIN_MENU_PAGE = "Going back to Main Menu...\n"
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
            + "9. notes\n"
            + "10. change password\n"
            + "To exit: bye\n";

    /** Welcome message.*/
    private static final String WELCOME = "Welcome to your CAP Calculator page! "
            + "What would you like to do?\n\n";

    /**
     * Constructor for CapCommandParser.
     *
     * @param moduleCode   name of the module
     * @param moduleCredit about of Modular Credit of the module
     * @param grade        Alphabetical score attained
     */
    public CapCommandParser(final String moduleCode,
                            final int moduleCredit, final String grade) {
        this.moduleCode = moduleCode;
        this.moduleCredit = moduleCredit;
        this.grade = grade;
    }

    /**
     * Shows the list of commands for CAP page.
     */
    public static void showListOfCommands() {
        System.out.print("__________________"
                + "________________________________________\n"
                + "1. Add module: add semester number,"
                + "module's code, module's credit, module's grade\n"
                + "2. Find module: find moduleCode\n"
                + "3. Delete a module: delete module\n"
                + "4. See your CAP list: list all/semester number\n"
                + "5. List of commands for CAP page: commands\n"
                + "6. Help page: help\n"
                + "7. Exit CAP page: esc\n"
                + "_________________"
                + "_________________________________________\n\n");
    }

    /**
     * Decodes the command input in the CAP page.
     */
    @Override
    public void execute(final ArrayList<Task> list,
                        final Ui ui, final Storage storage,
                        final Stack<ArrayList<Task>> commandStack,
                        final ArrayList<Task> deletedTask,
                        final TriviaManager triviaManager)
            throws DukeException, ParseException,
            IOException, NullPointerException {
        try {
            CapPageStorage capPageStorage = new CapPageStorage();
            HashMap<String, ArrayList<CapCommandParser>> map
                    = capPageStorage.readFromCapFile(); //Read the file
            Map<String, ArrayList<CapCommandParser>> caplist
                    = new TreeMap<>(map);
            System.out.print(WELCOME);
            showListOfCommands();
            String lineBreak = "------------------------------\n";
            ui.readCommand();
            while (!(ui.fullCommand.equals("esc")
                    || ui.fullCommand.equals("7"))) {
                double cap = new CalculateCapCommand().calculateCap(caplist);
                if (ui.fullCommand.split(" ")[0].equals("add")
                        || ui.fullCommand.equals("1")) {
                    new AddCapCommand(ui, caplist);
                } else if (ui.fullCommand.split(" ")[0].equals("find")
                        || ui.fullCommand.equals("2")) {
                    new FindCapCommand(ui, caplist, lineBreak);
                } else if (ui.fullCommand.split(" ")[0].equals("list")
                        || ui.fullCommand.equals("4")) {
                    new ListCapCommand(ui, caplist, lineBreak);
                } else if (ui.fullCommand.split(" ")[0].equals("delete")
                        || ui.fullCommand.equals("3")) {
                    new DeleteCapCommand(ui, caplist);
                } else if (ui.fullCommand.split(" ")[0].equals("help")
                        || ui.fullCommand.equals("6")) {
                    (new HelpCommand()).execute(null, ui, null,
                            null, null, null);
                } else if (ui.fullCommand.equals("commands")
                        || ui.fullCommand.equals("5")) {
                    showListOfCommands();
                } else {
                    ui.showDontKnowErrorMessage();
                }
                String toStore = "";
                for (String key : caplist.keySet()) {
                    for (int i = 0; i < caplist.get(key).size(); i++) {
                        toStore = toStore.concat(key + "|"
                                + caplist.get(key).get(i).moduleCode
                                + "|" + caplist.get(key).get(i).moduleCredit
                                + "|" + caplist.get(key).get(i).grade + "\n");


                    }
                }
                capPageStorage.writeToCapFile(toStore);
                System.out.println("What do you want to do next ?");
                ui.readCommand();
            }
            System.out.print(MAIN_MENU_PAGE);
        } catch (IOException | NumberFormatException
                | ArrayIndexOutOfBoundsException e) {
            System.out.println("Error in CAP.txt");
            LOGGER.log(Level.WARNING, "Error in CAP.txt", e);
        }
    }

    /**
     * Exits program.
     *
     * @return true to exit
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
