package utils;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {
    @Test
    public void dateTest() throws DukeException {
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
        String testDate = "25/10/2019 1030";
        try {
            Date tempz = ft.parse(testDate);
            assertEquals(tempz, Parser.parseDate(testDate));
        } catch (ParseException e) {
            throw new DukeException("Invalid date format, the correct format is: dd/MM/yyyy");
        }
    }

}
