package gazeeebo.parsers;

import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;
import gazeeebo.commands.contact.*;
import gazeeebo.commands.help.HelpCommand;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

/**
 * Deals with the user input in the contacts page.
 */
public class ContactCommandParser extends Command {
    /**
     * Print the line separator between contacts.
     */
    private static final String LINEBREAK
            = "------------------------------------------\n";

    /**
     * This method is the list of all the
     * contact numbers and you got add/find/delete contacts.
     *
     * @param list         list of all tasks
     * @param ui           printing things to the user.
     * @param storage      the object that deals with storing data.
     * @param commandStack store the commands in stack
     * @throws IOException Catch error if the read file fails
     */
    @Override
    public void execute(final ArrayList<Task> list,
                        final Ui ui,
                        final Storage storage,
                        final Stack<ArrayList<Task>> commandStack,
                        final ArrayList<Task> deletedTask,
                        final TriviaManager triviaManager)
            throws IOException, DukeException {
        HashMap<String, String> map
                = storage.readFromContactFile(); //Read the file
        Map<String, String> contactList = new TreeMap<String, String>(map);
        Stack<Map<String, String>> oldcontacts = new Stack<>();
        System.out.print("Welcome to your contacts page! "
                + "What would you like to do?\n\n");
        String helpContact = "_________________________"
                + "_________________________________\n"
                + "1. Add contacts: add name,number\n"
                + "2. Find contacts base on name: find name\n"
                + "3. Delete a contact: delete name\n"
                + "4. See your contacts list: list\n"
                + "5. Undo Command: undo\n"
                + "6. List of commands for contacts page: commands\n"
                + "7. Help page: help\n"
                + "8. Exit contact page: esc\n"
                + "_____________________________"
                + "_____________________________\n\n";
        System.out.print(helpContact);
        ui.readCommand();
        while (!ui.fullCommand.equals("esc")) {

            if (ui.fullCommand.split(" ")[0].equals("add")
                    || ui.fullCommand.equals("1")) {
                copyMap(contactList, oldcontacts);
                new AddContactCommand(ui, contactList);
            } else if (ui.fullCommand.split(" ")[0].equals("find")
                    || ui.fullCommand.equals("2")) {
                new FindContactCommand(ui, contactList);
            } else if (ui.fullCommand.split(" ")[0].equals("delete")
                    || ui.fullCommand.equals("3")) {
                copyMap(contactList, oldcontacts);
                new DeleteContactCommand(ui, contactList);
            } else if (ui.fullCommand.equals("list")
                    || ui.fullCommand.equals("4")) {
                new ListContactCommand(contactList);
            } else if (ui.fullCommand.equals("undo")
                    || ui.fullCommand.equals("5")) {
                contactList = UndoContactCommand.
                        undo(contactList, oldcontacts, storage);
            } else if (ui.fullCommand.equals("commands")
                    || ui.fullCommand.equals("6")) {
                System.out.println(helpContact);
            } else if (ui.fullCommand.equals("help")
                    || ui.fullCommand.equals("7")) {
                (new HelpCommand()).execute(null, ui, null,
                        null, null, null);
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
        System.out.print("Going back to Main Menu...\n"
                + "Content Page:\n"
                + "------------------ \n"
                + "1. help\n"
                + "2. contacts\n"
                + "3. expenses\n"
                + "4. places\n"
                + "5. tasks\n"
                + "6. cap\n"
                + "7. spec\n"
                + "8. moduleplanner\n"
                + "9. notes\n"
                + "To exit: bye\n");
    }

    /**
     * Copy of old contacts.
     *
     * @param contacts    current contacts list.
     * @param oldcontacts store deleted contacts.
     */
    private void copyMap(final Map<String, String> contacts,
                         final Stack<Map<String, String>> oldcontacts) {
        Map<String, String> currentcontacts = new TreeMap<>();
        for (String key : contacts.keySet()) {
            currentcontacts.put(key, contacts.get(key));
        }
        oldcontacts.push(currentcontacts);
    }

    /**
     * When isExit is true, it will stop the system.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}