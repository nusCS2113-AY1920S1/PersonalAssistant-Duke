package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.common.model.Model;
import seedu.duke.email.EmailList;
import seedu.duke.email.entity.KeywordPair;
import seedu.duke.email.parser.EmailContentParseHelper;
import seedu.duke.email.parser.EmailFormatParseHelper;
import seedu.duke.email.entity.Email;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.duke.email.parser.EmailContentParseHelper.editDistance;
import static seedu.duke.email.parser.EmailContentParseHelper.keywordInString;

public class EmailContentParseHelperTest {
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
        String input1 = "CS2113T CS2113 Akshay Narayan CS2113 TAN KIAN WEI";
        String input2 = "CS2113 Tutorial CS2113ABC CS2113";
        String input3 = "CS2113 CS2113 CS2113 CS2113";
        KeywordPair keywordPair = new KeywordPair(
                "CS2113T", new ArrayList<String>(List.of("CS2113T", "CS2113", "TAN KIAN WEI, JASON",
                "Leow Wei Xiang", "Akshay Narayan", "Akshay")));

        assertEquals(5, keywordInString(input1, keywordPair));
        assertEquals(2, keywordInString(input2, keywordPair));
        assertEquals(4, keywordInString(input3, keywordPair));
    }

    @Test
    public void allKeyWordInEmailTest() {
        String body = "CS2113T Akshay Narayan CS2113 TAN KIAN WEI ";
        EmailFormatParseHelper.Sender sender = new EmailFormatParseHelper.Sender("Akshay", null);
        Email email = new Email("CS2113", sender, null, body, null);

        EmailList emailList = new EmailList();
        emailList.add(email);

        Model model = Model.getInstance();
        model.setIsUpdateGui(false);
        model.setEmailList(emailList);
        model.initModel();

        EmailContentParseHelper.allKeywordInEmail(email);

        ArrayList<Email.Tag> tags = email.getTags();

        for (Email.Tag tag : tags) {
            if (tag.getKeywordPair().getKeyword().equals("CS2113T")) {
                assertEquals(12, tag.getRelevance());
            }
        }
    }
}
