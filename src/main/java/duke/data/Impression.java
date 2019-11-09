package duke.data;

import duke.exception.DukeException;
import duke.exception.DukeFatalException;
import duke.ui.card.ImpressionCard;
import duke.ui.context.Context;

import java.util.ArrayList;
import java.util.Comparator;

public class Impression extends DukeObject {

    private String description;
    private ArrayList<Evidence> evidences;
    private ArrayList<Treatment> treatments;
    private transient Patient parent;

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
     *
     * @param name        the name of the impression.
     * @param description the description of the impression.
     * @param patient     the patient who has the impression.
     */
    public Impression(String name, String description, Patient patient) {
        super(name, patient);

        this.parent = patient;
        this.description = description;
        this.evidences = new ArrayList<>();
        this.treatments = new ArrayList<>();
    }

    @Override
    public void setParent(DukeObject obj) {
        this.parent = (Patient) obj;
    }

    @Override
    public Patient getParent() {
        return parent;
    }

    /**
     * This functions search for treatment relevant to the searchTerm.
     *
     * @param searchTerm the term to be searched for
     * @return ArrayList of the Treatments
     */
    public SearchResults findTreatments(String searchTerm) {
        ArrayList<Treatment> treatmentList = new ArrayList<>();
        String lowerSearchTerm = searchTerm.toLowerCase();
        for (Treatment treatment : treatments) {
            if (treatment.contains(lowerSearchTerm)) {
                treatmentList.add(treatment);
            }
        }
        return new SearchResults(searchTerm, treatmentList, this);
    }

    /**
     * This functions search for Evidence relevant to the searchTerm.
     *
     * @param searchTerm the term to be searched for
     * @return ArrayList of the Evidence
     */
    public SearchResults findEvidences(String searchTerm) {
        ArrayList<Evidence> evidenceList = new ArrayList<>();
        String lowerSearchTerm = searchTerm.toLowerCase();
        for (Evidence evidence : evidences) {
            if (evidence.contains(lowerSearchTerm)) {
                evidenceList.add(evidence);
            }
        }
        return new SearchResults(searchTerm, evidenceList, this);
    }

    /**
     * Searches through all DukeData associated with this impression containing the search term.
     *
     * @param searchTerm String to be used to filter the DukeData
     * @return the list of DukeData
     */
    public SearchResults searchAll(String searchTerm) {
        SearchResults results = new SearchResults(searchTerm, new ArrayList<DukeObject>(), this);
        results.addAll(findEvidences(searchTerm));
        results.addAll(findTreatments(searchTerm));
        return results;
    }

    /**
     * This function searches for treatments whose names contain the searchTerm.
     *
     * @param searchTerm the term to be searched for
     * @return ArrayList of the Treatments
     */
    public SearchResults findTreatmentsByName(String searchTerm) {
        ArrayList<Treatment> treatmentList = new ArrayList<>();
        String lowerSearchTerm = searchTerm.toLowerCase();
        for (Treatment entry : treatments) {
            if (entry.getName().toLowerCase().contains(lowerSearchTerm)) {
                treatmentList.add(entry);
            }
        }
        return new SearchResults(searchTerm, treatmentList, this);
    }

    /**
     * This function searches for evidences whose names contain the searchTerm.
     *
     * @param searchTerm the term to be searched for
     * @return ArrayList of the Evidences
     */
    public SearchResults findEvidencesByName(String searchTerm) {
        ArrayList<Evidence> evidenceList = new ArrayList<>();
        String lowerSearchTerm = searchTerm.toLowerCase();
        for (Evidence entry : evidences) {
            if (entry.getName().toLowerCase().contains(lowerSearchTerm)) {
                evidenceList.add(entry);
            }
        }
        return new SearchResults(searchTerm, evidenceList, this);
    }

    /**
     * This function searches for DukeData whose names contain the searchTerm.
     *
     * @param searchTerm String to be used to filter the DukeData
     * @return the list of DukeData
     */
    public SearchResults findByName(String searchTerm) {
        SearchResults results = new SearchResults(searchTerm, new ArrayList<DukeObject>(), this);
        results.addAll(findEvidencesByName(searchTerm));
        results.addAll(findTreatmentsByName(searchTerm));
        return results;
    }

    private void sortEvidences() {
        evidences.sort(((Comparator<Evidence>) (a1, a2) -> {
            int a1Priority = a1.getPriority();
            int a2Priority = a2.getPriority();
            if (a1Priority != 0 && a2Priority != 0) {
                if (a1Priority == a2Priority) {
                    return 0;
                }
                return (a1Priority < a2Priority) ? -1 : 1;
            } else if (a1Priority == 0 && a2Priority == 0) {
                return 0;
            } else {
                return (a1Priority == 0) ? 1 : -1;
            }
        }
            ));
    }

    private void sortTreatments() {
        treatments.sort(((Comparator<Treatment>) (a1, a2) -> {
                    int a1Priority = a1.getPriority();
                    int a2Priority = a2.getPriority();
                    if (a1Priority != 0 && a2Priority != 0) {
                        if (a1Priority == a2Priority) {
                            return 0;
                        }
                        return (a1Priority < a2Priority) ? -1 : 1;
                    } else if (a1Priority == 0 && a2Priority == 0) {
                        return 0;
                    } else {
                        return (a1Priority == 0) ? 1 : -1;
                    }
                }
            ).thenComparing(Treatment::getStatusIdx));
    }

    /**
     * This addNewEvidence function adds a new evidence to the evidence list.
     *
     * @param newEvidence the evidence to be added
     * @return the Evidence added
     */
    public Evidence addNewEvidence(Evidence newEvidence) throws DukeException {
        if (getEvidence(newEvidence.getName()) == null) {
            evidences.add(newEvidence);
            sortEvidences();
            return newEvidence;
        }
        throw new DukeException("I already have an Evidence named that");
    }

    /**
     * This deleteEvidence function deletes an evidence at the specified index from the evidence list.
     *
     * @param keyIdentifier name of the evidence
     * @return the deleted Evidence
     */
    public Evidence deleteEvidence(String keyIdentifier) throws DukeException {
        Evidence deletedEvidence = getEvidence(keyIdentifier);
        if (deletedEvidence != null) {
            evidences.remove(deletedEvidence);
            sortEvidences();
            return deletedEvidence;
        } else {
            throw new DukeException("I don't have an evidence named that!");
        }
    }

    /**
     * This getEvidence function returns the evidence with the specified name.
     *
     * @param keyIdentifier name of the evidence
     * @return the evidence specified by the index
     */
    public Evidence getEvidence(String keyIdentifier) {
        String lowerKey = keyIdentifier.toLowerCase();
        for (Evidence evidence : evidences) {
            String dataName = evidence.getName().toLowerCase();
            if (dataName.equals(lowerKey)) {
                return evidence;
            }
        }
        return null;
    }

    /**
     * Adds a new treatment to the treatment list.
     *
     * @param newTreatment the treatment to be added
     * @return the treatment added
     */
    public Treatment addNewTreatment(Treatment newTreatment) throws DukeException {
        if (getTreatment(newTreatment.getName()) == null) {
            treatments.add(newTreatment);
            sortTreatments();
            return newTreatment;
        }
        throw new DukeException("I already have a treatment named that.");
    }

    // TODO create parent reference in object

    /**
     * This function only allows medicine to be added if the patient is not allergic.
     * @param newMedicine the medicine to be added
     * @return the medicine added if successful
     * @throws DukeException if the patient is allergic or if the medicine is a duplicate entry
     */
    public Medicine addNewMedicine(Medicine newMedicine) throws DukeException {
        if (parent.isAllergic(newMedicine.getName())) {
            throw new DukeException("The patient is allergic to this medicine!");
        }
        addNewTreatment(newMedicine);
        return newMedicine;
    }

    /**
     * This deleteTreatment function deletes a treatment at the specified index from the treatment list.
     *
     * @param keyIdentifier name of the treatment
     * @return the deleted treatment
     */
    public Treatment deleteTreatment(String keyIdentifier) throws DukeException {
        Treatment deletedTreatment = getTreatment(keyIdentifier);
        if (deletedTreatment != null) {
            treatments.remove(deletedTreatment);
            sortEvidences();
            return deletedTreatment;
        }
        throw new DukeException("I don't have a treatment named that!");
    }

    /**
     * This getTreatment function returns the treatment from the treatment list at the specified index.
     *
     * @param keyIdentifier index of the treatment
     * @return the treatment specified by the index
     */
    public Treatment getTreatment(String keyIdentifier) throws DukeException {
        String lowerKey = keyIdentifier.toLowerCase();
        for (Treatment treatment : treatments) {
            String dataName = treatment.getName().toLowerCase();
            if (dataName.equals(lowerKey)) {
                return treatment;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder infoStrBuilder = new StringBuilder();
        infoStrBuilder.append("Description: ").append(this.description).append("\n");
        for (Evidence evidence: this.evidences) {
            infoStrBuilder.append(evidence.toString());
        }
        for (Treatment treatment : this.treatments) {
            infoStrBuilder.append(treatment.toString());
        }

        String title = "Impression details\n";
        return title + super.toString() + infoStrBuilder.toString();
    }

    @Override
    public String toReportString() {
        StringBuilder informationString;
        informationString = new StringBuilder("\n\tDescription of impression: " + this.description + "\n");
        for (Evidence evidence: this.evidences) {
            informationString.append("/t").append(evidence.toReportString());
        }

        for (Treatment treatment : this.treatments) {
            informationString.append("\t").append(treatment.toReportString());
        }
        return informationString + "\n\n";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Evidence> getEvidences() {
        return evidences;
    }

    public ArrayList<Treatment> getTreatments() {
        return treatments;
    }

    /**
     * This function initialises the parent of each evidence and treatment held to this Impression.
     */
    public void initChildren() {
        for (Evidence evidence : evidences) {
            evidence.setParent(this);
        }

        for (Treatment treatment : treatments) {
            treatment.setParent(this);
        }
    }

    /**
     * Computes the number of critical items in this impression: items with priority 1.
     *
     * @return The number of critical items in this impression.
     */
    public int getCriticalCount() {
        int count = 0;
        for (Treatment treatment : treatments) {
            if (treatment.getPriority() == 1) {
                ++count;
            }
        }
        for (Evidence evidence : evidences) {
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
     *
     * @return A string indicating the number of follow-up items in this impression.
     */
    public String getFollowUpCountStr() {
        int count = 0;
        for (Treatment treatment : treatments) {
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
     *
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

    public boolean equals(Impression impression) {
        return getName().equals(impression.getName()) && description.equals(impression.description)
                && getParent() == impression.getParent();
    }

    public ImpressionCard toCard() throws DukeFatalException {
        return new ImpressionCard(this);
    }

    public boolean contains(String searchTerm) {
        return super.contains(searchTerm) || description.toLowerCase().contains(searchTerm.toLowerCase());
    }

    @Override
    public Context toContext() {
        return Context.IMPRESSION;
    }

    /**
     * This functions returns the evidence at a specified index.
     * @param idx the zero based index
     * @return the evidence at the index
     * @throws DukeException if the index is out of bounds
     */
    public Evidence getEvidenceAtIdx(int idx) throws DukeException {
        if (idx >= 0 && idx < evidences.size()) {
            return evidences.get(idx);
        } else {
            throw new DukeException("I don't have an evidence at that index!");
        }
    }

    /**
     * This functions returns the treatment at a specified index.
     * @param idx the zero based index
     * @return the treatment at the index
     * @throws DukeException if the index is out of bounds
     */
    public Treatment getTreatmentAtIdx(int idx) throws DukeException {
        if (idx >= 0 && idx < treatments.size()) {
            return treatments.get(idx);
        } else {
            throw new DukeException("I don't have an treatment at that index!");
        }
    }
}
