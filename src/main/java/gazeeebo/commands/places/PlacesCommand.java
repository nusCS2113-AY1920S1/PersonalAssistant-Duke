package gazeeebo.commands.places;

import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class PlacesCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws DukeException, ParseException, IOException, NullPointerException {
        System.out.print("PLACES IN SOC\n\nCommands:\n'list' list all places in SOC\n'add' adds a new place\n'delete-a place' delete a place\n'find-a place in SOC' find a place in SOC\n");
        HashMap<String, String> map = storage.Read_Places();
        Map<String, String> places = new TreeMap<String, String>(map);
        String LINE_BREAK = "------------------------------------------\n";
        boolean isExitFromPlaces = false;
        while (!isExitFromPlaces) {
            ui.ReadCommand();
            if (ui.FullCommand.equals("add")) {
                new AddPlacesCommand(ui,storage,places);
            } else if (ui.FullCommand.split("-")[0].equals("find")) {
                new FindPlacesCommand(ui,places, LINE_BREAK);
            } else if (ui.FullCommand.equals("list")) {
                new ListPlacesCommand(places, LINE_BREAK);
            } else if (ui.FullCommand.contains("delete")) {
                new DeletePlacesCommand(ui,storage,places);
            } else if (ui.FullCommand.equals("esc")) {
                System.out.println("Going back to Main Menu");
                isExitFromPlaces = true;
            } else {
                System.out.println("There is no such command in Places.");
            }
        }
    }
    @Override
    public boolean isExit() {
        return false;
    }
}
