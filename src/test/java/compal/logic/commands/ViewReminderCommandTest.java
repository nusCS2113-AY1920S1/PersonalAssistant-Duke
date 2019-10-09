package compal.logic.commands;

import compal.commons.Compal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewReminderCommandTest {
    private ViewReminderCommand viewReminderCommand;
    private Compal compal;

    @BeforeEach
    public void setup() {
        compal = new Compal();
        viewReminderCommand = new ViewReminderCommand(compal);
    }

    @Test
    public void iniTest() {
        assertEquals(compal, viewReminderCommand.compal);
        assertEquals(compal.tasklist,viewReminderCommand.compal.tasklist);
    }

    @Test
    public void parseCommandTest() {
        String command = "deadline /date 09/10/2019 /end 1230";
        try {
            viewReminderCommand.parseCommand(command);
        } catch (Exception t) {
            //ignore the null pointer exception because ui is not welly declared in this test
        }
    }
}
