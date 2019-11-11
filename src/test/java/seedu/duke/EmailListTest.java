package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.common.model.Model;
import seedu.duke.email.EmailList;
import seedu.duke.email.entity.Email;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class EmailListTest {

    @Test
    public void clearListTest() {
        Email emailOne = new Email("TestEmailOne");
        Email emailTwo = new Email("TestEmailTwo");
        Email emailThree = new Email("TestEmailThree");

        EmailList emailList = new EmailList();
        emailList.add(emailOne);
        emailList.add(emailTwo);
        emailList.add(emailThree);

        Model model = Model.getInstance();
        model.setIsUpdateGui(false);
        model.setEmailList(emailList);

        emailList.clearList();
        assertEquals(0, emailList.size());

        String msg = emailList.clearList();
        assertEquals("The email list has already been cleared", msg);
    }

    @Test
    public void deleteLocalEmailTest() {
        Email emailOne = new Email("TestEmailOne");
        Email emailTwo = new Email("TestEmailTwo");
        Email emailThree = new Email("TestEmailThree");

        EmailList emailList = new EmailList();
        emailList.add(emailOne);
        emailList.add(emailTwo);
        emailList.add(emailThree);

        Model model = Model.getInstance();
        model.setIsUpdateGui(false);
        model.setEmailList(emailList);

        assertEquals(3, emailList.size());

        emailList.deleteLocalEmail(1);
        assertEquals(2, emailList.size());

        emailList.deleteLocalEmail(1);
        assertEquals(1, emailList.size());
    }

    @Test
    public void addTagsTest() {
        Email emailOne = new Email("TestEmailOne");

        EmailList emailList = new EmailList();
        emailList.add(emailOne);

        Model model = Model.getInstance();
        model.setIsUpdateGui(false);
        model.setEmailList(emailList);

        ArrayList<String> tags = new ArrayList<>();
        tags.add("tag1");
        tags.add("tag2");

        String msg = "Tags added: " + tags.toString() + System.lineSeparator() + "to email: "
                        + emailList.get(0).getSubject();

        // tags added to the first email
        assertEquals(msg, emailList.addTags(0, tags));

        // tags already exist in the email, no tags is added to the email
        assertEquals("", emailList.addTags(0, tags));

    }

}
