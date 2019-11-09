//@@author jessteoxizhi

package gazeeebo.commands.places;

import gazeeebo.UI.Ui;

import java.io.IOException;
import java.util.Map;

public class FindPlacesCommand {
    /**
     * Search for a place prints places that you searched for.
     *
     * @param ui the object that deals with printing things to the user.
     * @param places Map each place to a location
     * @param lineBreak String separator
     */
    public FindPlacesCommand(Ui ui, Map<String,String> places, String lineBreak) throws ArrayIndexOutOfBoundsException, IOException {
        String placeSearchingFor;
        if (ui.fullCommand.equals("2")) {
            System.out.println("Input what you want to find");
            ui.readCommand();
            placeSearchingFor = ui.fullCommand;
        } else {
            placeSearchingFor = ui.fullCommand.split("-")[1];
        }
        boolean isFound = false;
        for (String keys : places.keySet()) {
            if (keys.contains(placeSearchingFor)) {
                System.out.print(keys);
                isFound = true;
                int whiteSpaces = 50 - keys.length();
                for (int i = 0; i < whiteSpaces; i++) {
                    System.out.print(" ");
                }
                System.out.print("| ");
                System.out.print(places.get(keys) + "\n" + lineBreak);
            }
        }
        if (!isFound) {
            System.out.println(placeSearchingFor + " is not found in the list.");
        }
    }
}
