package duke.task;

import duke.exception.DukeException;

import java.util.ArrayList;

public class Impression extends DukeObject {

    private String description;
    private ArrayList<Evidence> evidences;
    private ArrayList<Treatment> treatments;
    private String patientbedNo;

    /**
     * Represents the impression a doctor has about a Patient.
     * An Impression object corresponds to the impression a Doctor has of a patient’s Condition,
     * the impression, the description of an impression and a list of Evidences
     * that led to the impression as well as a Treatment list with the treatments determined by a Doctor.
     * It also has a handler to the Patient it is observed about.
     * Attributes:
     * - name: the name of the impression
     * - description: the description of the patient
     * - evidence: the list of evidences contributing to the impression
     * - treatments: the list of treatments determined by a doctor to deal with the impression
     * - patient: the Patient it is tagged to
     */
    public Impression(String name, String description, Patient patient) {
        super(patient.getBedNo() + "\t" + name);
        this.description = description;
        this.patientbedNo = patient.getBedNo();
        this.evidences = new ArrayList<Evidence>();
        this.treatments = new ArrayList<Treatment>();
    }

    /**
     * This find function returns a list of all DukeObjs related to the impression containing the search term.
     * @param searchTerm String to be used to filter the DukeObj
     * @return the list of DukeObjs
     */
    public ArrayList<DukeObject> find(String searchTerm) throws DukeException {
        int i = 1;
        ArrayList<DukeObject> searchResult = new ArrayList<DukeObject>();
        for (Evidence evidence : this.evidences) {
            if (evidence.getName().contains(searchTerm)) {
                searchResult.add(evidence);
                ++i;
            }
        }

        for (Treatment treatment : this.treatments) {
            if (treatment.getName().contains(searchTerm)) {
                searchResult.add(treatment);
                ++i;
            }
        }

        if (i == 1) {
            throw new DukeException("Can't find any matching tasks!");
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
        this.evidences.add(newEvidence);
        return newEvidence;
    }

    /**
     * This deleteEvidence function deletes an evidence at the specified index from the evidence list.
     * @param idx index of the evidence
     * @return the deleted Evidence
     */
    public Evidence deleteEvidence(int idx) throws DukeException {
        if (idx >= 0 && idx < this.evidences.size()) {
            Evidence evidence = this.evidences.get(idx);
            this.evidences.remove(idx);
            return evidence;
        } else {
            throw new DukeException("I don't have that entry in the list!");
        }
    }

    /**
     * This getEvidence function returns the evidence from the evidence list at the specified index.
     * @param idx index of the evidence
     * @return the evidence specified by the index
     */
    public Evidence getEvidence(int idx) throws DukeException {
        if (idx >= 0 && idx < this.evidences.size()) {
            return this.evidences.get(idx);
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
        this.treatments.add(newTreatment);
        return newTreatment;
    }

    /**
     * This deleteTreatment function deletes a treatment at the specified index from the treatment list.
     * @param idx index of the treatment
     * @return the deleted treatment
     */
    public Treatment deleteTreatment(int idx) throws DukeException {
        if (idx >= 0 && idx < this.treatments.size()) {
            Treatment treatment = this.treatments.get(idx);
            this.treatments.remove(idx);
            return treatment;
        } else {
            throw new DukeException("I don't have that entry in the list!");
        }
    }

    /**
     * This getTreatment function returns the treatment from the treatment list at the specified index.
     * @param idx index of the treatment
     * @return the treatment specified by the index
     */
    public Treatment getTreatment(int idx) throws DukeException {
        if (idx >= 0 && idx < this.treatments.size()) {
            return this.treatments.get(idx);
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

    public ArrayList<Evidence> getEvidences() {
        return evidences;
    }

    public ArrayList<Treatment> getTreatments() {
        return treatments;
    }
}
