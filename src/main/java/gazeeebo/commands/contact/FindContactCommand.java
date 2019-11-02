package gazeeebo.commands.contact;

import gazeeebo.UI.Ui;

import java.io.IOException;
import java.util.Map;

/**
 * Finds a contact frm contact list.
 */
public class FindContactCommand {

    /**
     * Finds the contact number with the contact name.
     *
     * @param ui          deals with printing things to the user.
     * @param contactList map each name to its own phone number
     * @param lineBreak   String separator
     */
    public FindContactCommand(final Ui ui,
                              final Map<String, String> contactList,
                              final String lineBreak) {
        try {
            String nameToFind = "";
            String toPrint = "";
            boolean isExist = false;
            if (ui.fullCommand.split(" ").length == 1) {
                System.out.println("What is the name you want to find?");
                ui.readCommand();
                nameToFind = ui.fullCommand;
            } else {
                for (int i = 1; i < ui.fullCommand.split(" ").length; i++) {
                    nameToFind
                            = nameToFind.concat(ui.fullCommand.split(" ")[i] + " ");
                }
                nameToFind = nameToFind.trim();
            }
            for (String keys : contactList.keySet()) {
                if (keys.contains(nameToFind)) {
                    isExist = true;
                    toPrint += keys;
                    int l = 30 - keys.length();
                    for (int i = 0; i < l; i++) {
                        toPrint += " ";
                    }
                    toPrint += "| " + contactList.get(keys) + "\n" + lineBreak;
                }
            }
            if (!isExist) {
                System.out.print(nameToFind + " is not found in the list.\n");
            } else {
                System.out.print("Name:                         "
                        + "| Number:\n" + lineBreak + toPrint);
            }
        } catch (IOException e) {
            System.out.print("Please Input in the correct format\n");
        }
    }
}
