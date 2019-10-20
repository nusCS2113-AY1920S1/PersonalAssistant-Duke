package gazeeebo.notes;

import java.util.ArrayList;

public class Module {
    public String name;
    public ArrayList<Assessment> assessments;
    public ArrayList<String> miscellaneousInfo;

    public Module(String name) {
        this.name = name;
        this.assessments = new ArrayList<>();
        this.miscellaneousInfo = new ArrayList<>();
    }

    public void editName(String editedName) {
        name = editedName;
    }

    public void addAssessment(String name, String percentage) {
        assessments.add(new Assessment(name, percentage));
    }

    public void

}
