package duchess.logic.commands;

import duchess.logic.commands.exceptions.DukeException;
import duchess.model.TimeFrame;
import duchess.model.task.Task;
import duchess.model.task.TaskList;
import duchess.storage.Storage;
import duchess.ui.Ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ViewScheduleCommand extends Command {
    private List<String> words;
    private TimeFrame timeFrame;

    /**
     * Constructor for class.
     *
     * @param words User input with start and end date time
     * @throws DukeException Exception thrown for invalid or missing date time
     */
    public ViewScheduleCommand(List<String> words) throws DukeException {
        this.words = words;

        Date start = processDate(" 0000");
        Date end = processDate(" 2359");
        this.timeFrame = new TimeFrame(start, end);
    }

    /**
     * Creates Date object from string input in dd/MM/yyyy HHmm.
     *
     * @return Date
     * @throws DukeException Exception thrown for invalid or missing date time
     */
    private Date processDate(String time) throws DukeException {
        try {
            int index = words.indexOf("/for");
            if (index == -1) {
                throw new DukeException("Format for viewing schedule: schedule /for <date>");
            }
            String dateString = words.get(index + 1) + time;
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
        List<Task> tasksForToday =
                taskList.getTasks().stream()
                        .filter(task -> task.getTimeFrame().fallsWithin(this.timeFrame))
                        .collect(Collectors.toList());

        if (tasksForToday.size() <= 0) {
            throw new DukeException("There are no tasks in the schedule.");
        }

        Collections.sort(tasksForToday);
        ui.showScheduleResult(tasksForToday, words.get(words.indexOf("/for") + 1));
    }
}