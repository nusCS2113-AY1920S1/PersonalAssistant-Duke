package compal.logic.commands;

import compal.commons.Compal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClearCommandTest {
    private ClearCommand clearCommand;
    private Compal compal;

    @BeforeEach
    public void setup() {
        compal = new Compal();
        clearCommand = new ClearCommand(compal);
    }

    @Test
    public void iniTest() {
        assertEquals(compal, clearCommand.compal);
    }
}
