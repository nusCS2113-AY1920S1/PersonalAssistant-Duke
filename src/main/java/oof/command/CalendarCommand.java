package oof.command;

import java.time.YearMonth;
import java.util.Calendar;

import oof.Storage;
import oof.TaskList;
import oof.Ui;

public class CalendarCommand extends Command {

    private int month;
    private int year;
    private static final int INDEX_MONTH = 1;
    private static final int INDEX_YEAR = 2;
    private static final int MONTH_JANUARY = 1;
    private static final int MONTH_DECEMBER = 12;

    /**
     * Constructor for CalendarCommand.
     *
     * @param argumentArray Array of command arguments input by user.
     */
    public CalendarCommand(String[] argumentArray) {
        Calendar calendar = Calendar.getInstance();
        try {
            this.month = Integer.parseInt(argumentArray[INDEX_MONTH]);
            this.year = Integer.parseInt(argumentArray[INDEX_YEAR]);
            if (month < MONTH_JANUARY || month > MONTH_DECEMBER) {
                // 0-based indexing to 1-based indexing
                this.month = calendar.get(Calendar.MONTH) + 1;
                this.year = calendar.get(Calendar.YEAR);
            }
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            // 0-based indexing to 1-based indexing
            this.month = calendar.get(Calendar.MONTH) + 1;
            this.year = calendar.get(Calendar.YEAR);
        }
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        YearMonth yearMonth = YearMonth.of(year, month);
        ui.printCalendar(yearMonth);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
