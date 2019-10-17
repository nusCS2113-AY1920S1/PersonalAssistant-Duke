package duke.storage;

import duke.task.ContactList;
import duke.task.Contacts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ContactStorage {
    protected String filePathForContacts = "./";
//    protected String filePathForContacts = "";
//    String storageClassPath = Storage.class.getProtectionDomain().getCodeSource().getLocation().getPath();

    /**
     * Creates a storage with a specified filePathForContacts.
     *
     * @param filePathForContacts The location of the contacts text file.
     */
    public ContactStorage(String filePathForContacts) {
//        String[] pathSplitter = storageClassPath.split("/");
//        for (String directory: pathSplitter) {
//            if (!directory.isEmpty() && !directory.equals("build")) {
//                this.filePathForContacts += directory + "/";
//            } else if (directory.equals("build")) {
//                break;
//            }
//        }
        this.filePathForContacts += filePathForContacts;
    }


    /**
     * Updates the contact list from reading the contents of the contacts text file.
     *
     * @return ArrayList to update the contactList.
     * @throws IOException  If there is an error reading the text file.
     */
    public ArrayList<Contacts> read() throws IOException {
        ArrayList<Contacts> contacts = new ArrayList<>();
        File file = new File(filePathForContacts);

        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        String name;
        String contact;
        String email;
        String office;
        while ((st = br.readLine()) != null) {  //name + "," + contact + "," + email + "," + office
            String[] contactDetails = st.split(",");
            name = contactDetails[0];
            contact = contactDetails[1];
            email = contactDetails[2];
            office = contactDetails[3];
            Contacts contactObj = new Contacts(name, contact, email, office);
            contacts.add(contactObj);
        }
        br.close();
        return contacts;
    }

    /**
     * Updates the text file from interpreting the contacts in contactList.
     *
     * @param contacts The contact list that contains a list of contacts.
     * @throws IOException If there is an error writing the text file.
     */
    public void write(ContactList contacts) throws IOException {
        String fileContent = "";
        for (int i = 0; i < contacts.size(); i++) {
            fileContent += contacts.get(i).toFile() + "\n";
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePathForContacts));
        writer.write(fileContent);
        writer.close();
    }
}
