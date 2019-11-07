package gazeeebo.commands.specialization;

import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Stack;

public class SpecializationCommand extends Command {
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
                = storage.Specialization(); //Read the file
        Map<String, ArrayList<ModuleCategory>> specMap = new TreeMap<>(sMap);
        HashMap<String, ArrayList<String>> eMap
                = storage.completedElectives(); //Read the file
        Map<String, ArrayList<String>> completedEMap = new TreeMap<>(eMap);
        new ListOfSpecializationAndModules(specMap);

        System.out.print("Welcome to your specialization page!"
                + "What would you like to do?\n\n");
        System.out.println("____________________________"
                + "_____________________________");
        System.out.println("1. Show list of specializations"
                + "and technical electives : list");
        System.out.println("2. Key in completed electives: complete");
        System.out.println("3. Exit contact page: esc");
        System.out.println("____________________________"
                + "______________________________");


        ui.readCommand();
        while (!ui.fullCommand.equals("esc")) {
            if (ui.fullCommand.equals("list")) {
                new ListSpecializationCommand(ui, storage,
                        specMap, completedEMap);
            } else if (ui.fullCommand.equals("complete")) {
                new CompletedCommand(ui, storage, specMap, completedEMap);
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
