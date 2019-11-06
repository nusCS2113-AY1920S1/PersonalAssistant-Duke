package duke.data;

import duke.exception.DukeException;
import duke.exception.DukeFatalException;
import duke.exception.DukeResetException;

import java.util.ArrayList;

public class PatientList {

    private ArrayList<Patient> patientList;

    /**
     * Creates a new PatientList, loading data from the Storage object provided.
     *
     * @param storage The Storage object pointing to the TSV file containing the data to load.
     * @throws DukeResetException If file is corrupted or the data has been edited to be unreadable.
     * @throws DukeFatalException If unable to write data file.
     */
    public PatientList(GsonStorage storage) throws DukeFatalException {
        patientList = storage.loadPatients();

        for (Patient patient : patientList) {
            for (Impression imp: patient.getImpressions()) {
                imp.setParent(patient);
                imp.initChild();
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
    public PatientList() {
        patientList = new ArrayList<>();
    }

    /**
     * Adds a new Patient to the list.
     *
     * @param newPatient The task to be added.
     * @return the patient object added.
     */
    public Patient addPatient(Patient newPatient) throws DukeException {
        if (getPatient(newPatient.getBedNo()) != null){
            throw new DukeException("This patients bed is occupied");
        }
        patientList.add(newPatient);
        return newPatient;
    }

    /**
     * Deletes a patient from the list.
     *
     * @param keyIdentifier The argument given by the user to identify the patient to be deleted.
     * @return the patient object deleted
     * @throws DukeException If bedNo cannot be resolved to a valid bedNo.
     */
    public Patient deletePatient(String keyIdentifier) throws DukeException {
        Patient deletedPatient = getPatient(keyIdentifier);
        if (deletedPatient != null) {
            patientList.remove(deletedPatient);
            return deletedPatient;
        }
        throw new DukeException("The patient cannot be identified");
    }

    /**
     * Gets a patient from the list.
     *
     * @param keyIdentifier The argument given by the user to identify the patient.
     * @return the patient object
     */
    public Patient getPatient(String keyIdentifier) {
        String lowerKey = keyIdentifier.toLowerCase();
        for (Patient patient : patientList) {
            String lowerBed = patient.getBedNo().toLowerCase();
            String lowerName = patient.getName().toLowerCase();
            if (lowerKey.equals(lowerBed) || lowerKey.equals(lowerName)) {
                return patient;
            }
        }
        return null;
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
        for (Patient patient : patientList) {
            if (patient.toString().toLowerCase().contains(lowerSearchTerm)) {
                filteredList.add(patient);
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
    public ArrayList<Patient> findPatientsByName(String searchTerm) {
        String lowerSearchTerm = searchTerm.toLowerCase();
        ArrayList<Patient> resultList = new ArrayList<Patient>(patientList);
        return resultList;
    }

    /**
     * PatientList of all patients whose allergies contain the searchTerm.
     *
     * @param searchTerm String to search if any patients are allergic.
     * @return PatientList of matching patients.
     */
    public PatientList findAllergies(String searchTerm) throws DukeException {
        int i = 1;
        PatientList filteredList = new PatientList();
        for (Patient patient : patientList) {
            if (patient.isAllergic(searchTerm)) {
                filteredList.addPatient(patient);
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
        int patientCount = patientList.size();
        String patientCountStr = patientCount + ((patientCount == 1) ? " patient" : " patients");
        return "Now you have " + patientCountStr + " in the list.";
    }

    public boolean patientExist(String keyIdentifier) {
        return getPatient(keyIdentifier) != null;
    }

    public ArrayList<Patient> getPatientList() {
        return patientList;
    }
}
