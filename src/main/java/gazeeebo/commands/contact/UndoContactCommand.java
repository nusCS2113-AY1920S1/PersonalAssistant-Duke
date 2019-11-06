package gazeeebo.commands.Contact;

import gazeeebo.storage.Storage;

import java.io.IOException;
import java.util.Map;
import java.util.Stack;

public class UndoContactCommand {
    /**
     * Undo previous commands.
     *
     * @param contactList to Map each name to its phone number.
     * @param oldcontacts keep deleted contacts
     * @param storage     stores
     * @return the contactList
     * @throws IOException catch error if error during access to file.
     */
    public static Map<String, String> undo(Map<String, String> contactList,
                                           final Stack<Map<String, String>>
                                                   oldcontacts,
                                           final Storage storage)
            throws IOException {
        if (!oldcontacts.empty()) {
            contactList = oldcontacts.peek();
            String toStore = "";
            for (String key : contactList.keySet()) {
                toStore = toStore.concat(key + "|"
                        + contactList.get(key) + "\n");
            }
            storage.storagesPlaces(toStore);
            oldcontacts.pop();
            System.out.println("You have undo the previous command.");
        } else {
            System.out.println("The previous command cannot be undo");
        }
        return contactList;
    }
}
