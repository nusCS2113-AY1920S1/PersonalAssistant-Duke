package seedu.hustler.game.achievement;

import seedu.hustler.ui.Ui;

import java.util.ArrayList;

import static seedu.hustler.game.achievement.AddTask.addAchievementLevel;
import static seedu.hustler.game.achievement.AddTask.addPoints;
import static seedu.hustler.game.achievement.ConsecutiveLogin.loginPoints;
import static seedu.hustler.game.achievement.ConsecutiveLogin.loginAchievementLevel;
import static seedu.hustler.game.achievement.DoneTask.doneAchievementLevel;
import static seedu.hustler.game.achievement.DoneTask.donePoints;

/**
 * Deals with addition of achievements and unlocking of achievements.
 * Also prints out achievement list.
 */
public class AchievementList {

    /**
     * List of all unlocked and locked achievements.
     */
    private ArrayList<Achievements> achievementList;

    /**
     * Creates a new instance of achievement list and populates
     * achievement list with achievements.
     */
    public AchievementList() {
        achievementList = new ArrayList<>();
        populate();
    }

    /**
     * Adds all available achievements to achievement list
     * and initialise the achievements.
     * @return achievement list.
     */
    private AchievementList populate() {

        this.achievementList.add(new DoneTask("Bronze"));
        this.achievementList.add(new DoneTask("Silver"));
        this.achievementList.add(new DoneTask("Gold"));
        this.achievementList.add(new AddTask("Bronze"));
        this.achievementList.add(new AddTask("Silver"));
        this.achievementList.add(new AddTask("Gold"));
        this.achievementList.add(new ConsecutiveLogin("Bronze"));
        this.achievementList.add(new ConsecutiveLogin("Silver"));
        this.achievementList.add(new ConsecutiveLogin("Gold"));
        this.achievementList.add(new FirstLogin());
        FirstLogin.updatePoints();
        return this;
    }

    /**
     * Updates status of an achievement.
     * @param lock to unlock achievement.
     * @param index index of achievement inside achievement list.
     * @param points set points gained from unlocking achievement.
     */
    public void updateStatus(Boolean lock, int index, int points) {
        achievementList.get(index).setLock(lock);
        achievementList.get(index).setPoints(points);
    }

    /**
     * Checks if the condition for busybee have been satisfied
     * and updates status of achievement accordingly.
     * @return updated version of achievement list.
     */
    public ArrayList<Achievements> updateBusyBee() {
        Ui ui = new Ui();
        for (int i = 0; i < this.achievementList.size(); i += 1) {
            if (this.achievementList.get(i).getDescription().equals("Busybee")
                && achievementList.get(i).getAchievementLevel().equals(addAchievementLevel)
                && achievementList.get(i).checkLock()) {
                this.achievementList.get(i).setLock(false);
                this.achievementList.get(i).setPoints(addPoints);
                ui.showAchievementUnlocked(this.achievementList.get(i));
            }
        }
        return achievementList;
    }

    /**
     * Checks if the condition for completionist have been satisfied
     * and updates status of achievement accordingly.
     * @return updated version of achievement list.
     */
    public ArrayList<Achievements> updateCompletionist() {
        Ui ui = new Ui();
        for (int i = 0; i < achievementList.size(); i += 1) {
            if (achievementList.get(i).getDescription().equals("Completionist")
                && achievementList.get(i).getAchievementLevel().equals(doneAchievementLevel)
                && achievementList.get(i).checkLock()) {
                achievementList.get(i).setLock(false);
                achievementList.get(i).setPoints(donePoints);
                ui.showAchievementUnlocked(this.achievementList.get(i));
            }
        }
        return achievementList;
    }

    /**
     * Checks if the condition for consecutive login have been satisfied
     * and updates status of achievement accordingly.
     * @return updated version of achievement list.
     */
    public ArrayList<Achievements> updateDedicated() {
        Ui ui = new Ui();
        for (int i = 0; i < achievementList.size(); i += 1) {
            if (achievementList.get(i).getDescription().equals("Dedicated to the art")
                && achievementList.get(i).getAchievementLevel().equals(loginAchievementLevel)
                && achievementList.get(i).checkLock()) {
                achievementList.get(i).setLock(false);
                achievementList.get(i).setPoints(loginPoints);
                ui.showAchievementUnlocked(this.achievementList.get(i));
            }
        }
        return achievementList;
    }

    /**
     * Adds achievements to achievement list.
     * @param achievements achievement to be added to achievement list.
     */
    public void add(Achievements achievements) {
        achievementList.add(achievements);
    }

    /**
     * Gets a particular achievement from achievement list.
     * @param i index of achievement.
     * @return an achievement.
     */
    public Achievements get(int i) {
        return achievementList.get(i);
    }

    /**
     * Gets achievement list.
     * @return current achievement list.
     */
    public ArrayList<Achievements> getAchievementList() {
        return achievementList; }

    /**
     * Gets the size of achievement list.
     * @return size of achievement.
     */
    public int size() {
        return achievementList.size();
    }

    /**
     * Creates a duplicate of achievement list.
     * @return array of achievements.
     */
    public ArrayList<Achievements> createCopy() {
        ArrayList<Achievements> copy = new ArrayList<>();
	for (int i = 0; i < achievementList.size(); i += 1) {
            copy.add(achievementList.get(i));
	}
	return copy;
    }
}
