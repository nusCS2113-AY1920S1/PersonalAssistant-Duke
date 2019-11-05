package owlmoney.model.goals;

import owlmoney.model.bank.Bank;
import owlmoney.model.goals.exception.GoalsException;
import owlmoney.storage.Storage;
import owlmoney.ui.Ui;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Contains the list of goals in the profile.
 */
public class GoalsList {
    private ArrayList<Goals> goalList;
    private static final int ONE_INDEX = 1;
    private static final boolean ISMULTIPLE = true;
    private static final boolean ISSINGLE = false;
    private static final int ISZERO = 0;
    private Storage storage;
    private static final String PROFILE_GOAL_LIST_FILE_NAME = "profile_goallist.csv";
    private static final String UNTIEDBANK = "-NOT TIED-";

    /**
     * Creates a instance of GoalsList that contains an arrayList of Goals.
     *
     * @param storage for importing and exporting purposes.
     */
    public GoalsList(Storage storage) {
        goalList = new ArrayList<Goals>();
        this.storage = storage;
    }

    /**
     * Limits the number of goals that user can have when setting goals.
     *
     * @throws GoalsException If number of goals exceeds 20.
     */
    private void checkNumGoals() throws GoalsException {
        if (goalList.size() >= 20) {
            throw new GoalsException("You've reached the limit of 20 goals!");
        }
    }

    /**
     * Lists all goals in GoalsList.
     *
     * @param ui required for printing.
     */
    public void listGoals(Ui ui) {
        if (goalList.size() <= ISZERO) {
            ui.printError("There are no goals set");
        } else {
            ui.printGoalHeader();
            for (int i = ISZERO; i < goalList.size(); i++) {
                printOneGoal((i + ONE_INDEX), goalList.get(i), ISMULTIPLE, ui);
            }
            ui.printGoalDivider();
            try {
                exportGoalList();
            } catch (IOException e) {
                ui.printError("Error trying to save your goals to disk. Your data is"
                        + " at risk, but we will try again, feel free to continue using the program.");
            }
        }
    }

    /**
     * Adds an instance of goals into GoalsList.
     *
     * @param goals a new goal object.
     * @param ui    required for printing.
     * @throws GoalsException If a duplicate goal name is found.
     */
    public void addToGoals(Goals goals, Ui ui) throws GoalsException {
        if (goalExists(goals.getGoalsName())) {
            throw new GoalsException("There is already a goal with the same name " + goals.getGoalsName());
        }
        if (goals.getRawStatus()) {
            throw new GoalsException("You cannot add a goal that is already achieved!");
        }
        checkNumGoals();
        goalList.add(goals);
        try {
            exportGoalList();
        } catch (IOException e) {
            ui.printError("Error trying to save your goals to disk. Your data is"
                    + " at risk, but we will try again, feel free to continue using the program.");
        }
        ui.printMessage("Added a new goal with the below details: ");
        printOneGoal(ONE_INDEX, goals, ISSINGLE, ui);
    }

    /**
     * Deletes a goal from GoalsList.
     *
     * @param goalName The name of the goal.
     * @param ui       required for printing.
     * @throws GoalsException If trying to delete from empty GoalsList
     */
    public void deleteFromGoalList(String goalName, Ui ui) throws GoalsException {
        if (goalList.size() <= ISZERO) {
            throw new GoalsException("There are no goals set!");
        } else {
            String capitalGoalName = goalName.toUpperCase();
            for (int i = ISZERO; i < goalList.size(); i++) {
                Goals currentGoal = goalList.get(i);
                String currentGoalName = currentGoal.getGoalsName();
                String capitalCurrentGoalName = currentGoalName.toUpperCase();
                if (capitalGoalName.equals(capitalCurrentGoalName)) {
                    Goals temp = goalList.get(i);
                    goalList.remove(i);
                    ui.printMessage("Details of the goal being removed:");
                    printOneGoal(ONE_INDEX, temp, ISSINGLE, ui);
                    try {
                        exportGoalList();
                    } catch (IOException e) {
                        ui.printError("Error trying to save your goals to disk. Your data is"
                                + " at risk, but we will try again, feel free to continue using the program.");
                    }
                    return;
                }
            }
            throw new GoalsException("There are no goals with the name: " + goalName);
        }
    }

    /**
     * Compares if the current goal name is the same as the new intended goal name and if there is already a goal that
     * exists.
     *
     * @param currentGoal Current Goal Name of the Goal.
     * @param newGoalName New Goal Name that user intends to change.
     * @throws GoalsException If there's a goal of the same name.
     */
    private void compareGoals(Goals currentGoal, String newGoalName) throws GoalsException {
        String currentGoalName = currentGoal.getGoalsName();
        String capitalCurrentGoalName = currentGoalName.toUpperCase();
        for (int i = ISZERO; i < goalList.size(); i++) {
            Goals checkGoal = goalList.get(i);
            String checkGoalName = checkGoal.getGoalsName();
            String capitalCheckGoalName = checkGoalName.toUpperCase();
            if (capitalCheckGoalName.equals(capitalCurrentGoalName) && !checkGoal.equals(currentGoal)) {
                throw new GoalsException("There is already a goal with the same name: " + newGoalName);
            }
        }
    }

    private boolean compareGoalSavingAcc(Goals currentGoal, Bank savingAcc) {
        if (!currentGoal.getSavingAccount().equals("-NOT TIED-")
                && currentGoal.getSavingAccount().equals(savingAcc.getAccountName())) {
            return true;
        } else return false;
    }

    /**
     * Checks if a goal exists when wanting to add a new goal.
     *
     * @param goalName refers to the about-to-add goal name.
     * @return True if it exists and False if it doesn't.
     */
    private boolean goalExists(String goalName) {
        String capitalGoalName = goalName.toUpperCase();
        for (int i = ISZERO; i < goalList.size(); i++) {
            Goals currentGoal = goalList.get(i);
            String currentGoalName = currentGoal.getGoalsName();
            String capitalCurrentGoalName = currentGoalName.toUpperCase();
            if (capitalGoalName.equals(capitalCurrentGoalName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Edits an instance of the goal.
     *
     * @param goalName To identify and retrieve the goal details.
     * @param amount   New amount of the goal.
     * @param date     New date to reach the goal.
     * @param newName  New name to identify the goal.
     * @param ui       required for printing.
     * @throws GoalsException If date is not in correct format, or changing to a name that already exists,
     *                        or no goal with the goalName.
     */
    public void editGoals(String goalName, String amount, Date date, String newName, Bank savingAcc,
                          boolean markDone, Ui ui) throws GoalsException {
        String capitalGoalName = goalName.toUpperCase();
        for (int i = ISZERO; i < goalList.size(); i++) {
            Goals currentGoal = goalList.get(i);
            String currentGoalName = currentGoal.getGoalsName();
            String capitalCurrentGoalName = currentGoalName.toUpperCase();
            if (capitalGoalName.equals(capitalCurrentGoalName)) {
                if (currentGoal.getRawStatus()) {
                    throw new GoalsException("Sorry, you cannot edit a goal that's already achieved! "
                            + "Try creating a new goal instead!");
                }
                if (!(newName.isEmpty() || newName.isBlank())) {
                    compareGoals(currentGoal, newName);
                    currentGoal.setGoalsName(newName);
                }
                if (!(amount.isBlank() || amount.isEmpty())) {
                    currentGoal.setGoalsAmount(Double.parseDouble(amount));
                }
                if (date != null) {
                    currentGoal.setGoalsDate(date);
                }
                if (savingAcc != null) {
                    if (compareGoalSavingAcc(currentGoal, savingAcc)) {
                        currentGoal.setSavingAccount(null);
                    } else if (savingAcc.getCurrentAmount() < currentGoal.getGoalsAmount()) {
                        currentGoal.setSavingAccount(savingAcc);
                    } else {
                        throw new GoalsException("You cannot add a goal that is already achieved!");
                    }
                }
                if (markDone) {
                    if (currentGoal.getRawStatus()) {
                        throw new GoalsException("Cannot change status of a goal that is already achieved!");
                    }
                    if (currentGoal.savingAccNotTied()) {
                        currentGoal.markDone();
                    } else {
                        throw new GoalsException("You cannot mark a goal that is linked to a saving account!");
                    }
                }
                try {
                    exportGoalList();
                } catch (IOException e) {
                    ui.printError("Error trying to save your goals to disk. Your data is"
                            + " at risk, but we will try again, feel free to continue using the program.");
                }
                ui.printMessage("New details of goals changed: ");
                printOneGoal(ONE_INDEX, goalList.get(i), ISSINGLE, ui);
                return;
            }
        }
        throw new GoalsException("There are no goals with the name: " + goalName);
    }

    /**
     * Prints goal details.
     *
     * @param num                Represents the numbering of the goal.
     * @param goal               The goal object to be printed.
     * @param isMultiplePrinting Represents whether the function will be called for printing once or multiple
     *                           time
     * @param ui                 The object use for printing.
     */
    private void printOneGoal(int num, Goals goal, boolean isMultiplePrinting, Ui ui) {
        if (!isMultiplePrinting) {
            ui.printGoalHeader();
        }
        if (!goal.getSavingAccount().isBlank()) {
            goal.isDone(Double.parseDouble(goal.getRemainingAmount()));
        }
        ui.printGoal(num, goal.getGoalsName(), "$"
                        + new DecimalFormat("0.00").format(goal.getGoalsAmount()), goal.getSavingAccount(),
                "$" + goal.getRemainingAmount(), goal.getGoalsDate(), goal.getStatus());
        if (!isMultiplePrinting) {
            ui.printGoalDivider();
        }
    }

    /**
     * Updates all goals in the list.
     */
    public void updateGoals() {
        for (int i = 0; i < goalList.size(); i++) {
            goalList.get(i).isDone(Double.parseDouble(goalList.get(i).getRemainingAmount()));
        }
    }

    /**
     * Get the goal from the specified index.
     *
     * @param index Index of goal in goals list.
     * @return Goal in the specified index.
     */
    private Goals getGoal(int index) {
        return goalList.get(index);
    }

    /**
     * Change all goals tied to a deleted account to untied.
     *
     * @param bankName Name of deleted bank account.
     */
    public void changeTiedAccountsToNull(String bankName) {
        String capitalBankName = bankName.toUpperCase();
        for (int i = ISZERO; i < goalList.size(); i++) {
            Goals currentGoal = goalList.get(i);
            String currentGoalBank = currentGoal.getSavingAccount();
            if (currentGoalBank == null) {
                continue;
            }
            String capitalCurrentGoalBank = currentGoalBank.toUpperCase();
            if (capitalBankName.equals(capitalCurrentGoalBank)) {
                currentGoal.setSavingAccount(null);
            }
        }
    }

    /**
     * Gets the size of the goalList which counts all the goals stored in the ArrayList of goals.
     *
     * @return size of goalList.
     */
    public int getGoalListSize() {
        return goalList.size();
    }

    /**
     * Prepares the goalList for exporting of attributes of each goal.
     *
     * @return ArrayList of String arrays for containing each bank in the bank list.
     */
    private ArrayList<String[]> prepareExportGoalList() {
        ArrayList<String[]> exportArrayList = new ArrayList<>();
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        SimpleDateFormat exportDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        exportArrayList.add(new String[]{
            "goalName", "amount", "date", "savingsAccountName", "doneStatus", "achieveStatus"});
        for (int i = 0; i < getGoalListSize(); i++) {
            String goalName = goalList.get(i).getGoalsName();
            double amount = goalList.get(i).getGoalsAmount();
            String stringAmount = decimalFormat.format(amount);
            String date = exportDateFormat.format(goalList.get(i).getGoalsDateInDateFormat());
            String savingsAccountName = goalList.get(i).getSavingAccount();
            if (UNTIEDBANK.equals(savingsAccountName)) {
                savingsAccountName = null;
            }
            boolean doneStatus = goalList.get(i).getRawStatus();
            String stringDoneStatus = String.valueOf(doneStatus);
            boolean achievementStatus = goalList.get(i).getGoalAchievementStatus();
            String achievementStatusString = String.valueOf(achievementStatus);
            exportArrayList.add(new String[]{
                goalName, stringAmount, date, savingsAccountName, stringDoneStatus, achievementStatusString});
        }
        return exportArrayList;
    }

    /**
     * Writes the data of the bank list that was prepared to permanent storage.
     *
     * @throws IOException when unable to write to file.
     */
    private void exportGoalList() throws IOException {
        ArrayList<String[]> inputData = prepareExportGoalList();
        storage.writeFile(inputData, PROFILE_GOAL_LIST_FILE_NAME);
    }

    /**
     * Imports goals loaded from save file into goalList.
     *
     * @param newGoal an instance of the goal to be imported.
     */
    public void goalListImportNewGoal(Goals newGoal) {
        goalList.add(newGoal);
    }

    /**
     * Checks if goals can get achievement.
     *
     * @param i  index of goals.
     * @param ui Required for Printing.
     * @return Achievement object to create new achievement.
     */
    public Achievement checkForAchievement(int i, Ui ui) {

        Goals checkAchievement = goalList.get(i);
        if (checkAchievement.getRawStatus() && checkAchievement.getGoalsDateInDateFormat().after(new Date())
                && !checkAchievement.getGoalAchievementStatus()) {
            Achievement unlocked = new Achievement(checkAchievement.getGoalsName(), checkAchievement.getGoalsAmount(),
                    "[GOALS]", checkAchievement.getGoalsDateInDateFormat());
            checkAchievement.achieveGoal();
            try {
                exportGoalList();
            } catch (IOException e) {
                ui.printError("Error trying to save your goals to disk. Your data is"
                        + " at risk, but we will try again, feel free to continue using the program.");
            }
            return unlocked;
        }
        try {
            exportGoalList();
        } catch (IOException e) {
            ui.printError("Error trying to save your goals to disk. Your data is"
                    + " at risk, but we will try again, feel free to continue using the program.");
        }
        return null;
    }

    /**
     * Check goals that is due in 10 days.
     *
     * @return Goal due in 10 days.
     */
    public Goals reminderForGoals() {
        for (int i = 0; i < goalList.size(); i++) {
            if (goalList.get(i).convertDateToDays() <= 10 && !goalList.get(i).getRawStatus()) {
                return goalList.get(i);
            }
        }
        return null;
    }
}
