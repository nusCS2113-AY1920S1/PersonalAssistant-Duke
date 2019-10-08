package seedu.hustler.data;


import seedu.hustler.game.achievement.*;


import java.io.*;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

import static seedu.hustler.game.achievement.AchievementList.*;
import static seedu.hustler.game.achievement.Achievements.totalPoints;
import static seedu.hustler.game.achievement.AddTask.numberOfTasks;
import static seedu.hustler.game.achievement.DoneTask.numberOfDone;

public class AchievementStorage {

    public static final String ACHIEVEMENT_FILEPATH = "data/achievement.txt";
    public static final String LOGIN_FILEPATH = "data/login.txt";
    public static final String STATUS_FILEPATH = "data/status.txt";
    private static Formatter formatter;
    private static int loginCount = 0;

    /**
     * Keeps track of login count.
     * @return login count.
     * @throws IOException if file is not found.
     */
    public static int logon() throws IOException {
        try {
            File file = new File(LOGIN_FILEPATH);
            Scanner loginCounter = new Scanner(new File(LOGIN_FILEPATH));
            if (loginCounter.hasNextLine()) {
                String[] txt = loginCounter.nextLine().split(" ");
                int temp = Integer.parseInt(txt[1]);
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write("logon: " + (temp += 1));
                writer.close();
                loginCount = (temp);
                return loginCount;
            } else {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write("logon: 1");
                writer.close();
                return loginCount = 1;
            }
        } catch (FileNotFoundException e) {
            formatter = new Formatter(LOGIN_FILEPATH);
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(LOGIN_FILEPATH)));
            writer.write("logon: 1");
            writer.close();
            return loginCount = 1;
        }
    }

    /**
     * Keeps track of login count.
     * @return login count.
     * @throws IOException if file is not found.
     */
    public static int loadStatus() throws IOException {
        try {
            Scanner scanner = new Scanner(new File(STATUS_FILEPATH));
            while (scanner.hasNextLine()) {
                String[] txt = scanner.nextLine().split(" ");
                if(txt[0].equals("Add:")) {
                    numberOfTasks = Integer.parseInt(txt[1]);
                } else if(txt[0].equals("Done:")) {
                    numberOfDone = Integer.parseInt(txt[1]);
                } else if(txt[0].equals("TotalPoints:")) {
                    totalPoints = Integer.parseInt(txt[1]);
                }
            }
            return numberOfTasks;
        } catch (FileNotFoundException e) {
            formatter = new Formatter(STATUS_FILEPATH);
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(STATUS_FILEPATH)));
            writer.write("Add: 0\n");
            writer.write("Done: 0\n");
            writer.write("TotalPoints: 0\n");
            writer.close();
            return numberOfTasks;
        }
    }

    public static int saveStatus() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(STATUS_FILEPATH)));
        writer.write("Add: " + numberOfTasks);
        writer.write("\n");
        writer.write("Done: " + numberOfDone);
        writer.write("\n");
        writer.write("TotalPoints: " + totalPoints);
        writer.close();
        return numberOfTasks;
    }

    public static ArrayList<Achievements> loadAchievements() throws FileNotFoundException {
        try {
            Scanner scanner = new Scanner(new File(ACHIEVEMENT_FILEPATH));
            while (scanner.hasNextLine()) {
                String[] txt = scanner.nextLine().split("\\|");
                    if(txt[3].equals("Busybee")) {
                        AddTask addTask = new AddTask(txt[2]);
                        addTask.setPoints(Integer.parseInt(txt[1]));
                        if(txt[0].equals("true")) {
                            addTask.setLock(true);
                        } else if(txt[0].equals("false")) {
                            addTask.setLock(false);
                        }
                        achievementList.add(addTask);
                    } else if(txt[3].equals("Completionist")) {
                        DoneTask doneTask = new DoneTask(txt[2]);
                        doneTask.setPoints(Integer.parseInt(txt[1]));
                        if(txt[0].equals("true")) {
                            doneTask.setLock(true);
                        } else if(txt[0].equals("false")) {
                            doneTask.setLock(false);
                        }
                        achievementList.add(doneTask);
                    } else if(txt[3].equals("Fresh off the boat")) {
                        FirstLogin firstLogin = new FirstLogin();
                        achievementList.add(firstLogin);
                    }
                }
                return achievementList;
            } catch(FileNotFoundException e) {
            return achievementList;
        }
    }


    public static ArrayList<Achievements> saveAchievements(ArrayList<Achievements> achievementsList) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(ACHIEVEMENT_FILEPATH)));
        for(int i = 0; i < achievementsList.size(); i += 1) {
            writer.write(achievementsList.get(i).toTxt());
            writer.write("\n");
        }
        writer.close();
        return achievementsList;
    }

}
