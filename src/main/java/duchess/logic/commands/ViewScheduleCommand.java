package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.AcademicContext;
import duchess.model.TimeFrame;
import duchess.model.task.Task;
import duchess.parser.Util;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ViewScheduleCommand extends Command {
    private String date;
    private String view;
    private TimeFrame timeFrame;
    private AcademicContext academicContext;

    /**
     * Constructor for class.
     *
     * @param date Date
     * @param view Either week or day
     * @throws DuchessException Exception thrown for invalid or missing date time
     */
    public ViewScheduleCommand(String date, String view) throws DuchessException {
        this.date = date;
        this.view = view;
        LocalDateTime start = processDate(" 0000");
        LocalDateTime end = processDate(" 2359");
        this.academicContext = new AcademicContext(start);
        this.timeFrame = new TimeFrame(start, end);
    }

    /**
     * Process date by setting time of LocalDateTime to either 0000 or 2359.
     * Also sets LocalDateTime to nearest previous or same Monday date/ nearest
     * next or same Friday date if user desires week view.
     *
     * @param time either " 0000" or " 2359" to indicate timeframe
     * @return LocalDateTime
     * @throws DuchessException Thrown for invalid or missing date time and command format
     */
    private LocalDateTime processDate(String time) throws DuchessException {
        try {
            boolean isWeek = view.equals("week");
            boolean isStartOfDay = time.equals(" 0000");
            boolean isStartOfWeek = isWeek && isStartOfDay;
            boolean isEndOfWeek = isWeek && !isStartOfDay;

            LocalDateTime localDateTime = Util.parseDateTime(date + time);
            if (isStartOfWeek) {
                localDateTime = localDateTime.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            } else if (isEndOfWeek) {
                localDateTime = localDateTime.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
            }
            return localDateTime;
        } catch (DateTimeParseException e) {
            throw new DuchessException("Invalid date format. Please follow dd/MM/yyyy.");
        } catch (IndexOutOfBoundsException e) {
            throw new DuchessException("Format for viewing schedule: schedule /for <date> view <view>");
        }
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        List<Task> tasksForToday =
                store.getTaskList().stream()
                        .filter(task -> task.getTimeFrame().fallsWithin(this.timeFrame))
                        .collect(Collectors.toList());
        if (tasksForToday.size() <= 0) {
            throw new DuchessException("There are no tasks in the schedule.");
        } else if (view.equals("week")) {
            date = "the week of " + date;
        }
        Collections.sort(tasksForToday);
        ui.showScheduleResult(tasksForToday, date, academicContext.getAcademicContext()
        );

    }
}