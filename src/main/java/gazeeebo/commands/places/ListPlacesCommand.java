//@@author jessteoxizhi

package gazeeebo.commands.places;

import gazeeebo.exception.DukeException;

import java.util.Map;

public class ListPlacesCommand {
    private static final String LINE_BREAK = "------------------------------------------\n";
    /**
     * This method list out all the places.
     *
     * @param places Map each place to a location
     */
    public ListPlacesCommand(Map<String,String> places) throws ArrayIndexOutOfBoundsException {
        System.out.print("Room:                                             | Location:\n" + LINE_BREAK);
        for (String key : places.keySet()) {
            System.out.print(key);
            int whiteSpaces = 50 - key.length();
            for (int i = 0; i < whiteSpaces; i++) {
                System.out.print(" ");
            }
            System.out.print("| ");
            System.out.print(places.get(key) + "\n" + LINE_BREAK);
        }
    }
}