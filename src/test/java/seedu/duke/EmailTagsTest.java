package seedu.duke;

import org.junit.jupiter.api.Test;

import seedu.duke.email.EmailList;
import seedu.duke.email.EmailTags;
import seedu.duke.email.entity.Email;
import seedu.duke.email.parser.EmailContentParseHelper;
import seedu.duke.email.parser.EmailFormatParseHelper;
import seedu.duke.email.storage.EmailStorage;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class EmailTagsTest {
    @Test
    public void updateEmailTagListTestFromStorage() {
        // To get emailList from local storage
        EmailList emailList = EmailStorage.readEmailFromFile("emailTagsTestFile.txt");
        System.out.println(emailList.toString());
        HashMap<String, EmailTags.SubTagMap> tagMap = EmailTags.updateTagMap(emailList);
        System.out.println(tagMap);
        assertNotNull(tagMap);
        //assertTrue(tagMap.containsKey("SEP"));
        //assertTrue(tagMap.containsKey("Spam"));
        //assertTrue(tagMap.containsKey("CS2113T"));
        //assertTrue(tagMap.containsKey("CG2271"));
        //assertTrue(tagMap.containsKey("Assignment"));
        //
        //assertTrue(tagMap.get("CS2113T").containsKey("CS2113T"));
        //assertTrue(tagMap.get("CS2113T").containsKey("Spam"));
        //assertTrue(tagMap.get("CS2113T").containsKey("Tutorial"));
        //assertFalse(tagMap.get("CS2113T").containsKey("SEP"));
        //
        //assertTrue(tagMap.get("CG2271").containsKey("Spam"));
        //assertTrue(tagMap.get("CG2271").containsKey("Tutorial"));
        //assertFalse(tagMap.get("CG2271").containsKey("SEP"));
        //
        //assertTrue(tagMap.get("Spam").containsKey("Spam"));
        //assertTrue(tagMap.get("Spam").containsKey("Tutorial"));
        //assertTrue(tagMap.get("Spam").containsKey("CG2271"));
        //assertFalse(tagMap.get("Spam").containsKey("SEP"));
        //
        //assertTrue(tagMap.get("Tutorial").containsKey("Tutorial"));
        //assertTrue(tagMap.get("Tutorial").containsKey("Spam"));
        //assertTrue(tagMap.get("Tutorial").containsKey("CS2113T"));
        //assertTrue(tagMap.get("Tutorial").containsKey("CG2271"));
        //assertFalse(tagMap.get("Tutorial").containsKey("SEP"));
    }

    @Test
    public void updateEmailTagListTest() {
        // To create a dummy emailList
        String body = "CS2113T Akshay Narayan CS2113 TAN KIAN WEI uhc Wellness";
        EmailFormatParseHelper.Sender sender = new EmailFormatParseHelper.Sender("Akshay", null);
        Email email = new Email("CS2113 SEP", sender, null, body, null);

        EmailContentParseHelper.initKeywordList();
        EmailContentParseHelper.allKeywordInEmail(email);

        EmailList emailList = new EmailList();
        emailList.add(email);

        HashMap<String, EmailTags.SubTagMap> tagMap = EmailTags.updateTagMap(emailList);

        assertTrue(tagMap.containsKey("SEP"));
        assertTrue(tagMap.containsKey("Spam"));
        assertTrue(tagMap.containsKey("CS2113T"));
        assertFalse(tagMap.containsKey("CG2271"));
        assertFalse(tagMap.containsKey("Assignment"));


        assertTrue(tagMap.get("SEP").containsKey("SEP"));
        assertTrue(tagMap.get("Spam").containsKey("Spam"));
        assertTrue(tagMap.get("CS2113T").containsKey("CS2113T"));

        assertTrue(tagMap.get("SEP").containsKey("CS2113T"));
        assertTrue(tagMap.get("SEP").containsKey("Spam"));

        assertTrue(tagMap.get("CS2113T").containsKey("Spam"));
        assertTrue(tagMap.get("CS2113T").containsKey("SEP"));

        assertTrue(tagMap.get("Spam").containsKey("CS2113T"));
        assertTrue(tagMap.get("Spam").containsKey("SEP"));

        assertFalse(tagMap.get("CS2113T").containsKey("Tutorial"));
    }
}