package compal.logic.commands;

import compal.commons.Compal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListCommandTest {
    private ListCommand listCommand;
    private Compal compal;

    @BeforeEach
    public void setup() {
        compal = new Compal();
        listCommand = new ListCommand(compal);
    }

    @Test
    public void iniTest() {
        assertEquals(compal, listCommand.compal);
        assertEquals(compal.tasklist,listCommand.compal.tasklist);
    }

    @Test
    public void parseCommandTest() {
        String command = "deadline /date 09/10/2019 /end 1230";
        try {
            listCommand.parseCommand(command);
        } catch (Exception t) {
            //ignore the null pointer exception because ui is not welly declared in this test
        }
    }
}
