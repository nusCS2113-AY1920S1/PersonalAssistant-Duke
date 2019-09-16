package duke.command;

import duke.dukeexception.DukeException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.TaskList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ViewScheduleCommand extends Command {
    private List<String> words;
    private Date start;
    private Date end;
    private SimpleDateFormat formatter;

    public ViewScheduleCommand(List<String> words) throws DukeException {
        this.words = words;
        this.start = inputStringDate_returnDateDate("/from");
        this.end = inputStringDate_returnDateDate("to");
    }

    /**
     * Creates Date object from string input in dd/MM/yyyy HHmm.
     *
     * @param string User input.
     * @return Date
     * @throws DukeException Exception thrown for invalid or missing datetime
     */
    public Date inputStringDate_returnDateDate(String string) throws DukeException {
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

    private Boolean isWithinSchedule(Date d1, Date d2) {
        return d1.compareTo(start) >= 0 && d2.compareTo(end) <= 0;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        Ui.showScheduledTask(formatter.format(start), formatter.format(end));
        int counter = 1;
        for (Task task : taskList.getTasks()) {
            Boolean isWithinSchedule = false;
            if (task instanceof Deadline)
                isWithinSchedule = isWithinSchedule(((Deadline) task).deadline, ((Deadline) task).deadline);
            if (task instanceof Event) isWithinSchedule = isWithinSchedule(((Event) task).start, ((Event) task).end);
            if (isWithinSchedule) {
                Ui.printIndented(counter + ". " + task.toString());
                counter++;
            }
        }
        if (counter == 1) Ui.printIndented("\tThere are no tasks within the given time frame.");
    }
}
