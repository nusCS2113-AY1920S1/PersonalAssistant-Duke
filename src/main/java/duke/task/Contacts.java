package duke.task;

/**
 * Represents a contact that stores information of a person.
 */
//@@author e0318465
public class Contacts {
    protected String name;
    protected String contact;
    protected String email;
    protected String office;

    /**
     * Creates a contact with the relevant details.
     *
     * @param name Name of contact.
     * @param contact Number of contact.
     * @param email Electronic mail of contact.
     * @param office Office of contact.
     */
    public Contacts(String name, String contact, String email, String office) {
        this.name = name.trim();
        this.contact = contact.trim();
        this.email = email.trim();
        this.office = office.trim();
    }

    /**
     * Extracting a contact content into string that is suitable for text file.
     *
     * @return String that contains the details of the contact.
     */
    public String toFile() {
        return  name + "," + contact + "," + email + "," + office;
    }

    /**
     * Extracting a contact content into readable string.
     *
     * @return String that contains the details of the contact.
     */
    @Override
    public String toString() {
        return "Name: " + name
                + "\nNumber: " + contact
                + "\nEmail: " + email
                + "\nOffice: " + office;
    }

    /**
     * Extracting a contact content into readable string (GUI).
     *
     * @return String that contains the details of the contact.
     */
    public String toStringGui() {
        return "Name: " + name + ", " + contact + ", " + email + ", " + office;
    }
}