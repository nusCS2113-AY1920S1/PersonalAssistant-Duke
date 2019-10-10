package duke.commands;

import duke.DateTime;
import duke.Storage;
import duke.lists.TaskList;
import duke.Ui;
import duke.items.tasks.Event;
import duke.items.tasks.Task;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FindFreeCommand extends Command {
    private Date startDate;
    private Date endDate;
    private List<Pair<Date, Date>> freeTime;

    /**
     * Constructor for FindFree Command.
     * @param date In the format of MM/dd/yyyy
     */
    public FindFreeCommand(String date) {
        com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
        this.startDate = parser.parse(date + " 00:00").get(0).getDates().get(0);
        this.endDate = parser.parse(date + " 23:59").get(0).getDates().get(0);;
    }

    /**
     * Output all the free time interval from startDate to endDate.
     * @param taskList TaskList instance.
     * @param storage Storage instance.
     * @param ui Ui instance.
     */
    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui) {
        List<Task> tasks = taskList.getList();
        freeTime = new ArrayList<>();
        freeTime.add(new Pair<>(startDate, endDate));
        for (Task task : tasks) {
            if (task.isOverlapping(startDate,endDate)) {
                Event event = (Event) task;
                Date taskStart = event.getStartDate().getDateTime();
                Date taskEnd = event.getEndDate().getDateTime();
                freeTime = newFreeTime(taskStart, taskEnd);
            }
        }
        List<String> formattedOutput = new ArrayList<String>();
        formattedOutput.add("Free Times are:");
        for (Pair<Date, Date> timeInterval : freeTime) {
            DateTime start = new DateTime(timeInterval.getKey());
            DateTime end = new DateTime(timeInterval.getValue());
            formattedOutput.add("From:" + start.toString() + " To:" + end.toString());
        }
        return ui.showFormatted(formattedOutput);
    }

    private List<Pair<Date, Date>> newFreeTime(Date taskStart, Date taskEnd) {
        List<Pair<Date, Date>> newFreeTime = new ArrayList<>();
        for (int i = 0; i < freeTime.size(); i++) {
            Date startTime = freeTime.get(i).getKey();
            Date endTime = freeTime.get(i).getValue();
            if ((taskStart.before(startTime) || taskStart.equals(startTime))
                    && taskEnd.after(startTime)
                    && taskEnd.before(endTime)) {
                newFreeTime.add(new Pair<>(taskEnd, endTime));
            }
            if ((taskEnd.after(endTime) || taskEnd.equals(endTime))
                    && taskStart.after(startTime)
                    && taskStart.before(endTime)) {
                newFreeTime.add(new Pair<>(startTime, taskStart));
            }
            if (taskStart.before(startTime) && taskEnd.after(endTime)) {
                assert true;
            }
            if (taskStart.after(startTime) && taskEnd.before(endTime)) {
                newFreeTime.add(new Pair<>(startTime,taskStart));
                newFreeTime.add(new Pair<>(taskEnd,endTime));
            }
            if (!(taskStart.before(endTime) && startTime.before(taskEnd))) {
                newFreeTime.add(new Pair<>(startTime,endTime));
            }
        }
        return newFreeTime;
    }
}
