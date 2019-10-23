package seedu.hustler.data;

import seedu.hustler.game.avatar.Avatar;
import seedu.hustler.game.avatar.Level;
import seedu.hustler.game.avatar.Stats;
import seedu.hustler.game.shop.items.armors.Armor;
import seedu.hustler.game.shop.items.weapons.Weapon;

import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.Formatter;
import java.util.Optional;
import java.util.Scanner;

/**
 * A class that stores User's avatar and loads it on request from disc.
 */
public class AvatarStorage {

    public static final String FILEPATH = "data/avatar.txt";
    public static final String FILEPATHBACKUP = FILEPATH.split("avatar.txt")[0] + "backup/avatarBackup.txt";
    private static Formatter formatter;

    /**
     * Loads up the current avatar on the destination of this
     * Hustler app.
     * @return the Avatar that is currently loaded.
     * @throws FileNotFoundException when file is not found, will create a
     *     new txtfile to start data storage.
     */
    public static Avatar load() {
        Avatar avatar = new Avatar();
        try {
            Scanner avatarTxt = new Scanner(new File(FILEPATH));
            String name = new String();
            Level level = new Level();
            Stats stats = new Stats();
            Optional<Weapon> weapon = Optional.empty();
            Optional<Armor> armor = Optional.empty();
            while (avatarTxt.hasNextLine()) {
                String[] txt = avatarTxt.nextLine().split(" ");
                if (txt[0].equals("Name")) {
                    name = txt[1];
                } else if (txt[0].equals("Level")) {
                    level = new Level(Integer.parseInt(txt[1]), Integer.parseInt(txt[2]));
                } else if (txt[0].equals("Stats")) {
                    stats = new Stats(Integer.parseInt(txt[1]), Integer.parseInt(txt[2]),
                            Integer.parseInt(txt[3]), Integer.parseInt(txt[4]));
                } else if (txt[0].equals("Weapon")) {
                    weapon = Weapon.getWeapon(txt[1]);
                } else if (txt[0].equals("Armor")) {
                    armor = Armor.getArmor(txt[1]);
                }
            }
            avatarTxt.close();
            avatar = new Avatar(name, level, stats, weapon, armor);
        } catch (FileNotFoundException e) {
            System.out.println("\t_____________________________________");
            System.out.println("\tNo Avatar saved in database, creating a new Avatar now.");
            System.out.println("\t_____________________________________\n\n");
        }
        return avatar;
    }

    /**
     * Reloads the avatar based on a copy of the initial avatar.txt of this
     * Hustler app.
     * @return the Avatar that is being reloaded.
     * @throws FileNotFoundException as this reload happens in the background,
     *     no message is shown.
     */
    public static Avatar reloadBackup() {
        Avatar avatar = new Avatar();
        try {
            Scanner avatarBackupTxt = new Scanner(new File(FILEPATHBACKUP));
            String name = new String();
            Level level = new Level();
            Stats stats = new Stats();
            Optional<Weapon> weapon = Optional.empty();
            Optional<Armor> armor = Optional.empty();
            while (avatarBackupTxt.hasNextLine()) {
                String[] backupTxt = avatarBackupTxt.nextLine().split(" ");
                if (backupTxt[0].equals("Name")) {
                    name = backupTxt[1];
                } else if (backupTxt[0].equals("Level")) {
                    level = new Level(Integer.parseInt(backupTxt[1]), Integer.parseInt(backupTxt[2]));
                } else if (backupTxt[0].equals("Stats")) {
                    stats = new Stats(Integer.parseInt(backupTxt[1]), Integer.parseInt(backupTxt[2]),
                            Integer.parseInt(backupTxt[3]), Integer.parseInt(backupTxt[4]));
                } else if (backupTxt[0].equals("Weapon")) {
                      weapon = Weapon.getWeapon(backupTxt[1]);
                } else if (backupTxt[0].equals("Armor")) {
                      armor = Armor.getArmor(backupTxt[1]);
                }
            }
            avatarBackupTxt.close();
            avatar = new Avatar(name, level, stats, weapon, armor);
        } catch (FileNotFoundException e) {

        }
        return avatar;
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

    /**
     * Creates and saves the a backup of the current avatar when the user first starts this app..
     * @param avatar the updated avatar to be saved.
     * @return the updated avatar.
     * @throws IOException when writing of file has errors.
     */
    public static Avatar createBackup(Avatar avatar) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(FILEPATHBACKUP)));
        writer.write(avatar.toTxt());
        writer.close();
        return avatar;
    }
}
