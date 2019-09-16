package duke.task;

import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoAfterTest {

    @Test
    void doAfterTodoTest() throws ParseException {
        Task todoTask = new Todo("Todo Task");
        Task doAftertask = new DoAfter("After Todo", "Todo Task");
        assertEquals("[A][X] After Todo (Do after: Todo Task)", doAftertask.toString());
    }

    @Test
    void doAfterEventTest() throws ParseException {
        Task eventTask = new Event("Event Task", "10/11/2012 2345");
        Task doAftertask = new DoAfter("After Event", "Event Task");
        assertEquals("[A][X] After Event (Do after: Event Task)", doAftertask.toString());
    }

    @Test
    void doAfterDeadlineTest() throws ParseException {
        Task deadlineTask = new Event("Deadline Task", "10/11/2012 2345");
        Task doAftertask = new DoAfter("After Deadline", "Deadline Task");
        assertEquals("[A][X] After Deadline (Do after: Deadline Task)", doAftertask.toString());
    }
}
