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
    private static final int ONE_INDEX = 1;
    private static final boolean ISSINGLE = false;
    private static final boolean ISMULTIPLE = true;

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
        printOneAchievement(ONE_INDEX, achievement, ISSINGLE, ui);
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
                ui.printAchievementHeader();
                printOneAchievement(ONE_INDEX, achievementList.get(i), ISMULTIPLE, ui);
                ui.printDivider();
            }
        }
    }

    /**
     * Prints goal details.
     *
     * @param num                Represents the numbering of the goal.
     * @param achievement        The achievement object to be printed.
     * @param isMultiplePrinting Represents whether the function will be called for printing once or multiple
     *                           time
     * @param ui                 The object use for printing.
     */
    private void printOneAchievement(int num, Achievement achievement, boolean isMultiplePrinting, Ui ui) {
        if (!isMultiplePrinting) {
            ui.printAchievementHeader();
        }
        ui.printAchievement(num, achievement.getName(), "$"
                        + new DecimalFormat("0.00").format(achievement.getAmount()), achievement.getDate());
        if (!isMultiplePrinting) {
            ui.printDivider();
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

