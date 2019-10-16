package gazeeebo.commands.Contact;

import gazeeebo.Storage.Storage;
import gazeeebo.Tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

public class ContactsCommand extends Command {
    static final int HORT_LINE_SEPARATOR = 30;

    /**
     * This method is the list of all the contact numbers and you got add/find/delete contacts.
     *
     * @param list    list of all tasks
     * @param ui      the object that deals with printing things to the user.
     * @param storage the object that deals with storing data.
     * @throws IOException Catch error if the read file fails
     */
    @Override
    public void execute(final ArrayList<Task> list, final Ui ui, final Storage storage, final Stack<String> commandStack, final ArrayList<Task> deletedTask, final TriviaManager triviaManager) throws IOException {
        System.out.print("CONTACTS PAGE\n\n");
        HashMap<String, String> map = storage.Contact(); //Read the file

        Map<String, String> contact = new TreeMap<String, String>(map);

        String lineBreak = "------------------------------------------\n";
        System.out.print("Name:                         | Number:\n" + lineBreak);
        for (String key : contact.keySet()) {
            if (!key.contains("NUS")) {
                System.out.print(key);
                int l = HORT_LINE_SEPARATOR - key.length();
                for (int i = 0; i < l; i++) {
                    System.out.print(" ");
                }
                System.out.print("| ");
                System.out.print(contact.get(key) + "\n" + lineBreak);
            }
        }
        System.out.print("\nNUS CONTACTS:\n");
        for (String key : contact.keySet()) {
            if (key.contains("NUS")) {
                System.out.print(key);
                int l = HORT_LINE_SEPARATOR - key.length();
                for (int i = 0; i < l; i++) {
                    System.out.print(" ");
                }
                System.out.print("| ");
                System.out.print(contact.get(key) + "\n" + lineBreak);
            }
        }
        ui.readCommand();
        while (!ui.fullCommand.equals("esc")) {
            if (ui.fullCommand.equals("add")) {
                new AddContactCommand(ui, contact);
            } else if (ui.fullCommand.split(" ")[0].equals("find")) {
                new FindContactCommand(ui, contact, lineBreak);
            } else if (ui.fullCommand.equals("c_list")) {
                new ListContactCommand(contact, lineBreak);
            } else if (ui.fullCommand.contains("delete")) {
                new DeleteContactCommand(ui, contact);
            }
            String toStore = "";
            for (String key : contact.keySet()) {

                toStore = toStore.concat(key + "|" + contact.get(key) + "\n");
            }
            storage.storagesContact(toStore);
            ui.readCommand();
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
