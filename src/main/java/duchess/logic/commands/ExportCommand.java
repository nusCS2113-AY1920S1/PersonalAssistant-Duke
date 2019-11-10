package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.calendar.CalendarEntry;
import duchess.model.calendar.CalendarUtil;
import duchess.parser.Util;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Command to export calendar in either the week or day view.
 */
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
        String context = CalendarUtil.toString(start);
        List<String> display;
        if (isWeek) {
            List<CalendarEntry> query = currCalendar
                    .stream()
                    .filter(ce -> ce.getDate().compareTo(start) >= 0 && ce.getDate().compareTo(end) <= 0)
                    .collect(Collectors.toList());
            display = ui.stringCalendar(query, context);
        } else {
            CalendarEntry ce = currCalendar
                    .stream()
                    .filter(e -> e.getDate().equals(start))
                    .findFirst()
                    .orElse(null);
            if (ce == null) {
                throw new DuchessException("You have no events scheduled on " + start.toString());
            }
            display = ui.stringCalendar(ce, context);
        }
        for (String s : display) {
            file.println(s);
        }
        file.flush();
        file.close();
        ui.showFinishedExport(filepath);
    }
}
