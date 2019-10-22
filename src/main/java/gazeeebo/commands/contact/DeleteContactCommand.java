package gazeeebo.commands.contact;

import gazeeebo.UI.Ui;

import java.io.IOException;
import java.util.Map;

public class DeleteContactCommand {

    /**
     * This method deletes the contact from the contact list.
     *
     * @param ui      the object that deals with printing things to the user.
     * @param contact the object that map each name to its own phone number
     * @throws IOException catch any error if read file fails
     */
    public DeleteContactCommand(final Ui ui, final Map<String, String> contact) throws IOException {
        String nameToDelete = "";
        for (int i = 1; i < ui.fullCommand.split(" ").length; i++) {
            if (i != ui.fullCommand.split(" ").length - 1) {
                nameToDelete = nameToDelete.concat(ui.fullCommand.split(" ")[i] + " ");
            } else {
                nameToDelete = nameToDelete.concat(ui.fullCommand.split(" ")[i]);
            }
        }
        if (ui.fullCommand.equals("delete")) {
            System.out.print("Incorrect format: delete name\n");
        } else if (contact.containsKey(nameToDelete)) {
            contact.remove(nameToDelete);
            System.out.print("Successfully deleted: "+ nameToDelete + "\n");
        } else {
            System.out.print(nameToDelete + " is not found in the list.\n");
        }
    }
}
