//@@author jessteoxizhi

package gazeeebo.commands.places;

import gazeeebo.storage.Storage;

import java.io.IOException;
import java.util.Map;
import java.util.Stack;

public class UndoPlacesCommand {
    /**
     *  Undo places previous command
     *
     * @param places map of current places
     * @param oldplaces stack of map of previous places
     * @param storage object that stores and read from txt files
     * @return previous map before the command.
     * @throws IOException input or output error when interacting with user.
     */
    public static Map<String,String> undoPlaces(Map<String, String> places, Stack<Map<String, String>> oldplaces,
                                         Storage storage) throws IOException {
        if (!oldplaces.empty()) {
            places = oldplaces.peek();
            String toStore = "";
            for (String key:places.keySet()) {
            toStore = toStore.concat(key + "|" + places.get(key) + "\n");
            }
            storage.storagesPlaces(toStore);
            oldplaces.pop();
            System.out.println("You have undo the previous command.");
        } else {
            System.out.println("The previous command cannot be undo");
        }
        return places;
    }
}
