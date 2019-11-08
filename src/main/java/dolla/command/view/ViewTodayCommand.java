package dolla.command.view;

import java.time.LocalDate;

public class ViewTodayCommand extends ViewCommand {

    public ViewTodayCommand() {
        cmpDate = LocalDate.now();
        dateStr = "today";
    }
}
