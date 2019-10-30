package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.calendar.CalendarEntry;
import duchess.model.calendar.CalendarManager;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.SortedMap;

public class DisplayCalendarCommand extends Command {
    private LocalDate start;
    private LocalDate end;

    /**
     * Initialises start and end dates of calendar view.
     *
     * @param start start date
     * @param end end date
     */
    public DisplayCalendarCommand(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        List<CalendarEntry> currCalendar = store.getDuchessCalendar();
        SortedMap<LocalTime, String[]> flatCalendar = CalendarManager.flatCalendar(currCalendar, start, end);
        String context = CalendarManager.getDateInformation(start);
        ui.displayCalendar(flatCalendar, context);
    }
}
