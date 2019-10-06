package seedu.hustler.data;


import seedu.duke.game.achievement.AchievementList;


import java.io.*;
import java.util.Formatter;
import java.util.Scanner;

public class AchievementStorage {

    public static final String FILEPATH = "data/achievement.txt";
    private static Formatter formatter;
//
//    /**
//     * Loads up the current avatar on the destination of this
//     * Hustler app.
//     * @return the Avatar that is currently loaded.
//     * @throws FileNotFoundException when file is not found, will create a
//     * new txtfile to start data storage.
//     */
//    public static AchievementList load() throws FileNotFoundException {
//        try {
//            Scanner avatarTxt = new Scanner(new File(FILEPATH));
//            String name = new String();
////            Level level = new Level();
////            Stats stats = new Stats();
//            while (avatarTxt.hasNextLine()) {
//                String[] txt = avatarTxt.nextLine().split(" ");
//                if (txt[0].equals("Name")) {
//                    name = txt[1];
//                } else if (txt[0].equals("Level")) {
//                    level = new Level(Integer.parseInt(txt[1]), Integer.parseInt(txt[2]));
//                } else if (txt[0].equals("Stats")) {
//                    stats = new Stats(Integer.parseInt(txt[1]), Integer.parseInt(txt[2]),
//                            Integer.parseInt(txt[3]), Integer.parseInt(txt[4]));
//                }
//            }
//            return new Achievements(name, level, stats);
//        } catch (FileNotFoundException e) {
//            formatter = new Formatter(FILEPATH);
//            AchievementList newAchievements = new AchievementList();
//            return newAchievements;
//        }
//    }
//
//    /**
//     * Updates and saves the current avatar onto the txtfile.
//     * @param avatar the updated avatar to be saved.
//     * @return the updated avatar.
//     * @throws IOException when writing of file has errors.
//     */
//    public static AchievementList save(Avatar avatar) throws IOException {
//        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(FILEPATH)));
//        writer.write(avatar.toTxt());
//        writer.close();
//        return A;
//    }

}
