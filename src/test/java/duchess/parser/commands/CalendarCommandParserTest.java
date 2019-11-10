package duchess.parser.commands;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.DisplayCalendarCommand;
import duchess.parser.Util;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CalendarCommandParserTest {
    private List<String> invalidDates = List.of("10/08/2019", "11/08/2019", "09/12/2019", "12/01/2020", "11/05/2020");
    private List<String> validDates = List.of("12/08/2019", "08/12/2019", "13/01/2020", "10/05/2020");

    @Test
    public void parse_invalidViewType_throwsDuchessException() {
        Map<String, String> parameters = Util.parameterize("calendar display /view rubbish /date 11/10/2019");
        assertThrows(DuchessException.class, () -> CalendarCommandParser.parse(parameters));
    }

    @Test
    public void parse_invalidCalendarInteraction_throwsDuchessException() {
        Map<String, String> parameters = Util.parameterize("calendar rubbish /date 11/10/2019 /view day");
        assertThrows(DuchessException.class, () -> CalendarCommandParser.parse(parameters));
    }

    @Test
    public void parse_givenDatesOutsideAcademicYear_throwsDuchessException() {
        for (String date : invalidDates) {
            Map<String, String> parameters = Util.parameterize("calendar display /view day /date " + date);
            assertThrows(DuchessException.class, () -> CalendarCommandParser.parse(parameters));
        }
    }

    @Test
    public void parse_givenDatesWithinAcademicYear_successfulReturnDisplayCalendarCommand() throws DuchessException {
        for (String date : validDates) {
            Map<String, String> parameters = Util.parameterize("calendar display /view day /date " + date);
            assertTrue(CalendarCommandParser.parse(parameters) instanceof DisplayCalendarCommand);
        }
    }
}
