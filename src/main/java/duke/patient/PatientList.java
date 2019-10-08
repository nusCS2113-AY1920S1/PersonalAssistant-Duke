package duke.patient;

import java.util.ArrayList;
import java.util.HashMap;

public class PatientList {

    private HashMap<Integer, Patient> patientIdMap = new HashMap<Integer, Patient>();
    //private HashMap<Integer, Patient> patientNameMap = new HashMap<String, Patient>();

    /**
     * instantiate a new TaskList with a empty list.
     */
    public PatientList(ArrayList<Patient> patientList) {
        for (Patient patient : patientList) {
            patientIdMap.put(patient.getID(), patient);
        }
    }

    public Patient getPatient(int id) {
        return patientIdMap.get(id);
    }

    public void addPatient(Patient patient) {
        patientIdMap.put(patient.getID(), patient);
    }

    public void updatePatientInfo(Patient patient) {
        patientIdMap.put(patient.getID(), patient);
    }

    public ArrayList<Patient> getPatientList() {
        return new ArrayList<>(patientIdMap.values());
    }
}
