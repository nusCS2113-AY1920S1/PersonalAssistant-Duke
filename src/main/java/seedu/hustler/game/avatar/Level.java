package seedu.hustler.game.avatar;

/**
 * Class representing the level of the avatar. Also contains the current xp.
 */
public class Level implements Convertible {
    /**
     * Integer level of avatar.
     */
    private int level;
    /**
     * Integer value of XP. Avatar has to hit this threshold
     * to level up.
     */
    private int xp;

    /**
     * Constructs a default Level.
     */
    public Level() {
        this.level = 1;
        this.xp = 0;
    }

    /**
     * Constructs Level with the given level and xp.
     *
     * @param level the current level of the avatar.
     * @param xp the current xp of the avatar.
     */
    public Level(int level, int xp) {
        this.level = level;
        this.xp = xp;
    }

    /**
     * Gets the current level of the avatar.
     * @return the current level of the avatar.
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Gets the current xp of the avatar.
     * @return the current xp of the avatar.
     */
    public int getXp() {
        return this.xp;
    }

    /**
     * Increment xp by 1.
     *
     * @return the current Level.
     */
    public Level increaseXp() {
        this.xp++;
        return this;
    }

    /**
     * Levels up by 1.
     *
     * @return the current level.
     */
    public Level levelUp() {
        return new Level(this.level + 1, this.xp);
    }

    /**
     * Checks if avatar can reach the next level.
     *
     * @return true if avatar can gain a level; false if otherwise.
     */
    public boolean canLevel() {
        if (this.xp >= xpNeeded(this.level)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * The simple algorithm to calculate the xp needed for the
     * next level.
     *
     * @param level the level of the avatar.
     * @return the xp needed to hit the next level.
     */
    private int xpNeeded(int level) {
        if (level <= 3) {
            return 5 * level;
        } else {
            return (3 * (level * level)) - ((level - 3) * 15);
        }
    }

    @Override
    public String toString() {
        return "Level: " + this.level + " | XP: " + this.xp;
    }

    @Override
    public String toTxt() {
        return this.level + " " + this.xp;
    }

}
