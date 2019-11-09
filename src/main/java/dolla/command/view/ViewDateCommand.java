package dolla.command.view;

import dolla.Time;

import java.time.LocalDate;

public class ViewDateCommand extends ViewCommand {

    public ViewDateCommand(LocalDate date) {
        cmpDate = date;
        dateStr = Time.dateToString(cmpDate);
    }

}
