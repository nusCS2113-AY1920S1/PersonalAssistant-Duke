//@@author muserr

package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.calendar.CalendarEntry;
import duchess.model.calendar.CalendarManager;
import duchess.model.calendar.CalendarUtil;
import duchess.model.task.Event;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Adds recurring lessons.
 */
public class AddLessonCommand extends Command {
    private String description;
    private LocalDate startDate;
    private LocalDateTime end;
    private LocalDateTime start;
    private LocalDateTime endCopy;
    private LocalDateTime startCopy;
    private String moduleCode;
    private final int studyWeeks = 15;
    private final int recessWeek = 7;
    private final String invalidStartDate
            = "Invalid start date, start date provided must be within a semester.";
    private final String invalidModuleCode
            = "Unrecognized module code, add module first before assigning lessons.";

    /**
     * Constructor to add lessons.
     *
     * @param description lesson description
     * @param start start date and time
     * @param end end date and time
     * @param moduleCode module code of lesson
     */
    public AddLessonCommand(String description, LocalDateTime start, LocalDateTime end, String moduleCode) {
        this.description = description + " (" + moduleCode + ")";
        this.start = start;
        this.end = end;
        this.startCopy = start;
        this.endCopy = end;
        this.moduleCode = moduleCode;
        this.startDate = start.toLocalDate();
    }

    /**
     * Compares user inputs and adds recurring lessons for the semester.
     *
     * @param store store object
     * @param ui user interaction object
     * @param storage storage object
     * @throws DuchessException exception thrown if invalid module code or start date
     */
    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        if (store.findModuleByCode(moduleCode).isEmpty()) {
            throw new DuchessException(invalidModuleCode);
        } else if (CalendarUtil.processDate(startDate) == -1) {
            throw new DuchessException(invalidStartDate);
        } else {
            int currentWeek = CalendarUtil.getWeekAsInt(startDate);
            int prevTaskListSize = store.getTaskList().size();

            for (int i = currentWeek; i < studyWeeks; i++) {
                if (i != recessWeek) {
                    addLessons(store, storage);
                }
                startCopy = startCopy.plusWeeks(1);
                endCopy = endCopy.plusWeeks(1);
            }

            int currTaskListSize = store.getTaskList().size();

            if (currTaskListSize > prevTaskListSize) {
                ui.showLessonsAdded(moduleCode);
            } else {
                ui.showNoLessonsAdded(moduleCode);
            }
        }
    }

    /**
     * Adds lessons to task list.
     *
     * @param store store object
     * @param storage storage object
     * @throws DuchessException exception thrown if adding lessons is unsuccessful
     */
    private void addLessons(Store store, Storage storage) throws DuchessException {
        Event task = new Event(description, endCopy, startCopy);
        assert (task != null);

        if (!store.isClashing(task)) {
            store.getTaskList().add(task);
            List<CalendarEntry> ce = store.getDuchessCalendar();
            CalendarManager.addEntry(ce, task, startCopy.toLocalDate());
            store.setDuchessCalendar(ce);
            storage.save(store);
        }
    }
}
