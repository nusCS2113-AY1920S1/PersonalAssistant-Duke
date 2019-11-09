import mistermusik.commons.Contact;
import mistermusik.commons.budgeting.CostExceedsBudgetException;
import mistermusik.commons.events.eventtypes.Event;
import mistermusik.commons.events.eventtypes.eventsubclasses.assessmentsubclasses.Exam;
import mistermusik.commons.events.eventtypes.eventsubclasses.assessmentsubclasses.Recital;
import mistermusik.commons.events.eventtypes.eventsubclasses.recurringeventsubclasses.Lesson;
import mistermusik.commons.events.eventtypes.eventsubclasses.recurringeventsubclasses.Practice;
import mistermusik.logic.ClashException;
import mistermusik.logic.EndBeforeStartException;
import mistermusik.logic.EventList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

//@@author YuanJiayi
public class ContactTest {
    @Test
    public void addContactTest() throws CostExceedsBudgetException, EndBeforeStartException, ClashException {
        ArrayList<String> testListString = new ArrayList<>();
        EventList testList = new EventList(testListString);
        Event eventTest = new Practice("eventTest", "12-12-2019 1200", "12-12-2019 1300");
        testList.addEvent(eventTest);
        //test if contact is added
        Contact normalContact = new Contact("name 1", "email 1", "phone 1");
        testList.getEvent(0).addContact(normalContact);
        assertTrue(eventTest.getContactList().contains(normalContact));
        //test empty email
        Contact emptyEmailContact = new Contact("name 2", "", "phone 2");
        testList.getEvent(0).addContact(emptyEmailContact);
        assertTrue(eventTest.getContactList().contains(emptyEmailContact));
        //test empty phone
        Contact emptyPhoneContact = new Contact("name 3", "email 3", "");
        testList.getEvent(0).addContact(emptyPhoneContact);
        assertTrue(eventTest.getContactList().contains(emptyPhoneContact));
    }

    @Test
    public void removeContactTest() throws CostExceedsBudgetException, EndBeforeStartException, ClashException {
        ArrayList<String> testListString = new ArrayList<>();
        EventList testList = new EventList(testListString);
        Event eventTest = new Lesson("eventTest", "12-12-2019 1200", "12-12-2019 1300");
        testList.addEvent(eventTest);
        Contact normalContact = new Contact("name 1", "email 1", "phone 1");
        testList.getEvent(0).addContact(normalContact);
        //test if contact is removed
        testList.getEvent(0).removeContact(0);
        assertFalse(eventTest.getContactList().contains(normalContact));
        //test remove a contact not in the list
        try {
            testList.getEvent(0).removeContact(1);
            fail();
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    @Test
    public void getContactTest() throws CostExceedsBudgetException, EndBeforeStartException, ClashException {
        ArrayList<String> testListString = new ArrayList<>();
        EventList testList = new EventList(testListString);
        Event eventTest = new Exam("eventTest", "12-12-2019 1200", "12-12-2019 1300");
        testList.addEvent(eventTest);
        Contact contact1 = new Contact("name 1", "email 1", "phone 1");
        Contact contact2 = new Contact("name 2", "", "phone 2");

        testList.getEvent(0).addContact(contact1);
        testList.getEvent(0).addContact(contact2);
        assertEquals("name 1", testList.getEvent(0).getContactList().get(0).getName());
        assertEquals("name 2", testList.getEvent(0).getContactList().get(1).getName());
        assertEquals("email 1", testList.getEvent(0).getContactList().get(0).getEmail());
        assertEquals("", testList.getEvent(0).getContactList().get(1).getEmail());
        assertEquals("phone 1", testList.getEvent(0).getContactList().get(0).getPhoneNo());
        assertEquals("phone 2", testList.getEvent(0).getContactList().get(1).getPhoneNo());
    }

    @Test
    public void editContactTest() throws CostExceedsBudgetException, EndBeforeStartException, ClashException {
        ArrayList<String> testListString = new ArrayList<>();
        EventList testList = new EventList(testListString);
        Event eventTest = new Recital("eventTest", "12-12-2019 1200", "12-12-2019 1300");
        testList.addEvent(eventTest);
        Contact contact1 = new Contact("name 1", "email 1", "phone 1");

        boolean addContactSucceed = false;
        try {
            testList.getEvent(0).addContact(contact1);
            addContactSucceed = true;
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            System.out.println("1");
        }
        assertTrue(addContactSucceed);

        int contactIndex = 0;

        //test if name edited
        String newName = "name a";
        testList.getEvent(0).editContact(contactIndex, 'N', newName);
        assertEquals(newName, testList.getEvent(0).getContactList().get(contactIndex).getName());
        newName = "name";
        eventTest.getContactList().get(contactIndex).setName(newName);
        assertEquals(newName, eventTest.getContactList().get(contactIndex).getName());

        //test if email edited
        String newEmail = "email a";
        testList.getEvent(0).editContact(contactIndex, 'E', newEmail);
        assertEquals(newEmail, testList.getEvent(0).getContactList().get(contactIndex).getEmail());
        newEmail = "email";
        eventTest.getContactList().get(contactIndex).setEmail(newEmail);
        assertEquals(newEmail, eventTest.getContactList().get(contactIndex).getEmail());

        //test if phone number is edited
        String newPhone = "phone a";
        testList.getEvent(0).editContact(contactIndex, 'P', newPhone);
        assertEquals(newPhone, testList.getEvent(0).getContactList().get(contactIndex).getPhoneNo());
        newPhone = "phone";
        eventTest.getContactList().get(contactIndex).setPhoneNo(newPhone);
        assertEquals(newPhone, eventTest.getContactList().get(contactIndex).getPhoneNo());

        //test wrong contact index
        String newContact = "wrong contact index";
        try {
            testList.getEvent(0).editContact(23, 'E', newContact);
            fail();
        } catch (IndexOutOfBoundsException ignored) {
        }
    }
}
