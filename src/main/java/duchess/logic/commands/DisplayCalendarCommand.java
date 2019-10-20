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
     * Constructor.
     *
     * @param dates start and end dates of calendar view
     */
    public DisplayCalendarCommand(List<LocalDate> dates) {
        this.start = dates.get(0);
        this.end = dates.get(1);
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        List<CalendarEntry> currCalendar = store.getDuchessCalendar();
        SortedMap<LocalTime, String[]> flatCalendar = CalendarManager.flatCalendar(currCalendar, start, end);
        String context = CalendarManager.getDateInformation(start);
        ui.displayCalendar(flatCalendar, context);
    }
}
