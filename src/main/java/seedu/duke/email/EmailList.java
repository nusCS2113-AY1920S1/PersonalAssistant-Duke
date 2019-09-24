package seedu.duke.email;

import seedu.duke.Parser;
import seedu.duke.email.Email;

import java.io.IOException;
import java.util.ArrayList;

public class EmailList extends ArrayList<Email> {

    @Override
    public String toString() {
        if (this.size() == 0) {
            return "There is nothing in your email list.";
        }
        int index = 0;
        String listOfEmails = "This is your list of emails " + "(total of " + this.size() + "): ";
        for (Email email : this) {
            listOfEmails += "\n" + (++index) + ". " + email.getTitle();
        }
        return listOfEmails;
    }

    public String show(int index) throws Parser.UserInputException, IOException {
        System.out.println(this.size());
        if (index < 0 || index >= this.size()) {
            throw new Parser.UserInputException("Invalid index");
        }
        Email email = this.get(index);
        email.showEmail();
        String responseMsg = "Email showing in browser: " + email.getTitle();
        return responseMsg;
    }

}
