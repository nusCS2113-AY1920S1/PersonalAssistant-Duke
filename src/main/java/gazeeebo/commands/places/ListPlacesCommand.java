//@@author jessteoxizhi

package gazeeebo.commands.places;

import gazeeebo.exception.DukeException;

import java.util.Map;

public class ListPlacesCommand {
    /**
     * This method list out all the places.
     *
     * @param places Map each place to a location
     * @param lineBreak String separator
     */
    public ListPlacesCommand(Map<String,String> places, String lineBreak) throws ArrayIndexOutOfBoundsException {
        System.out.print("Room:                                             | Location:\n" + lineBreak);
        for (String key : places.keySet()) {
            System.out.print(key);
            int whiteSpaces = 50 - key.length();
            for (int i = 0; i < whiteSpaces; i++) {
                System.out.print(" ");
            }
            System.out.print("| ");
            System.out.print(places.get(key) + "\n" + lineBreak);
        }
    }
}