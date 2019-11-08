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

public class PlacesCommandParser extends Command {
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
        HashMap<String, String> map = storage.readPlaces();
        Map<String, String> places = new TreeMap<String, String>(map);
        Stack<Map<String, String>> oldplaces = new Stack<>();
        String lineBreak = "------------------------------------------\n";
        boolean isExitFromPlaces = false;
        while (!isExitFromPlaces) {
            try {
                ui.readCommand();
                if (ui.fullCommand.contains("add")) {
                    copyMap(places,oldplaces);
                    new AddPlacesCommand(ui, storage, places);
                } else if (ui.fullCommand.split("-")[0].equals("find")) {
                    new FindPlacesCommand(ui, places, lineBreak);
                } else if (ui.fullCommand.equals("list")) {
                    new ListPlacesCommand(places, lineBreak);
                } else if (ui.fullCommand.contains("delete")) {
                    copyMap(places,oldplaces);
                    new DeletePlacesCommand(ui, storage, places);
                } else if (ui.fullCommand.equals("commands")) {
                    System.out.println(helpPlaces);
                } else if (ui.fullCommand.equals("help")) {
                    new HelpCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
                } else if (ui.fullCommand.equals("esc")) {
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
                } else if (ui.fullCommand.equals("undo")) {
                    places = UndoPlacesCommand.undoPlaces(places,oldplaces,storage);
                } else {
                    System.out.println("There is no such command in Places.");
                    System.out.println("What do you want to do next ?");
                }
            } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
                throw new DukeException("Check input format again");
            }
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