package gazeeebo.commands.specialization;

import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;

import java.io.IOException;
import java.util.*;

public class SpecializationCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<ArrayList<Task>> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws IOException, DukeException {
        HashMap<String, ArrayList<ModuleCategory>> sMap = storage.Specialization(); //Read the file
        Map<String, ArrayList<ModuleCategory>> specMap = new TreeMap<>(sMap);
        HashMap<String, ArrayList<String>> eMap = storage.completedElectives(); //Read the file
        Map<String, ArrayList<String>> completedEMap = new TreeMap<>(eMap);
        new ListOfSpecializationAndModules(ui, storage, specMap);

        System.out.print("Welcome to your specialization page! What would you like to do?\n\n");
        System.out.println("__________________________________________________________");
        System.out.println("1. Show list of specializations and technical electives : list");
        System.out.println("2. Key in completed electives: complete");
        System.out.println("3. Exit contact page: esc");
        System.out.println("__________________________________________________________");



        ui.readCommand();
        while(!ui.fullCommand.equals("esc")) {
            if (ui.fullCommand.equals("list")) {
                new ListSpecializationCommand(ui, storage, specMap, completedEMap);
            }
            else if (ui.fullCommand.equals("complete")) {
                new CompletedCommand(ui, storage, specMap, completedEMap);
            }

            ui.readCommand();
        }
        System.out.println("Go back to Main Menu...\n" +
                "Content Page:\n" +
                "------------------ \n" +
                "1. help\n" +
                "2. contacts\n" +
                "3. expenses\n" +
                "4. places\n" +
                "5. tasks\n" +
                "6. cap\n" +
                "7. spec\n" +
                "8. moduleplanner\n" +
                "9. notes\n");

    }
    @Override
    public boolean isExit() {
        return false;
    }
}
