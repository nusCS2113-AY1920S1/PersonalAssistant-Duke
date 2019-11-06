package gazeeebo.commands.Contact;

import gazeeebo.UI.Ui;

import java.io.IOException;
import java.util.Map;

/**
 * Adds a new contact to the contact list.
 */
public class AddContactCommand {

    /**
     * Add new contact into the contact page.
     *
     * @param ui          deals with printing things to the user.
     * @param contactList map each name to its own phone number
     * @throws IOException catch any error if read file fails
     */
    public AddContactCommand(final Ui ui,
                             final Map<String, String> contactList)
            throws IOException {
        try {
            String toAdd = "";
            String[] splitInput = ui.fullCommand.split(" ");
            switch (splitInput.length) {
                case 1:
                    System.out.print("Input in this format: Name,Number\n");
                    ui.readCommand();
                    toAdd = ui.fullCommand;
                    break;
                case 2:
                    toAdd = splitInput[1];
                    break;
                default:
                    throw new ArrayIndexOutOfBoundsException();
            }
            String[] splitCommand = toAdd.split(",");
            String name = splitCommand[0];
            String number = splitCommand[1];
            contactList.put(name, number);
            System.out.print("Successfully added: "
                    + toAdd + "\n");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.print("Please Input in the correct format\n");
        }
    }
}
