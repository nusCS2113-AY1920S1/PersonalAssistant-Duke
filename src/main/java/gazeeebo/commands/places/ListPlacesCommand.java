package gazeeebo.commands.places;

import java.util.Map;

public class ListPlacesCommand {
    /**
     * This method list out all the phone numbers.
     * @param places Map each name to its phone number
     * @param LINE_BREAK String separator
     */
    public ListPlacesCommand(Map<String,String> places, String LINE_BREAK) {
        System.out.print("Room:                                             | Location:\n" + LINE_BREAK);
        for (String key : places.keySet()) {
            System.out.print(key);
            int whiteSpaces = 50 - key.length();
            for (int i = 0; i < whiteSpaces; i++) {
                System.out.print(" ");
            }
            System.out.print("| ");
            System.out.print(places.get(key)+ "\n" + LINE_BREAK);
        }
    }
}