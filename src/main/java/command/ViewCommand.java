package command;
import process.*;
import process.DukeException;
import task.TaskList;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Represents a command that finds items from tasks
 */
public class ViewCommand extends Command {
    private String keyword;
    protected Date by;
    DateFormat fmt = new SimpleDateFormat("dd MMMM yyyy, h:mm a", Locale.US);

    /**
     * Creates a new ViewCommand object with the given keyword
     *
     * @param keyword used to find task
     */
    public ViewCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes the ViewCommand
     */
    public String execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        try {

            keyword += " 0000";
            DatetimeFormatter.check(keyword);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
            keyword = keyword.trim();
            Date date = sdf.parse(keyword);
            this.by = date;
        } catch (ParseException e) {
            throw new DukeException("datetime");
        }
        String[] str1 = DatetimeFormatter.view(this.fmt.format(by)).split(",",2);

        return ui.print_this(tasks.find(str1[0]));
    }
}

