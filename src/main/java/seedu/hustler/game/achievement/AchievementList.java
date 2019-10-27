package seedu.hustler.game.achievement;

import java.util.ArrayList;

import static seedu.hustler.game.achievement.AddTask.addAchievementLevel;
import static seedu.hustler.game.achievement.AddTask.addPoints;
import static seedu.hustler.game.achievement.ConsecutiveLogin.*;
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
    public static ArrayList<Achievements> achievementList = new ArrayList<>();


    private ArrayList<Achievements> listAchievement;

    public AchievementList() {
        listAchievement = new ArrayList<>();
        populate();
    }

//    /**
//     * When user first starts Hustler, Hustler will create a list of locked achievements.
//     * It will then store achievements inside achievements.txt.
//     * @return list of locked achievements.
//     */
//    public static ArrayList<Achievements> firstStart(int loginCount) {
//
//        if(loginCount == 1) {
//            achievementList.add(new DoneTask("Bronze"));
//            achievementList.add(new DoneTask("Silver"));
//            achievementList.add(new DoneTask("Gold"));
//            achievementList.add(new AddTask("Bronze"));
//            achievementList.add(new AddTask("Silver"));
//            achievementList.add(new AddTask("Gold"));
//            achievementList.add(new ConsecutiveLogin("Bronze"));
//            achievementList.add(new ConsecutiveLogin("Silver"));
//            achievementList.add(new ConsecutiveLogin("Gold"));
//            achievementList.add(new FirstLogin());
//            System.out.println("You have unlocked this achievement!\n" + new FirstLogin());
//            FirstLogin.updatePoints();
//            storedDateTime = LocalDateTime.now();
//        }
//        return achievementList;
//    }

    private ArrayList<Achievements> populate() {

        listAchievement.add(new DoneTask("Bronze"));
        listAchievement.add(new DoneTask("Silver"));
        listAchievement.add(new DoneTask("Gold"));
        listAchievement.add(new AddTask("Bronze"));
        listAchievement.add(new AddTask("Silver"));
        listAchievement.add(new AddTask("Gold"));
        listAchievement.add(new ConsecutiveLogin("Bronze"));
        listAchievement.add(new ConsecutiveLogin("Silver"));
        listAchievement.add(new ConsecutiveLogin("Gold"));
        listAchievement.add(new FirstLogin());

        return listAchievement;
    }

    public ArrayList<Achievements> updateBusyBee() {
        for(int i = 0; i < listAchievement.size(); i += 1) {
            if (listAchievement.get(i).getDescription().equals("Busybee") && listAchievement.get(i).getAchievementLevel().equals(addAchievementLevel) && listAchievement.get(i).checkLock()) {
                listAchievement.get(i).setLock(false);
                listAchievement.get(i).setPoints(addPoints);
                System.out.println("You have unlocked this achievement!\n" + listAchievement.get(i));
            }
        }
        return listAchievement;
    }

    public ArrayList<Achievements> updateCompletionist() {
        for(int i = 0; i < listAchievement.size(); i += 1) {
            if (listAchievement.get(i).getDescription().equals("Completionist") && listAchievement.get(i).getAchievementLevel().equals(doneAchievementLevel) && listAchievement.get(i).checkLock()) {
                listAchievement.get(i).setLock(false);
                listAchievement.get(i).setPoints(addPoints);
                System.out.println("You have unlocked this achievement!\n" + listAchievement.get(i));
            }
        }
        return listAchievement;
    }

    public ArrayList<Achievements> updateDedicate () {
        for(int i = 0; i < listAchievement.size(); i += 1) {
            if(listAchievement.get(i).getDescription().equals("Dedicated to the art") && listAchievement.get(i).getAchievementLevel().equals(loginAchievementLevel) && achievementList.get(i).checkLock()) {
                listAchievement.get(i).setLock(false);
                listAchievement.get(i).setPoints(loginPoints);
                System.out.println("You have unlocked this achievement!\n" + listAchievement.get(i));
            }
        }
        return listAchievement;
    }

    public void add(Achievements achievements) {
        listAchievement.add(achievements);
    }

    public Achievements get(int i) {
        return listAchievement.get(i);
    }

    public int size() {
        return listAchievement.size();
    }

    public void list() {
        System.out.println("\uD83D\uDD13 ACHIEVEMENTS UNLOCKED \uD83D\uDD13");
        if(!listAchievement.isEmpty()) {
            int l = 0;
            for(int i = 0; i < listAchievement.size(); i ++) {
                if(!listAchievement.get(i).checkLock()) {
                    l ++;
                    System.out.print(l + ". ");
                    System.out.println(listAchievement.get(i));
                }
            }
            System.out.println("\uD83D\uDD12 LOCKED ACHIEVEMENTS \uD83D\uDD12");
            int j = 0;
            for(int i = 0; i < listAchievement.size(); i ++) {
                if(listAchievement.get(i).checkLock()) {
                    j ++;
                    System.out.print(j + ". ");
                    System.out.println(listAchievement.get(i));
                }
            }
            System.out.println("Total Points = " + totalPoints + " \uD83D\uDCB0");
        }

    }

    public ArrayList<Achievements> repopulate(Achievements achievements) {
        listAchievement.add(achievements);
        return listAchievement;
    }

    /**
     * Checks if user meets any condition of achievement for Busybee.
     * Unlocks Busybee achievements accordingly.
     * @param achievementLevel achievement level of the achievement.
     * @return returns list of achievement.
     */
    public static ArrayList<Achievements> updateAddTask (String achievementLevel) {
        for(int i = 0; i < achievementList.size(); i += 1) {
            if(achievementList.get(i).getDescription().equals("Busybee") && achievementList.get(i).getAchievementLevel().equals(addAchievementLevel) && achievementList.get(i).checkLock()) {
                achievementList.get(i).setLock(false);
                achievementList.get(i).setPoints(addPoints);
                System.out.println("You have unlocked this achievement!\n" + achievementList.get(i));
            }
        }

        return achievementList;
    }

    /**
     * Checks if user meets any condition of achievement for Completionist.
     * Unlocks Completionist achievements accordingly.
     * @param achievementLevel achievement level of the achievement.
     * @return returns list of achievement.
     */
    public static ArrayList<Achievements> updateDoneTask (String achievementLevel) {
        for(int i = 0; i < achievementList.size(); i += 1) {
            if(achievementList.get(i).getDescription().equals("Completionist") && achievementList.get(i).getAchievementLevel().equals(doneAchievementLevel) && achievementList.get(i).checkLock()) {
                achievementList.get(i).setLock(false);
                achievementList.get(i).setPoints(donePoints);
                System.out.println("You have unlocked this achievement!\n" + achievementList.get(i));
            }
        }
        return achievementList;
    }

    /**
     * Checks if user meets any condition of achievement for Dedicated to the art.
     * Unlocks Dedicated to the art achievements accordingly.
     * @param achievementLevel achievement level of the achievement.
     * @return returns list of achievement.
     */
    public static ArrayList<Achievements> updateConsecutiveLogin (String achievementLevel) {
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
     * Shows achievement list.
     */
    public static void showList() {
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

    public static ArrayList<Achievements> createCopy() {
        ArrayList<Achievements> copy = new ArrayList<>();
	for (int i = 0; i < achievementList.size(); i += 1) {
            copy.add(achievementList.get(i));
	}
	return copy;
    }
}
