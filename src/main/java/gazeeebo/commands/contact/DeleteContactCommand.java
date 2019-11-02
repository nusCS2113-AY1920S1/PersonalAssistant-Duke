package gazeeebo.commands.Contact;

import gazeeebo.UI.Ui;

import java.util.Map;

/**
 * Deletes a contact from the contact list.
 */
public class DeleteContactCommand {

    /**
     * Deletes the contact from the contact list.
     *
     * @param ui      deals with printing things to the user.
     * @param contactList map each name to its own phone number
     */
    public DeleteContactCommand(final Ui ui,
                                final Map<String, String> contactList) {
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
        } else if (contactList.containsKey(nameToDelete)) {
            contactList.remove(nameToDelete);
            System.out.print("Successfully deleted: " + nameToDelete + "\n");
        } else {
            System.out.print(nameToDelete + " is not found in the list.\n");
        }
    }
}
