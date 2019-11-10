package duke.model.task.recipetasks;

import java.util.ArrayList;

import static duke.common.Messages.DISPLAYED_INDEX_OFFSET;

public class PrepSteps {


    ArrayList<String> prepStepsList;

    public PrepSteps() {
        this.prepStepsList = new ArrayList<String>();
    }

    public PrepSteps(String prepStepsFromStorage) {
        this.prepStepsList = new ArrayList<String>();
        parseStepsFromStorage(prepStepsFromStorage);
    }

    public void parseStepsFromStorage(String prepStepsFromStorage) {
        String[] split = prepStepsFromStorage.split("/");
        for (int i = 0; i < split.length; i++) {
            this.prepStepsList.add(split[i]);
        }
    }

    public void insertStep(String position, String prepStep) {
        prepStepsList.add(Integer.parseInt(position) - DISPLAYED_INDEX_OFFSET, prepStep);
    }

    public void appendStep(String prepStep) {
        prepStepsList.add(prepStep);
    }

    public String deleteStep(String position) {
        String deletedPrepStep = prepStepsList.get(Integer.parseInt(position) - DISPLAYED_INDEX_OFFSET);
        prepStepsList.remove(Integer.parseInt(position) - DISPLAYED_INDEX_OFFSET);
        return deletedPrepStep;
    }

    public void clearSteps() {
        prepStepsList.clear();
    }

    public int getSize() {
        return prepStepsList.size();
    }

    public String toViewString() {
        String joinedString = "";
        if (prepStepsList.isEmpty()) {
            joinedString = "No preparation steps provided yet.\n";
        } else {
            int i = 0;
            prepStepsList.remove("No preparation steps provided yet.");
            if (prepStepsList.isEmpty()) {
                joinedString = "No preparation steps provided yet.\n";
            } else {
                for (String step : prepStepsList) {
                    System.out.println(step + ".....");
                    prepStepsList.remove("No preparation steps provided yet.");
                    ++i;
                    joinedString = joinedString.concat("    " + i + ". " + step + "\n");
                    // joinedString = joinedString.concat(String.join("\n", Integer.toString(i) + ". " + ingredient.toString()));
                }
            }
        }
        return joinedString;
    }

    public String toSaveString() {
        String joinedString = "";
        if (prepStepsList.isEmpty()) {
            joinedString = "No preparation steps provided yet.";
        } else {
            for (String step : prepStepsList) {
                if (joinedString.isEmpty()) {
                    joinedString = joinedString.concat(step);
                } else {
                    joinedString = joinedString.concat(" / ");
                    joinedString = joinedString.concat(step);
                }
            }
        }
        return joinedString;
    }
}
