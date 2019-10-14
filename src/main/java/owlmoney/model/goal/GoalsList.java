package owlmoney.model.goal;

import owlmoney.ui.Ui;

import java.util.ArrayList;

public class GoalsList {
    private ArrayList<Goals> goalList;

    public GoalsList() {
        goalList = new ArrayList<>();
    }

    public void listGoals(Ui ui) {
        if(goalList.size() <= 0) {
            ui.printError("Goals not found!");
        } else {
            for (int i = 0; i < goalList.size(); i++) {
                ui.printMessage( (i + 1) + ":\n" + goalList.get(i).getGoalsDetails());
            }
        }
    }

    public void addToGoals(Goals goals, Ui ui) {
        goalList.add(goals);
        ui.printMessage("Added goals: " + goals.getGoalsDetails());
    }

    public void deleteFromGoalList(String goalName, Ui ui) {
        if (goalList.size() <= 0) {
            ui.printError("There are no goals set in your profile");
        } else if (goalList.size() == 1) {
            ui.printError("There must be at least 1 goals");
        } else {
            for (int i = 0; i < goalList.size(); i++) {
                if (goalList.get(i).getGoalsName().equals(goalName)) {
                    ui.printMessage("Removing " + goalList.get(i).getGoalsName());
                    goalList.remove(i);
                    break;
                }
            }
        }
    }

    public void editGoals(String goalName, String new_name, double amount, String date, Ui ui) {

    }
}
