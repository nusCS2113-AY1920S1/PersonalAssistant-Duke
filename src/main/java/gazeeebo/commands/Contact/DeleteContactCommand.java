package gazeeebo.commands.Contact;

import gazeeebo.Storage.Storage;
import gazeeebo.UI.Ui;

import java.io.IOException;
import java.util.Map;

public class DeleteContactCommand {
    /**
     *
     * @param ui the object that deals with printing things to the user.
     * @param storage the object that deals with storing data.
     * @param contact Map each name to its own phone number
     * @throws IOException catch any error if read file fails
     */
    public DeleteContactCommand(Ui ui, Storage storage, Map<String,String> contact) throws IOException {
        String name_to_delete = "";
        for (int i = 1; i < ui.FullCommand.split(" ").length; i++) {
            if (i != ui.FullCommand.split(" ").length - 1) {
                name_to_delete = name_to_delete.concat(ui.FullCommand.split(" ")[i] + " ");
            } else {
                name_to_delete = name_to_delete.concat(ui.FullCommand.split(" ")[i]);
            }
        }
        if (ui.FullCommand.equals("delete")) {
            System.out.println("You need to indicate what you want to delete, Format: delete name");
        } else if (contact.containsKey(name_to_delete)) {
            contact.remove(name_to_delete);
            System.out.println(name_to_delete + " has been removed.");
        } else {
            System.out.println(name_to_delete + " is not in the list.");
        }
        String toStore = "";
        for (String key : contact.keySet()) {
            toStore = toStore.concat(key + "|" + contact.get(key) + "\n");
        }
        storage.Storages_Contact(toStore);
    }
}
