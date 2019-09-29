package duchess.logic.commands;

import duchess.logic.commands.exceptions.DukeException;
import duchess.model.TimeFrame;
import duchess.model.task.Task;
import duchess.model.task.TaskList;
import duchess.storage.Storage;
import duchess.ui.Ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ViewScheduleCommand extends Command {
    private List<String> words;
    private List<TimeFrame> schedules;
    private Date start;
    private Date end;

    /**
     * Constructor for class.
     *
     * @param words User input with start and end date time
     * @throws DukeException Exception thrown for invalid or missing date time
     */
    public ViewScheduleCommand(List<String> words) throws DukeException {
        this.words = words;
        this.schedules = new ArrayList<>();
        this.start = returnDate(" 0000");
        this.end = returnDate(" 2359");
    }

    /**
     * Creates Date object from string input in dd/MM/yyyy HHmm.
     *
     * @return Date
     * @throws DukeException Exception thrown for invalid or missing date time
     */
    private Date returnDate(String time) throws DukeException {
        try {
            // todo fix bug which allows input 'schedule 12/12/2019' without /for
            String dateString = words.get(words.indexOf("/for") + 1) + time;
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
            formatter.setLenient(false);
            return formatter.parse(dateString);
        } catch (ParseException e) {
            throw new DukeException("Invalid datetime. Correct format: dd/mm/yyyy");
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("Format for viewing schedule: schedule /for <date>");
        }
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        for (Task t : taskList.getTasks()) {
            TimeFrame tempSchedule = t.getTimeFrame(start, end);
            if (tempSchedule != null) {
                (this.schedules).add(tempSchedule);
            }
        }
        if (schedules.size() <= 0) {
            throw new DukeException("There are no tasks in the schedule.");
        } else {
            Collections.sort(schedules);
            ui.showScheduleResult(schedules, words.get(words.indexOf("/for") + 1));
        }
    }
}
