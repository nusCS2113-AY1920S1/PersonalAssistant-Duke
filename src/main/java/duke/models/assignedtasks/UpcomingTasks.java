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

    /**
     * Constructor for Upcoming Tasks class. Will create task list for tasks assigned to provided date,
     * and find relevant corresponding information to be provided to the user such as task descriptions,
     * or patients involved in tasks.
     * @param dateTime The provided date for the desired upcoming tasks.
     * @param assignedTaskManager The user's list of assigned tasks.
     * @param taskManager The user's list of all tasks.
     * @param patientManager The user's list of all patient profiles.
     * @throws DukeException Thrown when the constructor is unable to find all the required information.
     */
    public UpcomingTasks(LocalDateTime dateTime, AssignedTaskManager assignedTaskManager,
                         TaskManager taskManager, PatientManager patientManager) throws DukeException {
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

    /**
     * Returns AssignedTask array list with all tasks designated for the provided date.
     * @return Array list with designated date's tasks
     */
    public ArrayList<AssignedTask> getUpcomingTasks() {
        return tasks;
    }

    /**
     * Returns String array list with all descriptions of designated tasks for provided date.
     * @return Array list with task descriptions for designated date's tasks
     */
    public ArrayList<String> getUpcomingTaskDescriptions() {
        return taskDescriptions;
    }

    /**
     * Returns String array list with all patient names of designated tasks for provided date.
     * @return Array list with patient names for designated date's tasks
     */
    public ArrayList<String> getPatientNames() {
        return patientsForTasks;
    }

    /**
     * Returns String version of provided date in 'E, dd/MM' format.
     * @return Reformatted date.
     */
    public String getFormattedDate() {
        return dateTime.format(dateFormatParser);
    }

}
