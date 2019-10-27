package gazeeebo.commands.places;

import java.util.Map;

public class ListPlacesCommand {
    /**
     * This method list out all the phone numbers.
     * @param places Map each name to its phone number
     * @param lineBreak String separator
     */
    public ListPlacesCommand(Map<String,String> places, String lineBreak) {
        System.out.print("Room:                                             | Location:\n" + lineBreak);
        for (String key : places.keySet()) {
            System.out.print(key);
            int whiteSpaces = 50 - key.length();
            for (int i = 0; i < whiteSpaces; i++) {
                System.out.print(" ");
            }
            System.out.print("| ");
            System.out.print(places.get(key)+ "\n" + lineBreak);
        }
    }
}