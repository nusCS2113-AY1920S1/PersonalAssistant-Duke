//@@author jessteoxizhi
package gazeeebo.commands.places;

import gazeeebo.UI.Ui;

import java.util.Map;

public class FindPlacesCommand {
    /**
     * Search for a place prints places that you searched for
     *
     * @param ui the object that deals with printing things to the user.
     * @param places Map each place to a location
     * @param lineBreak String separator
     */
    public FindPlacesCommand(Ui ui, Map<String,String> places, String lineBreak) throws ArrayIndexOutOfBoundsException{
        if (ui.fullCommand.equals("find")) {
            System.out.println("You need to indicate what you want to find, Format: delete name");
        }
        else {
            String placeSearchingFor = ui.fullCommand.split("-")[1];
            Boolean isFound = false;
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
}
