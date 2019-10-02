package duke.task;

import java.util.ArrayList;

/**
 * Represents a list of contacts.
 */
public class ContactList {
    protected ArrayList<Contacts> contactList;

    /**
     * Creates an empty contact list using an array list.
     */
    public ContactList() {
        contactList = new ArrayList<>();
    }

    /**
     * Creates an updated contact list with the specified array list.
     *
     * @param contactList The updated array list.
     */
    public ContactList(ArrayList<Contacts> contactList) {
        this.contactList = contactList;
    }

    /**
     * To add a contact into the contact list.
     *
     * @param contactObj A contact to be added.
     */
    public void add(Contacts contactObj) {
        contactList.add(contactObj);
    }

    /**
     * Retrieves the contact from the contact list via index.
     *
     * @param index The index of contact.
     * @return Contact which is retrieved from the index.
     */
    public Contacts get(int index) {
        return contactList.get(index);
    }

    /**
     * The size of the contact list.
     *
     * @return int that represents the contact list size.
     */

    public int size() {
        return contactList.size();
    }
}