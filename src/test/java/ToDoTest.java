
import duke.task.ToDo;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ToDoTest {

    @Test
    void initialize() {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
        ToDo test = new ToDo("TodoSomething");
        assertEquals("Something", test.taskName, "taskName interpretation error");

        test = new ToDo("TODO SomethingElse / yolo");
        assertEquals("SomethingElse", test.taskName, "taskName interpretation error");
        assertEquals("yolo", test.taskDetails, "taskDetails interpretation error");

        test = new ToDo("todo fire in my pants /by   yolo  ");
        assertEquals("fire in my pants", test.taskName, "taskName interpretation error");
        assertEquals("yolo", test.taskDetails, "taskDetails interpretation error");
        assertEquals("by", test.detailDesc, "detailDesc interpretation error");

        test = new ToDo("todosurvive /between 19/09/1997 19/09/2019");
        assertEquals("survive", test.taskName, "taskName interpretation error");
        assertEquals("19/09/1997 19/09/2019", test.taskDetails, "taskDetails interpretation error");
        assertEquals("between", test.detailDesc, "detailDesc interpretation error");
        assertEquals("19/09/1997", dateFormat.format(test.getDateFrom()), "dateFrom interpretation error");
        assertEquals("19/09/2019",  dateFormat.format(test.getDateTo()), "dateTo interpretation error");
    }
}
