package duke.data;

import duke.exception.DukeException;
import duke.exception.DukeFatalException;
import duke.exception.DukeResetException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.HashMap;
import java.util.Map;

public class PatientMap {

    private ObservableMap<String, Patient> patientObservableMap;

    /**
     * Creates a new PatientMap, loading data from the Storage object provided.
     *
     * @param storage The Storage object pointing to the TSV file containing the data to load.
     * @throws DukeResetException If file is corrupted or the data has been edited to be unreadable.
     * @throws DukeFatalException If unable to write data file.
     */
    public PatientMap(GsonStorage storage) throws DukeResetException, DukeFatalException {
        HashMap<String, Patient> patientHashMap = storage.loadPatientHashMap();
        patientObservableMap = FXCollections.observableMap(patientHashMap);
    }

    /**
     * Creates a new, empty TaskList.
     */
    public PatientMap() {
        patientObservableMap = FXCollections.observableMap(new HashMap<String, Patient>());
    }

    /**
     * Adds a new Patient to the list.
     *
     * @param newPatient The task to be added.
     * @return the patient object added.
     */
    public Patient addPatient(Patient newPatient) {
        patientObservableMap.put(newPatient.getBedNo(), newPatient);
        return newPatient;
    }

    /**
     * Deletes a patient from the map.
     *
     * @param keyIdentifier The argument given by the user to identify the patient to be deleted.
     * @return the patient object deleted
     * @throws DukeException If bedNo cannot be resolved to a valid bedNo.
     */
    public Patient deletePatient(String keyIdentifier) throws DukeException {
        if (patientObservableMap.containsKey(keyIdentifier)) {
            Patient deletedP = patientObservableMap.get(keyIdentifier);
            patientObservableMap.remove(keyIdentifier);
            return deletedP;
        } else {
            throw new DukeException("The patient cannot be identified");
        }
    }

    /**
     * Gets a patient from the map.
     *
     * @param keyIdentifier The argument given by the user to identify the patient.
     * @return the patient object
     * @throws DukeException If bedNo cannot be resolved to a valid bedNo.
     */
    public Patient getPatient(String keyIdentifier) throws DukeException {
        if (patientObservableMap.containsKey(keyIdentifier)) {
            Patient thePatient = patientObservableMap.get(keyIdentifier);
            return thePatient;
        } else {
            throw new DukeException("The patient cannot be identified");
        }
    }

    /**
     * PatientList of all patients whose names contain the searchTerm.
     *
     * @param searchTerm String to search through the patients for.
     * @return PatientList of matching patients.
     */
    public PatientMap find(String searchTerm) throws DukeException {
        int i = 1;
        PatientMap filteredList = new PatientMap();
        for (Map.Entry mapElement : patientObservableMap.entrySet()) {
            Patient value = (Patient)mapElement.getValue();
            if (value.getName().contains(searchTerm)) {
                filteredList.addPatient(value);
                ++i;
            }
        }

        if (i == 1) {
            throw new DukeException("Can't find any matching Patients!");
        } else {
            return filteredList;
        }
    }

    /**
     * Reports the number of tasks currently in the list.
     *
     * @return A String reporting the current number of tasks.
     */
    public String getPatientCountStr() {
        int patientCount = patientObservableMap.size();
        String patientCountStr = patientCount + ((patientCount == 1) ? " patient" : " patients");
        return "Now you have " + patientCountStr + " in the map.";
    }

    public HashMap<String, Patient> getPatientHashMap() {
        HashMap<String, Patient> patientHashMap = new HashMap<String, Patient>(patientObservableMap);
        return patientHashMap;
    }

    public ObservableMap<String, Patient> getPatientObservableMap() {
        return patientObservableMap;
    }
}
