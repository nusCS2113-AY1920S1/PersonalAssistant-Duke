package gazeeebo.commands.Contact;

import gazeeebo.UI.Ui;

import java.util.Map;

public class FindContactCommand {

    /**
     * This method finds the contact number with the contact name.
     *
     * @param ui         the object that deals with printing things to the user.
     * @param contact    the object that map each name to its own phone number
     * @param lineBreak String separator
     */
    public FindContactCommand(final Ui ui, final Map<String, String> contact, final String lineBreak) {
        int a = ui.fullCommand.split(" ")[1].toCharArray()[0];
        String command = ui.fullCommand.split(" ")[0];
        String nameToFind = "";
        for (int i = 1; i < ui.fullCommand.split(" ").length; i++) {
            if (i != ui.fullCommand.split(" ").length - 1) {
                nameToFind = nameToFind.concat(ui.fullCommand.split(" ")[i] + " ");
            } else {
                nameToFind = nameToFind.concat(ui.fullCommand.split(" ")[i]);
            }
        }
        if (a <= 9) {
            System.out.println("Name not found.");
        } else {
            System.out.print("Name:                         | Number:\n" + lineBreak);
            for (String keys : contact.keySet()) {
                if (keys.contains(nameToFind)) {
                    System.out.print(keys);
                    int l = 30 - keys.length();
                    for (int i = 0; i < l; i++) {
                        System.out.print(" ");
                    }
                    System.out.print("| ");
                    System.out.print(contact.get(keys) + "\n" + lineBreak);
                }
            }
        }
    }
}
