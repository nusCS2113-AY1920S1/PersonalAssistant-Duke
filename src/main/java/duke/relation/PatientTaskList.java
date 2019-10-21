package duke.relation;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import duke.core.DukeException;
import duke.patient.Patient;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Represents a list of Task that can perform operations such as
 * add and delete on the tasks.
 */
public class PatientTaskList {
    /**
     * An ArrayList structure.
     */
    private Multimap<Integer, PatientTask> patientTaskIdMap = ArrayListMultimap.create();
    int maxId = 0;

    /**
     * .
     *
     * @param newPatientTaskList .
     */
    public PatientTaskList(ArrayList<PatientTask> newPatientTaskList) {
        for (PatientTask patientTasK : newPatientTaskList) {
            if (patientTasK.getUid() == 0) {
                maxId += 1;
                patientTasK.setUid(maxId);
            }
            patientTaskIdMap.put(patientTasK.getPatientId(), patientTasK);
        }

        if (!newPatientTaskList.isEmpty()) {
            this.maxId = newPatientTaskList.get(newPatientTaskList.size() - 1).getUid();
        }
    }

    /**
     * .
     *
     * @return .
     */
    public ArrayList<PatientTask> fullPatientTaskList() {
        return new ArrayList<PatientTask>(patientTaskIdMap.values());
    }

    /**
     * .
     *
     * @param t .
     */
    public void addPatientTask(PatientTask t) {
        if (t.getUid() == 0) {
            maxId += 1; //Increment maxId by 1 for the new coming patient
            t.setUid(maxId); //Set the unique id to patient
        }
        patientTaskIdMap.put(t.getPatientId(), t);
    }


    /**
     * .
     *
     * @param uid .
     * @throws DukeException .
     */
    public void deletePatientTaskByUniqueId(int uid) throws DukeException {
        for (PatientTask patientTask : patientTaskIdMap.values()) {
            if (patientTask.getUid() == uid) {
                patientTaskIdMap.remove(patientTask.getPatientId(), patientTask);
                return;
            }
        }
    }


    /**
     * .
     *
     * @return .
     */
    public boolean isIdExist(int id) {
        for (PatientTask patientTask: patientTaskIdMap.values()) {
            if (patientTask.getUid() == id) {
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
    public boolean isPatientIdExist(int id) {
        if (patientTaskIdMap.containsKey(id)) {
            return true;
        }
        return false;
    }

    /**
     * .
     *
     * @return .
     */
    public boolean isSameTaskExist(PatientTask patientTask) {
        for (PatientTask newPatientTask: patientTaskIdMap.values()) {
            if (newPatientTask.equals(patientTask)) {
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
    public void deleteEntirePatientTask(Integer pid) throws DukeException {
        if (patientTaskIdMap.containsKey(pid)) {
            patientTaskIdMap.removeAll(pid);
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
    public ArrayList<PatientTask> getPatientTask(int pid) throws DukeException {
        if (patientTaskIdMap.containsKey(pid)) {
            ArrayList<PatientTask> tempArray = new ArrayList<PatientTask>();
            tempArray.addAll(patientTaskIdMap.get(pid));
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
    public ArrayList<PatientTask> getTaskPatient(int tid) throws DukeException {
        ArrayList<PatientTask> tempArray = new ArrayList<PatientTask>();
        for (PatientTask patientTask : patientTaskIdMap.values()) {
            if (patientTask.getTaskID() == tid) {
                tempArray.add(patientTask);
            }
        }
        if (tempArray.size() != 0) {
            return tempArray;
        } else {
            throw new DukeException("The Task with id " + tid + " has not been assigned to any patients");
        }

    }


}
