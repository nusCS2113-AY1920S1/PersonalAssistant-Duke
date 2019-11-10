//@@author Ryan-Wong-Ren-Wei

import mistermusik.commons.events.formatting.DateStringValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DateValidatorTest {
    @Test
    public void dateValidatorTestEvent() {
        String correctString1 = "14-12-2019 1500";
        String correctString2 = "12-05-4938 1800";
        String correctString3 = "5-5-2000 0800";
        String wrongString1 = "5-5-5-5-3513";
        String wrongString2 = "5-5-3 3301";
        String wrongString3 = "21-12-1900 6000 7000";
        String wrongString4 = "alkjawfwe";
        String wrongString5 = "21-12-2019 15awawer";

        assertTrue(DateStringValidator.isValidDateForEvent(correctString1));
        assertTrue(DateStringValidator.isValidDateForEvent(correctString2));
        assertTrue(DateStringValidator.isValidDateForEvent(correctString3));

        assertFalse(DateStringValidator.isValidDateForEvent(wrongString1));
        assertFalse(DateStringValidator.isValidDateForEvent(wrongString2));
        assertFalse(DateStringValidator.isValidDateForEvent(wrongString3));
        assertFalse(DateStringValidator.isValidDateForEvent(wrongString4));
        assertFalse(DateStringValidator.isValidDateForEvent(wrongString5));
    }

    @Test
    public void dateValidatorTestToDo() {
        String correctString1 = "14-12-2019";
        String correctString2 = "12-05-4938";
        String correctString3 = "5-5-2000";

        String wrongString1 = "5-5--3931-5-3513";
        String wrongString2 = "5-5dsafs-3 3301";
        String wrongString3 = "21-12 6000 7000";
        String wrongString4 = "alkjawfwe";
        String wrongString5 = "50-50-50";

        assertTrue(DateStringValidator.isValidDateForToDo(correctString1));
        assertTrue(DateStringValidator.isValidDateForToDo(correctString2));
        assertTrue(DateStringValidator.isValidDateForToDo(correctString3));

        assertFalse(DateStringValidator.isValidDateForToDo(wrongString1));
        assertFalse(DateStringValidator.isValidDateForToDo(wrongString2));
        assertFalse(DateStringValidator.isValidDateForToDo(wrongString3));
        assertFalse(DateStringValidator.isValidDateForToDo(wrongString4));
        assertFalse(DateStringValidator.isValidDateForToDo(wrongString5));
    }
}
