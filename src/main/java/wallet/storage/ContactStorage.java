package wallet.storage;

import wallet.model.contact.Contact;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class ContactStorage extends Storage<Contact> {
    public static final String DEFAULT_STORAGE_FILEPATH_CONTACT = "./data/contact.txt";

    /**
     * Loads the contacts from contact.txt into a temporary ArrayList of Contact objects.
     *
     * @return The ArrayList of Contact objects.
     */
    @Override
    public ArrayList<Contact> loadFile() {
        ArrayList<Contact> contactList = new ArrayList<>();

        try {
            RandomAccessFile raf = new RandomAccessFile(DEFAULT_STORAGE_FILEPATH_CONTACT, "r");
            String str;
            while (raf.getFilePointer() != raf.length()) {
                str = raf.readLine();
                String[] data = str.split(",");
                Contact contact = null;

                if (data.length == 4) {
                    contact = new Contact(data[1],data[2], data[3]);
                }

                if (contact != null) {
                    contact.setId(Integer.parseInt(data[0]));
                    contactList.add(contact);
                }
            }
            raf.close();
        } catch (FileNotFoundException e) {
            System.out.println("No saved contacts found.");
        } catch (IOException e) {
            System.out.println("End of file.");
        }
        return contactList;
    }

    /**
     * Writes the contact into contacts.txt.
     *
     * @param contact The Contact object.
     */
    @Override
    public void writeToFile(Contact contact) {
        try {
            RandomAccessFile raf = new RandomAccessFile(DEFAULT_STORAGE_FILEPATH_CONTACT, "rws");
            raf.seek(raf.length());
            if (raf.getFilePointer() != 0) {
                raf.writeBytes("\r\n");
            }
            raf.writeBytes(contact.writeToFile());
            raf.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    @Override
    public void updateToFile(Contact contact, int index) {

    }

    @Override
    public void removeFromFile(ArrayList<Contact> contactList, int index) {

    }
}
