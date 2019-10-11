package compal.logic.commands;

import compal.commons.Compal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FixedDurationCommandTest {
    private FixedDurationCommand fixedDurationCommand;
    private Compal compal;

    @BeforeEach
    public void setUp() {
        compal = new Compal();
        fixedDurationCommand = new FixedDurationCommand(compal);
    }

    @Test
    public void iniTest() {
        assertEquals(compal, fixedDurationCommand.compal);
        assertEquals(compal.tasklist,fixedDurationCommand.compal.tasklist);
    }
}
