package owlmoney.model.goals;

import owlmoney.ui.Ui;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Contains list of achievements in profile.
 */
public class AchievementList {

    private ArrayList<Achievement> achievementList;
    private static final int ISZERO = 0;

    /**
     * Creates a instance of AchievementList that contains an arrayList of Achievements.
     */
    public AchievementList() {
        achievementList = new ArrayList<Achievement>();
    }

    /**
     * Adds an instance of achievement into Achievement List.
     *
     * @param achievement Achievement object containing achievement details.
     * @param ui Required for printing.
     */
    public void addAchievement(Achievement achievement, Ui ui) {
        achievementList.add(achievement);
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
                ui.printMessage("Achieved Goals:\n");
                ui.printMessage(i + 1 + ". SAVED " + "$"
                        + new DecimalFormat("0.00").format(achievementList.get(i).getAmount())
                        + " for your " + achievementList.get(i).getCategory() + " " + achievementList.get(i).getName());
            }
        }
    }
}

