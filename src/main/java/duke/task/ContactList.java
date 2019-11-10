package duke.task;

import duke.enums.Numbers;

import java.util.ArrayList;

/**
 * Represents a list of contacts.
 */
//@@author e0318465
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
     * Adds a contact into the contact list.
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
     * Displays the selected contact detail.
     *
     * @param index The index of contact.
     * @return Contact details in String format.
     */
    public String getAndDisplay(int index) {
        Contacts contacts = contactList.get(index);
        return contacts.toString();
    }

    /**
     * Retrieves all contacts from the contact list.
     *
     * @return String that contains the whole list of contacts.
     */
    public String getFullContactList() {
        String fullContactList = "";
        for (int i = Numbers.ZERO.value; i < contactList.size(); i++) {
            fullContactList += "     " + (i + Numbers.ONE.value) + ". " + contactList.get(i).toStringGui() + "\n";
        }
        return fullContactList;
    }

    /**
     * Extracts only the details so that only relevant details are being searched.
     *
     * @param index The index of contact.
     * @return Contact which is retrieved from contact list.
     */
    public String getOnlyDetails(int index) {
        Contacts details = contactList.get(index);
        return details.toFile();
    }

    /**
     * Retrieves some contacts from the contact list.
     *
     * @param index The index of the task.
     * @return String that contains the whole list of contacts.
     */
    public String getSpecificContactList(int index) {
        return contactList.get(index).toStringGui();
    }

    /**
     * Retrieves size of the contact list.
     *
     * @return int that represents the contact list size.
     */
    public int size() {
        return contactList.size();
    }

    /**
     * Removes a contact from the contact list.
     *
     * @param indexOfContact The index of contact to be removed.
     */
    public void remove(int indexOfContact) {
        contactList.remove(indexOfContact);
    }
}