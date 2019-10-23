package seedu.duke;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import seedu.duke.email.EmailList;
import seedu.duke.email.EmailStorage;
import seedu.duke.email.EmailTags;

import java.util.HashMap;

public class EmailTagsTest {

    @Test
    public void updateEmailTagListTest() {
        EmailList emailList = EmailStorage.readEmailFromFile();
        System.out.println(emailList.toString());

        HashMap<String, EmailTags.SubTagMap> tagMap = EmailTags.updateEmailTagList(emailList);
        System.out.println(tagMap);

        assertTrue(tagMap.containsKey("SEP"));
        assertTrue(tagMap.containsKey("Spam"));
        assertTrue(tagMap.containsKey("CS2113T"));
        assertTrue(tagMap.containsKey("CG2271"));
        assertTrue(tagMap.containsKey("Assignment"));

        assertTrue(tagMap.get("CS2113T").containsKey("CS2113T"));
        assertTrue(tagMap.get("CS2113T").containsKey("Spam"));
        assertTrue(tagMap.get("CS2113T").containsKey("Tutorial"));
        assertFalse(tagMap.get("CS2113T").containsKey("SEP"));

        assertTrue(tagMap.get("CG2271").containsKey("Spam"));
        assertTrue(tagMap.get("CG2271").containsKey("Tutorial"));
        assertFalse(tagMap.get("CG2271").containsKey("SEP"));

        assertTrue(tagMap.get("Spam").containsKey("Spam"));
        assertTrue(tagMap.get("Spam").containsKey("Tutorial"));
        assertTrue(tagMap.get("Spam").containsKey("CG2271"));
        assertTrue(tagMap.get("Spam").containsKey("SEP"));

        assertTrue(tagMap.get("Tutorial").containsKey("Tutorial"));
        assertTrue(tagMap.get("Tutorial").containsKey("Spam"));
        assertTrue(tagMap.get("Tutorial").containsKey("CS2113T"));
        assertTrue(tagMap.get("Tutorial").containsKey("CG2271"));
        assertFalse(tagMap.get("Tutorial").containsKey("SEP"));
    }

}