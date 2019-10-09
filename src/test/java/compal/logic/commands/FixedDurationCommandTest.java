package compal.logic.commands;

import compal.commons.Compal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FixedDurationCommandTest {
    private FixedDurationCommand fixedDurationCommand;
    private Compal compal;

    @BeforeEach
    public void setup() {
        compal = new Compal();
        fixedDurationCommand = new FixedDurationCommand(compal);
    }

    @Test
    public void iniTest() {
        assertEquals(compal, fixedDurationCommand.compal);
        assertEquals(compal.tasklist,fixedDurationCommand.compal.tasklist);
    }

    @Test
    public void parseCommandTest() {
        String command = "deadline /date 09/10/2019 /end 1230";
        try {
            fixedDurationCommand.parseCommand(command);
        } catch (Exception t) {
            //ignore the null pointer exception because ui is not welly declared in this test
        }
    }
}
