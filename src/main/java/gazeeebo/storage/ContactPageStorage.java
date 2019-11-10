//@@author JasonLeeWeiHern
package gazeeebo.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class ContactPageStorage {

    /** Contact storage file name. */
    private String relativePathContactResource
            = "Contact.txt";

    /**
     * THis method writes to the file Contact.txt.
     *
     * @param fileContent save the contact information into this file
     * @throws IOException catch the error if the read file fails.
     */
    public void writeToContactFile(
            final String fileContent) throws IOException {
        FileWriter fileWriter = new FileWriter(relativePathContactResource);
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * This method read from the file
     * Contact.txt and put the details into a HashMap.
     *
     * @return Returns the HashMap of contacts, key
     * is the contact name and the value is the phone number.
     * @throws FileNotFoundException catch the error if the read file fails.
     */
    public HashMap<String, String> readFromContactFile()
            throws FileNotFoundException {
        HashMap<String, String> contactList = new HashMap<String, String>();


        File f = new File(relativePathContactResource);
        Scanner sc = new Scanner(f);
        while (sc.hasNext()) {
            String[] split = sc.nextLine().split("\\|");
            contactList.put(split[0], split[1]);
        }
        return contactList;
    }
}
