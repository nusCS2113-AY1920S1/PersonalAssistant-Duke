package compal.logic.commands;

import compal.commons.Compal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewReminderCommandTest {
    private ViewReminderCommand viewReminderCommand;
    private Compal compal;

    @BeforeEach
    public void setUp() {
        compal = new Compal();
        viewReminderCommand = new ViewReminderCommand(compal);
    }

    @Test
    public void iniTest() {
        assertEquals(compal, viewReminderCommand.compal);
        assertEquals(compal.tasklist,viewReminderCommand.compal.tasklist);
    }
}
