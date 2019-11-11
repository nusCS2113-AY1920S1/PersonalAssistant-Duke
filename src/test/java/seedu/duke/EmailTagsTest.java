package seedu.duke;

import org.junit.jupiter.api.Test;

import seedu.duke.common.model.Model;
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
    }

    @Test
    public void updateTagMapTest() {
        // To create a dummy emailList
        EmailFormatParseHelper.Sender sender = new EmailFormatParseHelper.Sender("Akshay", null);
        Email emailOne = new Email("CS2113 luminus-do-not-reply tutorial", sender, null, "", null);
        Email emailTwo = new Email("CS2101 Assignment Tutorial", sender, null, "", null);
        Email emailThree = new Email("UHC Wellness Tutorial", sender, null, "", null);

        EmailList emailList = new EmailList();
        emailList.add(emailOne);
        emailList.add(emailTwo);
        emailList.add(emailThree);

        Model model = Model.getInstance();
        model.setIsUpdateGui(false);
        model.setEmailList(emailList);
        model.initModel();

        EmailContentParseHelper.allKeywordInEmail(emailOne);
        EmailContentParseHelper.allKeywordInEmail(emailTwo);
        EmailContentParseHelper.allKeywordInEmail(emailThree);

        HashMap<String, EmailTags.SubTagMap> tagMap = EmailTags.updateTagMap(emailList);

        // check the root tags in TagMap that exist in the emails
        assertTrue(tagMap.containsKey("CS2113T"));
        assertTrue(tagMap.containsKey("Tutorial"));
        assertTrue(tagMap.containsKey("Spam"));
        assertTrue(tagMap.containsKey("CS2101"));
        assertTrue(tagMap.containsKey("Assignment"));

        // check the sub tags for the root tags, which is the co-existence of the first tag and the second tag

        // emailOne has tags #CS2113T, #Spam and #Tutorial co-existing.
        assertTrue(tagMap.get("CS2113T").containsKey("CS2113T"));
        assertTrue(tagMap.get("CS2113T").containsKey("Spam"));
        assertTrue(tagMap.get("CS2113T").containsKey("Tutorial"));

        assertTrue(tagMap.get("Spam").containsKey("CS2113T"));
        assertTrue(tagMap.get("Tutorial").containsKey("CS2113T"));

        // emailTwo has tags #CS2101, #Tutorial and #Assignment co-existing.
        assertTrue(tagMap.get("CS2101").containsKey("CS2101"));
        assertTrue(tagMap.get("CS2101").containsKey("Assignment"));
        assertTrue(tagMap.get("CS2101").containsKey("Tutorial"));

        assertTrue(tagMap.get("Assignment").containsKey("CS2101"));
        assertTrue(tagMap.get("Tutorial").containsKey("CS2101"));

        // emailThree has the tags #Spam and #Tutorial co-existing.
        assertTrue(tagMap.get("Spam").containsKey("Spam"));
        assertTrue(tagMap.get("Spam").containsKey("Tutorial"));

        assertTrue(tagMap.get("Tutorial").containsKey("Spam"));

        //negative cases
        assertFalse(tagMap.containsKey("SEP"));
        assertFalse(tagMap.containsKey("CG2271"));
        assertFalse(tagMap.get("CS2113T").containsKey("SEP"));
        assertFalse(tagMap.get("Spam").containsKey("SEP"));
        assertFalse(tagMap.get("Tutorial").containsKey("CG2271"));
    }
}