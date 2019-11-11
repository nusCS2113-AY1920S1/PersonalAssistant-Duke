package duke.storage;

import duke.enums.Numbers;
import duke.task.ContactList;
import duke.task.Contacts;
import duke.ui.Ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.ArrayList;

//@@author e0318465
public class ContactStorage {
    private String filePathForContacts = System.getProperty("user.dir") + "/";

    /**
     * Creates a storage with a specified filePathForContacts.
     *
     * @param filePathForContacts The location of the contacts text file.
     */
    public ContactStorage(String filePathForContacts) {
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
        Ui ui = new Ui();
        File file = new File(filePathForContacts);

        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        String name;
        String contact;
        String email;
        String office;

        while ((st = br.readLine()) != null) {  //name + "," + contact + "," + email + "," + office
            String[] contactDetails = st.split(",");
            if (contactDetails.length != Numbers.FOUR.value) {
                ui.showErrorMsg("     Not all contact details entered, please key in nil for empty fields.");
            } else {
                name = contactDetails[Numbers.ZERO.value];
                contact = contactDetails[Numbers.ONE.value];
                email = contactDetails[Numbers.TWO.value];
                office = contactDetails[Numbers.THREE.value];
                Contacts contactObj = new Contacts(name, contact, email, office);
                contacts.add(contactObj);
            }
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
        for (int i = Numbers.ZERO.value; i < contacts.size(); i++) {
            fileContent += contacts.get(i).toFile() + "\n";
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePathForContacts));
        writer.write(fileContent);
        writer.close();
    }

    //@@author maxxyx96
    //Solution adapted from https://stackoverflow.com/questions/20389255/reading-a-resource-file-from-within-jar
    /**
     * Extracts the sample data from jar file and moves it to data folder in the computer.
     *
     * @param samplePath path of the sample data set for contacts.
     * @throws IOException When there is an error writing to the text file.
     */
    public void writeSample(String samplePath) throws IOException {
        String fileContent = "";
        InputStream in = ContactStorage.class.getResourceAsStream(samplePath);
        if (in == null) {
            in = ContactStorage.class.getClassLoader().getResourceAsStream(samplePath);
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String input;
        while ((input = bufferedReader.readLine()) != null) {
            fileContent += input + "\n";
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePathForContacts));
        writer.write(fileContent);
        writer.close();
        bufferedReader.close();
        in.close();
    } //@@author

}
