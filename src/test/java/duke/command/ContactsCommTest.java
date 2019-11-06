package duke.command;

import duke.task.ContactList;
import duke.task.Contacts;
import duke.task.TaskList;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author e0318465
public class ContactsCommTest {

    @Test
    void contactsTest() {
        TaskList items = new TaskList();
        Ui ui = new Ui();
        ContactList contactList = new ContactList();
        Contacts contactObj1 = new Contacts("Prof Tan", "91234567"," ", "E1-08-11");
        Command cmd1 = new AddContactsCommand(contactObj1, contactList);
        assertEquals("\n     Got it, now you have 1 contacts. Contact added:\n"
                + "     Name: Prof Tan\n"
                + "     Number: 91234567\n"
                + "     Email: \n"
                + "     Office: E1-08-11", cmd1.executeGui(items, ui));

        Contacts contactObj2 = new Contacts("Prof Tan", "91234567","tancc@nus.edu.sg", "E1-08-11");
        Command cmd2 = new AddContactsCommand(contactObj2, contactList);
        assertEquals("\n     Got it, now you have 2 contacts. Contact added:\n"
                + "     Name: Prof Tan\n"
                + "     Number: 91234567\n"
                + "     Email: tancc@nus.edu.sg\n"
                + "     Office: E1-08-11", cmd2.executeGui(items, ui));

        Command cmd3 = new DeleteContactCommand(1, contactList);
        assertEquals("Now you have 1 contact(s). I've removed this contact:\n"
                + "     Name: Prof Tan\n"
                + "     Number: 91234567\n"
                + "     Email: tancc@nus.edu.sg\n"
                + "     Office: E1-08-11\n"
                + "     Now you have 1 contact(s) in the list.", cmd3.executeGui(items, ui)
                + "\n     Now you have 1 contact(s) in the list.");

        Command cmd4 = new ListContactsCommand(contactList);
        assertEquals("Here are all your contacts:\n"
                + "     1. Name: Prof Tan, 91234567, , E1-08-11\n", cmd4.executeGui(items, ui));

        Command cmd5 = new FindContactCommand("prof tan", contactList);
        assertEquals("     Here are the matching contacts in your list:\n"
                + "     Name: Prof Tan, 91234567, , E1-08-11\n", cmd5.executeGui(items, ui));
    }

}
