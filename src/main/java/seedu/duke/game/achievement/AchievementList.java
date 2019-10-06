package seedu.duke.game.achievement;

import java.util.ArrayList;

public class AchievementList {


    /**
     * List of achievement that the user unlocked.
     */
    private ArrayList<Achievements> achievementUnlockedList = new ArrayList<>();

    /**
     * List of achievement that can be unlocked.
     */
    private ArrayList<Achievements> achievementLockedList = new ArrayList<>();



    /**
     * Adds new achievement to the list of achievement unlocked.
     * @param achievement achievement to be added.
     * @return unlocked achievement list.
     */
    private ArrayList<Achievements> unlockAchievement(Achievements achievement) {
        achievementUnlockedList.add(achievement);
        return achievementUnlockedList;
    }

    /**
     * Remove achievements that have been unlocked from locked achievement list.
     * @param achievement achievement to be removed.
     * @return locked achievement list.
     */
    private ArrayList<Achievements> remove(Achievements achievement) {
        achievementLockedList.remove(achievement);
        return achievementLockedList;
    }

    private ArrayList <Achievements> showList() {

        System.out.println("\uD83D\uDD13 ACHIEVEMENTS UNLOCKED \uD83D\uDD13");
        for(int i = 0; i < achievementUnlockedList.size(); i ++){
            System.out.println(achievementUnlockedList.get(i));
        }

        System.out.println("\uD83D\uDD12 LOCKED ACHIEVEMENTS \uD83D\uDD12");

        for(int i = 0; i < achievementLockedList.size(); i ++){
            System.out.println(achievementUnlockedList.get(i));
        }

        return achievementUnlockedList;
    }

}
