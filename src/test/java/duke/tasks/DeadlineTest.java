package duke.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {

    @Test
    public void testToString() {
        assertEquals("[D][x] Read book (by: whenever)", new Deadline("Read book",
                "whenever").toString());
        assertEquals("[D][x] Celebrate My Birthday (by: 12/12/2019 11:59 PM)", new Deadline("Celebrate My Birthday",
                "12/12/2019 2359").toString());
        assertEquals("[D][x] Celebrate My Birthday (by: 12/12/2019 12:20 PM)", new Deadline("Celebrate My Birthday",
                "12/12/2019 12:20 PM").toString());
        assertEquals("[D][x] Read book (by: 12/09/2019 11:59 PM)", new Deadline("Read book",
                "12/09/2019").toString());
    }

}