package dolla.command.view;

import dolla.Time;
import dolla.command.Command;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewTodayCommandTest {

    @Test
    public void viewTodayCommandInfo() {
        Command newViewTodayCommand = new ViewTodayCommand();
        String expected = Time.dateToString(LocalDate.now()) + " " + "today";
        assertEquals(expected, newViewTodayCommand.getCommandInfo());
    }
}
