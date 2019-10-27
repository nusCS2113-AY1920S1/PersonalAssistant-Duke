package duke.models.assignedtasks;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import duke.exceptions.DukeException;

import java.util.ArrayList;

/**
 * Represents a list of Task that can perform operations such as
 * add and delete on the tasks.
 */
public class AssignedTaskManager {
    /**
     * An ArrayList structure.
     */
    private Multimap<Integer, AssignedTask> assignedTaskIdMap = ArrayListMultimap.create();
    int maxId = 0;

    /**
     * .
     *
     * @param assignedTasks .
     */
    public AssignedTaskManager(ArrayList<AssignedTask> assignedTasks) {
        for (AssignedTask assignedTask : assignedTasks) {
            if (assignedTask.getUuid() == 0) {
                maxId += 1;
                assignedTask.setUuid(maxId);
            }
            assignedTaskIdMap.put(assignedTask.getPid(), assignedTask);
        }

        if (!assignedTasks.isEmpty()) {
            this.maxId = assignedTasks.get(assignedTasks.size() - 1).getUuid();
        }
    }

    /**
     * .
     *
     * @return .
     */
    public ArrayList<AssignedTask> getAssignTasks() {
        return new ArrayList<AssignedTask>(assignedTaskIdMap.values());
    }

    /**
     * .
     *
     * @param t .
     */
    public void addPatientTask(AssignedTask t) {
        if (t.getUuid() == 0) {
            maxId += 1; //Increment maxId by 1 for the new coming patient
            t.setUuid(maxId); //Set the unique id to patient
        }
        assignedTaskIdMap.put(t.getPid(), t);
    }


    /**
     * .
     *
     * @param uid .
     * @throws DukeException .
     */
    public void deletePatientTaskByUniqueId(int uid) throws DukeException {
        for (AssignedTask assignedTask : assignedTaskIdMap.values()) {
            if (assignedTask.getUuid() == uid) {
                assignedTaskIdMap.remove(assignedTask.getPid(), assignedTask);
                return;
            }
        }
    }

    /**
     * .
     *
     * @param id .
     * @throws DukeException .
     */
    public void deleteAllPatientTaskByTaskId(int id) throws DukeException {
        for (AssignedTask assignedTask : assignedTaskIdMap.values()) {
            if (assignedTask.getTid() == id) {
                assignedTaskIdMap.remove(assignedTask.getPid(), assignedTask);
                return;
            }
        }
    }


    /**
     * .
     *
     * @return .
     */
    public boolean doesUidExist(int id) {
        for (AssignedTask assignedTask: assignedTaskIdMap.values()) {
            if (assignedTask.getUuid() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * .
     *
     * @return .
     */
    public boolean doesPatientIdExist(int id) {
        if (assignedTaskIdMap.containsKey(id)) {
            return true;
        }
        return false;
    }

    /**
     * .
     *
     * @return .
     */
    public boolean isSameTaskExist(AssignedTask patientTask) {
        for (AssignedTask assignedTask: assignedTaskIdMap.values()) {
            if (assignedTask.equals(patientTask)) {
                return true;
            }
        }
        return false;
    }

    /**
     * .
     *
     * @param pid .
     * @throws DukeException .
     */
    public void deleteAllTasksBelongToThePatient(Integer pid) throws DukeException {
        if (assignedTaskIdMap.containsKey(pid)) {
            assignedTaskIdMap.removeAll(pid);
        } else {
            throw new DukeException("Patient id: " + pid + " does not have any tasks!");
        }
    }

    /**
     * .
     *
     * @param pid .
     * @return .
     * @throws DukeException .
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
     * .
     *
     * @param tid .
     * @return .
     * @throws DukeException .
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
