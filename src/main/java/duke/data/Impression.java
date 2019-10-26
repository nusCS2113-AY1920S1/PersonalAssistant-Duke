package duke.data;

import duke.exception.DukeException;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Impression extends DukeObject {

    private String description;
    private HashMap<String, Evidence> evidences;
    private HashMap<String, Treatment> treatments;
    private String patientBedNo;

    private transient ObservableMap<String, Evidence> observableEvidences;
    private transient ObservableMap<String, Treatment> observableTreaments;
    private transient ObservableMap<String, Object> attributes;

    // TODO: integrate finding with autocorrect?

    /**
     * Represents the impression a doctor has about a Patient.
     * An Impression object corresponds to the impression a Doctor has of a patient’s Condition,
     * the impression, the description of an impression and a list of Evidences
     * that led to the impression as well as a Treatment list with the treatments determined by a Doctor.
     * It also has a handler to the Patient it is observed about.
     * Attributes:
     * - evidence the list of evidences contributing to the impression
     * - treatments: the list of treatments determined by a doctor to deal with the impression
     * - patient: the Patient it is tagged to
     * @param name the name of the impression
     * @param description the description of the impression
     */
    public Impression(String name, String description, String patientBedNo) {
        super(name);
        this.description = description;
        this.patientBedNo = patientBedNo;
        this.evidences = new HashMap<>();
        this.treatments = new HashMap<>();

        initObservables();
    }

    /**
     * Attaches a listener to the treatments and evidences map.
     * This listener update the {@code treatments} and {@code evidences} whenever the observable map is updated.
     */
    private void attachListener() {
        observableTreaments.addListener((MapChangeListener<String, Treatment>) change -> {
            if (change.wasAdded()) {
                treatments.put(change.getKey(), change.getValueAdded());
            } else if (change.wasRemoved()) {
                treatments.remove(change.getKey(), change.getValueRemoved());
            }
        });
        observableEvidences.addListener((MapChangeListener<String, Evidence>) change -> {
            if (change.wasAdded()) {
                evidences.put(change.getKey(), change.getValueAdded());
            } else if (change.wasRemoved()) {
                evidences.remove(change.getKey(), change.getValueRemoved());
            }
        });
    }

    /**
     * This functions search for treatment relevant to the searchTerm.
     * @param searchTerm the term to be searched for
     * @return ArrayList of the Treatments
     */
    public ArrayList<Treatment> findTreatment(String searchTerm) {
        ArrayList<Treatment> searchResult = new ArrayList<>();
        for (Map.Entry<String, Treatment> mapElement : this.observableTreaments.entrySet()) {
            Treatment valueT = mapElement.getValue();
            if (valueT.toString().contains(searchTerm)) {
                searchResult.add(valueT);
            }
        }
        return searchResult;
    }

    /**
     * This functions search for Evidence relevant to the searchTerm.
     * @param searchTerm the term to be searched for
     * @return ArrayList of the Evidence
     */
    public ArrayList<Evidence> findEvidence(String searchTerm) {
        ArrayList<Evidence> searchResult = new ArrayList<>();
        for (Map.Entry<String, Evidence> mapElement : this.observableEvidences.entrySet()) {
            Evidence valueE = mapElement.getValue();
            if (valueE.toString().contains(searchTerm)) {
                searchResult.add(valueE);
            }
        }
        return searchResult;
    }

    /**
     * This find function returns a list of all DukeData related to the impression containing the search term.
     *
     * @param searchTerm String to be used to filter the DukeData
     * @return the list of DukeData
     */
    public ArrayList<DukeData> find(String searchTerm) {
        ArrayList<DukeData> searchResult = new ArrayList<>();
        searchResult.addAll(findEvidence(searchTerm));
        searchResult.addAll(findTreatment(searchTerm));
        return searchResult;
    }

    /**
     * This addNewEvidence function adds a new evidence to the evidence list.
     *
     * @param newEvidence the evidence to be added
     * @return the Evidence added
     */
    public Evidence addNewEvidence(Evidence newEvidence) {
        this.observableEvidences.put(newEvidence.getName(), newEvidence);
        return newEvidence;
    }

    /**
     * This deleteEvidence function deletes an evidence at the specified index from the evidence list.
     *
     * @param keyIdentifier name of the evidence
     * @return the deleted Evidence
     */
    public Evidence deleteEvidence(String keyIdentifier) throws DukeException {
        if (this.observableEvidences.containsKey(keyIdentifier)) {
            Evidence evidence = this.observableEvidences.get(keyIdentifier);
            this.observableEvidences.remove(keyIdentifier);
            return evidence;
        } else {
            throw new DukeException("I don't have that entry in the list!");
        }
    }

    /**
     * This getEvidence function returns the evidence from the evidence list at the specified index.
     *
     * @param keyIdentifier name of the evidence
     * @return the evidence specified by the index
     */
    public Evidence getEvidence(String keyIdentifier) throws DukeException {
        if (this.observableEvidences.containsKey(keyIdentifier)) {
            return this.observableEvidences.get(keyIdentifier);
        } else {
            throw new DukeException("I don't have that entry in the list!");
        }
    }

    /**
     * Adds a new treatment to the treatment list.
     *
     * @param newTreatment the treatment to be added
     * @return the treatment added
     */
    public Treatment addNewTreatment(Treatment newTreatment) {
        this.observableTreaments.put(newTreatment.getName(), newTreatment);
        return newTreatment;
    }

    /**
     * This deleteTreatment function deletes a treatment at the specified index from the treatment list.
     *
     * @param keyIdentifier name of the treatment
     * @return the deleted treatment
     */
    public Treatment deleteTreatment(String keyIdentifier) throws DukeException {
        if (this.observableTreaments.containsKey(keyIdentifier)) {
            Treatment treatment = this.observableTreaments.get(keyIdentifier);
            this.observableTreaments.remove(keyIdentifier);
            return treatment;
        } else {
            throw new DukeException("I don't have that entry in the list!");
        }
    }

    /**
     * This getTreatment function returns the treatment from the treatment list at the specified index.
     *
     * @param keyIdentifier index of the treatment
     * @return the treatment specified by the index
     */
    public Treatment getTreatment(String keyIdentifier) throws DukeException {
        if (this.observableTreaments.containsKey(keyIdentifier)) {
            return this.observableTreaments.get(keyIdentifier);
        } else {
            throw new DukeException("I don't have that entry in the list!");
        }
    }

    @Override
    public String toString() {
        StringBuilder informationString;
        informationString = new StringBuilder("Impression details\n");
        informationString.append("Description: ").append(this.description).append("\n");
        informationString.append("Patient Bed: ").append(this.patientBedNo).append("\n");
        for (Map.Entry mapElement : this.evidences.entrySet()) {
            Evidence valueE = (Evidence) mapElement.getValue();
            informationString.append(valueE.toString());
        }
        for (Map.Entry mapElement : this.treatments.entrySet()) {
            Treatment valueT = (Treatment) mapElement.getValue();
            informationString.append(valueT.toString());
        }
        return super.toString() + informationString + "\n";
    }

    @Override
    public String toDisplayString() {
        // Todo
        return null;
    }

    @Override
    public String toReportString() {
        StringBuilder informationString;
        informationString = new StringBuilder("\n\tDescription of impression: " + this.description + "\n");
        for (Map.Entry mapElement : this.evidences.entrySet()) {
            Evidence valueE = (Evidence) mapElement.getValue();
            informationString.append("/t").append(valueE.toReportString());
        }

        for (Map.Entry mapElement : this.treatments.entrySet()) {
            Treatment valueT = (Treatment) mapElement.getValue();
            informationString.append("\t").append(valueT.toReportString());
        }
        return informationString + "\n\n";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPatient() {
        return patientBedNo;
    }

    public HashMap<String, Evidence> getEvidences() {
        return evidences;
    }

    public HashMap<String, Treatment> getTreatments() {
        return treatments;
    }

    public ObservableMap<String, Evidence> getObservableEvidences() {
        return observableEvidences;
    }

    public ObservableMap<String, Treatment> getObservableTreaments() {
        return observableTreaments;
    }

    public void initObservables() {
        initObservableAttributes();
        initObservableTreatmentsEvidences();
    }

    private void initObservableAttributes() {
        attributes = FXCollections.observableMap(new HashMap<>());
        updateAttributes();
    }

    private void updateObservableAttributes() {
        attributes.put("description", getDescription());
        attributes.put("evidences", getEvidences());
        attributes.put("treatments", getTreatments());
        attributes.put("patientBedNo", getPatient());
        attributes.put("name", getName());
    }

    private void initObservableTreatmentsEvidences() {
        this.observableTreaments = FXCollections.observableMap(treatments);
        this.observableEvidences = FXCollections.observableMap(evidences);
        attachListener();
    }

    public ObservableMap<String, Object> getAttributes() {
        return attributes;
    }

    public void updateAttributes() {
        updateObservableAttributes();
    }
}
