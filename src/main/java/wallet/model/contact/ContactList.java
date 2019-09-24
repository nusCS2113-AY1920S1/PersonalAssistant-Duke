package wallet.model.contact;

import java.util.ArrayList;

public class ContactList {
    /**
     * Stores the current list of contacts of the user.
     */
    private ArrayList<Contact> contactList;

    /**
     * Constructs a new ContactList object.
     */
    public ContactList() {
        this.contactList = new ArrayList<Contact>();
    }

    /**
     * Add the given contact into the contactList.
     *
     * @param contact The contact to be added.
     */
    public void addContact(Contact contact) {
        contactList.add(contact);
    }

    /**
     * Retrieve the contact at the given index of the contactList.
     *
     * @param index The index of the contact in the contactList.
     * @return The contact at the given index.
     */
    public Contact getContact(int index) {
        return contactList.get(index);
    }

    /**
     * Modify the value of the contact at the given index in the contactList.
     *
     * @param index  The index of the contact in the list.
     * @param contact The contact with modified values.
     */
    public void editContact(int index, Contact contact) {
        contactList.set(index, contact);
    }

    /**
     * Removes the contact at the given index of the contactList.
     *
     * @param index The index of the contact in the list
     */
    public void deleteContact(int index) {
        contactList.remove(index);
    }

    /**
     * Get the current number of contacts in the contactList.
     *
     * @return The number of contacts in the list.
     */
    public int getContactListSize() {
        return contactList.size();
    }

    /**
     * Returns the list of contacts in the contactList.
     *
     * @return The list of contacts.
     */
    public ArrayList<Contact> getContactList() {
        return contactList;
    }

    /**
     * Creates Contact object.
     * @param name Name of the contact.
     * @param detail Details of the contact.
     * @param phoneNum Phone Number of the contact.
     * @return The Contact Object.
     */
    public Contact createContact(String name, String detail, String phoneNum) {
        return new Contact(name, detail, phoneNum);
    }
}


