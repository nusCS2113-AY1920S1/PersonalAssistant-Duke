package gazeeebo.commands.Modules;

import gazeeebo.Storage.Storage;
import gazeeebo.Tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class ModuleCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws IOException {
        HashMap<String, ArrayList<ArrayList<String>>> map = storage.Modules(); //Read the file
        Map<String, ArrayList<ArrayList<String>>> modules = new TreeMap<LocalDate, ArrayList<String>>(map);

        //3 arraylists of tutorials, projects and other notes for each module
        ArrayList<String> tutorialList = new ArrayList<>();
        ArrayList<String> projList = new ArrayList<>();
        ArrayList<String> othersList = new ArrayList<>();

        ArrayList<ArrayList> modOrganiser = new ArrayList<>();
        modOrganiser.add(tutorialList);
        modOrganiser.add(projList);
        modOrganiser.add(othersList);

        ui.ReadCommand();
        while(!ui.FullCommand.equals("esc")) {
            if (ui.FullCommand.contains("desc")) {
                new ModuleDescriptions();
            }
            ui.ReadCommand();
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
