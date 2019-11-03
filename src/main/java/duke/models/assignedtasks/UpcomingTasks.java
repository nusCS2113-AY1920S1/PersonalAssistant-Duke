package duke.models.assignedtasks;

import duke.exceptions.DukeException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UpcomingTasks {

    LocalDateTime dateTime;
    LocalDate date;
    ArrayList<AssignedTask> tasks = new ArrayList<AssignedTask>();
    DateTimeFormatter dateFormatParser = DateTimeFormatter.ofPattern("E, dd/MM");

    public UpcomingTasks(LocalDateTime dateTime, AssignedTaskManager assignedTaskManager) throws DukeException {
        this.dateTime = dateTime;
        this.date = dateTime.toLocalDate();
        for (AssignedTask task : assignedTaskManager.getAssignTasks()) {
            if (task.getTodoDate() != null) {
                LocalDateTime taskTime = task.getTodoDate();
                LocalDate taskDate = task.getTodoDate().toLocalDate();

                if (taskTime.isAfter(dateTime) && taskDate.isBefore(date.plusDays(1))) {
                    tasks.add(task);
                }
            } else if (task.getStartDate() != null && task.getEndDate() != null) {
                LocalDateTime startTime = task.getStartDate();
                LocalDate startDate = task.getStartDate().toLocalDate();
                LocalDateTime endTime = task.getEndDate();
                LocalDate endDate = task.getEndDate().toLocalDate();

                if ((startTime.isAfter(dateTime)
                        && startDate.isBefore(date.plusDays(1)))
                        || (endTime.isAfter(dateTime)
                        && endDate.isBefore(date.plusDays(1)))) {
                    tasks.add(task);
                }
            } else {
                throw new DukeException("Unable to determine upcoming tasks for "
                + getFormattedDate() + ".");
            }
        }
    }

    public ArrayList<AssignedTask> getUpcomingTasks() {
        return tasks;
    }

    public String getFormattedDate() {
        return dateTime.format(dateFormatParser);
    }

}
