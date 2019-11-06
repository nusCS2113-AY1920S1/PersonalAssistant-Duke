package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.calendar.CalendarEntry;
import duchess.model.calendar.CalendarManager;
import duchess.model.task.Task;
import duchess.parser.Util;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public class ExportCommand extends Command {
    private LocalDate start;
    private LocalDate end;
    private PrintStream file;
    private String filepath;
    private boolean isWeek;

    /**
     * Initialises start and end dates, file and filepath of export.
     *
     * @param input    date
     * @param file     file to save the text file in
     * @param filepath filepath name
     * @param isWeek   true for week, false for day
     */
    public ExportCommand(LocalDate input, PrintStream file, String filepath, boolean isWeek) {
        this.file = file;
        this.filepath = filepath;
        this.isWeek = isWeek;
        if (isWeek) {
            List<LocalDate> dateRange = Util.parseToWeekDates(input);
            this.start = dateRange.get(0);
            this.end = dateRange.get(1);
        } else {
            this.start = input;
            this.end = input;
        }
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        List<CalendarEntry> currCalendar = store.getDuchessCalendar();
        String context = CalendarManager.getDateInformation(start);
        if (isWeek) {
            file.println(ui.plainSeparator);
            file.println(ui.processCentred(context, ui.horizontalLength));
            file.println(ui.blockSeparator);
            file.println(ui.calendarHeader);
            file.println(ui.blockSeparator);
            SortedMap<LocalTime, String[]> query = CalendarManager.flatCalendar(currCalendar, start, end);
            for (Map.Entry<LocalTime, String[]> entry : query.entrySet()) {
                String time = entry.getKey().toString().replaceAll(":", "");
                String[] strArr = entry.getValue();
                file.println(ui.processRow(time, strArr));
                file.println(ui.blockSeparator);
            }
        } else {
            CalendarEntry ce = currCalendar
                    .stream()
                    .filter(e -> e.getDate().equals(start))
                    .findFirst()
                    .orElse(null);
            if (ce == null) {
                throw new DuchessException("You have no existing calendar entry on " + start.toString());
            }
            file.println(ui.plainShort);
            file.println(ui.processCentred(context, ui.longLength));
            file.println(ui.plainShort);
            String dayOfWeek = ce.getDate().getDayOfWeek().toString();
            file.println("|  TIME  " + ui.processCentred(dayOfWeek, ui.horizontalBlock));
            file.println(ui.blockShort);
            for (Task t : ce.getDateTasks()) {
                file.println(ui.processRow(t));
                file.println(ui.blockShort);
            }
        }
        file.flush();
        file.close();
        ui.showFinishedExport(filepath);
    }
}
