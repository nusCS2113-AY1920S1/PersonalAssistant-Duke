package duke.task;

import duke.exception.DukeException;
import duke.exception.DukeFatalException;
import duke.exception.DukeResetException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientList {
    // TSV files will have one entry per line, tabs disallowed in input

    private HashMap<String, Patient> patientMap;

    /**
     * Creates a new TaskList, loading data from the Storage object provided.
     *
     * @param storage The Storage object pointing to the TSV file containing the data to load.
     * @throws DukeResetException If file is corrupted or the data has been edited to be unreadable.
     * @throws DukeFatalException If unable to write data file.
     */
    public PatientList(GsonStorage storage) throws DukeResetException, DukeFatalException {
        patientMap = storage.loadPatientHashMap();
    }

    /**
     * Creates a new, empty TaskList.
     */
    public PatientList() {
        patientMap = new HashMap<>();
    }

    /**
     * Adds a new Patient to the list.
     *
     * @param newPatient The task to be added.
     * @return the patient object added.
     */
    public Patient addPatient(Patient newPatient) {
        patientMap.put(newPatient.getName(), newPatient);
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
        if (patientMap.containsKey(keyIdentifier)) {
            Patient deletedP = patientMap.get(keyIdentifier);
            patientMap.remove(keyIdentifier);
            return deletedP;
        } else {
            throw new DukeException("The patient cannot be indentified");
        }

    }

    /**
     * PatientList of all patients whose names contain the searchTerm.
     *
     * @param searchTerm String to search through the patients for.
     * @return PatientList of matching patients.
     */
    public PatientList find(String searchTerm) throws DukeException {
        int i = 1;
        PatientList filteredList = new PatientList();
        for (Map.Entry mapElement : patientMap.entrySet()) {
            Patient value = (Patient)mapElement.getValue();
            if (value.getName().contains(searchTerm)){
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
        int patientCount = patientMap.size();
        String patientCountStr = patientCount + ((patientCount == 1) ? " patient" : " patients");
        return "Now you have " + patientCountStr + " in the map.";
    }
}
