package duke.command;

import duke.task.ContactList;
import duke.task.Contacts;
import duke.task.TaskList;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author e0318465
public class AddContactsCommTest {

    @Test
    void addContactsTest() {
        TaskList items = new TaskList();
        Ui ui = new Ui();
        ContactList contactList = new ContactList();
        Contacts contactObj1 = new Contacts("Prof Tan", "91234567"," ", "E1-08-11");
        Command cmd1 = new AddContactsCommand((contactObj1));
        assertEquals("\n     Got it, now you have 1 contacts. Contact added:\n"
                + "     Name: Prof Tan\n"
                + "     Number: 91234567\n"
                + "     Email: \n     Office: E1-08-11\n"
                + "     Now you have 1 contacts.", cmd1.executeGui(items, contactList, ui)
                + "\n     Now you have 1 contacts.");

        Contacts contactObj2 = new Contacts("Prof Tan", "91234567","tancc@nus.edu.sg", "E1-08-11");
        Command cmd2 = new AddContactsCommand((contactObj2));
        assertEquals("\n     Got it, now you have 2 contacts. Contact added:\n"
                + "     Name: Prof Tan\n"
                + "     Number: 91234567\n     Email: tancc@nus.edu.sg\n"
                + "     Office: E1-08-11\n"
                + "     Now you have 2 contacts.", cmd2.executeGui(items, contactList, ui)
                + "\n     Now you have 2 contacts.");

        Command cmd3 = new DeleteContactCommand(1);
        assertEquals("Now you have 1 contact(s). I've removed this contact:\n"
                + "     Name: Prof Tan\n"
                + "     Number: 91234567\n     Email: tancc@nus.edu.sg\n"
                + "     Office: E1-08-11\n"
                + "     Now you have 1 contact(s) in the list.", cmd3.executeGui(items, contactList, ui)
                + "\n     Now you have 1 contact(s) in the list.");
    }
}
