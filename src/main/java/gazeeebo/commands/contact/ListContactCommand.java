package gazeeebo.commands.contact;


import java.util.Map;

public class ListContactCommand {
    static final int HORT_LINE_SEPARATOR = 30;

    /**
     * This method list out all the phone numbers.
     *
     * @param contact   the object that Map each name to its phone number.
     * @param lineBreak the object that print out a separator to separate each line in the list.
     */
    public ListContactCommand(final Map<String, String> contact, final String lineBreak) {
        System.out.print("Name:                         | Number:\n" + lineBreak);
        for (String key : contact.keySet()) {
            if (!key.contains("NUS") || !key.contains("CEG")) {
                forPrint(contact, lineBreak, key);
            }
        }
        System.out.print("\nCEG CONTACTS:\n");
        for (String key : contact.keySet()) {
            if (key.contains("CEG")) {
                forPrint(contact, lineBreak, key);
            }
        }
        System.out.print("\nNUS CONTACTS:\n");
        for (String key : contact.keySet()) {
            if (key.contains("NUS")) {
                forPrint(contact, lineBreak, key);
            }
        }
    }

    /**
     * To print the contact list.
     *
     * @param contact   the object that Map each name to its phone number.
     * @param lineBreak the object that print out a separator to separate each line in the list.
     * @param key       gets the key of the contact.
     */
    public void forPrint(final Map<String, String> contact, final String lineBreak, final String key) {
        System.out.print(key);
        int l = HORT_LINE_SEPARATOR - key.length();
        for (int i = 0; i < l; i++) {
            System.out.print(" ");
        }
        System.out.print("| ");
        System.out.print(contact.get(key) + "\n" + lineBreak);
    }
}
