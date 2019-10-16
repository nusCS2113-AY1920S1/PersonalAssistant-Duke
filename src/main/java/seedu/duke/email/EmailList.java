package seedu.duke.email;

import seedu.duke.CommandParser;
import seedu.duke.email.entity.Email;

import java.io.IOException;
import java.util.ArrayList;

public class EmailList extends ArrayList<Email> {

    /**
     * Converts the email list to a string of the pre-determined format that is ready to be displayed by the
     * UI.
     *
     * @return list of emails in formatted String.
     */
    @Override
    public String toString() {
        if (this.size() == 0) {
            return "There is nothing in your email list.";
        }
        int index = 0;
        String listOfEmails = "This is your list of emails " + "(total of " + this.size() + "): ";
        for (Email email : this) {
            listOfEmails += "\n" + (++index) + ". " + email.getSubject();
        }
        return listOfEmails;
    }

    /**
     * Show the email in browser.
     *
     * @param index of the email to be shown in the email list.
     * @return a string to inform the user that the particular email is being shown in browser.
     * @throws CommandParser.UserInputException thrown when index parsing failed or out of range
     * @throws IOException                      if fails to load the filepath or open the browser.
     */
    public String[] show(int index) throws CommandParser.UserInputException, IOException {
        if (index < 0 || index >= this.size()) {
            throw new CommandParser.UserInputException("Invalid index");
        }
        Email email = this.get(index);
        String emailContent = email.colorBodyOnTag();
        String responseMsg = "Showing email in browser: " + email.getSubject();
        String[] responseArray = {responseMsg, emailContent};
        return responseArray;
    }

}
