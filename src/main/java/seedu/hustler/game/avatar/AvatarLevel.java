package seedu.hustler.game.avatar;

/**
 * Class representing the level of the avatar. Also contains the current xp.
 */
public class AvatarLevel implements Level {

    /**
     * Integer level of avatar.
     */
    private final int level;

    /**
     * Integer value of XP.
     */
    private final int xp;

    /**
     * Constructs a default Level.
     */
    public AvatarLevel() {
        this.level = 1;
        this.xp = 0;
    }

    /**
     * Constructs Level with the given level and xp.
     * @param level the level of the avatar.
     * @param xp the xp of the avatar.
     */
    public AvatarLevel(int level, int xp) {
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
     * Gets the current xp of the avatar
     * @return the current level of the avatar.
     */
    public int getXp() {
        return this.xp;
    }

    /**
     * Increment xp by 1.
     * @return the current Level.
     */
    public AvatarLevel increaseXp() {
        return new AvatarLevel(this.level, this.xp + 1);
    }

    /**
     * Levels up by 1.
     * @return the current level.
     */
    public AvatarLevel upLevel() {
        return new AvatarLevel(this.level + 1, this.xp);
    }

    /**
     * Checks if avatar can reach the next level.
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
     * @param level the level of the avatar.
     * @return the xp needed to hit the next level.
     */
    public int xpNeeded(int level) {
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

    @Override
    public boolean equals(Object obj) {
        return obj == this || (obj instanceof AvatarLevel
                && this.toString().equals(obj.toString()));
    }

}
