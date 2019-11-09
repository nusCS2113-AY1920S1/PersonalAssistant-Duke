package gazeeebo.parsers;

import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;
import gazeeebo.commands.help.HelpCommand;
import gazeeebo.commands.specialization.CompletedCommand;
import gazeeebo.commands.specialization.ListOfSpecializationAndModules;
import gazeeebo.commands.specialization.ListSpecializationCommand;
import gazeeebo.commands.specialization.ModuleCategory;
import gazeeebo.exception.DukeException;
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
     * This method is allows user to call commands to add expenses,
     * find expenses on a certain date, delete a chosen expense,
     * see the expense list and exit the expense page.
     *
     * @param list          list of all tasks
     * @param ui            the object that deals with
     *                      printing things to the user
     * @param storage       the object that deals with storing data
     * @param commandStack
     * @param deletedTask
     * @param triviaManager
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
        HashMap<String, ArrayList<ModuleCategory>> sMap
                = storage.readFromSpecializationFile(); //Read the file
        Map<String, ArrayList<ModuleCategory>> specMap = new TreeMap<>(sMap);
        HashMap<String, ArrayList<String>> eMap
                = storage.readFromCompletedElectivesFile(); //Read the file
        Map<String, ArrayList<String>> completedEMap = new TreeMap<>(eMap);
        new ListOfSpecializationAndModules(specMap);

        System.out.println("Welcome to your specialization page!"
                + "What would you like to do?\n\n");
        String helpSpec = "____________________________"
                + "_____________________________\n"
                + "1. Show list of specializations"
                + " and technical electives : list\n"
                + "2. Key in completed electives: complete\n"
                + "3. List of commands for specialization page: commands\n"
                + "4. Help page: help\n"
                + "5. Exit contact page: esc\n"
                + "____________________________"
                + "______________________________";
        System.out.println(helpSpec);

        ui.readCommand();
        while (!ui.fullCommand.equals("esc")) {
            if (ui.fullCommand.equals("list")) {
                new ListSpecializationCommand(ui, storage,
                        specMap, completedEMap);
            } else if (ui.fullCommand.equals("complete")) {
                new CompletedCommand(ui, storage, specMap, completedEMap);
            } else if (ui.fullCommand.equals("commands")) {
                System.out.println(helpSpec);
            } else if (ui.fullCommand.equals("help")) {
                (new HelpCommand()).execute(null, ui, null,
                        null, null, null);
            } else {
                System.out.println("Command not found, please re-enter!");
            }

            ui.readCommand();
        }
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
                + "To exit: bye\n");
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
