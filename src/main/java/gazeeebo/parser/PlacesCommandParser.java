//@@author jessteoxizhi

package gazeeebo.parser;

import gazeeebo.commands.help.HelpCommand;
import gazeeebo.commands.places.*;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.logging.Logger;

public class PlacesCommandParser extends Command {
    private final static Logger LOGGER = Logger.getLogger(PlacesCommandParser.class.getName());
    /**
     * Parses the user input and return a command object.
     *
     * @param list the list of all tasks.
     * @param ui the object that deals with printing things to the user.
     * @param storage the object that deals with storing data.
     * @param commandStack the stack of previous commands.
     * @param deletedTask the list of deleted task.
     * @param triviaManager the object for triviaManager
     * @throws DukeException exception thrown when there is an input error.
     * @throws ParseException parse exception from help command
     * @throws IOException input or output error when interacting with user.
     */
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<ArrayList<Task>> commandStack,
                        ArrayList<Task> deletedTask, TriviaManager triviaManager) throws DukeException, ParseException,
            IOException {
        String helpPlaces = "__________________________________________________________\n"
                + "1. Add location: add-room,location\n"
                + "2. Find a place in SOC: find-place\n"
                + "3. Delete a place: delete-place\n"
                + "4. See all places in SOC: list\n"
                + "5. Undo previous command: undo\n"
                + "6. See all commands: commands\n"
                + "7. Help command: help\n"
                + "8. Exit places: esc\n"
                + "__________________________________________________________\n\n";
        System.out.println("Welcome to your places in SOC! What would you like to do?");
        System.out.println(helpPlaces);
        try {
            HashMap<String, String> map = storage.readPlaces();
            Map<String, String> places = new TreeMap<String, String>(map);
            Stack<Map<String, String>> oldplaces = new Stack<>();
            String lineBreak = "------------------------------------------\n";
            boolean isExitFromPlaces = false;
            while (!isExitFromPlaces) {
                ui.readCommand();
                if (ui.fullCommand.contains("add-") || ui.fullCommand.equals("1")) {
                    copyMap(places,oldplaces);
                    new AddPlacesCommand(ui, places);
                } else if (ui.fullCommand.equals("find-") || ui.fullCommand.equals("2")) {
                    new FindPlacesCommand(ui, places, lineBreak);
                } else if (ui.fullCommand.equals("list") || ui.fullCommand.equals("4")) {
                    new ListPlacesCommand(places, lineBreak);
                } else if (ui.fullCommand.contains("delete-") || ui.fullCommand.equals("3")) {
                    copyMap(places,oldplaces);
                    new DeletePlacesCommand(ui, places);
                } else if (ui.fullCommand.equals("commands") || ui.fullCommand.equals("6")) {
                    System.out.println(helpPlaces);
                } else if (ui.fullCommand.equals("help") || ui.fullCommand.equals("7")) {
                    new HelpCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
                } else if (ui.fullCommand.equals("esc") || ui.fullCommand.equals("8")) {
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
                    isExitFromPlaces = true;
                } else if (ui.fullCommand.equals("undo") || ui.fullCommand.equals("5")) {
                    places = UndoPlacesCommand.undoPlaces(places,oldplaces);
                } else {
                    System.out.println("There is no such command in Places.");
                    System.out.println("What do you want to do next ?");
                }
                String toStore = "";
                for (String key : places.keySet()) {
                    toStore = toStore.concat(key + "|" + places.get(key) + "\n");
                }
                storage.storagesPlaces(toStore);
            }
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Check input format again");
        }
    }

    /**
     * copy map of places into a stack of maps.
     *
     * @param places map of current places
     * @param oldplaces stack of map of previous places
     */
    private void copyMap(Map<String, String> places, Stack<Map<String, String>> oldplaces) {
        Map<String, String> currentplaces = new TreeMap<>();
        for (String key : places.keySet()) {
            currentplaces.put(key, places.get(key));
        }
        oldplaces.push(currentplaces);
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