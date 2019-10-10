package compal.logic.commands;

import compal.commons.Compal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SetReminderCommandTest {
    private SetReminderCommand setReminderCommand;
    private Compal compal;

    @BeforeEach
    public void setUp() {
        compal = new Compal();
        setReminderCommand = new SetReminderCommand(compal);
    }

    @Test
    public void iniTest() {
        assertEquals(compal, setReminderCommand.compal);
        assertEquals(compal.tasklist,setReminderCommand.compal.tasklist);
    }
}
