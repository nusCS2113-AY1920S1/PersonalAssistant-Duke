//@@author WEIFENG-NUSCEG

package duke.models.assignedtasks;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import duke.exceptions.DukeException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a list of Assigned Task that can perform operations such as
 * add, delete, find on the tasks.
 */
public class AssignedTaskManager {
    private Multimap<Integer, AssignedTask> assignedTaskIdMap = ArrayListMultimap.create();
    private HashMap<Integer, AssignedTask> assignedTaskUniqueIdMap = new HashMap<>();
    int maxId = 0;

    /**
     * Create a new Assigned Task Manager to save all the information of the tasks assigend to a
     * patient by passing a List of AssignedTask to both the Multimap and the Hashmap.
     *
     * @param assignedTasks .
     */
    public AssignedTaskManager(ArrayList<AssignedTask> assignedTasks) {
        for (AssignedTask assignedTask : assignedTasks) {
            assignedTaskIdMap.put(assignedTask.getPid(), assignedTask);
            assignedTaskUniqueIdMap.put(assignedTask.getUuid(), assignedTask);
        }

        if (!assignedTasks.isEmpty()) {
            this.maxId = assignedTasks.get(assignedTasks.size() - 1).getUuid();
        }
    }

    /**
     * Retrieved the saved AssignedTask list from the AssignedTaskManager.
     *
     * @return a ArrayList of Assigned Task.
     */
    public ArrayList<AssignedTask> getAssignTasks() {
        return new ArrayList<AssignedTask>(assignedTaskUniqueIdMap.values());
    }

    /**
     * This method add a new Assigned Task to the current Assigned Task List.
     *
     * @param t a new AssignedTask to be added into the list.
     */
    public void addPatientTask(AssignedTask t) {
        if (t.getUuid() == 0) {
            maxId += 1; //Increment maxId by 1 for the new coming patient
            t.setUuid(maxId); //Set the unique id to patient
        }
        assignedTaskIdMap.put(t.getPid(), t);
        assignedTaskUniqueIdMap.put(maxId, t);
    }


    /**
     * This method delete a Assigned Task from the existing list through the
     * AssignedTask's unique ID.
     *
     * @param uid the unique ID of the assigned task to be delete.
     * @throws DukeException throws exception if such assigned task with the unique id does not exist.
     */
    public void deletePatientTaskByUniqueId(int uid) throws DukeException {
        try {
            AssignedTask assignedTask = assignedTaskUniqueIdMap.get(uid);
            int patientID = assignedTask.getPid();
            assignedTaskIdMap.remove(patientID, assignedTask);
            assignedTaskUniqueIdMap.remove(uid);
        } catch (Exception e) {
            throw new DukeException("Such Unique ID does not exist" + e.getMessage());
        }
    }

    /**
     * This method deletes a AssignedTask from the existing list through the task id.
     *
     * @param id the id of the task to be deleted.
     */
    public void deleteAssignedTaskByTaskId(int id) {
        for (AssignedTask assignedTask : assignedTaskIdMap.values()) {
            if (assignedTask.getTid() == id) {
                assignedTaskIdMap.remove(assignedTask.getPid(), assignedTask);
                assignedTaskUniqueIdMap.remove(assignedTask.getUuid());
                return;
            }
        }
    }

    /**
     * This method deletes all the AssignedTask from the existing list through the task ID.
     *
     * @param id id of the task to be deleted.
     * @throws DukeException throws exception if null pointer exception occurs.
     */
    public void deleteAllAssignedTaskByTaskId(int id) throws DukeException {
        int count = 0;
        for (AssignedTask assignedTask : assignedTaskIdMap.values()) {
            if (assignedTask.getTid() == id) {
                count++;
            }
        }

        try {
            for (int i = 0; i < count; i++) {
                deleteAssignedTaskByTaskId(id);
            }
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
    }

    /**
     * Check if any AssignedTask in the existing list contains the same unique id.
     *
     * @param id the unique id of a AssignedTask.
     * @return a boolean represents the existence of the AssignedTask with same uid.
     */
    public boolean doesUidExist(int id) {
        return assignedTaskUniqueIdMap.containsKey(id);
    }

    /**
     * Check if any AssignedTask in the existing list contains the same patient id.
     *
     * @param id the patient id of a AssignedTask.
     * @return a boolean represents the existence of the AssignedTask with same pid.
     */
    public boolean doesPatientIdExist(int id) {
        return assignedTaskIdMap.containsKey(id);
    }

    /**
     * Check if any AssignedTask in the existing list has exactly the same starting
     * time and ending time with the given AssignedTask.
     *
     * @param patientTask a AssignedTask to be checked.
     * @return a boolean represents if the same start and end time exist.
     */
    public boolean isSameStartEndTimeExist(AssignedTask patientTask) {
        for (AssignedTask assignedTask: assignedTaskUniqueIdMap.values()) {
            if (assignedTask instanceof AssignedTaskWithPeriod
                    && assignedTask.getStartDate().equals(patientTask.getStartDate())
                    && assignedTask.getEndDate().equals(patientTask.getEndDate())
                    && (assignedTask.getPid() == patientTask.getPid())
                    && (assignedTask.getTid() == patientTask.getTid())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if any AssignedTask in the existing list has exactly the same deadline
     * with the given AssignedTask.
     *
     * @param patientTask a AssignedTask to be checked.
     * @return a boolean represents if the same deadline exist in the list.
     */
    public boolean isSameDeadlineExist(AssignedTask patientTask) {
        for (AssignedTask assignedTask: assignedTaskUniqueIdMap.values()) {
            if (assignedTask instanceof AssignedTaskWithDate
                    && assignedTask.getTodoDate().equals(patientTask.getTodoDate())
                    && (assignedTask.getPid() == patientTask.getPid())
                    && (assignedTask.getTid() == patientTask.getTid())) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method deletes the Task that has been assigned to a patient by its patient id.
     *
     * @param id the patient id of the AssignedTask to be deleted.
     */
    public void deleteAssignedTaskBelongToThePatientFromUniqueIdMap(int id) {
        for (AssignedTask assignedTask : assignedTaskUniqueIdMap.values()) {
            if (assignedTask.getPid() == id) {
                assignedTaskUniqueIdMap.remove(assignedTask.getUuid());
                return;
            }
        }
    }

    /**
     * This method deletes all the Task that has been assigned to a patient by its patient id.
     *
     * @param pid the patient id of the AssignedTask to be deleted.
     * @throws DukeException throws exception if there is a null pointer exception.
     */
    public void deleteAllTasksBelongToThePatient(Integer pid) throws DukeException {
        if (assignedTaskIdMap.containsKey(pid)) {
            int count = 0;
            for (AssignedTask assignedTask : assignedTaskUniqueIdMap.values()) {
                if (assignedTask.getPid() == pid) {
                    count++;
                }
            }

            try {
                for (int i = 0; i < count; i++) {
                    deleteAssignedTaskBelongToThePatientFromUniqueIdMap(pid);
                }
            } catch (Exception e) {
                throw new DukeException(e.getMessage());
            }

            assignedTaskIdMap.removeAll(pid);
        } else {
            throw new DukeException("Patient id: " + pid + " does not have any tasks!");
        }
    }

    /**
     * Return a array list of the AssignedTask belong to a specific patient by its patient id.
     *
     * @param pid the id of the patient.
     * @return a array list of the AssignedTask belongs to this patient.
     * @throws DukeException throws exception if such patient does not has any associated tasks.
     */
    public ArrayList<AssignedTask> getPatientTask(int pid) throws DukeException {
        if (assignedTaskIdMap.containsKey(pid)) {
            ArrayList<AssignedTask> tempArray = new ArrayList<AssignedTask>();
            tempArray.addAll(assignedTaskIdMap.get(pid));
            return tempArray;
        } else {
            throw new DukeException("The patient with id " + pid + " does not have any tasks.");
        }
    }

    /**
     * Returns all the tasks with the same task id in the existing list.
     *
     * @param tid task's id.
     * @return a list of AssignedTask belongs to this task id.
     * @throws DukeException throws exception if such task has not been assigned to any patient.
     */
    public ArrayList<AssignedTask> getTaskPatient(int tid) throws DukeException {
        ArrayList<AssignedTask> tempArray = new ArrayList<AssignedTask>();
        for (AssignedTask assignedTask : assignedTaskIdMap.values()) {
            if (assignedTask.getTid() == tid) {
                tempArray.add(assignedTask);
            }
        }
        if (tempArray.size() != 0) {
            return tempArray;
        } else {
            throw new DukeException("The Task with id " + tid + " has not been assigned to any patients");
        }

    }


}
