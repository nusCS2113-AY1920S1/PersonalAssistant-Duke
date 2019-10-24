package gazeeebo.commands.contact;
import gazeeebo.UI.Ui;
import java.io.IOException;
import java.util.Map;

public class AddContactCommand {

    /**
     * Add new contact into the contact page.
     *
     * @param ui      deals with printing things to the user.
     * @param contactList map each name to its own phone number
     * @throws IOException catch any error if read file fails
     */
    public AddContactCommand(Ui ui, Map<String, String> contactList) throws IOException {
        System.out.print("Input in this format: Name,Number\n");
        ui.readCommand();
        String[] splitCommand = ui.fullCommand.split(",");
        String name = splitCommand[0];
        String number = splitCommand[1];
        contactList.put(name, number);
        System.out.print("Successfully added: "
                + ui.fullCommand + "\n");
    }
}
