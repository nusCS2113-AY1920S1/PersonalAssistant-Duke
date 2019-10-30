package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.calendar.CalendarManager;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.SortedMap;

public class ExportCommand extends Command {
    private LocalDate start;
    private LocalDate end;
    private PrintStream file;
    private String filepath;

    /**
     * Initialises start and end dates, file and filepath of export.
     *
     * @param start start date
     * @param end end date
     * @param file file to save the text file in
     * @param filepath filepath name
     */
    public ExportCommand(LocalDate start, LocalDate end, PrintStream file, String filepath) {
        this.start = start;
        this.end = end;
        this.file = file;
        this.filepath = filepath;
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        file.println(ui.plainSeparator);
        file.println(ui.processHeader(CalendarManager.getDateInformation(start)));
        file.println(ui.blockSeparator);
        file.println(ui.calendarHeader);
        file.println(ui.blockSeparator);
        SortedMap<LocalTime, String[]> query = CalendarManager.flatCalendar(store.getDuchessCalendar(), start, end);
        for (Map.Entry<LocalTime, String[]> entry : query.entrySet()) {
            String time = entry.getKey().toString().replaceAll(":", "");
            String[] strArr = entry.getValue();
            file.println(ui.processRow(time, strArr));
            file.println(ui.blockSeparator);
        }
        file.flush();
        file.close();
        ui.showFinishedExport(filepath);
    }
}
