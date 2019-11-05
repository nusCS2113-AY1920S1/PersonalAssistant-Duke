package gazeeebo.commands.Contact;
import gazeeebo.UI.Ui;

import java.io.IOException;
import java.util.Map;

/**
 * Deletes a contact from the contact list.
 */
public class DeleteContactCommand {

    /**
     * Deletes the contact from the contact list.
     *
     * @param ui          deals with printing things to the user.
     * @param contactList map each name to its own phone number
     */
    public DeleteContactCommand(final Ui ui,
                                final Map<String, String> contactList) {
        try {
            String nameToDelete = "";
            if (ui.fullCommand.split(" ").length == 1) {
                System.out.println("What is the name you want to delete?");
                ui.readCommand();
                nameToDelete = ui.fullCommand;
            } else if (ui.fullCommand.split(" ").length == 2) {
                for (int i = 1; i < ui.fullCommand.split(" ").length; i++) {
                    nameToDelete = nameToDelete.
                            concat(ui.fullCommand.split(" ")[i] + " ");
                }
                 nameToDelete = nameToDelete.trim();
            } else {
                throw new ArrayIndexOutOfBoundsException();
            }
            if (contactList.containsKey(nameToDelete)) {
                contactList.remove(nameToDelete);
                System.out.print("Successfully deleted: " + nameToDelete + "\n");
            } else {
                System.out.print(nameToDelete + " is not found in the list.\n");
            }
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            System.out.print("Please Input in the correct format\n");
        }
    }
}
