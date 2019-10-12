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

    /**
     * Creates a storage with a specified filePathForContacts.
     *
     * @param filePathForContacts The location of the text file.
     */
    public ContactStorage(String filePathForContacts) {
        this.filePathForContacts += filePathForContacts;
    }


    /**
     * Updates the task list from reading the contents of the text file.
     *
     * @return ArrayList to update the priorityList.
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
     * Updates the text file from interpreting the priorities of the priorityList.
     *
     * @param contacts The list of contacts.
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
