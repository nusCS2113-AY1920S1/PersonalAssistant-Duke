package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.log.Log;
import duchess.model.calendar.CalendarEntry;
import duchess.model.calendar.CalendarManager;
import duchess.model.calendar.CalendarUtil;
import duchess.parser.Util;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command to export calendar in either the week or day view.
 */
public class ExportCommand extends Command {
    private LocalDate start;
    private LocalDate end;
    private PrintStream file;
    private String filepath;
    private boolean isWeek;
    private final Logger logger = Log.getLogger();

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
            List<CalendarEntry> query = CalendarManager.findEntries(currCalendar, start, end);
            display = ui.stringCalendar(query, context);
            logger.log(Level.INFO, "Export week in calendar from " + start.toString() + " to " + end.toString());
        } else {
            CalendarEntry ce = CalendarManager.findEntry(currCalendar, start);
            display = ui.stringCalendar(ce, context);
            logger.log(Level.INFO, "Export day in calendar on " + start.toString());
        }
        for (String s : display) {
            file.println(s);
        }
        file.flush();
        file.close();
        ui.showFinishedExport(filepath);
    }
}
