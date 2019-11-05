package owlmoney.model.goals;

import owlmoney.storage.Storage;
import owlmoney.ui.Ui;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Contains list of achievements in profile.
 */
public class AchievementList {

    private Storage storage;
    private ArrayList<Achievement> achievementList;
    private static final String PROFILE_ACHIEVEMENT_LIST_FILE_NAME = "profile_achievementlist.csv";
    private static final int ISZERO = 0;

    /**
     * Creates a instance of AchievementList that contains an arrayList of Achievements.
     */
    public AchievementList(Storage storage) {
        achievementList = new ArrayList<Achievement>();
        this.storage = storage;
    }

    /**
     * Adds an instance of achievement into Achievement List.
     *
     * @param achievement Achievement object containing achievement details.
     * @param ui Required for printing.
     */
    public void addAchievement(Achievement achievement, Ui ui) {
        achievementList.add(achievement);
        try {
            exportAchievementList();
        } catch (IOException e) {
            ui.printError("Error trying to save your achievements to disk. Your data is"
                    + " at risk, but we will try again, feel free to continue using the program.");
        }
        ui.printMessage("Achievement unlocked! Details: ");
        ui.printMessage(achievement.getCategory() + " Achieved goal [" + achievement.getName() + "] to save ["
                + new DecimalFormat("0.00").format(achievement.getAmount())
                + "] before your target date " + achievement.getDate());
    }

    /**
     * Lists all achievements in OwlMoney.
     *
     * @param ui required for printing.
     */
    public void listAchievements(Ui ui) {
        if (achievementList.size() <= ISZERO) {
            ui.printError("Save more to unlock achievements!");
        } else {
            for (int i = ISZERO; i < achievementList.size(); i++) {
                try {
                    exportAchievementList();
                } catch (IOException e) {
                    ui.printError("Error trying to save your achievements to disk. Your data is"
                            + " at risk, but we will try again, feel free to continue using the program.");
                }

                ui.printMessage("Achieved Goals:\n");
                ui.printMessage(i + 1 + ". SAVED " + "$"
                        + new DecimalFormat("0.00").format(achievementList.get(i).getAmount())
                        + " for your " + achievementList.get(i).getCategory() + " " + achievementList.get(i).getName());
            }
        }
    }

    /**
     * Prepares the achievementList for exporting of attributes of each achievements.
     *
     * @return ArrayList of String arrays for containing each bank in the achievement list.
     */
    private ArrayList<String[]> prepareExportAchievementList() {
        ArrayList<String[]> exportArrayList = new ArrayList<>();
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        SimpleDateFormat exportDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        exportArrayList.add(new String[]{"achievementName", "amount", "category", "date"});
        for (int i = 0; i < achievementList.size(); i++) {
            String achievementName = achievementList.get(i).getName();
            double amount = achievementList.get(i).getAmount();
            String stringAmount = decimalFormat.format(amount);
            String date = exportDateFormat.format(achievementList.get(i).getAchievementDateInDateFormat());
            String category = achievementList.get(i).getCategory();
            exportArrayList.add(new String[]{achievementName, stringAmount, category, date});
        }
        return exportArrayList;
    }

    /**
     * Writes the data of the achievement list that was prepared to permanent storage.
     *
     * @throws IOException when unable to write to file.
     */
    private void exportAchievementList() throws IOException {
        ArrayList<String[]> inputData = prepareExportAchievementList();
        storage.writeFile(inputData, PROFILE_ACHIEVEMENT_LIST_FILE_NAME);
    }

    /**
     * Imports achievement loaded from save file into achievementList.
     *
     * @param newAchievement an instance of the achievement to be imported.
     */
    public void achievementListImportNewAchievement(Achievement newAchievement) {
        achievementList.add(newAchievement);
    }
}

