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

    private transient ObservableMap<String, Evidence> observableEvidences;
    private transient ObservableMap<String, Treatment> observableTreatments;
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
    public Impression(String name, String description, Patient patient) {
        super(name, patient);
        this.description = description;
        this.evidences = new HashMap<>();
        this.treatments = new HashMap<>();

        initObservables();
    }

    /**
     * Attaches a listener to the treatments and evidences map.
     * This listener update the {@code treatments} and {@code evidences} whenever the observable map is updated.
     */
    private void attachListener() {
        observableTreatments.addListener((MapChangeListener<String, Treatment>) change -> {
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
    public ArrayList<Treatment> findTreatments(String searchTerm) {
        ArrayList<Treatment> searchResult = new ArrayList<>();
        String lowerSearchTerm = searchTerm.toLowerCase();
        for (Treatment treatment : this.observableTreatments.values()) {
            if (treatment.toString().toLowerCase().contains(lowerSearchTerm)) {
                searchResult.add(treatment);
            }
        }
        return searchResult;
    }

    /**
     * This functions search for Evidence relevant to the searchTerm.
     * @param searchTerm the term to be searched for
     * @return ArrayList of the Evidence
     */
    public ArrayList<Evidence> findEvidences(String searchTerm) {
        ArrayList<Evidence> searchResult = new ArrayList<>();
        String lowerSearchTerm = searchTerm.toLowerCase();
        for (Evidence evidence : this.observableEvidences.values()) {
            if (evidence.toString().toLowerCase().contains(lowerSearchTerm)) {
                searchResult.add(evidence);
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
        searchResult.addAll(findEvidences(searchTerm));
        searchResult.addAll(findTreatments(searchTerm));
        return searchResult;
    }

    /**
     * This function searches for treatments whose names contain the searchTerm.
     * @param searchTerm the term to be searched for
     * @return ArrayList of the Treatments
     */
    public ArrayList<Treatment> findTreatmentsByName(String searchTerm) {
        ArrayList<Treatment> searchResult = new ArrayList<>();
        String lowerSearchTerm = searchTerm.toLowerCase();
        for (Map.Entry<String, Treatment> entry : this.observableTreatments.entrySet()) {
            if (entry.getKey().toLowerCase().contains(lowerSearchTerm)) {
                searchResult.add(entry.getValue());
            }
        }
        return searchResult;
    }

    /**
     * This function searches for evidences whose names contain the searchTerm.
     * @param searchTerm the term to be searched for
     * @return ArrayList of the Evidences
     */
    public ArrayList<Evidence> findEvidencesByName(String searchTerm) {
        ArrayList<Evidence> searchResult = new ArrayList<>();
        String lowerSearchTerm = searchTerm.toLowerCase();
        for (Map.Entry<String, Evidence> entry : this.observableEvidences.entrySet()) {
            if (entry.getKey().toLowerCase().contains(lowerSearchTerm)) {
                searchResult.add(entry.getValue());
            }
        }
        return searchResult;
    }

    /**
     * This function searches for DukeData whose names contain the searchTerm.
     * @param searchTerm String to be used to filter the DukeData
     * @return the list of DukeData
     */
    public ArrayList<DukeData> findByName(String searchTerm) {
        ArrayList<DukeData> searchResult = new ArrayList<>();
        searchResult.addAll(findEvidencesByName(searchTerm));
        searchResult.addAll(findTreatmentsByName(searchTerm));
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
            throw new DukeException("I can't delete that evidence because I don't have it!");
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
            throw new DukeException("I don't have any evidence called that!");
        }
    }

    /**
     * Adds a new treatment to the treatment list.
     *
     * @param newTreatment the treatment to be added
     * @return the treatment added
     */
    public Treatment addNewTreatment(Treatment newTreatment) {
        this.observableTreatments.put(newTreatment.getName(), newTreatment);
        return newTreatment;
    }

    /**
     * This deleteTreatment function deletes a treatment at the specified index from the treatment list.
     *
     * @param keyIdentifier name of the treatment
     * @return the deleted treatment
     */
    public Treatment deleteTreatment(String keyIdentifier) throws DukeException {
        if (this.observableTreatments.containsKey(keyIdentifier)) {
            Treatment treatment = this.observableTreatments.get(keyIdentifier);
            this.observableTreatments.remove(keyIdentifier);
            return treatment;
        } else {
            throw new DukeException("I can't delete that treatment because I don't have it!");
        }
    }

    /**
     * This getTreatment function returns the treatment from the treatment list at the specified index.
     *
     * @param keyIdentifier index of the treatment
     * @return the treatment specified by the index
     */
    public Treatment getTreatment(String keyIdentifier) throws DukeException {
        if (this.observableTreatments.containsKey(keyIdentifier)) {
            return this.observableTreatments.get(keyIdentifier);
        } else {
            throw new DukeException("I don't have any treatment called that!");
        }
    }

    @Override
    public String toString() {
        StringBuilder infoStrBuilder = new StringBuilder("Impression details\n");
        infoStrBuilder.append("Description: ").append(this.description).append("\n");
        for (Map.Entry mapElement : this.evidences.entrySet()) {
            Evidence valueE = (Evidence) mapElement.getValue();
            infoStrBuilder.append(valueE.toString());
        }
        for (Map.Entry mapElement : this.treatments.entrySet()) {
            Treatment valueT = (Treatment) mapElement.getValue();
            infoStrBuilder.append(valueT.toString());
        }
        return super.toString() + infoStrBuilder.toString() + "\n";
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

    public HashMap<String, Evidence> getEvidences() {
        return evidences;
    }

    public HashMap<String, Treatment> getTreatments() {
        return treatments;
    }

    public ObservableMap<String, Evidence> getObservableEvidences() {
        return observableEvidences;
    }

    public ObservableMap<String, Treatment> getObservableTreatments() {
        return observableTreatments;
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
        attributes.put("parent", getParent());
        attributes.put("name", getName());
    }

    private void initObservableTreatmentsEvidences() {
        this.observableTreatments = FXCollections.observableMap(treatments);
        this.observableEvidences = FXCollections.observableMap(evidences);
        attachListener();
    }

    public ObservableMap<String, Object> getAttributes() {
        return attributes;
    }

    public void updateAttributes() {
        updateObservableAttributes();
    }

    /**
     * This function initialises the parent object of each evidence and treatment.
     */
    public void initChild() {
        for (Map.Entry<String, Evidence> mapElement : this.observableEvidences.entrySet()) {
            mapElement.getValue().setParent(this);
        }

        for (Map.Entry<String, Treatment> mapElement : this.observableTreatments.entrySet()) {
            mapElement.getValue().setParent(this);
        }
    }

    /**
     * Computes the number of critical items in this impression: items with priority 1.
     * @return The number of critical items in this impression.
     */
    public int getCriticalCount() {
        int count = 0;
        for (Treatment treatment : treatments.values()) {
            if (treatment.getPriority() == 1) {
                ++count;
            }
        }
        for (Evidence evidence : evidences.values()) {
            if (evidence.getPriority() == 1) {
                ++count;
            }
        }
        return count;
    }


    /**
     * Computes the number of follow up items: the number of Investigations not yet ordered, or whose results have not
     * been reviewed, and the number of plan items that have not been started on, and returns a string representing
     * these items.
     * @return A string indicating the number of follow-up items in this impression.
     */
    public String getFollowUpCountStr() {
        int count = 0;
        for (Treatment treatment : treatments.values()) {
            if ((treatment instanceof Investigation && treatment.getStatusIdx() <= 1)
                    || (treatment instanceof Plan && treatment.getStatusIdx() < 1)) {
                ++count;
            }
        }
        if (count == 0) {
            return "No follow-ups";
        } else if (count == 1) {
            return "1 follow-up";
        } else {
            return count + "follow-ups";
        }
    }

    /**
     * Calls getCriticalCount() to compute the number of critical itmes (items with priority 1) and returns a string
     * indicating this value.
     * @return A string indicating the number of critical items that are associated with this Impression.
     */
    public String getCriticalCountStr() {
        int count = getCriticalCount();
        if (count == 0) {
            return "No critical issues";
        } else if (count == 1) {
            return "1 critical issue";
        } else {
            return count + "critical issues";
        }
    }
}
