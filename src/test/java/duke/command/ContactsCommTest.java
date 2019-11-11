package duke.command;

import duke.dukeexception.DukeException;
import duke.enums.ErrorMessages;
import duke.enums.Numbers;
import duke.task.ContactList;
import duke.task.Contacts;
import duke.task.TaskList;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//@@author e0318465
/**
 * Tests to check whether all contacts functionality are working.
 */
public class ContactsCommTest {
    @Test
    void contactsTest() {
        TaskList items = new TaskList();
        Ui ui = new Ui();
        ContactList contactList = new ContactList();

        Command cmd = new ListContactsCommand(contactList);
        assertEquals("Here are all your contacts:\n", cmd.executeGui(items, ui));

        Contacts contactObj1 = new Contacts("Prof Tan", "91234567", " ", "E1-08-11");
        Command cmd1 = new AddContactsCommand(contactObj1, contactList);
        assertEquals("\n     Got it, now you have 1 contacts. Contact added:\n"
                + "     Name: Prof Tan\n"
                + "     Number: 91234567\n"
                + "     Email: \n"
                + "     Office: E1-08-11", cmd1.executeGui(items, ui));

        Contacts contactObj2 = new Contacts("Prof Tan", "91234567", "tancc@nus.edu.sg", "E1-08-11");
        Command cmd2 = new AddContactsCommand(contactObj2, contactList);
        assertEquals("\n     Got it, now you have 2 contacts. Contact added:\n"
                + "     Name: Prof Tan\n"
                + "     Number: 91234567\n"
                + "     Email: tancc@nus.edu.sg\n"
                + "     Office: E1-08-11", cmd2.executeGui(items, ui));
    }

    @Test
    void deleteContact() {
        TaskList items = new TaskList();
        Ui ui = new Ui();
        ContactList contactList = new ContactList();

        Contacts contactObj1 = new Contacts("Prof Tan", "+65 91234567", " ", "E1-08-11");
        contactList.add(contactObj1);

        Command cmd1 = new DeleteContactCommand(1, contactList);
        assertEquals("Now you have 0 contact(s). I've removed this contact:\n"
                + "     Name: Prof Tan\n"
                + "     Number: +65 91234567\n"
                + "     Email: \n"
                + "     Office: E1-08-11", cmd1.executeGui(items, ui));
    }

    @Test
    void listContact() {
        TaskList items = new TaskList();
        Ui ui = new Ui();
        ContactList contactList = new ContactList();

        Contacts contactObj1 = new Contacts("Prof Tan", "91234567", "Nil", "E1-08-11");
        Contacts contactObj2 = new Contacts("Rahman", "83783890", "Nil", "Nil");
        contactList.add(contactObj1);
        contactList.add(contactObj2);

        Command cmd = new ListContactsCommand(contactList);
        assertEquals("Here are all your contacts:\n"
                + "     1. Name: Prof Tan, 91234567, Nil, E1-08-11\n"
                + "     2. Name: Rahman, 83783890, Nil, Nil\n", cmd.executeGui(items, ui));
    }

    @Test
    void findContact() {
        TaskList items = new TaskList();
        Ui ui = new Ui();
        ContactList contactList = new ContactList();

        Contacts contactObj1 = new Contacts("Prof Tan", "91234567", "Nil", "E1-08-11");
        contactList.add(contactObj1);

        Command cmd1 = new FindContactCommand("prof tan", contactList);
        assertEquals("     Here are the matching contacts in your list:\n"
                + "     Name: Prof Tan, 91234567, Nil, E1-08-11\n", cmd1.executeGui(items, ui));

        Command cmd2 = new FindContactCommand("Lester", contactList);
        assertEquals("     Here are the matching contacts in your list:\n"
                + "     No matching tasks found.", cmd2.executeGui(items, ui));
    }

    @Test
    void deleteContactsTest_exceptionThrown() {
        TaskList items = new TaskList();
        Ui ui = new Ui();
        ContactList contactList = new ContactList();

        try {
            String nonIntegerInput = "abc";
            Command cmd1 = new DeleteContactCommand(Integer.parseInt(nonIntegerInput), contactList);
            assertEquals("No contacts to be deleted!\n", cmd1.executeGui(items, ui));
            fail(); // the test should not reach this line
        } catch (Exception e) {
            assertEquals("     Input is not an integer value!", ErrorMessages.NON_INTEGER_ALERT.message);
        }

        try {
            String emptyKeyword = "";
            Command cmd2 = new DeleteContactCommand(Integer.parseInt(emptyKeyword), contactList);
            assertEquals("No contacts to be deleted!\n", cmd2.executeGui(items, ui));
            fail(); // the test should not reach this line
        } catch (Exception e) {
            assertEquals("     (>_<) OOPS!!! The keyword cannot be empty.", ErrorMessages.KEYWORD_IS_EMPTY.message);
        }
    }

    @Test
    void addContactsTest_exceptionThrown() {
        TaskList items = new TaskList();
        Ui ui = new Ui();
        ContactList contactList = new ContactList();

        try {
            String[] contactDetails = {"Alexa", "Nil", "emailNotValid", "Nil"};
            if (!contactDetails[Numbers.TWO.value].contains("@")
                    && !contactDetails[Numbers.TWO.value].contains("Nil")) {
                throw new DukeException(ErrorMessages.CONTACT_FORMAT.message);
            }
            Contacts contactObj = new Contacts(contactDetails[Numbers.ZERO.value], contactDetails[Numbers.ONE.value],
                    contactDetails[Numbers.TWO.value], contactDetails[Numbers.THREE.value]);

            Command cmd = new AddContactsCommand(contactObj, contactList);
            assertEquals("\n"
                    + "     Got it, now you have 1 contacts. Contact added:\n"
                    + "     Name: Alexa\n"
                    + "     Number: Nil\n"
                    + "     Email: emailValid@\n"
                    + "     Office: Nil", cmd.executeGui(items, ui));
            fail();
        } catch (Exception e) {
            assertEquals("Format is in: addcontact <name>, <contact>, <email>, <office>\n"
                    + "Check that email has an '@'", ErrorMessages.CONTACT_FORMAT.message);
        }
    }
}