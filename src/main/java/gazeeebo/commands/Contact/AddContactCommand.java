package gazeeebo.commands.Contact;

import gazeeebo.Storage.Storage;
import gazeeebo.UI.Ui;

import java.io.IOException;
import java.util.Map;

public class AddContactCommand {
    /**
     * This method allows add new contact into the contact page
     * @param ui the object that deals with printing things to the user.
     * @param storage the object that deals with storing data.
     * @param contact Map each name to its own phone number
     * @throws IOException catch any error if read file fails
     */
    public AddContactCommand(Ui ui, Storage storage, Map<String,String> contact) throws IOException {
        System.out.println("Input in this format: Name,Number");
        ui.ReadCommand();
        String[] split_info = ui.FullCommand.split(",");
        String name = split_info[0];
        String number = split_info[1];
        contact.put(name, number);
        System.out.println("Okay we have successfully added your new contact :) " + ui.FullCommand);
        String toStore = "";
        for (String key : contact.keySet()) {
            toStore = toStore.concat(key + "|" + contact.get(key) + "\n");
        }
        storage.Storages_Contact(toStore);
    }
}
