package gazeeebo.commands.Contact;

import gazeeebo.UI.Ui;

import java.util.Map;

public class FindContactCommand {

    /**
     * This method finds the contact number with the contact name.
     *
     * @param ui         the object that deals with printing things to the user.
     * @param contact    the object that map each name to its own phone number
     * @param LINE_BREAK String separator
     */
    public FindContactCommand(Ui ui, Map<String, String> contact, String LINE_BREAK) {
        int a = ui.FullCommand.split(" ")[1].toCharArray()[0];
        String command = ui.FullCommand.split(" ")[0];
        String name_to_find = "";
        for (int i = 1; i < ui.FullCommand.split(" ").length; i++) {
            if (i != ui.FullCommand.split(" ").length - 1) {
                name_to_find = name_to_find.concat(ui.FullCommand.split(" ")[i] + " ");
            } else {
                name_to_find = name_to_find.concat(ui.FullCommand.split(" ")[i]);
            }
        }
        if (a <= 9) {
            System.out.println("Name not found.");
        } else {
            System.out.print("Name:                         | Number:\n" + LINE_BREAK);
            for (String keys : contact.keySet()) {
                if (keys.contains(name_to_find)) {
                    System.out.print(keys);
                    int l = 30 - keys.length();
                    for (int i = 0; i < l; i++) {
                        System.out.print(" ");
                    }
                    System.out.print("| ");
                    System.out.print(contact.get(keys) + "\n" + LINE_BREAK);
                }
            }
        }
    }
}
