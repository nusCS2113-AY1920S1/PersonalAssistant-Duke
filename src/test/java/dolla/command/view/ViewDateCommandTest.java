package dolla.command.view;

import dolla.Time;
import dolla.command.Command;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewDateCommandTest {

    @Test
    public void viewDateCommandInfo() {
        LocalDate inputDate = LocalDate.parse("2001-12-03");
        Command newViewTodayCommand = new ViewDateCommand(inputDate);
        String expected = Time.dateToString(inputDate) + " " + Time.dateToString(inputDate);
        assertEquals(expected, newViewTodayCommand.getCommandInfo());
    }
}
