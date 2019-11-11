package parsertests;

import duke.models.ToDo;
import duke.parser.ParseScheduleTable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ParserScheduleTableTest {

    @Test
    void testCreateToDo() {
        String input = "add n/Swimming s/0900 d/1000 loc/NUS";
        String date = "2019-03-01";
        ParseScheduleTable parseScheduleTable = new ParseScheduleTable();
        ToDo toDo = parseScheduleTable.createToDo(input, date);
        assertEquals(date, toDo.getDate());
        assertEquals("Swimming ", toDo.getClassName());
        assertEquals("0900 ", toDo.getStartTime());
        assertEquals("1000 ", toDo.getEndTime());
        assertEquals("NUS", toDo.getLocation());
    }

    @Test
    void testCreateNull() {
        String input = "add n/trucated....";
        String date = "2019-09-12";
        ToDo toDo = new ParseScheduleTable().createToDo(input, date);
        assertNull(toDo);
    }


}
