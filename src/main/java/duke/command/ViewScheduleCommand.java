package duke.command;

import duke.dukeexception.DukeException;
import duke.task.Task;
import duke.task.TaskList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ViewScheduleCommand extends Command {
    private List<String> words;
    private Date start;
    private Date end;
    private SimpleDateFormat formatter;

    /**
     * Constructor for class.
     *
     * @param words User input with start and end date time
     * @throws DukeException Exception thrown for invalid or missing date time
     */
    public ViewScheduleCommand(List<String> words) throws DukeException {
        this.words = words;
        this.start = returnDate("/from");
        this.end = returnDate("to");
        if (end.before(start)) {
            throw new DukeException("Start datetime cannot be after end datetime.");
        }
    }

    /**
     * Creates Date object from string input in dd/MM/yyyy HHmm.
     *
     * @param string User input.
     * @return Date
     * @throws DukeException Exception thrown for invalid or missing date time
     */
    private Date returnDate(String string) throws DukeException {
        try {
            String dateString = words.get(words.indexOf(string) + 1)
                    + " "
                    + words.get(words.indexOf(string) + 2);
            formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
            formatter.setLenient(false);
            return formatter.parse(dateString);
        } catch (ParseException e) {
            throw new DukeException("Invalid datetime. Correct format: dd/mm/yyyy hhmm");
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("Format for viewing schedule: schedule /from <start datetime> to <end datetime>");
        }
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        List<Task> scheduleTasks =
                taskList.getTasks().stream()
                        .filter(task -> task.isWithinTimeFrame(start, end))
                        .collect(Collectors.toList());
        if (scheduleTasks.size() > 0) {
            ui.showSearchResult(scheduleTasks, formatter.format(start), formatter.format(end));
        } else {
            throw new DukeException("There are no matching tasks.");
        }
    }
}
