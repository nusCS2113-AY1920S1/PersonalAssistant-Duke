package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.email.entity.Email;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmailTest {
    @Test
    public void addTagTest() {
        Email email = new Email("Test Email");
        String keyword = "Spam";
        email.addTag(keyword);

        int infinity = 0x3f3f3f;

        ArrayList<Email.Tag> tags = email.getTags();

        for (Email.Tag tag : tags) {
            if (tag.getKeywordPair().getKeyword().equals(keyword)) {
                assertEquals(infinity, tag.getRelevance());
            }
        }
    }


}
