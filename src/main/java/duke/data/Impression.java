package duke.data;

import duke.exception.DukeException;

import java.util.HashMap;
import java.util.Map;

public class Impression extends DukeObject {

    private String description;
    private HashMap<String, Evidence> evidences;
    private HashMap<String, Treatment> treatments;
    private String patientbedNo;

    /**
     * Represents the impression a doctor has about a Patient.
     * An Impression object corresponds to the impression a Doctor has of a patient’s Condition,
     * the impression, the description of an impression and a list of Evidences
     * that led to the impression as well as a Treatment list with the treatments determined by a Doctor.
     * It also has a handler to the Patient it is observed about.
     * Attributes:
     * @param name the name of the impression
     * @param description the description of the impression
     * - evidence the list of evidences contributing to the impression
     * - treatments: the list of treatments determined by a doctor to deal with the impression
     * - patient: the Patient it is tagged to
     */
    public Impression(String name, String description, Patient patient) {
        super(patient.getBedNo() + "\t" + name);
        this.description = description;
        this.patientbedNo = patient.getBedNo();
        this.evidences = new HashMap<String, Evidence>();
        this.treatments = new HashMap<String, Treatment>();
    }

    /**
     * This find function returns a list of all DukeObjs related to the impression containing the search term.
     * @param searchTerm String to be used to filter the DukeObj
     * @return the list of DukeObjs
     */
    public HashMap<String, DukeData> find(String searchTerm) throws DukeException {
        int i = 1;
        HashMap<String, DukeData> searchResult = new HashMap<String, DukeData>();
        for (Map.Entry mapElement : this.evidences.entrySet()) {
            Evidence valueE = (Evidence)mapElement.getValue();
            if (valueE.toString().contains(searchTerm)) {
                searchResult.put(valueE.getName(), valueE);
                ++i;
            }
        }

        for (Map.Entry mapElement : this.treatments.entrySet()) {
            Treatment valueT = (Treatment)mapElement.getValue();
            if (valueT.toString().contains(searchTerm)) {
                searchResult.put(valueT.getName(), valueT);
                ++i;
            }
        }

        if (i == 1) {
            return null;
        } else {
            return searchResult;
        }
    }

    /**
     * This addNewEvidence function adds a new evidence to the evidence list.
     * @param newEvidence the evidence to be added
     * @return the Evidence added
     */
    public Evidence addNewEvidence(Evidence newEvidence) {
        this.evidences.put(newEvidence.getName(), newEvidence);
        return newEvidence;
    }

    /**
     * This deleteEvidence function deletes an evidence at the specified index from the evidence list.
     * @param keyIdentifier name of the evidence
     * @return the deleted Evidence
     */
    public Evidence deleteEvidence(String keyIdentifier) throws DukeException {
        if (this.evidences.containsKey(keyIdentifier)) {
            Evidence evidence = this.evidences.get(keyIdentifier);
            this.evidences.remove(keyIdentifier);
            return evidence;
        } else {
            throw new DukeException("I don't have that entry in the list!");
        }
    }

    /**
     * This getEvidence function returns the evidence from the evidence list at the specified index.
     * @param keyIdentifier name of the evidence
     * @return the evidence specified by the index
     */
    public Evidence getEvidence(String keyIdentifier) throws DukeException {
        if (this.evidences.containsKey(keyIdentifier)) {
            Evidence evidence = this.evidences.get(keyIdentifier);
            return evidence;
        } else {
            throw new DukeException("I don't have that entry in the list!");
        }
    }

    /**
     * Adds a new treatment to the treatment list.
     * @param newTreatment the treatment to be added
     * @return the treatment added
     */
    public Treatment addNewTreatment(Treatment newTreatment) {
        this.treatments.put(newTreatment.getName(), newTreatment);
        return newTreatment;
    }

    /**
     * This deleteTreatment function deletes a treatment at the specified index from the treatment list.
     * @param keyIdentifier name of the treatment
     * @return the deleted treatment
     */
    public Treatment deleteTreatment(String keyIdentifier) throws DukeException {
        if (this.treatments.containsKey(keyIdentifier)) {
            Treatment treatment = this.treatments.get(keyIdentifier);
            this.treatments.remove(keyIdentifier);
            return treatment;
        } else {
            throw new DukeException("I don't have that entry in the list!");
        }
    }

    /**
     * This getTreatment function returns the treatment from the treatment list at the specified index.
     * @param keyIdentifier name of the treatment
     * @return the treatment specified by the index
     */
    public Treatment getTreatment(String keyIdentifier) throws DukeException {
        if (this.treatments.containsKey(keyIdentifier)) {
            Treatment treatment = this.treatments.get(keyIdentifier);
            return treatment;
        } else {
            throw new DukeException("I don't have that entry in the list!");
        }
    }

    @Override
    public String toString() {
        // Todo
        return null;
    }

    @Override
    public String toDisplayString() {
        // Todo
        return null;
    }

    @Override
    public String toReportString() {
        // Todo
        return null;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPatient() {
        return patientbedNo;
    }

    public HashMap<String, Evidence> getEvidences() {
        return evidences;
    }

    public HashMap<String, Treatment> getTreatments() {
        return treatments;
    }
}
