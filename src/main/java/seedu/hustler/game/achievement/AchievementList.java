package seedu.hustler.game.achievement;

import java.util.ArrayList;

import static seedu.hustler.game.achievement.AddTask.addAchievementLevel;
import static seedu.hustler.game.achievement.AddTask.addPoints;
import static seedu.hustler.game.achievement.ConsecutiveLogin.*;
import static seedu.hustler.game.achievement.DoneTask.doneAchievementLevel;

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
     *
     */
    public AchievementList() {
        achievementList = new ArrayList<>();
        populate();
    }

    /**
     *
     * @return
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
     *
     * @param lock
     * @param index
     * @param points
     */
    public void updateStatus(Boolean lock, int index, int points) {
        achievementList.get(index).setLock(lock);
        achievementList.get(index).setPoints(points);
    }

    /**
     *
     * @return
     */
    public ArrayList<Achievements> updateBusyBee() {
        for(int i = 0; i < this.achievementList.size(); i += 1) {
            if (this.achievementList.get(i).getDescription().equals("Busybee") && achievementList.get(i).getAchievementLevel().equals(addAchievementLevel) && achievementList.get(i).checkLock()) {
                this.achievementList.get(i).setLock(false);
                this.achievementList.get(i).setPoints(addPoints);
                System.out.println("You have unlocked this achievement!\n" + this.achievementList.get(i));
            }
        }
        return achievementList;
    }

    /**
     *
     * @return
     */
    public ArrayList<Achievements> updateCompletionist() {
        for(int i = 0; i < achievementList.size(); i += 1) {
            if (achievementList.get(i).getDescription().equals("Completionist") && achievementList.get(i).getAchievementLevel().equals(doneAchievementLevel) && achievementList.get(i).checkLock()) {
                achievementList.get(i).setLock(false);
                achievementList.get(i).setPoints(addPoints);
                System.out.println("You have unlocked this achievement!\n" + achievementList.get(i));
            }
        }
        return achievementList;
    }

    /**
     *
     * @return
     */
    public ArrayList<Achievements> updateDedicated() {
        for(int i = 0; i < achievementList.size(); i += 1) {
            if(achievementList.get(i).getDescription().equals("Dedicated to the art") && achievementList.get(i).getAchievementLevel().equals(loginAchievementLevel) && achievementList.get(i).checkLock()) {
                achievementList.get(i).setLock(false);
                achievementList.get(i).setPoints(loginPoints);
                System.out.println("You have unlocked this achievement!\n" + achievementList.get(i));
            }
        }
        return achievementList;
    }

    /**
     *
     * @param achievements
     */
    public void add(Achievements achievements) {
        achievementList.add(achievements);
    }

    /**
     *
     * @param i
     * @return
     */
    public Achievements get(int i) {
        return achievementList.get(i);
    }

    /**
     *
     * @return
     */
    public int size() {
        return achievementList.size();
    }

    /**
     *
     */
    public void list() {
        System.out.println("\uD83D\uDD13 ACHIEVEMENTS UNLOCKED \uD83D\uDD13");
        if(!achievementList.isEmpty()) {
            int l = 0;
            for(int i = 0; i < achievementList.size(); i ++) {
                if(!achievementList.get(i).checkLock()) {
                    l ++;
                    System.out.print(l + ". ");
                    System.out.println(achievementList.get(i));
                }
            }
            System.out.println("\uD83D\uDD12 LOCKED ACHIEVEMENTS \uD83D\uDD12");
            int j = 0;
            for(int i = 0; i < achievementList.size(); i ++) {
                if(achievementList.get(i).checkLock()) {
                    j ++;
                    System.out.print(j + ". ");
                    System.out.println(achievementList.get(i));
                }
            }
            System.out.println("Total Points = " + totalPoints + " \uD83D\uDCB0");
        }

    }

    /**
     *
     * @return
     */
    public ArrayList<Achievements> createCopy() {
        ArrayList<Achievements> copy = new ArrayList<>();
	for (int i = 0; i < achievementList.size(); i += 1) {
            copy.add(achievementList.get(i));
	}
	return copy;
    }
}
