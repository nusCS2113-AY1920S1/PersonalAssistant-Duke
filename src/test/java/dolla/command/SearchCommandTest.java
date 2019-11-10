package dolla.command;

import dolla.ModeStringList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author tatayu
public class SearchCommandTest {

    @Test
    public void searchCommandTest1() {
        Command commandTest = new SearchCommand(ModeStringList.MODE_DEBT,
                "name", "tata");
        String expected = "name tata";
        assertEquals(expected, commandTest.getCommandInfo());
    }

    @Test
    public void searchCommandTest2() {
        Command commandTest = new SearchCommand(ModeStringList.MODE_LIMIT,
                "duration", "monthly");
        String expected = "duration monthly";
        assertEquals(expected, commandTest.getCommandInfo());
    }

    @Test
    public void searchCommandTest3() {
        Command commandTest = new SearchCommand(ModeStringList.MODE_ENTRY,
                "date", "12");
        String expected = "date 12";
        assertEquals(expected, commandTest.getCommandInfo());
    }

    @Test
    public void searchCommandTest4() {
        Command commandTest = new SearchCommand(ModeStringList.MODE_SHORTCUT,
                "description", "food");
        String expected = "description food";
        assertEquals(expected, commandTest.getCommandInfo());
    }
}
