package duke.task;

/**
 * Represents a contact that stores information of a person.
 */
public class Contacts {
    protected String name = "";
    protected String contact = "";
    protected String email = "";
    protected String office = "";

    /**
     * Creates a contact with name.
     *
     * @param name The name of the contact.
     */
    public Contacts(String name, String contact, String email, String office){
        this.name = name.trim();
        this.contact = contact.trim();
        this.email = email.trim();
        this.office = office.trim();
    }

    public String toFile() {
        return  name + "," + contact + "," + email + "," + office;
    }

    /**
     * Extracting a task content into readable string.
     *
     * @return String that contains the status and the description of the task.
     */
    @Override
    public String toString() {
        return "Name: " + name +
                "\nNumber: " + contact +
                "\nEmail: " + email +
                "\nOffice: " + office;
    }

    /**
     * Extracting a contacts content into readable string (GUI).
     *
     * @return String that contains all details of a contact.
     */
    public String toStringGui() {
        return "Name: " + name + ", " + contact + ", " + email + ", " + office;
    }
}