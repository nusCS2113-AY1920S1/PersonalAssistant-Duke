//@@author jessteoxizhi

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
    /**
     * Parses the user input and return a command object.
     *
     * @param list
     * @param ui
     * @param storage
     * @param commandStack
     * @param deletedTask
     * @param triviaManager
     * @throws DukeException
     * @throws ParseException
     * @throws IOException
     * @throws NullPointerException
     */
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<ArrayList<Task>> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws DukeException, ParseException, IOException, NullPointerException {
        String helpPlaces = "__________________________________________________________\n"
                + "1. Add location: add\n"
                + "2. Find a place in SOC: find-place\n"
                + "3. Delete a place: delete-place\n"
                + "4. See all places in SOC: list\n"
                + "5. Help Command: help\n"
                + "6. Exit places: esc\n"
                + "__________________________________________________________\n\n";
        System.out.println("Welcome to your places in SOC! What would you like to do?");
        System.out.println(helpPlaces);
        HashMap<String, String> map = storage.readPlaces();
        Map<String, String> places = new TreeMap<String, String>(map);
        Stack<Map<String, String>> oldplaces = new Stack<>();
        String lineBreak = "------------------------------------------\n";
        boolean isExitFromPlaces = false;
        while (!isExitFromPlaces) {
            try {
                ui.readCommand();
                if (ui.fullCommand.equals("add")) {
                    copyMap(places,oldplaces);
                    new AddPlacesCommand(ui, storage, places);
                } else if (ui.fullCommand.split("-")[0].equals("find")) {
                    new FindPlacesCommand(ui, places, lineBreak);
                } else if (ui.fullCommand.equals("list")) {
                    new ListPlacesCommand(places, lineBreak);
                } else if (ui.fullCommand.contains("delete")) {
                    copyMap(places,oldplaces);
                    new DeletePlacesCommand(ui, storage, places);
                } else if (ui.fullCommand.contains("help")) {
                        System.out.println(helpPlaces);
                } else if (ui.fullCommand.equals("esc")) {
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
                    isExitFromPlaces = true;
                } else if (ui.fullCommand.equals("undo")){
                     places = UndoPlacesCommand.Undo(places,oldplaces,storage);
                } else {
                    System.out.println("There is no such command in Places.");
                    System.out.println("What do you want to do next ?");
                }
            }
            catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Check input format again");
                System.out.println("What do you want to do next ?");
            }
        }
    }

    /**
     * copy map of places into a stack of maps.
     *
     * @param places
     * @param oldplaces
     */
    private void copyMap(Map<String, String> places, Stack<Map<String, String>> oldplaces) {
        Map<String, String> currentplaces = new TreeMap<>();
        for (String key : places.keySet()) {
            currentplaces.put(key, places.get(key));
        }
        oldplaces.push(currentplaces);
    }

    /**
     * Exits program
     *
     * @return true to exit
     */
    @Override
    public boolean isExit() {
        return false;
    }
}