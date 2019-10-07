package duke.patient;

import duke.core.DukeException;
import duke.task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PatientList {

//    private HashMap<Integer, Patient> allPatientsMap = new HashMap<Integer, Patient>();
//    private HashMap<Integer, Patient> activePatientsMap = new HashMap<Integer, Patient>();
    private List<Patient> patients = new ArrayList<Patient>();

    /**
     * instantiate a new TaskList with a empty list.
     */
    public PatientList(List<Patient> patientList) {
        this.patients = patientList;
    }

    public Patient getPatient(int id){
        return patients.get(id);
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public void updatePatientInfo(int id, Patient patient){
        patients.set(id, patient);
    }
}
