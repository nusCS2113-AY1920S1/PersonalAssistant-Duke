package gazeeebo.commands.contact;

import gazeeebo.UI.Ui;

import java.util.Map;

public class findContactCommand {

    /**
     * Finds the contact number with the contact name.
     *
     * @param ui        deals with printing things to the user.
     * @param contactList   map each name to its own phone number
     * @param lineBreak String separator
     */
    public findContactCommand(Ui ui, Map<String, String> contactList, String lineBreak) {
        int a = ui.fullCommand.split(" ")[1].toCharArray()[0];
        String nameToFind = "";
        String toPrint = "";
        boolean isExist = false;
        for (int i = 1; i < ui.fullCommand.split(" ").length; i++) {
            if (i != ui.fullCommand.split(" ").length - 1) {
                nameToFind = nameToFind.concat(ui.fullCommand.split(" ")[i] + " ");
            } else {
                nameToFind = nameToFind.concat(ui.fullCommand.split(" ")[i]);
            }
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
        if(!isExist) {
            System.out.print(nameToFind + " is not found in the list.");
        } else {
            System.out.print("Name:                         | Number:\n" + lineBreak + toPrint);
        }
    }
}
