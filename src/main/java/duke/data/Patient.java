package duke.data;

import duke.exception.DukeException;

import java.util.ArrayList;

public class Patient extends DukeObject {

    private String bedNo;
    private String allergies;
    private Impression priDiagnosis;
    private ArrayList<Impression> impressions;

    private int height;
    private int weight;
    private int age;
    private int number;
    private String address;
    private String history;

    /**
     * Represents the patient.
     * A Patient object corresponds to the biometric information of a patient,
     * patient details, medical history, the impressions the doctor has about a patient.
     * Attributes:
     * - name: the name of the patient
     * - bedNo: the bed number of the patient
     * - height: the height of the patient
     * - weight: the weight of the patient
     * - age: the age of the patient
     * - number: the contact details of a patient's NOK
     * - address: the residential address of a patient
     * - history: the medical history of a patient
     * - priDiagnosis: the chief complaint or most serious impression of a patient
     * - allergies: the Food and Drug allergies a patient has
     * - impression: the list of all impressions of a patient
     */
    public Patient(String name, String bedNo, String allergies) {
        super(name);
        this.bedNo = bedNo;
        this.allergies = allergies;
        this.priDiagnosis = null;
        this.impressions = new ArrayList<Impression>();

        this.height = 0;
        this.weight = 0;
        this.age = 0;
        this.number = 0;
        this.address = null;
        this.history = null;
    }

    /**
     * This discharge function runs the procedure to discharges a patient from the hospital.
     * Todo write the function
     */
    public void discharge() {
        // Todo
    }

    @Override
    public String toString() {
        // Todo
        return null;
    }

        /**
         * This addNewImpression function adds a new impression to the impressions list.
         * @param newImpression the impression to be added
         * @return the Impression newly added
         */

    public Impression addNewImpression(Impression newImpression) {
        this.impressions.add(newImpression);
        return newImpression;
    }

    /**
     * This deleteImpression function deletes an impression at the specified index
     * from the impressions list.
     * @param idx index of the impression
     * @return the Impression of the deleted Impression
     */
    public Impression deleteImpression(int idx) throws DukeException {
        if (idx >= 0 && idx < this.impressions.size()) {
            Impression imp = this.impressions.get(idx);
            this.impressions.remove(idx);
            return imp;
        } else {
            throw new DukeException("I don't have that entry in the list!");
        }
    }

    /**
     * This getImpression function returns the impression from the impressions list at the specified index.
     * @param idx index of the impression
     * @return Impression the impression specified by the index
     */
    public Impression getImpression(int idx) throws DukeException {
        if (idx >= 0 && idx < this.impressions.size()) {
            return this.impressions.get(idx);
        } else {
            throw new DukeException("I don't have that entry in the list!");
        }
    }

    /**
     * Sets the Primary Diagnosis of the patient specified by the index chosen.
     * @param idx index of the impression
     */
    public void setPriDiagnosis(int idx) throws DukeException {
        if (idx >= 0 && idx < this.impressions.size()) {
            this.priDiagnosis = this.impressions.get(idx);
            return;
        } else {
            throw new DukeException("I don't have that entry in the list!");
        }
    }

    /**
     * This function find returns a list of all DukeObjs
     * with names related to the patient containing the search term.
     * @param searchTerm String to be used to filter the DukeObj
     * @return the list of DukeObjs
     */
    public ArrayList<DukeObject> find(String searchTerm) throws DukeException {
        int i = 1;
        ArrayList<DukeObject> searchResult = new ArrayList<DukeObject>();
        for (Impression imp : this.impressions) {
            if (imp.getName().contains(searchTerm)) {
                searchResult.add(imp);
                ++i;
            }
        }
        if (i == 1) {
            throw new DukeException("Can't find any matching tasks!");
        } else {
            return searchResult;
        }
    }

    @Override
    public String toReportString() {
        // TODO make this look better
        String toOutput = "Name: " + getName() + "\nBed number: " + bedNo + "\nAllergies: "
                + allergies + "\nPrimary Diagnosis: " + priDiagnosis + "\nHeight: " + height + "\nWeight: " + weight + "\nAge: " + age + "\nContact Number: "
                + number + "\nAddress: " + address + "\nHistory " + history + "\nImpressions:\n\t";
        for(Impression imp: impressions){
            toOutput += imp.toReportString() + "\n\t";
        }
        return toOutput;
    }

    @Override
    public String toDisplayString() {
        // Todo
        return null;
    }

    public String getBedNo() {
        return bedNo;
    }

    public ArrayList<Impression> getImpressions() {
        return impressions;
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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getNumber() {
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
}
