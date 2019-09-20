package wallet.model.contact;

public class Contact {

    private String name;
    private String detail;
    private String phoneNum;

    /**
     * Constructs a new Contact object.
     *
     * @param name     Name of the contact.
     * @param detail   Details of the contact.
     * @param phoneNum Phone number of the contact.
     */
    public Contact(String name, String detail, String phoneNum) {
        this.name = name;
        this.detail = detail;
        this.phoneNum = phoneNum;
    }

    /**
     * Returns the name of the contact.
     *
     * @return The name of the contact.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the contact.
     *
     * @param name The name of the contact.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the details of the contact.
     *
     * @return The details of the contact.
     */
    public String getDetail() {
        return detail;
    }

    /**
     * Sets the details of the contact.
     *
     * @param detail The details of the contact.
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * Returns the phone number of the contact.
     *
     * @return The phone number of the contact.
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * Sets the phone number of the contact.
     *
     * @param phoneNum The phone number of the contact.
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /**
     * Returns the concatenated string with all the required fields.
     *
     * @return The concatenated string with all the required fields.
     */
    @Override
    public String toString() {
        return this.name + " " + this.detail + " " + this.phoneNum;
    }
}
