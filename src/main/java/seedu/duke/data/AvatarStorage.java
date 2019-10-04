package seedu.duke.data;

import seedu.duke.game.avatar.*;
import java.io.*;
import java.util.Formatter;
import java.util.Scanner;

/**
 * A class that stores User's avatar and loads it on request from disc.
 */
public class AvatarStorage {

    public static final String FILEPATH = "data/avatar.txt";
    private static Formatter formatter;

    /**
     * Loads up the current avatar on the destination of this
     * Hustler app.
     * @return the Avatar that is currently loaded.
     * @throws FileNotFoundException when file is not found, will create a
     * new txtfile to start data storage.
     */
    public static Avatar load() throws FileNotFoundException {
        try {
            Scanner avatarTxt = new Scanner(new File(FILEPATH));
            String name = new String();
            Level level = new Level();
            Stats stats = new Stats();
            while (avatarTxt.hasNextLine()) {
                String[] txt = avatarTxt.nextLine().split(" ");
                if (txt[0].equals("Name")) {
                    name = txt[1];
                } else if (txt[0].equals("Level")) {
                    level = new Level(Integer.parseInt(txt[1]), Integer.parseInt(txt[2]));
                } else if (txt[0].equals("Stats")) {
                    stats = new Stats(Integer.parseInt(txt[1]), Integer.parseInt(txt[2]),
                        Integer.parseInt(txt[3]), Integer.parseInt(txt[4]));
                }
            }
            return new Avatar(name, level, stats);
        } catch (FileNotFoundException e) {
            formatter = new Formatter(FILEPATH);
            Avatar newAvatar = new Avatar();
            return newAvatar;
        }
    }

    /**
     * Updates and saves the current avatar onto the txtfile.
     * @param avatar the updated avatar to be saved.
     * @return the updated avatar.
     * @throws IOException when writing of file has errors.
     */
    public static Avatar save(Avatar avatar) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(FILEPATH)));
        writer.write(avatar.toTxt());
        writer.close();
        return avatar;
    }
}