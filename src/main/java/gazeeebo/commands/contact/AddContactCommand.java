package gazeeebo.commands.contact;
import gazeeebo.UI.Ui;
import java.io.IOException;
import java.util.Map;

public class AddContactCommand {

    /**
     * This method allows add new contact into the contact page.
     *
     * @param ui      the object that deals with printing things to the user.
     * @param contact the object that map each name to its own phone number
     * @throws IOException catch any error if read file fails
     */
    public AddContactCommand(final Ui ui, final Map<String, String> contact) throws IOException {
        System.out.print("Input in this format: Name,Number\n");
        ui.readCommand();
        String[] splitCommand = ui.fullCommand.split(",");
        String name = splitCommand[0];
        String number = splitCommand[1];
        contact.put(name, number);
        System.out.print("Successfully added: "
                + ui.fullCommand + "\n");
    }
}
