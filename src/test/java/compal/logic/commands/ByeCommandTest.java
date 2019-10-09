package compal.logic.commands;

import compal.commons.Compal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class ByeCommandTest {
    private ByeCommand byeCommand;
    private Compal compal;

    @BeforeEach
    public void setup() {
        compal = new Compal();
        byeCommand = new ByeCommand(compal);
    }

    @Test
    public void iniTest() {
        assertEquals(compal, byeCommand.compal);
    }
}
