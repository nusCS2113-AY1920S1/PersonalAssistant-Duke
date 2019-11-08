package dolla.command.view;

import java.time.LocalDate;

public class ViewTodayCommand extends ViewCommand {

    public ViewTodayCommand(LocalDate date) {
        cmpDate = date;
        dateStr = "today";
    }
}
