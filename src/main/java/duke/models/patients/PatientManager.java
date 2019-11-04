//@@author kkeejjuunn

package duke.models.patients;

import duke.exceptions.DukeException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;


public class PatientManager {

    private HashMap<Integer, Patient> patientIdMap = new HashMap<Integer, Patient>();
    private int maxId = 0;

    /**
     * instantiate a new TaskList with a empty list.
     */
    public PatientManager(ArrayList<Patient> patientList) {
        for (Patient patient : patientList) {
            patientIdMap.put(patient.getId(), patient);
        }
        if (!patientList.isEmpty()) {
            this.maxId = patientList.get(patientList.size() - 1).getId();
        }
    }

    /**
     * .
     *
     * @param id .
     * @return .
     */
    public boolean doesExist(int id) {
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
            if (patient.getName().toLowerCase().contains(name)) {
                patientsWithThisName.add(patient);
            }
        }
        return patientsWithThisName;
    }

    /**
     * It checks whether the name is valid.
     *
     * @param name contains the name to be checked.
     * @throws DukeException if the name is invalid.
     */
    public void nameIsValid(String name) throws DukeException {
        Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");
        if (regex.matcher(name).find()) {
            throw new DukeException(PatientManager.class, "The patient's name cannot contain any special characters.");
        } else if (name.length() < 3 && name.matches("^[a-zA-Z]*$")) {
            throw new DukeException("The patient's name must have at least 3 alphabets.");
        }
    }

    /**
     * It checks whether the nric is valid.
     *
     * @param nric contains the nric to be checked.
     * @throws DukeException if the nric is invalid.
     */
    public void nricIsValid(String nric) throws DukeException {
        Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");
        for (Patient patient : patientIdMap.values()) {
            if (patient.getNric().toLowerCase().contains(nric)) {
                throw new DukeException("The NRIC is existed.");
            }
        }
        if (regex.matcher(nric).find()) {
            throw new DukeException("The patient's NRIC cannot contain any special characters.");
        }
        char firstChar = nric.charAt(0);
        if (firstChar != 'S' && firstChar != 'T' && firstChar != 'F' && firstChar != 'G') {
            throw new DukeException("The first letter of NRIC can only be S, T, F or G.");
        }
        String nricSubstring = nric.substring(1);
        if (nricSubstring.length() != 8) {
            throw new DukeException("The length of NRIC can only 9 characters.");
        }
        if (Character.isAlphabetic(nricSubstring.charAt(7))) {
            String nricSubstring2 = nricSubstring.substring(0, nricSubstring.length() - 1);
            if (!nricSubstring2.matches("[0-9]+")) {
                throw new DukeException("The NRIC can only be numerical except the first and last character.");
            }
        } else {
            throw new DukeException("The last character of NRIC can only be alphabet.");
        }
    }

    /**
     * It checks whether the room number is valid.
     *
     * @param room contains the room number to be checked.
     * @throws DukeException if the room number is invalid.
     */
    public void roomIsValid(String room) throws DukeException {
        Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");
        if (regex.matcher(room).find()) {
            throw new DukeException("The patient's Room No. cannot contain any special characters.");
        }
    }

    /**
     * It adds a new patient to the patient list.
     *
     * @param patient contains the information of the patient to be added.
     * @throws DukeException if the patient is not added successfully.
     */
    public void addPatient(Patient patient) throws DukeException {
        try {
            nameIsValid(patient.getName());
            nricIsValid(patient.getNric());
            roomIsValid(patient.getRoom());
        } catch (Exception e) {
            throw e;
        }

        if (patient.getId() == 0) {
            maxId += 1; //Increment maxId by 1 for the new coming patient
            patient.setId(maxId); //Set the unique id to patient
        }
        patientIdMap.put(patient.getId(), patient);
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
