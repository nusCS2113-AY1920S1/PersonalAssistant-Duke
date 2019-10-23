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
     * This method is the list of all the contact numbers and you got add/find/delete contacts.
     *
     * @param list    list of all tasks
     * @param ui      the object that deals with printing things to the user.
     * @param storage the object that deals with storing data.
     * @throws IOException Catch error if the read file fails
     */
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws IOException {
        HashMap<String, String> map = storage.readFromContactFile(); //Read the file
        Map<String, String> contact = new TreeMap<String, String>(map);

        System.out.print("Welcome to your contacts page! What would you like to do?\n\n");
        System.out.println("__________________________________________________________");
        System.out.println("1. Add contacts: add");
        System.out.println("2. Find contacts base on name: find name");
        System.out.println("3. Delete a contact: delete name");
        System.out.println("4. See your contacts list: list");
        System.out.println("5. Exit contact page: esc");
        System.out.println("__________________________________________________________");


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
            }
            String toStore = "";
            for (String key : contact.keySet()) {

                toStore = toStore.concat(key + "|" + contact.get(key) + "\n");
            }
            storage.writeToContactFile(toStore);
            System.out.println("What do you want to do next ?");
            ui.readCommand();
        }
        System.out.println("Going back to Main Menu");
    }

    @Override
    public boolean isExit() {
        return false;
    }
}