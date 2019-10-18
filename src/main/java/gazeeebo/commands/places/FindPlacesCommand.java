package gazeeebo.commands.places;

import gazeeebo.UI.Ui;

import java.util.Map;

public class FindPlacesCommand {
    /**
     *
     * @param ui the object that deals with printing things to the user.
     * @param places Map each name to its own phone number
     * @param LINE_BREAK String separator
     */
    public FindPlacesCommand(Ui ui, Map<String,String> places, String LINE_BREAK) throws ArrayIndexOutOfBoundsException{
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
                    System.out.print(places.get(keys) + "\n" + LINE_BREAK);
                }
            }
            if (!isFound) {
                System.out.println("This place cannot be found.");
            }
        }
    }
}
