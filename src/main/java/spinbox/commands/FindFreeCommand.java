package spinbox.commands;

import spinbox.DateTime;
import spinbox.Storage;
import spinbox.entities.items.tasks.Schedulable;
import spinbox.containers.lists.TaskList;
import spinbox.Ui;
import spinbox.entities.items.tasks.Event;
import spinbox.entities.items.tasks.Task;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class FindFreeCommand extends Command {
    private DateTime startDate;
    private DateTime endDate;
    private List<Pair<DateTime, DateTime>> freeTime;

    /**
     * Constructor for FindFree Command.
     * @param date In the format of MM/dd/yyyy
     */
    public FindFreeCommand(String date) {
        this.startDate = new DateTime(date + " 00:00");
        this.endDate = new DateTime(date + " 23:59");;
    }

    /**
     * Output all the free time interval from startDate to endDate.
     * @param taskList TaskList instance.
     * @param storage Storage instance.
     * @param ui Ui instance.
     * @param guiMode boolean to check if in gui mode.
     */
    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui, boolean guiMode) {
        List<Task> tasks = taskList.getList();
        freeTime = new ArrayList<>();
        freeTime.add(new Pair<>(startDate, endDate));
        for (Task task : tasks) {
            if (task.isSchedulable()) {
                if (((Schedulable) task).isOverlapping(startDate,endDate)) {
                    Event event = (Event) task;
                    DateTime taskStart = event.getStartDate();
                    DateTime taskEnd = event.getEndDate();
                    freeTime = newFreeTime(taskStart, taskEnd);
                }
            }

        }
        List<String> formattedOutput = new ArrayList<String>();
        formattedOutput.add("Free Times are:");
        for (Pair<DateTime, DateTime> timeInterval : freeTime) {
            DateTime start = timeInterval.getKey();
            DateTime end = timeInterval.getValue();
            formattedOutput.add("From:" + start.toString() + " To:" + end.toString());
        }
        return ui.showFormatted(formattedOutput);
    }

    private List<Pair<DateTime, DateTime>> newFreeTime(DateTime taskStart, DateTime taskEnd) {
        List<Pair<DateTime, DateTime>> newFreeTime = new ArrayList<>();
        for (int i = 0; i < freeTime.size(); i++) {
            DateTime startTime = freeTime.get(i).getKey();
            DateTime endTime = freeTime.get(i).getValue();
            if ((taskStart.getDateTime().before(startTime.getDateTime()) || taskStart.equals(startTime))
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
