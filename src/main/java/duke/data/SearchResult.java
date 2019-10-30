package duke.data;

import java.util.ArrayList;

public class SearchResult extends DukeObject {

    private ArrayList<DukeObject> searchList;
    private ArrayList<Patient> patientList;
    private ArrayList<Impression> impressionList;
    private ArrayList<Evidence> evidenceList;
    private ArrayList<Treatment> treatmentList;

    /**
     * Abstraction of the evidence or treatment data of a patient.
     * A DukeData object corresponds to the evidence or treatment a doctor has,
     * the impression that led to that data as well as an integer
     * between 1-4 representing the priority or significance of the investigation.
     * Attributes:
     * @param name the list of DukeObjects
     * @param searchList the impression object the data is tagged to
     */
    public SearchResult(String name, ArrayList<DukeObject> searchList, DukeObject parent) {
        super(name, parent);
        this.searchList = searchList;
        this.patientList = new ArrayList<>();
        this.impressionList = new ArrayList<>();
        this.evidenceList = new ArrayList<>();
        this.treatmentList = new ArrayList<>();
        initList();
    }

    public ArrayList<DukeObject> getSearchList() {
        return searchList;
    }

    public ArrayList<Patient> getPatientList() {
        return patientList;
    }

    public ArrayList<Impression> getImpressionList() {
        return impressionList;
    }

    public ArrayList<Evidence> getEvidenceList() {
        return evidenceList;
    }

    public ArrayList<Treatment> getTreatmentList() {
        return treatmentList;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public String toReportString() {
        return "";
    }

    private void initList() {
        for (DukeObject object : searchList) {
            if (object instanceof Patient) {
                patientList.add((Patient) object);
            } else if (object instanceof Impression) {
                impressionList.add((Impression) object);
            } else if (object instanceof Observation || object instanceof Result) {
                evidenceList.add((Evidence) object);
            } else if (object instanceof Investigation || object instanceof Medicine || object instanceof Plan) {
                // TODO: index
                treatmentList.add((Treatment) object);
            }
        }
    }
}
