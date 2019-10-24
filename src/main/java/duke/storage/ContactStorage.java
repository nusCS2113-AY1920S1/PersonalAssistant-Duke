package duke.storage;

import duke.task.ContactList;
import duke.task.Contacts;
import duke.ui.Ui;

import java.io.*;
import java.util.ArrayList;

//@@author e0318465
public class ContactStorage {
    //protected String filePathForContacts = "./";
    protected String filePathForContacts = "";
    String storageClassPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;

    /**
     * Creates a storage with a specified filePathForContacts.
     *
     * @param filePathForContacts The location of the contacts text file.
     */
    public ContactStorage(String filePathForContacts) {
        int numberOfSlash;
        storageClassPath = storageClassPath.replaceAll("%20", " ");
        String[] pathSplitter = storageClassPath.split("/");
        numberOfSlash = pathSplitter.length - ONE;
        for (String directory: pathSplitter) {
            if (numberOfSlash == ZERO) {
                break;
            } else if (!directory.isEmpty() && !directory.equals("build") && !directory.equals("out")) {
                this.filePathForContacts += directory + "/";
            } else if (directory.equals("build") || directory.equals("out")) {
                break;
            }
            numberOfSlash--;
        }
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

                if(contactDetails.length != 4){
                    ui.showErrorMsg("     Not all contact details entered, please leave a space for empty fields.");
                } else{
                    name = contactDetails[ZERO];
                    contact = contactDetails[ONE];
                    email = contactDetails[TWO];
                    office = contactDetails[THREE];
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
        for (int i = ZERO; i < contacts.size(); i++) {
            fileContent += contacts.get(i).toFile() + "\n";
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePathForContacts));
        writer.write(fileContent);
        writer.close();
    }
}
