package gazeeebo.commands.Contact;


import java.util.Map;

public class ListContactCommand {
    static final int HORT_LINE_SEPARATOR = 30;
    /**
     * This method list out all the phone numbers.
     *
     * @param contact    Map each name to its phone number
     * @param lineBreak String separator
     */
    public ListContactCommand(final Map<String, String> contact, final String lineBreak         ) {
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
    }
}
