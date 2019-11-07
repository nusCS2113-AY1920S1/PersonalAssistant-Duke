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
            for (Impression imp: patient.getImpressionList()) {
                imp.setParent(patient);
                imp.initChildren();
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
        if (getPatientByBed(newPatient.getBedNo()) != null) {
            throw new DukeException("This patient's bed is occupied");
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
        Patient deletedPatient = getPatientByBed(keyIdentifier);
        if (deletedPatient != null) {
            patientList.remove(deletedPatient);
            return deletedPatient;
        }
        throw new DukeException("I don't have a patient called that!");
    }

    /**
     * Gets a patient from the list.
     *
     * @param keyIdentifier The argument given by the user to identify the patient.
     * @return the patient object
     */
    public Patient getPatientByBed(String keyIdentifier) throws DukeException {
        for (Patient patient : patientList) {
            if (patient.getBedNo().equals(keyIdentifier)) {
                return patient;
            }
        }
        throw new DukeException("I don't have a patient called that!");
    }

    /**
     * PatientList of all patients whose names contain the searchTerm.
     *
     * @param searchTerm String to search through the patients for.
     * @return PatientList of matching patients.
     */
    public SearchResults findPatients(String searchTerm) throws DukeException {
        String lowerSearchTerm = searchTerm.toLowerCase();
        ArrayList<Patient> resultList = new ArrayList<Patient>();
        for (Patient patient : patientList) {
            if (patient.contains(lowerSearchTerm)) {
                resultList.add(patient);
            }
        }
        return new SearchResults(searchTerm, resultList, null);
    }

    /**
     * PatientList of all patients whose names contain the searchTerm.
     *
     * @param searchTerm String to search through the patients for.
     * @return PatientList of matching patients.
     */
    public SearchResults findPatientsByName(String searchTerm) {
        String lowerSearchTerm = searchTerm.toLowerCase();
        ArrayList<Patient> resultList = new ArrayList<Patient>();
        for (Patient patient : patientList) {
            if (patient.getName().toLowerCase().contains(lowerSearchTerm)) {
                resultList.add(patient);
            }
        }
        return new SearchResults(searchTerm, resultList, null);
    }

    /**
     * Search entire database for search term.
     * @param searchTerm the term used for search
     * @return array list of objects relevant to the search
     * @throws DukeException if the database does not contain the information
     */
    public SearchResults searchAll(String searchTerm) throws DukeException {
        String lowerSearchTerm = searchTerm.toLowerCase();
        SearchResults results= findPatients(lowerSearchTerm);
        for (Patient patient : patientList) {
            result.addAll(patient.searchAll(lowerSearchTerm));
        }
        return result;
    }

    /**
     * Reports the number of patients currently in the list.
     *
     * @return A String reporting the current number of patients.
     */
    public String getPatientCountStr() {
        int patientCount = patientList.size();
        String patientCountStr = patientCount + ((patientCount == 1) ? " patient" : " patients");
        return "Now I have " + patientCountStr + " in the list.";
    }

    public ArrayList<Patient> getPatientList() {
        return patientList;
    }
}
