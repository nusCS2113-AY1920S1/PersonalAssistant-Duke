package duke.task;

import java.util.ArrayList;

public class StandardTask extends Task {

    ArrayList<Integer> patientIdList;

    public StandardTask(String description) {
        super(description);
        this.patientIdList = new ArrayList<Integer>(); //read doc + upload task's list of patients, or make new list
    }

    @Override
    public String toString() {
        return this.getDescription();
    }


}
