package gazeeebo.parser;

import gazeeebo.triviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;
import gazeeebo.commands.help.HelpCommand;
import gazeeebo.commands.specialization.CompletedCommand;
import gazeeebo.commands.specialization.ListOfSpecializationAndModules;
import gazeeebo.commands.specialization.ListSpecializationCommand;
import gazeeebo.commands.specialization.ModuleCategory;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.CompletedElectivesStorage;
import gazeeebo.storage.SpecializationPageStorage;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Stack;

public class SpecializationCommandParser extends Command {
    /**
     * Shows welcome message.
     */
    private static void showWelcomeMessage() {
        System.out.println("Welcome to your specialization page!"
                + "What would you like to do?\n\n");
    }

    /**
     * Shows main menu page.
     */
    private static void showMainMenu() {
        System.out.println("Going back to Main Menu...\n"
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
                + "To exit: bye\n");
    }

    /**
     * Shows list of commands in specialization page.
     */
    private static void showListOfCommands() {
        System.out.print("____________________________"
                + "_____________________________\n"
                + "1. Show list of specializations"
                + " and technical electives : list\n"
                + "2. Key in completed electives: complete\n"
                + "3. List of commands for specialization page: commands\n"
                + "4. Help page: help\n"
                + "5. Exit contact page: esc\n"
                + "____________________________"
                + "______________________________");
    }

    /**
     * This method is allows user to call commands to add expenses,
     * find expenses on a certain date, delete a chosen expense,
     * see the expense list and exit the expense page.
     *
     * @param list          list of all tasks
     * @param ui            the object that deals with
     *                      printing things to the user
     * @param storage       the object that deals with storing data
     * @param commandStack  Stores the stack of previous commands
     * @param deletedTask   Stores the list of deleted tasks
     * @param triviaManager The object for triviaManager
     * @throws IOException   Catch error if the read file fails
     * @throws DukeException throws a custom exception if
     *                       module index does not exist.
     */
    @Override
    public void execute(final ArrayList<Task> list,
                        final Ui ui, final Storage storage,
                        final Stack<ArrayList<Task>> commandStack,
                        final ArrayList<Task> deletedTask,
                        final TriviaManager triviaManager)
            throws IOException, DukeException {

        SpecializationPageStorage specPageStorage = new SpecializationPageStorage();
        HashMap<String, ArrayList<ModuleCategory>> smap
                = specPageStorage.readFromSpecializationFile(); //Read the file
        Map<String, ArrayList<ModuleCategory>> specMap = new TreeMap<>(smap);

        CompletedElectivesStorage completedElectivesStorage = new CompletedElectivesStorage();
        HashMap<String, ArrayList<String>> emap
                = completedElectivesStorage.readFromCompletedElectivesFile(); //Read the file
        Map<String, ArrayList<String>> completedEMap = new TreeMap<>(emap);

        new ListOfSpecializationAndModules(specMap);
        showWelcomeMessage();
        showListOfCommands();
        ui.readCommand();
        while (!ui.fullCommand.equals("esc")) {
            if (ui.fullCommand.equals("list")) {
                new ListSpecializationCommand(ui, specMap, completedEMap);
            } else if (ui.fullCommand.equals("complete")) {
                new CompletedCommand(ui, specMap, completedEMap);
            } else if (ui.fullCommand.equals("commands")) {
                showListOfCommands();
            } else if (ui.fullCommand.equals("help")) {
                (new HelpCommand()).execute(null, ui, null,
                        null, null, null);
            } else {
                ui.showDontKnowErrorMessage();
            }
            ui.readCommand();
        }
        showMainMenu();
    }

    /**
     * Program does not exit and continues running
     * since command "bye" is not called.
     *
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
