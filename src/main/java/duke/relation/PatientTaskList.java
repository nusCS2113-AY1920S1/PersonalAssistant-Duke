package duke.relation;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import duke.core.DukeException;

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

    /**
     * .
     *
     * @param newPatientTaskList .
     */
    public PatientTaskList(ArrayList<PatientTask> newPatientTaskList) {
        for (PatientTask patientTasK : newPatientTaskList) {
            patientTaskIdMap.put(patientTasK.getPatientId(), patientTasK);
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
        patientTaskIdMap.put(t.getPatientId(), t);
    }

    /**
     * .
     *
     * @param pid .
     * @param tid .
     * @throws DukeException .
     */
    public void deletePatientTask(Integer pid, Integer tid) throws DukeException {

        if (patientTaskIdMap.containsKey(pid)) {
            for (PatientTask patientTask : patientTaskIdMap.get(pid)) {
                if (patientTask.getTaskID().equals(tid)) {
                    patientTaskIdMap.remove(pid, patientTask);
                } else {
                    throw new DukeException(
                            "The patient with id: " + pid + " has not been assigned with such task: " + tid);
                }
            }
        } else {
            throw new DukeException("Patient id: " + pid + " does not have any tasks!");
        }
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
