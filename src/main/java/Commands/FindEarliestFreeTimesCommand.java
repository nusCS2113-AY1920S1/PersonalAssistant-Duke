package Commands;

import Interface.Storage;
import Interface.Ui;
import Tasks.TaskList;
import javafx.util.Pair;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents the command to find earliest free time from TaskList object.
 */
@SuppressWarnings("CanBeFinal")
public class FindEarliestFreeTimesCommand extends Command {
    private final String duration;
    private final String type;

    /**
     * Creates a FindEarliestFreeTimesCommand object.
     * @param duration The duration that the user wants to use to find in the TaskList object
     */
    public FindEarliestFreeTimesCommand(String duration, String type) {
        this.duration = duration;
        this.type = type;
    }

    /**
     * This method checks if two given datetime have the same date.
     * @param firstDate The first date given
     * @param secondDate The second date given
     * @return This returns true if the dates are the same
     */
    private boolean checkIfSameDate(Date firstDate, Date secondDate) {
        boolean isTrue = true;
        long diff = secondDate.getTime() - firstDate.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        if (diffDays == 0) isTrue = true;
        else isTrue = false;
        return isTrue;
    }

    /**
     * Executes the finding of earliest available block period inside the given TaskList objects with the given duration.
     * @param todos The todo TaskList object used to find free time with the given duration
     * @param events The event TaskList object used to find free time with the given duration
     * @param deadlines The deadline TaskList object used to find free time with the given duration
     * @param ui The Ui object to display the earliest free time message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display earliest free time
     * @throws Exception Throws ParseException is findEarliestFreeTime contains error
     */
    @Override
    public String execute(TaskList todos, TaskList events, TaskList deadlines, Ui ui, Storage storage) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm aa");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
        Pair<Date, Date> freeTime = events.findEarliestFreeTime(duration, type);
        String message;
        boolean isSameDate = checkIfSameDate(freeTime.getKey(), freeTime.getValue());
        if (isSameDate) message = dateFormat.format(freeTime.getKey()) + " until " + timeFormat.format(freeTime.getValue());
        else message = dateFormat.format(freeTime.getKey()) + " until " + dateFormat.format(freeTime.getValue());
        return ui.showFreeTimes(message);
    }
}
