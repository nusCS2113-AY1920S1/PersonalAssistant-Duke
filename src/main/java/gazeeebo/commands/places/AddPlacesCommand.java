package gazeeebo.commands.places;

import gazeeebo.storage.Storage;
import gazeeebo.UI.Ui;

import java.io.IOException;
import java.util.Map;

public class AddPlacesCommand {
    /**
     * This method allows add new contact into the contact page
     *
     * @param ui      the object that deals with printing things to the user.
     * @param storage the object that deals with storing data.
     * @param places  Map each name to its own phone number
     * @throws IOException catch any error if read file fails
     */
    public AddPlacesCommand(Ui ui, Storage storage, Map<String, String> places) {
        try {
            System.out.println("Input in this format: Room,Location");
            ui.readCommand();
            String[] split_info = ui.fullCommand.split(",");
            String name = split_info[0];
            String number = split_info[1];
            places.put(name, number);
            System.out.println("Successfully added :" + ui.fullCommand);
            String toStore = "";
            for (String key : places.keySet()) {
                toStore = toStore.concat(key + "|" + places.get(key) + "\n");
            }
            storage.Storages_Places(toStore);
        } catch (IOException|ArrayIndexOutOfBoundsException e) {
           System.out.println("Please Input in the correct format");
        }
    }
}
