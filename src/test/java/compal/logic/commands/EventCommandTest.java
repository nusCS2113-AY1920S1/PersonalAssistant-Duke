package compal.logic.commands;

import compal.commons.Compal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventCommandTest {
    private EventCommand eventCommand;
    private Compal compal;

    @BeforeEach
    public void setUp() {
        compal = new Compal();
        eventCommand = new EventCommand(compal);
    }

    @Test
    public void iniTest() {
        assertEquals(compal, eventCommand.compal);
        assertEquals(compal.tasklist, eventCommand.compal.tasklist);
    }
}
