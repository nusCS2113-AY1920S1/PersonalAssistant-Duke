package gazeeebo.commands.Contact;

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
    public DeleteContactCommand(Ui ui, Map<String, String> contact) throws IOException {
        String name_to_delete = "";
        for (int i = 1; i < ui.FullCommand.split(" ").length; i++) {
            if (i != ui.FullCommand.split(" ").length - 1) {
                name_to_delete = name_to_delete.concat(ui.FullCommand.split(" ")[i] + " ");
            } else {
                name_to_delete = name_to_delete.concat(ui.FullCommand.split(" ")[i]);
            }
        }
        if (ui.FullCommand.equals("delete")) {
            System.out.print("You need to indicate what you want to delete, Format: delete name\n");
        } else if (contact.containsKey(name_to_delete)) {
            contact.remove(name_to_delete);
            System.out.print(name_to_delete + " has been removed.\n");
        } else {
            System.out.print(name_to_delete + " is not in the list.\n");
        }
    }
}
