//@@author jessteoxizhi

package gazeeebo.commands.places;

import gazeeebo.storage.Storage;
import gazeeebo.UI.Ui;

import java.io.IOException;
import java.util.Map;

public class AddPlacesCommand {
    /**
     * This method allows add a new place.
     *
     * @param ui      the object that deals with printing things to the user.
     * @param storage the object that deals with storing data.
     * @param places  Map each place to a location
     * @throws IOException catch any error if read file fails
     */
    public AddPlacesCommand(final Ui ui, final Storage storage, final Map<String, String> places) {
        try {
            String[] parseInput = ui.fullCommand.split("-");
            String[] splitInfo = parseInput[1].split(",");
            String name = splitInfo[0];
            String number = splitInfo[1];
            places.put(name, number);
            System.out.println("Successfully added :" + parseInput[1]);
            String toStore = "";
            for (String key : places.keySet()) {
                toStore = toStore.concat(key + "|" + places.get(key) + "\n");
            }
            storage.storagesPlaces(toStore);
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Please Input in the correct format");
        }
    }
}
