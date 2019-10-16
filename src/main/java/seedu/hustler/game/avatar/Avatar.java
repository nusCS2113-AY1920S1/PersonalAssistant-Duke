package seedu.hustler.game.avatar;

import seedu.hustler.data.AvatarStorage;

import java.io.IOException;

/**
 * A class for the avatar in Hustler.
 */
public class Avatar implements Convertible {

    /**
     * The name of the avatar.
     */
    private String name;

    /**
     * Level of the avatar.
     */
    private Level level;

    /**
     * Stats of the avatar.
     */
    private Stats stats;

    /**
     * Default initialization of the level and stat.
     */
    public Avatar() {
        this.name = "Avatar";
        this.level = new Level();
        this.stats = new Stats();
    }

    /**
     * Initializing name, level and stat with specific
     * values.
     *
     * @param level object to initialize level with
     * @param stats object to initialize stats with
     */
    public Avatar(String name, Level level, Stats stats) {
        this.name = name;
        this.level = level;
        this.stats = stats;
    }

    /**
     * Sets the name for the avatar.
     * @param preferredName the new name to update to the avatar.
     * @return the avatar with the updated name.
     */
    public Avatar setName(String preferredName) {
        this.name = preferredName;
        return this;
    }

    /**
     * Gets the name for the avatar.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Increases avatar xp by 1. Avatar levels up and increases
     * its stats if the xp gained levels it up.
     *
     * @return the level of the avatar.
     */
    public Level gainXp() {
        this.level.increaseXp();
        if (this.level.canLevel()) {
            this.level.levelUp();
            this.stats.upStats(this.level.getLevel());
            showCongrats();
        }
        return this.level;
    }

    /**
     * Displays on the screen the congratulatory message to indicate that the User
     * has leveled up.
     */
    private void showCongrats() {
        System.out.println("\t_____________________________________");
        System.out.println("\tCongratulations, you've leveled up! Your avatar has gotten stronger:");
        System.out.println(this.toString());
        System.out.println("\t_____________________________________\n\n");
    }

    @Override
    public String toString() {
        return this.name + ", " + this.level.toString() + "\n"
            + this.stats.toString();
    }

    @Override
    public String toTxt() {
        return "Name " + this.name + "\n"
            + "Level " + this.level.toTxt() + "\n"
            + "Stats " + this.stats.toTxt();
    }
}
