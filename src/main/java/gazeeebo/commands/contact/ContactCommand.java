package gazeeebo.commands.Contact;

import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map;

/**
 * Deals with the user input in the contacts page.
 */
public class ContactCommand extends Command {
    private static final String LINEBREAK
            = "------------------------------------------\n";
    /**
     * This method is the list of all the
     * contact numbers and you got add/find/delete contacts.
     *
     * @param list    list of all tasks
     * @param ui      the object that deals with printing things to the user.
     * @param storage the object that deals with storing data.
     * @param commandStack
     * @throws IOException Catch error if the read file fails
     */
    @Override
    public void execute(final ArrayList<Task> list,
                        final Ui ui,
                        final Storage storage,
                        final Stack<ArrayList<Task>> commandStack,
                        final ArrayList<Task> deletedTask,
                        final TriviaManager triviaManager) throws IOException {
        HashMap<String, String> map
                = storage.readFromContactFile(); //Read the file
        Map<String, String> contactList = new TreeMap<String, String>(map);
        Stack<Map<String, String>> oldcontacts = new Stack<>();
        System.out.print("Welcome to your contacts page! "
                + "What would you like to do?\n\n");
        String helpContact = "__________________________________________________________\n"
                + "1. Add contacts: add\n"
                + "2. Find contacts base on name: find name\n"
                + "3. Delete a contact: delete name\n"
                + "4. See your contacts list: list\n"
                + "5. Undo Command: undo\n"
                + "6. Help Command: help\n"
                + "7. Exit contact page: esc\n"
                + "__________________________________________________________\n\n";
        System.out.print(helpContact);
        ui.readCommand();
        while (!ui.fullCommand.equals("esc")) {
            if (ui.fullCommand.equals("add")) {
                copyMap(contactList,oldcontacts);
                new AddContactCommand(ui, contactList);
            } else if (ui.fullCommand.split(" ")[0].equals("find")) {
                new FindContactCommand(ui, contactList, LINEBREAK);
            } else if (ui.fullCommand.equals("list")) {
                new ListContactCommand(contactList, LINEBREAK);
            } else if (ui.fullCommand.contains("delete")) {
                copyMap(contactList,oldcontacts);
                new DeleteContactCommand(ui, contactList);
            } else if (ui.fullCommand.equals("help")) {
                System.out.println(helpContact);
            } else if (ui.fullCommand.equals("undo")){
                contactList = UndoContactCommand.Undo(contactList,oldcontacts,storage);
            } else {
                System.out.println("Command not found:\n" + helpContact);
            }
            String toStore = "";
            for (String key : contactList.keySet()) {

                toStore = toStore.concat(key
                        + "|" + contactList.get(key) + "\n");
            }
            storage.writeToContactFile(toStore);
            System.out.println("What do you want to do next ?");
            ui.readCommand();
        }
        System.out.println("Go back to Main Menu...\n" +
                "Content Page:\n" +
                "------------------ \n" +
                "1. help\n" +
                "2. contacts\n" +
                "3. expenses\n" +
                "4. places\n" +
                "5. tasks\n" +
                "6. cap\n" +
                "7. spec\n" +
                "8. moduleplanner\n" +
                "9. notes\n");
    }

    /**
     * Copy of old contacts
     * @param contacts current contacts list
     * @param oldcontacts
     */
    private void copyMap(Map<String, String> contacts, Stack<Map<String, String>> oldcontacts) {
        Map<String, String> currentcontacts = new TreeMap<>();
        for (String key : contacts.keySet()) {
            currentcontacts.put(key, contacts.get(key));
        }
        oldcontacts.push(currentcontacts);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}