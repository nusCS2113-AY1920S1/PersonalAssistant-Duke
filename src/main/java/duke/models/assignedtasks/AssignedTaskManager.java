package duke.models.assignedtasks;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import duke.exceptions.DukeException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a list of Task that can perform operations such as
 * add and delete on the tasks.
 */
public class AssignedTaskManager {
    /**
     * An ArrayList structure.
     */
    private Multimap<Integer, AssignedTask> assignedTaskIdMap = ArrayListMultimap.create();
    private HashMap<Integer, AssignedTask> assignedTaskUniqueIdMap = new HashMap<>();
    int maxId = 0;

    /**
     * .
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
     * .
     *
     * @return .
     */
    public ArrayList<AssignedTask> getAssignTasks() {
        return new ArrayList<AssignedTask>(assignedTaskUniqueIdMap.values());
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
        assignedTaskUniqueIdMap.put(maxId, t);
    }


    /**
     * .
     *
     * @param uid .
     * @throws DukeException .
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
     * .
     *
     * @param id .
     * @throws DukeException .
     */
    public void deleteAssignedTaskByTaskId(int id) throws DukeException {
        for (AssignedTask assignedTask : assignedTaskIdMap.values()) {
            if (assignedTask.getTid() == id) {
                assignedTaskIdMap.remove(assignedTask.getPid(), assignedTask);
                assignedTaskUniqueIdMap.remove(assignedTask.getUuid());
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
        } catch (DukeException e) {
            throw new DukeException(e.getMessage());
        }
    }



    /**
     * .
     *
     * @return .
     */
    public boolean doesUidExist(int id) {
        return assignedTaskUniqueIdMap.containsKey(id);
    }

    /**
     * .
     *
     * @return .
     */
    public boolean doesPatientIdExist(int id) {
        return assignedTaskIdMap.containsKey(id);
    }

    /**
     * .
     *
     * @return .
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
     * .
     *
     * @return .
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
     * .
     *
     * @param id .
     * @throws DukeException .
     */
    public void deleteAssignedTaskBelongToThePatientFromUniqueIdMap(int id) throws DukeException {
        for (AssignedTask assignedTask : assignedTaskUniqueIdMap.values()) {
            if (assignedTask.getPid() == id) {
                assignedTaskUniqueIdMap.remove(assignedTask.getUuid());
                return;
            }
        }
    }

    /**
     * .
     *
     * @param pid .
     * @throws DukeException .
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
            } catch (DukeException e) {
                throw new DukeException(e.getMessage());
            }

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
