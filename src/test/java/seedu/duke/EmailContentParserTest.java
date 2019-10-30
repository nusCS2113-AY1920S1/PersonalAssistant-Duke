package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.common.model.Model;
import seedu.duke.email.entity.KeywordPair;
import seedu.duke.email.parser.EmailContentParseHelper;
import seedu.duke.email.parser.EmailFormatParseHelper;
import seedu.duke.email.entity.Email;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.duke.email.parser.EmailContentParseHelper.editDistance;
import static seedu.duke.email.parser.EmailContentParseHelper.keywordInString;

public class EmailContentParserTest {
    @Test
    public void editDistanceTest() {
        assertEquals(1, editDistance("a", "b"));
        assertEquals(1, editDistance("a", ""));
        assertEquals(2, editDistance("a", "bc"));
        assertEquals(1, editDistance("a", "ba"));
        assertEquals(4, editDistance("food", "money"));
        assertEquals(3, editDistance("kitten", "sitting"));
    }

    @Test
    public void keywordInStringTest() {
        String input = "CS2113T Akshay Narayan CS2113 TAN KIAN WEI";
        KeywordPair keywordPair = new KeywordPair(
                "CS2113T", new ArrayList<String>(List.of("CS2113T", "CS2113", "TAN KIAN WEI, JASON",
                "Leow Wei Xiang", "Akshay Narayan", "Akshay")));

        assertEquals(4, keywordInString(input, keywordPair));
    }

    @Test
    public void allKeyWordInEmailTest() {
        String body = "CS2113T Akshay Narayan CS2113 TAN KIAN WEI ";
        EmailFormatParseHelper.Sender sender = new EmailFormatParseHelper.Sender("Akshay", null);
        Email email = new Email("CS2113", sender, null, body, null);

        Model.getInstance().initModel();
        //EmailContentParseHelper.initKeywordList();
        EmailContentParseHelper.allKeywordInEmail(email);

        ArrayList<Email.Tag> tags = email.getTags();

        for (Email.Tag tag : tags) {
            if (tag.getKeywordPair().getKeyword().equals("CS2113T")) {
                assertEquals(12, tag.getRelevance());
            }
        }
    }
}
