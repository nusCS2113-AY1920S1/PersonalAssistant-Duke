package duke.task;

/**
 * Represents a contact that stores information of a person.
 */
public class Contacts {
    protected String name;
    protected String contact = "";
    protected String email = "";
    protected String office = "";
    protected String notes = "";

    /**
     * Creates a contact with name.
     *
     * @param name The name of the contact.
     */
    public Contacts(String name, String contact, String email, String office, String notes){
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.office = office;
        this.notes = notes;
    }

    /**
     * Extracting a contact into string that is suitable for text file.
     *
     * @return String that contains the name and other information of the contact.
     */
    public String toFile() {
        return  name + contact + email + office + notes;
    }

    /**
     * Extracting a task content into readable string.
     *
     * @return String that contains the status and the description of the task.
     */
    @Override
    public String toString() {
        return "Name: " + name +
                "\n Contacts: " + contact +
                "\n Email: " + email +
                "\n Office: " + office +
                "\n Notes: " + notes;
    }
}