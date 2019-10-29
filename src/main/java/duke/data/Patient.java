package duke.data;

import duke.exception.DukeException;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Patient extends DukeObject {
    private String bedNo;
    private String allergies;
    private Impression primaryDiagnosis;
    private HashMap<String, Impression> impressions;
    private Integer height;
    private Integer weight;
    private Integer age;
    private Integer number;
    private String address;
    private String history;

    private transient ObservableMap<String, Impression> observableImpressions;
    private transient ObservableMap<String, Object> attributes;

    /**
     * Represents the patient.
     * A Patient object corresponds to the biometric information of a patient,
     * patient details, medical history, the impressions the doctor has about a patient.
     * Attributes:
     * - primaryDiagnosis the chief complaint or most serious impression of a patient
     * - impression the list of all impressions of a patient
     *
     * @param name      the name of the patient
     * @param bedNo     the bed number of the patient
     * @param height    the height of the patient
     * @param weight    the weight of the patient
     * @param age       the age of the patient
     * @param number    the contact details of a patient's NOK
     * @param address   the residential address of a patient
     * @param history   the medical history of a patient
     * @param allergies the Food and Drug allergies a patient has
     */
    public Patient(String name, String bedNo, String allergies, Integer height, Integer weight,
                   Integer age, Integer number, String address, String history) {
        super(name, null);
        this.bedNo = bedNo;
        this.allergies = allergies;
        this.impressions = new HashMap<>();
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.number = number;
        this.address = address;
        this.history = history;
        this.primaryDiagnosis = null;

        initObservables();
    }

    /**
     * Attaches a listener to the impressions map.
     * This listener updates the {@code impressions} whenever the patient map is updated.
     */
    private void attachImpressionsListener() {
        observableImpressions.addListener((MapChangeListener<String, Impression>) change -> {
            if (observableImpressions.size() == 1) {
                primaryDiagnosis = observableImpressions.entrySet().iterator().next().getValue();
            }

            if (change.wasAdded()) {
                impressions.put(change.getKey(), change.getValueAdded());
            } else if (change.wasRemoved()) {
                impressions.remove(change.getKey(), change.getValueRemoved());
            }
        });
    }

    /**
     * This addNewImpression function adds a new impression to the impressions list.
     *
     * @param newImpression the impression to be added
     * @return the Impression newly added
     */

    public Impression addNewImpression(Impression newImpression) throws DukeException {
        if (observableImpressions.containsKey(newImpression.getName())) {
            throw new DukeException("Impression already exists!");
        }

        this.observableImpressions.put(newImpression.getName(), newImpression);
        return newImpression;
    }

    /**
     * This deleteImpression function deletes an impression at the specified index
     * from the impressions list.
     *
     * @param keyIdentifier name of the impression
     * @return the Impression of the deleted Impression
     */
    public Impression deleteImpression(String keyIdentifier) throws DukeException {
        if (this.observableImpressions.containsKey(keyIdentifier)) {
            Impression imp = this.observableImpressions.get(keyIdentifier);
            if (this.primaryDiagnosis.getName().equals(keyIdentifier)) {
                this.primaryDiagnosis = null;
            }
            this.observableImpressions.remove(keyIdentifier);
            return imp;
        } else {
            throw new DukeException("I don't have that entry in the list!");
        }
    }

    /**
     * This getImpression function returns the impression from the impressions list at the specified index.
     *
     * @param keyIdentifier index of the impression
     * @return Impression the impression specified by the index
     */
    public Impression getImpression(String keyIdentifier) throws DukeException {
        if (this.observableImpressions.containsKey(keyIdentifier)) {
            return this.observableImpressions.get(keyIdentifier);
        } else {
            throw new DukeException("I don't have that entry in the list!");
        }
    }

    /**
     * This function finds Impressions relevant to the searchTerm.
     *
     * @param searchTerm the search term
     * @return the list of impressions
     */
    public ArrayList<Impression> findImpressions(String searchTerm) {
        ArrayList<Impression> searchResult = new ArrayList<>();
        String lowerSearchTerm = searchTerm.toLowerCase();
        for (Impression impression : this.observableImpressions.values()) {
            if (impression.toString().toLowerCase().contains(lowerSearchTerm)) {
                searchResult.add(impression);
            }
        }
        return searchResult;
    }

    /**
     * This function finds Impressions whose names contain the searchTerm.
     *
     * @param searchTerm the search term
     * @return the list of impressions
     */
    public ArrayList<Impression> findImpressionsByName(String searchTerm) {
        ArrayList<Impression> searchResult = new ArrayList<>();
        String lowerSearchTerm = searchTerm.toLowerCase();
        for (Map.Entry<String, Impression> entry : this.observableImpressions.entrySet()) {
            if (entry.getKey().toLowerCase().contains(lowerSearchTerm)) {
                searchResult.add(entry.getValue());
            }
        }
        return searchResult;
    }

    /**
     * This function find returns a list of all DukeObjects.
     * with names related to the patient containing the search term.
     *
     * @param searchTerm String to be used to filter the DukeObj
     * @return the hashMap of DukeObjs
     */
    public ArrayList<DukeObject> find(String searchTerm) throws DukeException {
        ArrayList<Impression> filteredList = findImpressions(searchTerm);
        ArrayList<DukeObject> searchResult = new ArrayList<>();
        for (Impression imp : filteredList) {
            searchResult.add(imp);
            searchResult.addAll(imp.find(searchTerm));
        }
        return searchResult;
    }

    /**
     * This function appends an addition to the history of a Patient.
     *
     * @param addition the string to be added
     * @return the new history
     */
    public String appendHistory(String addition) {
        String newHistory;
        if (this.history != null && !this.history.equals("")) {
            newHistory = this.history + System.lineSeparator() + addition;
        } else {
            newHistory = addition;
        }

        setHistory(newHistory);
        return newHistory;
    }

    /**
     * This function find returns if a patient is allergic to an allergy.
     *
     * @param allergy String the possible allergy striped of spaces
     * @return boolean
     */
    public boolean isAllergic(String allergy) {
        return this.allergies.contains(allergy);
    }

    /**
     * Returns a string representation of a patient displaying all the values of the patient's attributes
     * in a reader-friendly format.
     *
     * @return string representation of patient
     */
    public String toString() {
        StringBuilder informationString;
        informationString = new StringBuilder("Personal details\n");
        informationString.append("Height: ").append(this.height).append("\n");
        informationString.append("Weight: ").append(this.weight).append("\n");
        informationString.append("Age: ").append(this.age).append("\n");
        informationString.append("Number: ").append(this.number).append("\n");
        informationString.append("Address: ").append(this.address).append("\n");
        informationString.append("History: ").append(this.history).append("\n");
        informationString.append("Registration details\n");
        informationString.append("Bed Number: ").append(this.bedNo).append("\n");
        informationString.append("Allergies: ").append(this.allergies).append("\n");
        informationString.append((primaryDiagnosis != null) ? "Primary Diagnosis: "
                + this.primaryDiagnosis.toString() + "\n" : "");
        for (Map.Entry mapElement : this.impressions.entrySet()) {
            Impression imp = (Impression) mapElement.getValue();
            informationString.append(imp.toString());
        }
        return super.toString() + informationString + "\n";
    }

    @Override
    public String toReportString() {
        StringBuilder informationString;
        informationString = new StringBuilder("\tName of patient: " + getName() + "\n");
        informationString.append("\tBed Number: ").append(this.bedNo).append("\n");
        if (this.height != null) {
            informationString.append("\tHeight: ").append(this.height).append("\n");
        }
        if (this.weight != null) {
            informationString.append("\tWeight: ").append(this.weight).append("\n");
        }
        if (this.allergies != null) {
            informationString.append("\tAllergies: ").append(this.allergies).append("\n");
        }
        if (this.age != null) {
            informationString.append("\tAge: ").append(this.age).append("\n");
        }
        if (this.number != null) {
            informationString.append("\tNumber: ").append(this.number).append("\n");
        }
        if (this.address != null) {
            informationString.append("\tAddress: ").append(this.address).append("\n");
        }
        if (this.history != null) {
            informationString.append("\tHistory: ").append(this.history).append("\n");
        }
        if (this.primaryDiagnosis != null) {
            informationString.append("\tRegistration details:\n");
            informationString.append("\tAllergies: ").append(this.allergies).append("\n");
            informationString.append("\tPrimary Diagnosis: ").append(this.primaryDiagnosis.getDescription())
                    .append("\n");
            informationString.append("\nData about doctors impression of the patient and associated"
                    + " treatments and evidences;");
            for (Map.Entry mapElement : this.impressions.entrySet()) {
                Impression imp = (Impression) mapElement.getValue();
                informationString.append(imp.toReportString());
            }
        }
        return informationString + "\n\n";
    }

    public String getBedNo() {
        return bedNo;
    }

    public ObservableMap<String, Impression> getImpressionsObservableMap() {
        return observableImpressions;
    }

    public void initObservables() {
        initObservableImpressions();
        initObservableAttributes();
    }

    private void initObservableAttributes() {
        attributes = FXCollections.observableMap(new HashMap<>());
        updateAttributes();
    }

    private void updateObservableAttributes() {
        attributes.put("name", getName());
        attributes.put("bedNo", getBedNo());
        attributes.put("age", getAge());
        attributes.put("height", getHeight());
        attributes.put("weight", getWeight());
        attributes.put("number", getNumber());
        attributes.put("address", getAddress());
        attributes.put("history", getHistory());
        attributes.put("allergies", getAllergies());
        attributes.put("diagnosis", getPrimaryDiagnosis());
        attributes.put("impressions", getImpressionsObservableMap().size());
    }

    private void initObservableImpressions() {
        this.observableImpressions = FXCollections.observableMap(impressions);
        attachImpressionsListener();
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public Impression getPrimaryDiagnosis() {
        return primaryDiagnosis;
    }

    /**
     * Sets the Primary Diagnosis of the patient specified by the index chosen.
     *
     * @param keyIdentifier index of the impression
     */
    public void setPrimaryDiagnosis(String keyIdentifier) throws DukeException {
        if (this.observableImpressions.containsKey(keyIdentifier)) {
            primaryDiagnosis = this.observableImpressions.get(keyIdentifier);
        } else {
            throw new DukeException("I don't have that entry in the list!");
        }
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public ObservableMap<String, Object> getAttributes() {
        return attributes;
    }

    public void updateAttributes() {
        updateObservableAttributes();
    }

    public void deletePriDiagnose() throws DukeException {
        this.impressions.remove(primaryDiagnosis.getName());
        this.primaryDiagnosis = null;
    }

    /**
     * Computes the number of critical items for this patient: DukeData objects with priority 1, across all impressions.
     * @return The number of critical DukeData items for this patient.
     */
    public String getCriticalCountStr() {
        int count = 0;
        for (Impression imp : impressions.values()) {
            count += imp.getCriticalCount();
        }

        if (count == 0) {
            return "No critical issues";
        } else if (count == 1) {
            return "1 critical issue";
        } else {
            return count + "critical issues";
        }
    }

    public boolean equals(Patient other) {
        return attributes.equals(other.attributes) && primaryDiagnosis == other.primaryDiagnosis
                && observableImpressions.equals(other.getImpressionsObservableMap());
    }
}
