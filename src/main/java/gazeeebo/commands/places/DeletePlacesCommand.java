//@@author jessteoxizhi

package gazeeebo.commands.places;

import gazeeebo.storage.Storage;
import gazeeebo.UI.Ui;

import java.io.IOException;
import java.util.Map;

public class DeletePlacesCommand {
    /**
     * Delete a place from the list of places.
     *
     * @param ui      the object that deals with printing things to the user.
     * @param places  Map each place to a location
     * @throws IOException catch any error if read file fails
     */

    public DeletePlacesCommand(Ui ui, Map<String, String> places) {
        try {
            String placeToDelete = null;
            if (ui.fullCommand.equals("3")) {
                System.out.println("Input place to delete");
                ui.readCommand();
                placeToDelete = ui.fullCommand;
            } else {
                if (!ui.fullCommand.equals("delete") && ui.fullCommand.contains("-")) {
                    placeToDelete = ui.fullCommand.split("-")[1];
                }
            }
            if (places.containsKey(placeToDelete)) {
                places.remove(placeToDelete);
                System.out.println("Successfully deleted: " + placeToDelete);
            } else {
                System.out.println(placeToDelete + " is not found in the list.");
            }
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Please Input in the correct format");
        }
    }
}