package compal.logic.commands;

import compal.commons.Compal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewCommandTest {
    private ViewCommand viewCommand;
    private Compal compal;

    @BeforeEach
    public void setUp() {
        compal = new Compal();
        viewCommand = new ViewCommand(compal);
    }

    @Test
    public void iniTest() {
        assertEquals(compal, viewCommand.compal);
        assertEquals(compal.tasklist,viewCommand.compal.tasklist);
    }
}
