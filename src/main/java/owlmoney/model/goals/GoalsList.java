package owlmoney.model.goals;

import owlmoney.model.goals.exception.GoalsException;
import owlmoney.ui.Ui;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GoalsList {
    private ArrayList<Goals> goalList;

    public GoalsList() {
        goalList = new ArrayList<>();
    }

    public void listGoals(Ui ui) {
        if(goalList.size() <= 0) {
            ui.printError("There are no goals set");
        } else {
            for (int i = 0; i < goalList.size(); i++) {
                ui.printMessage( (i + 1) + ":\n" + goalList.get(i).getGoalsDetails());
            }
        }
    }

    public void addToGoals(Goals goals, Ui ui) {
        goalList.add(goals);
        ui.printMessage("Added goals: \n" + goals.getGoalsDetails());
    }

    public void deleteFromGoalList(String goalName, Ui ui) {
        if (goalList.size() <= 0) {
            ui.printError("There are no goals set in your profile");
        } else if (goalList.size() == 1) {
            ui.printError("There must be at least 1 goals left");
        } else {
            for (int i = 0; i < goalList.size(); i++) {
                if (goalList.get(i).getGoalsName().equals(goalName)) {
                    ui.printMessage("Removing " + goalList.get(i).getGoalsName());
                    goalList.remove(i);
                    ui.printMessage("Removed!");
                    break;
                }
            }
        }
    }

    public void compareGoals (Goals currentGoals, String newGoalName) throws GoalsException {
        for (int i = 0; i < goalList.size(); i++) {
            if (goalList.get(i).getGoalsName().equals(newGoalName) && !goalList.get(i).equals(currentGoals)) {
                throw new GoalsException("There is already a goal with the name " + newGoalName);
            }
        }
    }
    public void editGoals(String goalName, String newName, String amount, String date, Ui ui) throws GoalsException {
        for (int i = 0; i < goalList.size(); i++) {
            if (goalList.get(i).getGoalsName().equals(goalName)) {
                if (!(newName.isEmpty() || newName.isBlank())) {
                    compareGoals(goalList.get(i), newName);
                    goalList.get(i).setGoalsName(newName);
                }
                if (!(amount.isBlank() || amount.isEmpty())) {
                    goalList.get(i).setGoalsAmount(Double.parseDouble(amount));
                }
                if (!(date.isBlank() || date.isEmpty())) {
                    DateFormat temp = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        goalList.get(i).setGoalsDate(temp.parse(date));
                    } catch (ParseException e) {
                        //check handled in ParseEditGoals
                        throw new GoalsException(e.toString());
                    }
                }
                ui.printMessage("New details of goals changed: ");
                ui.printMessage(goalList.get(i).getGoalsDetails());
                return;
            }
        }
        throw new GoalsException("There are no goals with the name: " + goalName);
    }
}
