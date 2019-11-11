
import storage.task.ToDo;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ToDoTest {

    @Test
    void initialize() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        ToDo test = new ToDo("TodoSomething");
        assertEquals("Something", test.getTaskName(), "taskName interpretation error");

        test = new ToDo("TODO SomethingElse / yolo");
        assertEquals("SomethingElse", test.getTaskName(), "taskName interpretation error");
        assertEquals("yolo", test.getTaskDetails(), "taskDetails interpretation error");

        test = new ToDo("todo fire in my pants /by   yolo  ");
        assertEquals("fire in my pants", test.getTaskName(), "taskName interpretation error");
        assertEquals("yolo", test.getTaskDetails(), "taskDetails interpretation error");
        assertEquals("by", test.getDetailDesc(), "detailDesc interpretation error");

        test = new ToDo("todosurvive /between 19/09/97 19/09/19");
        assertEquals("survive", test.getTaskName(), "taskName interpretation error");
        assertEquals("19/09/97 19/09/19", test.getTaskDetails(), "taskDetails interpretation error");
        assertEquals("between", test.getDetailDesc(), "detailDesc interpretation error");
        assertEquals("19/09/97", test.getDateFrom().format(formatter), "dateFrom interpretation error");
        assertEquals("19/09/19",  test.getDateTo().format(formatter), "dateTo interpretation error");

        test = new ToDo("todo SU /for weekly");
        assertEquals("SU", test.getTaskName(), "taskName interpretation error");
        assertEquals("for", test.getDetailDesc(), "detailDesc interpretation error");
        assertEquals("weekly", test.getTaskDetails(), "taskDetails interpretation error");
    }
}
