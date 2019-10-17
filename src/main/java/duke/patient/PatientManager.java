package duke.patient;

import duke.core.DukeException;

import java.util.ArrayList;
import java.util.HashMap;

public class PatientManager {

    private HashMap<Integer, Patient> patientIdMap = new HashMap<Integer, Patient>();
    private int maxId = 0;

    /**
     * instantiate a new TaskList with a empty list.
     */
    public PatientManager(ArrayList<Patient> patientList) {
        for (Patient patient : patientList) {
            patientIdMap.put(patient.getID(), patient);
        }
        if (!patientList.isEmpty()) {
            this.maxId = patientList.get(patientList.size() - 1).getID();
        }
    }

    /**
     * .
     *
     * @param id .
     * @return .
     */
    public boolean isExist(int id) {
        if (patientIdMap.containsKey(id)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * .
     *
     * @param id .
     * @return .
     * @throws DukeException .
     */
    public Patient getPatient(int id) throws DukeException {
        if (patientIdMap.containsKey(id)) {
            return patientIdMap.get(id);
        } else {
            throw new DukeException("The patient with id " + id + " does not exist.");
        }
    }

    /**
     * .
     *
     * @param name .
     * @return .
     */
    public ArrayList<Patient> getPatientByName(String name) {
        name = name.toLowerCase();
        ArrayList<Patient> patientsWithThisName = new ArrayList<>();
        for (Patient patient : patientIdMap.values()) {
            if (patient.getName().toLowerCase().equals(name)) {
                patientsWithThisName.add(patient);
            }
        }
        return patientsWithThisName;
    }

    /**
     * .
     *
     * @param patient .
     */
    public void addPatient(Patient patient) {
        if (patient.getID() == 0) {
            maxId += 1; //Increment maxId by 1 for the new coming patient
            patient.setID(maxId); //Set the unique id to patient
        }
        patientIdMap.put(patient.getID(), patient);
    }

    /**
     * .
     *
     * @return .
     */
    public ArrayList<Patient> getPatientList() {
        return new ArrayList<>(patientIdMap.values());
    }

    /**
     * .
     *
     * @param id .
     * @throws DukeException .
     */
    public void deletePatient(int id) throws DukeException {
        if (patientIdMap.containsKey(id)) {
            patientIdMap.remove(id);
        } else {
            throw new DukeException("The patient with id " + id + " does not exist.");
        }

    }
}
