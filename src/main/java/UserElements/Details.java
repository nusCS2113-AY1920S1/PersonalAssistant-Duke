package UserElements;

import java.util.ArrayList;

public class Details {
    private String venue;
    private ArrayList<Contact> contactList;
    private ArrayList<String> checkList;

    public Details(String venue) {
        this.venue = venue;
        this.contactList = new ArrayList<>();
        this.checkList = new ArrayList<>();
    }

    public void addContact(String name, String email, String phoneNo) {
        Contact tempContact = new Contact(name, email, phoneNo);
        boolean SameContact = false;
        for (Contact currContact : contactList) {
            if (currContact.equals(tempContact)) {
                SameContact = true;
                break;
            }
        }
        if (!SameContact)
            contactList.add(tempContact);
    }

    public void deleteContact(String name) {
        for (Contact currContact : contactList) {
            if (currContact.getName().equals(name)) {
                contactList.remove(currContact);
                break;
            }
        }
    }
    
}
