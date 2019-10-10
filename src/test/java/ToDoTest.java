
import executor.task.ToDo;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ToDoTest {

    @Test
    void initialize() {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
        ToDo test = new ToDo("TodoSomething");
        assertEquals("Something", test.getTaskName(), "taskName interpretation error");

        test = new ToDo("TODO SomethingElse / yolo");
        assertEquals("SomethingElse", test.getTaskName(), "taskName interpretation error");
        assertEquals("yolo", test.getTaskDetails(), "taskDetails interpretation error");

        test = new ToDo("todo fire in my pants /by   yolo  ");
        assertEquals("fire in my pants", test.getTaskName(), "taskName interpretation error");
        assertEquals("yolo", test.getTaskDetails(), "taskDetails interpretation error");
        assertEquals("by", test.getDetailDesc(), "detailDesc interpretation error");

        test = new ToDo("todosurvive /between 19/09/1997 19/09/2019");
        assertEquals("survive", test.getTaskName(), "taskName interpretation error");
        assertEquals("19/09/1997 19/09/2019", test.getTaskDetails(), "taskDetails interpretation error");
        assertEquals("between", test.getDetailDesc(), "detailDesc interpretation error");
        assertEquals("19/09/1997", dateFormat.format(test.getDateFrom()), "dateFrom interpretation error");
        assertEquals("19/09/2019",  dateFormat.format(test.getDateTo()), "dateTo interpretation error");
    }
}
