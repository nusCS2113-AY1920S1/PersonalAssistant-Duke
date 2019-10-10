package compal.commons;

import org.junit.jupiter.api.Test;

import compal.commons.Compal.DukeException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompalTest {
    private DukeException exception;

    @Test
    public void dukeExceptionTest() {
        String description = "Test";
        exception = new Compal.DukeException(description);
        assertEquals(description, exception.toString());
    }
}
