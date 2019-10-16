package gazeeebo.commands.Contact;


import java.util.Map;

public class ListContactCommand {

    /**
     * This method list out all the phone numbers.
     *
     * @param contact    Map each name to its phone number
     * @param LINE_BREAK String separator
     */
    public ListContactCommand(Map<String, String> contact, String LINE_BREAK) {
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
    }
}
