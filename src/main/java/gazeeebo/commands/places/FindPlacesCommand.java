//@@author jessteoxizhi

package gazeeebo.commands.places;

import gazeeebo.UI.Ui;
import gazeeebo.exception.DukeException;

import java.io.IOException;
import java.util.Map;

public class FindPlacesCommand {
    private static final String LINE_BREAK = "------------------------------------------\n";
    /**
     * Search for a place prints places that you searched for.
     *
     * @param ui the object that deals with printing things to the user.
     * @param places Map each place to a location
\     */
    public FindPlacesCommand(Ui ui, Map<String,String> places) throws IOException {
        try {
            String placeSearchingFor;
            if (ui.fullCommand.equals("2") || ui.fullCommand.equals("find") || ui.fullCommand.equals("find-")) {
                System.out.println("Input what you want to find");
                ui.readCommand();
                placeSearchingFor = ui.fullCommand;
            } else if (ui.fullCommand.split("-")[1] != null) {
                placeSearchingFor = ui.fullCommand.split("-")[1].trim();
            } else {
                throw new DukeException("Check find command input format again");
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
                    System.out.print(places.get(keys) + "\n" + LINE_BREAK);
                }
            }
            if (!isFound) {
                System.out.println(placeSearchingFor + " is not found in the list.");
            }
        } catch (DukeException e) {
            ui.showErrorMessage(e);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Check find command input format again.");
        }
    }
}
