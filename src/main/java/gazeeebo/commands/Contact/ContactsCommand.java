package gazeeebo.commands.contact;

import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;

import java.io.IOException;
import java.util.*;

public class ContactsCommand extends Command {
    /**
     * This method is the list of all the contact numbers and you got add/find/delete contacts.
     *
     * @param list    list of all tasks
     * @param ui      the object that deals with printing things to the user.
     * @param storage the object that deals with storing data.
     * @throws IOException Catch error if the read file fails
     */
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws IOException {
        HashMap<String, String> map = storage.Contact(); //Read the file
        Map<String, String> contact = new TreeMap<String, String>(map);

        System.out.print("Welcome to your contacts page! What would you like to do?\n\n");
        System.out.println("__________________________________________________________");
        System.out.println("1. Add contacts: add");
        System.out.println("2. Find contacts base on name: find");
        System.out.println("3. Delete a contact: delete name");
        System.out.println("4. See your contacts list: c_list");
        System.out.println("5. Exit contact page: esc");
        System.out.println("__________________________________________________________");

        String LINE_BREAK = "------------------------------------------\n";
        System.out.print("Name:                         | Number:\n" + LINE_BREAK);
        for (String key : contact.keySet()) {
            if (!key.contains("NUS")) {
                System.out.print(key);
                int l = 30 - key.length();
                for (int i = 0; i < l; i++) {
                    System.out.print(" ");
                }
                System.out.print("| ");
                System.out.print(contact.get(key) + "\n" + LINE_BREAK);
            }
        }
        System.out.print("\nNUS CONTACTS:\n");
        for (String key : contact.keySet()) {
            if (key.contains("NUS")) {
                System.out.print(key);
                int l = 30 - key.length();
                for (int i = 0; i < l; i++) {
                    System.out.print(" ");
                }
                System.out.print("| ");
                System.out.print(contact.get(key) + "\n" + LINE_BREAK);
            }
        }
        ui.readCommand();
        while (!ui.fullCommand.equals("esc")) {
            if (ui.fullCommand.equals("add")) {
                new AddContactCommand(ui, contact);
            } else if (ui.fullCommand.split(" ")[0].equals("find")) {
                new FindContactCommand(ui, contact, LINE_BREAK);
            } else if (ui.fullCommand.equals("c_list")) {
                new ListContactCommand(contact, LINE_BREAK);
            } else if (ui.fullCommand.contains("delete")) {
                new DeleteContactCommand(ui, contact);
            }
            String toStore = "";
            for (String key : contact.keySet()) {

                toStore = toStore.concat(key + "|" + contact.get(key) + "\n");
            }
            storage.Storages_Contact(toStore);
            ui.readCommand();
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}