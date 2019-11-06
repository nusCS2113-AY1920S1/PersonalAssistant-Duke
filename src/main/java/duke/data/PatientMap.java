package duke.data;

import duke.exception.DukeException;
import duke.exception.DukeFatalException;
import duke.exception.DukeResetException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.ArrayList;
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
    public PatientMap(GsonStorage storage) throws DukeFatalException {
        HashMap<String, Patient> patientHashMap = storage.loadPatientHashMap();
        patientObservableMap = FXCollections.observableMap(patientHashMap);

        for (Map.Entry<String, Patient> pair : patientHashMap.entrySet()) {
            Patient patient = pair.getValue();
            patient.initObservables();
            for (Map.Entry<String, Impression> mapElement : patient.getImpressionsObservableMap().entrySet()) {
                mapElement.getValue().setParent(patient);
                mapElement.getValue().initObservables();
                mapElement.getValue().initChild();
            }
            if (patient.getPrimaryDiagnosis() != null) {
                String primaryDiagnosisID = patient.getPrimaryDiagnosis().getName();
                try {
                    patient.setPrimaryDiagnosis(primaryDiagnosisID);
                } catch (DukeException e) {
                    e.printStackTrace();
                }
            }
        }
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
            Patient deletedPatient = patientObservableMap.get(keyIdentifier);
            patientObservableMap.remove(keyIdentifier);
            return deletedPatient;
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
            return patientObservableMap.get(keyIdentifier);
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
    public ArrayList<Patient> findPatient(String searchTerm) throws DukeException {
        String lowerSearchTerm = searchTerm.toLowerCase();
        ArrayList<Patient> filteredList = new ArrayList<Patient>();
        for (Map.Entry<String, Patient> mapElement : patientObservableMap.entrySet()) {
            Patient value = mapElement.getValue();
            if (value.toString().contains(lowerSearchTerm)) {
                filteredList.add(value);
            }
        }
        if (filteredList.isEmpty()) {
            throw new DukeException("No relevant Patients");
        }
        return filteredList;
    }

    /**
     * PatientList of all patients whose names contain the searchTerm.
     *
     * @param searchTerm String to search through the patients for.
     * @return PatientList of matching patients.
     */
    public ArrayList<Patient> findPatientsByName(String searchTerm) throws DukeException {
        String lowerSearchTerm = searchTerm.toLowerCase();
        ArrayList<Patient> resultList = new ArrayList<Patient>();
        for (Map.Entry<String, Patient> patientEntry: patientObservableMap.entrySet()) {
            if (patientEntry.getKey().toLowerCase().contains(lowerSearchTerm)) {
                resultList.add(patientEntry.getValue());
            }
        }
        return resultList;
    }

    /**
     * PatientMap of all patients whose allergies contain the searchTerm.
     *
     * @param searchTerm String to search if any patients are allergic.
     * @return PatientMap of matching patients.
     */
    public PatientMap findAllergies(String searchTerm) throws DukeException {
        int i = 1;
        PatientMap filteredList = new PatientMap();
        for (Map.Entry mapElement : patientObservableMap.entrySet()) {
            Patient value = (Patient) mapElement.getValue();
            if (value.isAllergic(searchTerm)) {
                filteredList.addPatient(value);
                ++i;
            }
        }

        if (i == 1) {
            throw new DukeException("No Patients are allergic!");
        } else {
            return filteredList;
        }
    }

    /**
     * Search entire database for search term.
     * @param searchTerm the term used for search
     * @return array list of objects relevant to the search
     * @throws DukeException if the database does not contain the information
     */
    public ArrayList<DukeObject> find(String searchTerm) throws DukeException {
        ArrayList<DukeObject> searchResult = new ArrayList<DukeObject>();
        ArrayList<Patient> filteredList = findPatient(searchTerm);
        for (Patient patient : filteredList) {
            searchResult.add(patient);
            searchResult.addAll(patient.find(searchTerm));
        }
        if (searchResult.isEmpty()) {
            throw new DukeException("No relevant search terms.");
        }
        return searchResult;
    }

    /**
     * Reports the number of patients currently in the list.
     *
     * @return A String reporting the current number of patients.
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

    public boolean patientExist(String keyIdentifier) {
        return patientObservableMap.containsKey(keyIdentifier);
    }

    public ObservableMap<String, Patient> getPatientObservableMap() {
        return patientObservableMap;
    }
}
