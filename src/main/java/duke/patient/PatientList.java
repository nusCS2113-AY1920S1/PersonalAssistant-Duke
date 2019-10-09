package duke.patient;

import java.util.ArrayList;
import java.util.HashMap;

public class PatientList {

    private HashMap<Integer, Patient> patientIdMap = new HashMap<Integer, Patient>();
    //private HashMap<Integer, Patient> patientNameMap = new HashMap<String, Patient>();
    private int maxId = 0;
    /**
     * instantiate a new TaskList with a empty list.
     */
    public PatientList(ArrayList<Patient> patientList) {
        for (Patient patient : patientList) {
            patientIdMap.put(patient.getID(), patient);
        }
        if (!patientList.isEmpty()){
            this.maxId = patientList.get(patientList.size()-1).getID();
        }
    }

    public Patient getPatient(int id) {
        return patientIdMap.get(id);
    }

    public void addPatient(Patient patient) {
        if (patient.getID() == 0){
            maxId += 1; //Increment maxId by 1 for the new coming patient
            patient.setID(maxId); //Set the unique id to patient
        }
        patientIdMap.put(patient.getID(), patient);
    }

    public void updatePatientInfo(Patient patient) {
        patientIdMap.put(patient.getID(), patient);
    }

    public ArrayList<Patient> getPatientList() {
        return new ArrayList<>(patientIdMap.values());
    }
}
