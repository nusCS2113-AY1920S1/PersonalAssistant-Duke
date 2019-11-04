//@@author YuanJiayi
package mistermusik.commons;

import java.util.logging.Logger;

public class Contact {
    private static Logger logger = Logger.getLogger("Contact");
    private String name;
    private String email;
    private String phoneNo;

    public Contact(String name, String email, String phoneNo) {
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setName(String name) {
        this.name = name;
//        logger.log(Level.INFO, "The name of the contact is edited");
    }

    public void setEmail(String email) {
        this.email = email;
//        logger.log(Level.INFO, "The email address of the contact is edited");
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
//        logger.log(Level.INFO, "The phone number of the contact is edited");
    }
}
