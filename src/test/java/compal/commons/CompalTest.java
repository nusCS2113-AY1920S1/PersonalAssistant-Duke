package compal.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompalTest {
    Compal.DukeException compal;

    @Test
    public void dukeExceptionTest() {
        String description = "Test";
        compal = new Compal.DukeException(description);
        assertEquals(description, compal.toString());
    }
}
