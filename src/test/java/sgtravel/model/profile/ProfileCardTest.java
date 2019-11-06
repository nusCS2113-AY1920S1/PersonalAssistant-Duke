package sgtravel.model.profile;

import sgtravel.commons.exceptions.ParseException;
import sgtravel.logic.parsers.ParserTimeUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfileCardTest {
    private ProfileCard profileCard = new ProfileCard();

    @Test
    void execute() throws ParseException {
        LocalDateTime birthday = ParserTimeUtil.parseStringToDate("01/01/01");

        profileCard.setPerson("Danny", birthday);
        assertEquals(profileCard.getPersonName(), "Danny");
        assertEquals(profileCard.getPersonBirthday(), birthday);
    }
}
