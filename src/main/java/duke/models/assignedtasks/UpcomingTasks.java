//@@lmtaek

package duke.models.assignedtasks;

import duke.exceptions.DukeException;
import duke.models.patients.PatientManager;
import duke.models.tasks.TaskManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UpcomingTasks {

    LocalDateTime dateTime;
    LocalDate date;
    ArrayList<AssignedTask> tasks = new ArrayList<AssignedTask>();
    ArrayList<String> taskDescriptions = new ArrayList<String>();
    ArrayList<String> patientsForTasks = new ArrayList<String>();
    DateTimeFormatter dateFormatParser = DateTimeFormatter.ofPattern("E, dd/MM");

    public UpcomingTasks(LocalDateTime dateTime, AssignedTaskManager assignedTaskManager, TaskManager taskManager, PatientManager patientManager) throws DukeException {
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

        for (AssignedTask task : tasks) {
            taskDescriptions.add(taskManager.getTask(task.getTid()).getDescription());
        }

        for (AssignedTask task : tasks) {
            patientsForTasks.add(patientManager.getPatient(task.getPid()).getName());
        }
    }

    public ArrayList<AssignedTask> getUpcomingTasks() {
        return tasks;
    }

    public ArrayList<String> getUpcomingTaskDescriptions() {
        return taskDescriptions;
    }

    public ArrayList<String> getPatientNames() {
        return patientsForTasks;
    }

    public String getFormattedDate() {
        return dateTime.format(dateFormatParser);
    }

}
