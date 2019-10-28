package seedu.hustler.data;

import seedu.hustler.Hustler;
import seedu.hustler.game.achievement.AchievementList;
import seedu.hustler.logic.parser.DateTimeParser;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Scanner;

import static seedu.hustler.game.achievement.Achievements.totalPoints;
import static seedu.hustler.game.achievement.AddTask.numberOfTasks;
import static seedu.hustler.game.achievement.ConsecutiveLogin.*;
import static seedu.hustler.game.achievement.DoneTask.numberOfDone;

/**
 * A class that stores User's achievements and loads it on request from disc.
 */
public class AchievementStorage {

    public static final String ACHIEVEMENT_FILEPATH = "data/achievement.txt";
    public static final String STATUS_FILEPATH = "data/status.txt";
    public static final String ACHIEVEMENT_FILEPATH_BACKUP = "data/achievementBackup.txt";
    public static final String STATUS_FILEPATH_BACKUP = "data/statusBackup.txt";
    
    public static int loadStatus() throws IOException {
        try {
            Scanner scanner = new Scanner(new File(STATUS_FILEPATH));
            while (scanner.hasNextLine()) {
                String[] txt = scanner.nextLine().split(" ");
                if (txt[0].equals("Add:")) {
                    numberOfTasks = Integer.parseInt(txt[1]);
                } else if (txt[0].equals("Done:")) {
                    numberOfDone = Integer.parseInt(txt[1]);
                } else if (txt[0].equals("TotalPoints:")) {
                    totalPoints = Integer.parseInt(txt[1]);
                } else if (txt[0].equals("LastLogin:")) {
                    storedDateTime = DateTimeParser.getDateTime(txt[1] + " " + txt[2]);
                }
            else if (txt[0].equals("ConsecutiveCount:")) {
                    consecutiveCount = Integer.parseInt(txt[1]);
                }
            }
            return numberOfTasks;
        } catch (FileNotFoundException e) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(STATUS_FILEPATH)));
            writer.write("Add: 0\n");
            writer.write("Done: 0\n");
            writer.write("TotalPoints: 0\n");
            writer.write("LastLogin: 0\n");
            writer.write("ConsecutiveCount:  " + LocalDateTime.now() + "\n");
            storedDateTime = LocalDateTime.now();
            writer.close();
            return numberOfTasks;
        }
    }

    public static int reloadStatus() {
        try {
            Scanner scanner = new Scanner(new File(STATUS_FILEPATH_BACKUP));
            while (scanner.hasNextLine()) {
                String[] txt = scanner.nextLine().split(" ");
                if(txt[0].equals("Add:")) {
                    numberOfTasks = Integer.parseInt(txt[1]);
                } else if(txt[0].equals("Done:")) {
                    numberOfDone = Integer.parseInt(txt[1]);
                } else if(txt[0].equals("TotalPoints:")) {
                    totalPoints = Integer.parseInt(txt[1]);
                } else if(txt[0].equals("LastLogin:")) {
                    storedDateTime = DateTimeParser.getDateTime(txt[1] + " " + txt[2]);
                } else if(txt[0].equals("ConsecutiveCount:")) {
                   consecutiveCount = Integer.parseInt(txt[1]);
                }
            }
            return numberOfTasks;
        } catch (FileNotFoundException e) {
            return numberOfTasks;
        }
    }

    /**
     * Save all current achievement progress.
     * @throws IOException when writing of file has errors.
     */
    public static void saveStatus() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(STATUS_FILEPATH)));
        writer.write("Add: " + numberOfTasks);
        writer.write("\n");
        writer.write("Done: " + numberOfDone);
        writer.write("\n");
        writer.write("TotalPoints: " + totalPoints);
        if (reset() || checkLogin()) {
            writer.write("\n");
            writer.write("LastLogin: " + DateTimeParser.convertDateTime(LocalDateTime.now()));
            writer.write("\n");
        } else {
            writer.write("\n");
            writer.write("LastLogin: " + DateTimeParser.convertDateTime(storedDateTime));
            writer.write("\n");
        }
        writer.write("ConsecutiveCount: " + consecutiveCount);
        writer.close();
    }

    public static AchievementList loadAchievements() {
        try {
            int index = 0;
            Scanner scanner = new Scanner(new File(ACHIEVEMENT_FILEPATH));
            while (scanner.hasNextLine()) {
                String[] txt = scanner.nextLine().split("\\|");
                Hustler.achievementList.updateStatus(Boolean.parseBoolean(txt[0]),index,Integer.parseInt(txt[1]));
                index += 1;
            }
            return Hustler.achievementList;
        } catch (FileNotFoundException e) {
            return Hustler.achievementList;
        }
    }


    public static AchievementList reloadAchievements() {
        try {
            int index = 0;
            Scanner scanner = new Scanner(new File(ACHIEVEMENT_FILEPATH_BACKUP));
            Hustler.achievementList = new AchievementList();

            while (scanner.hasNextLine()) {
                String[] txt = scanner.nextLine().split("\\|");
                Hustler.achievementList.updateStatus(Boolean.parseBoolean(txt[0]),index,Integer.parseInt(txt[1]));
                index += 1;
                }
            return Hustler.achievementList;
        } catch(FileNotFoundException e) {
            return Hustler.achievementList;
        }
    }

    public static AchievementList saveAchievements(AchievementList achievementList) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(ACHIEVEMENT_FILEPATH)));
        for (int i = 0; i < achievementList.size(); i += 1) {
            writer.write(achievementList.get(i).toTxt());
            writer.write("\n");
        }
        writer.close();

        return achievementList;
    }

    public static AchievementList createBackup(AchievementList achievementList) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(STATUS_FILEPATH_BACKUP)));
        writer.write("Add: " + numberOfTasks);
        writer.write("\n");
        writer.write("Done: " + numberOfDone);
        writer.write("\n");
        writer.write("TotalPoints: " + totalPoints);
        if(reset() || checkLogin()) {
            writer.write("\n");
            writer.write("LastLogin: " + DateTimeParser.convertDateTime(LocalDateTime.now()));
            writer.write("\n");
        } else {
            writer.write("\n");
            writer.write("LastLogin: " + DateTimeParser.convertDateTime(storedDateTime));
            writer.write("\n");
        }
        writer.write("ConsecutiveCount: " + consecutiveCount);
        writer.close();

        writer = new BufferedWriter(new FileWriter(new File(ACHIEVEMENT_FILEPATH_BACKUP)));
        for(int i = 0; i < achievementList.size(); i += 1) {
            writer.write(achievementList.get(i).toTxt());
            writer.write("\n");
        }
        writer.close();
        return achievementList;
    }

}

