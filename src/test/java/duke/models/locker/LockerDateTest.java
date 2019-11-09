package duke.models.locker;

import duke.exceptions.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LockerDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LockerDate(null));
    }

    @Test
    public void constructor_invalidFormat_throwsDukeException() {
        assertThrows(DukeException.class, () -> new LockerDate(""));
    }

    @Test
    public void checkIsValidDate() {
        //null and empty cases
        assertThrows(NullPointerException.class, () -> LockerDate.checkIsValidDate(null));
        assertFalse(LockerDate.checkIsValidDate(""));
        assertFalse(LockerDate.checkIsValidDate(" "));

        //invalid date
        assertFalse(LockerDate.checkIsValidDate("2 Dec 1999")); //invalid format
        assertFalse(LockerDate.checkIsValidDate("gibberish,-")); //no way related to date
        assertFalse(LockerDate.checkIsValidDate(" 02-10-2019")); //leading spaces
        assertFalse(LockerDate.checkIsValidDate("03-10-2109 ")); //trailing spaces
        assertFalse(LockerDate.checkIsValidDate("02/12/2010")); //invalid format
        assertFalse(LockerDate.checkIsValidDate("02-10-2010 1800")); //should only have date and no time
        assertFalse(LockerDate.checkIsValidDate("31-02-2019")); //31st feb does not exist
        assertFalse(LockerDate.checkIsValidDate("30-02-2019")); //30th feb does not exist
        assertFalse(LockerDate.checkIsValidDate("29-02-2019")); //not a leap year
        assertFalse(LockerDate.checkIsValidDate("31-04-2019")); //april has only 30 days
        assertFalse(LockerDate.checkIsValidDate("30-4-2019")); //invalid format- month should contain 04
        assertFalse(LockerDate.checkIsValidDate("1-04-2019")); //must be 01
        assertFalse(LockerDate.checkIsValidDate("2019-04-01")); //invalid format
        assertFalse(LockerDate.checkIsValidDate("01-04-19")); //invalid format

        //valid dates
        assertTrue(LockerDate.checkIsValidDate("11-11-2019"));
        assertTrue(LockerDate.checkIsValidDate("28-02-2019"));
        assertTrue(LockerDate.checkIsValidDate("29-02-2020"));
    }

    @Test
    public void isDifferenceBetweenDatesValid() {

        //invalid differences
        assertFalse(LockerDate
                .isDifferenceBetweenDatesValid("22-10-2019", "21-10-2019")); //start date cannot be before end date
        assertFalse(LockerDate
                .isDifferenceBetweenDatesValid("22-10-2019", "28-10-2019")); //difference cannot be less than 7 days

        assertFalse(LockerDate
                .isDifferenceBetweenDatesValid("22-10-2019", "23-10-2020")); //difference cannot be more than 365 days

        assertFalse(LockerDate.isDifferenceBetweenDatesValid("30-10-2019", "30-10-2020"));

        //valid differences
        assertTrue(LockerDate.isDifferenceBetweenDatesValid("22-10-2019", "29-10-2019"));
        assertTrue(LockerDate.isDifferenceBetweenDatesValid("30-10-2019", "08-11-2019"));
        assertTrue(LockerDate.isDifferenceBetweenDatesValid("22-10-2018", "22-10-2019"));
    }

    @Test
    public void isEndDateBeforeCurrentDate() {

        assertTrue(LockerDate.isEndDateBeforeCurrentDate("22-10-2019", "23-10-2019"));
        assertTrue(LockerDate.isEndDateBeforeCurrentDate("10-10-2019", "11-11-2019"));
        assertFalse(LockerDate.isEndDateBeforeCurrentDate("22-10-2019","22-10-2019"));
        assertFalse(LockerDate.isEndDateBeforeCurrentDate("22-10-2019","10-10-2019"));
    }

}
