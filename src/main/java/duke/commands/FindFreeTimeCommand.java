package duke.commands;

import duke.DateFormatter;
import duke.constant.DukeResponse;
import duke.Storage;
import duke.exception.DukeException;
import duke.task.Task;
import duke.task.TaskList;
import duke.Ui;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class FindFreeTimeCommand extends Command {
    private int value;
    private ChronoUnit units;

    public FindFreeTimeCommand(int value, String units) throws DukeException {
        this.value = value;
        switch (units) {
            case ("minutes") :
            case ("min"):
            case ("m"):
                this.units = ChronoUnit.MINUTES;
                break;
            case ("hours"):
            case ("h"):
                this.units = ChronoUnit.HOURS;
                break;
            default :
                throw new DukeException("invalid unit of time.");
        }
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) {
        String message;
        LocalDateTime startFreeTime = LocalDateTime.now(); // now might be in the wrong zone. check this
        LocalDateTime endFreeTime = startFreeTime.plus(value, units);
        ArrayList<Task> eventContainer = new ArrayList<>();
        // get all tasks
        for (Task i : tasks) {
            if (i.getSymbol().equals("[E]")) {
                eventContainer.add(i);
            }
        }
        // sort eventContainer by time
        for (int idx = 0; idx < eventContainer.size(); idx += 1) {
            for (int idx2 = 0; idx2 < eventContainer.size() - 1; idx2 += 1) {
                Task event1 = eventContainer.get(idx2);
                Task event2 = eventContainer.get(idx2 + 1);
                LocalDateTime time1 = event1.getLocalDate();
                LocalDateTime time2 = event2.getLocalDate();
                if (time1.isAfter(time2)) {
                    eventContainer.set(idx2 + 1, event1);
                    eventContainer.set(idx2, event2);
                }
            }
        }
        for (Task t: eventContainer) {
            LocalDateTime eventTime = t.getLocalDate();
            if (endFreeTime.isBefore(eventTime) ) {
                System.out.println("found free time");
                System.out.println(eventTime.toString());
                break;
            } else if (startFreeTime.isBefore(eventTime)){
                startFreeTime = eventTime;
                endFreeTime = startFreeTime.plus(value, units);
            }
        }
        message = new DukeResponse().FREE_TIME_FOUND;
        message += new DateFormatter("dummy").formatLocalDateTime(startFreeTime) + "\n";
        ui.setMessage(message);
    }
}
