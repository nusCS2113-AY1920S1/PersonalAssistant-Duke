package duke.data;

import duke.exception.DukeException;

import java.util.ArrayList;

public class Patient extends DukeObject {
    private String bedNo;
    private String allergies;
    private Impression primaryDiagnosis;
    private ArrayList<Impression> impressions;
    private Integer height;
    private Integer weight;
    private Integer age;
    private Integer number;
    private String address;
    private String history;

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
        this.impressions = new ArrayList<>();
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.number = number;
        this.address = address;
        this.history = history;
        this.primaryDiagnosis = null;
    }

    private Boolean isDuplicate(Impression newImpression) {
        String newLower = newImpression.getName().toLowerCase();
        for (Impression oldImpression : impressions) {
            String oldLower = oldImpression.getName().toLowerCase();
            if (oldLower.equals(newLower)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This addNewImpression function adds a new impression to the impressions list.
     *
     * @param newImpression the impression to be added
     * @return the Impression newly added
     */

    public Impression addNewImpression(Impression newImpression) throws DukeException {
        if (isDuplicate(newImpression)) {
            throw new DukeException("Impression already exists!");
        }
        this.impressions.add(newImpression);
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
        Impression deletedImpression = getImpression(keyIdentifier);
        if (deletedImpression != null) {
            impressions.remove(deletedImpression);
            return deletedImpression;
        }
        throw new DukeException("I don't have that entry in the list!");
    }

    /**
     * This getImpression function returns the impression from the impressions list at the specified index.
     *
     * @param keyIdentifier index of the impression
     * @return Impression the impression specified by the index
     */
    public Impression getImpression(String keyIdentifier) throws DukeException {
        String id = keyIdentifier.toLowerCase();
        for (Impression imp : impressions) {
            String impName = imp.getName().toLowerCase();
            if (id.equals(impName)) {
                return imp;
            }
        }
        throw new DukeException("I don't have that entry in the list!");
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
        for (Impression impression : impressions) {
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
        ArrayList<Impression> resultList = new ArrayList<>();
        String lowerSearchTerm = searchTerm.toLowerCase();
        for (Impression entry : impressions) {
            if (entry.getName().toLowerCase().contains(lowerSearchTerm)) {
                resultList.add(entry);
            }
        }
        return resultList;
    }

    /**
     * This function finds critical items in this patient whose names contain the searchTerm.
     *
     * @param searchTerm the search term
     * @return the list of critical items
     */
    public ArrayList<DukeData> findCriticalsByName(String searchTerm) {
        ArrayList<DukeData> resultList = new ArrayList<>();
        String lowerSearchTerm = searchTerm.toLowerCase();
        for (Impression entry : impressions) {
            for (Evidence evidenceEntry : entry.getEvidences()) {
                if (evidenceEntry.getName().toLowerCase().contains(lowerSearchTerm)
                        && evidenceEntry.getPriority() == 1) {
                    resultList.add(evidenceEntry);
                }
            }
            for (Treatment treatmentEntry : entry.getTreatments()) {
                if (treatmentEntry.getName().toLowerCase().contains(lowerSearchTerm)
                        && treatmentEntry.getPriority() == 1) {
                    resultList.add(treatmentEntry);
                }
            }
        }
        return resultList;
    }

    /**
     * This function finds follow-up items (Investigations for now) whose names contain the searchTerm.
     *
     * @param searchTerm the search term
     * @return the list of follow-up items
     */
    public ArrayList<DukeData> findFollowUpsByName(String searchTerm) {
        ArrayList<DukeData> resultList = new ArrayList<>();
        String lowerSearchTerm = searchTerm.toLowerCase();
        for (Impression imp : impressions) {
            for (Treatment treatmentEntry : imp.getTreatments()) {
                if (treatmentEntry.getName().toLowerCase().contains(lowerSearchTerm)
                        && treatmentEntry.getClass() == Investigation.class) {
                    resultList.add(treatmentEntry);
                }
            }
        }
        return resultList;
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
        for (Impression imp : this.impressions) {
            informationString.append(imp.toString());
        }
        return super.toString() + informationString + "\n";
    }

    @Override
    public String toReportString() {
        StringBuilder informationString;
        informationString = new StringBuilder("\tName of patient: " + getName() + "\n");
        informationString.append("\tBed Number: ").append(this.bedNo).append("\n");
        if (this.height != -1) {
            informationString.append("\tHeight: ").append(this.height).append("\n");
        }
        if (this.weight != -1) {
            informationString.append("\tWeight: ").append(this.weight).append("\n");
        }
        if (!this.allergies.equals("")) {
            informationString.append("\tAllergies: ").append(this.allergies).append("\n");
        }
        if (this.age != -1) {
            informationString.append("\tAge: ").append(this.age).append("\n");
        }
        if (this.number != -1) {
            informationString.append("\tNumber: ").append(this.number).append("\n");
        }
        if (!this.address.equals("")) {
            informationString.append("\tAddress: ").append(this.address).append("\n");
        }
        if (!this.history.equals("")) {
            informationString.append("\tHistory: ").append(this.history).append("\n");
        }
        if (this.primaryDiagnosis != null) {
            informationString.append("\tRegistration details:\n");
            informationString.append("\tAllergies: ").append(this.allergies).append("\n");
            informationString.append("\tPrimary Diagnosis: ").append(this.primaryDiagnosis.getDescription())
                    .append("\n");
            informationString.append("\nData about doctors impression of the patient and associated"
                    + " treatments and evidences;");
            for (Impression imp : this.impressions) {
                informationString.append(imp.toString());
            }
        }
        return informationString + "\n\n";
    }

    public String getBedNo() {
        return bedNo;
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
        Impression primaryImpression = getImpression(keyIdentifier);
        if (primaryImpression != null) {
            impressions.remove(primaryImpression);
            impressions.add(0, primaryImpression);
            primaryDiagnosis = primaryImpression;
        }
        throw new DukeException("I don't have that entry in the list!");
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

    public void deletePriDiagnose() throws DukeException {
        this.impressions.remove(primaryDiagnosis);
        this.primaryDiagnosis = null;
    }

    /**
     * Computes the number of critical items for this patient: DukeData objects with priority 1, across all impressions.
     * @return The number of critical DukeData items for this patient.
     */
    public String getCriticalCountStr() {
        int count = 0;
        for (Impression imp : impressions) {
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
        // todo
        return this.getName().equals(other.getName());
    }

    public ArrayList<Impression> getImpressions() {
        return impressions;
    }

}
