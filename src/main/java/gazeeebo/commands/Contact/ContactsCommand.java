package gazeeebo.commands.contact;

import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;

import java.io.IOException;
import java.util.*;

public class ContactsCommand extends Command {
    private static final String LINEBREAK = "------------------------------------------\n";
    /**
     * List of all the contact numbers and you got add/find/delete contacts.
     *
     * @param list    list of all contacts
     * @param ui      deals with printing things to the user.
     * @param storage deals with storing data.
     * @throws IOException Catch error if the read file fails
     */
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws IOException {
        HashMap<String, String> map = storage.readFromContactFile(); //Read the file
        Map<String, String> contact = new TreeMap<String, String>(map);
        System.out.print("Welcome to your contacts page! What would you like to do?\n\n");
        String helpContacts = "__________________________________________________________\n"
                + "1. Add contacts: add\n"
                + "2. Find contacts: find name\n"
                + "3. Delete a contact: delete name\n"
                + "4. See your contacts list: list\n"
                + "5. Exit contact page: esc\n"
                + "__________________________________________________________\n\n";
        System.out.print(helpContacts);


        ui.readCommand();
        while (!ui.fullCommand.equals("esc")) {
            if (ui.fullCommand.equals("add")) {
                new addContactCommand(ui, contact);
            } else if (ui.fullCommand.split(" ")[0].equals("find")) {
                new findContactCommand(ui, contact, LINEBREAK);
            } else if (ui.fullCommand.equals("list")) {
                new listContactCommand(contact, LINEBREAK);
            } else if (ui.fullCommand.contains("delete")) {
                new deleteContactCommand(ui, contact);
            } else if(ui.fullCommand.equals("help")) {
                System.out.println(helpContacts);
            } else {
                System.out.println("Command not Found:\n" + helpContacts);
            }
            String toStore = "";
            for (String key : contact.keySet()) {

                toStore = toStore.concat(key + "|" + contact.get(key) + "\n");
            }
            storage.writeToContactFile(toStore);
            System.out.println("What do you want to do next ?");
            ui.readCommand();
        }
        System.out.print("Going back to Main Menu\n");
    }

    @Override
    public boolean isExit() {
        return false;
    }
}