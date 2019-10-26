package duke.data;

import duke.exception.DukeException;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Patient extends DukeObject {
    private String bedNo;
    private String allergies;
    private Impression priDiagnosis;
    private HashMap<String, Impression> impressions;
    private Integer height;
    private Integer weight;
    private Integer age;
    private Integer number;
    private String address;
    private String history;

    private transient ObservableMap<String, Impression> observableImpressions;
    private transient PropertyChangeSupport pcs;

    /**
     * Represents the patient.
     * A Patient object corresponds to the biometric information of a patient,
     * patient details, medical history, the impressions the doctor has about a patient.
     * Attributes:
     * - priDiagnosis the chief complaint or most serious impression of a patient
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
        super(name);
        this.bedNo = bedNo;
        this.allergies = allergies;
        this.impressions = new HashMap<>();
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.number = number;
        this.address = address;
        this.history = history;
        this.priDiagnosis = null;

        initObservableImpressions();
        this.pcs = new PropertyChangeSupport(this);
    }

    /**
     * Represents the patient.
     * A Patient object corresponds to the biometric information of a patient,
     * patient details, medical history, the impressions the doctor has about a patient.
     * Attributes:
     *
     * @param name      the name of the patient
     * @param bedNo     the bed number of the patient
     * @param allergies the Food and Drug allergies a patient has
     */
    public Patient(String name, String bedNo, String allergies) {
        super(name);
        this.bedNo = bedNo;
        this.allergies = allergies;
        this.impressions = new HashMap<>();
        this.height = null;
        this.weight = null;
        this.age = null;
        this.number = null;
        this.address = null;
        this.history = null;
        this.priDiagnosis = null;

        initObservableImpressions();
        this.pcs = new PropertyChangeSupport(this);
    }

    /**
     * Attaches a listener to the impressions map.
     * This listener updates the {@code impressionHashMap} whenever the patient map is updated.
     */
    private void attachImpressionsListener() {
        observableImpressions.addListener((MapChangeListener<String, Impression>) change -> {
            if (change.wasAdded()) {
                impressions.put(change.getKey(), change.getValueAdded());
            } else if (change.wasRemoved()) {
                impressions.remove(change.getKey(), change.getValueRemoved());
            }
        });
    }

    /**
     * This discharge function runs the procedure to discharges a patient from the hospital.
     * Todo write the function
     */
    public void discharge() {
        // Todo
    }

    /**
     * This addNewImpression function adds a new impression to the impressions list.
     *
     * @param newImpression the impression to be added
     * @return the Impression newly added
     */

    public Impression addNewImpression(Impression newImpression) {
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
     * This function finds relavent Impressions to the searchTerm.
     *
     * @param searchTerm the serach term
     * @return the list of impressions
     */
    public ArrayList<Impression> findImpression(String searchTerm) {
        ArrayList<Impression> searchResult = new ArrayList<>();
        for (Map.Entry<String, Impression> mapElement : this.observableImpressions.entrySet()) {
            Impression value = mapElement.getValue();
            if (value.toString().contains(searchTerm)) {
                searchResult.add(value);
            }
        }
        return searchResult;
    }

    /**
     * This function find returns a list of all DukeObjs
     * with names related to the patient containing the search term.
     *
     * @param searchTerm String to be used to filter the DukeObj
     * @return the hashMap of DukeObjs
     */
    public ArrayList<DukeObject> find(String searchTerm) throws DukeException {
        ArrayList<Impression> filteredList = findImpression(searchTerm);
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
     * Adds a listener to listen for changes in {@code Patient} attributes.
     *
     * @param pcl PropertyChangeListener object.
     */
    public void addListener(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
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
     * in a reader-friendly format.      *
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
        informationString.append((priDiagnosis != null) ? "Primary Diagnosis: " + this.priDiagnosis.toString() + "\n" : "");
        for (Map.Entry mapElement : this.impressions.entrySet()) {
            Impression imp = (Impression) mapElement.getValue();
            informationString.append(imp.toString());
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
        if (this.priDiagnosis != null) {
            informationString.append("\tRegistration details:\n");
            informationString.append("\tAllergies: ").append(this.allergies).append("\n");
            informationString.append("\tPrimary Diagnosis: ").append(this.priDiagnosis.getDescription()).append("\n");
            informationString.append("\nData about doctors impression of the patient and associated" + " treatments and evidences;");
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
        this.pcs = new PropertyChangeSupport(this);
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

    public Impression getPriDiagnosis() {
        return priDiagnosis;
    }

    /**
     * Sets the Primary Diagnosis of the patient specified by the index chosen.
     *
     * @param keyIdentifier index of the impression
     */
    public void setPriDiagnosis(String keyIdentifier) throws DukeException {
        if (this.observableImpressions.containsKey(keyIdentifier)) {
            Impression oldPriDiagnosis = priDiagnosis;
            priDiagnosis = this.observableImpressions.get(keyIdentifier);
            pcs.firePropertyChange("Primary Diagnosis", oldPriDiagnosis, priDiagnosis);
        } else {
            throw new DukeException("I don't have that entry in the impressions list!");
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

    /**
     * Set medical history of {@code Patient}.
     *
     * @param history New medical history.
     */
    public void setHistory(String history) {
        String oldHistory = this.history;
        this.history = history;
        pcs.firePropertyChange("History", oldHistory, history);
    }
}
