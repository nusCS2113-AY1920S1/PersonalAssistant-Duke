package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.calendar.CalendarEntry;
import duchess.model.calendar.CalendarUtil;
import duchess.parser.Util;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Command to display calendar in either the week or day view.
 */
public class DisplayCalendarCommand extends Command {
    private LocalDate start;
    private LocalDate end;
    private boolean isWeek;

    /**
     * Initialises start and end (where applicable) dates of calendar week view.
     *
     * @param input user input date
     * @param isWeek true for week view, false for day view
     */
    public DisplayCalendarCommand(LocalDate input, boolean isWeek) {
        if (isWeek) {
            List<LocalDate> dateRange = Util.parseToWeekDates(input);
            this.start = dateRange.get(0);
            this.end = dateRange.get(1);
        } else {
            this.start = input;
            this.end = input;
        }
        this.isWeek = isWeek;
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        List<CalendarEntry> currCalendar = store.getDuchessCalendar();
        String context = CalendarUtil.toString(start);
        if (isWeek) {
            List<CalendarEntry> query = currCalendar
                    .stream()
                    .filter(ce -> ce.getDate().compareTo(start) >= 0 && ce.getDate().compareTo(end) <= 0)
                    .collect(Collectors.toList());
            ui.displayCalendar(query, context);
        } else {
            CalendarEntry ce = currCalendar
                    .stream()
                    .filter(e -> e.getDate().equals(start))
                    .findFirst()
                    .orElse(null);
            if (ce == null) {
                throw new DuchessException("You have no events scheduled on " + start.toString());
            }
            ui.displayCalendar(ce, context);
        }
    }
}
