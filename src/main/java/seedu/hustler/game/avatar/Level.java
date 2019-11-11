package seedu.hustler.game.avatar;

/**
 * The level interface that AvatarLevel must inherit in order
 * to properly level.
 */
public interface Level extends Convertible {

    /**
     * Gets the current level of the avatar.
     * @return the current level of the avatar.
     */
    int getLevel();

    /**
     * Gets the current xp of the avatar.
     * @return the current level of the avatar.
     */
    int getXp();

    /**
     * Increment xp by 1.
     * @return the new instance of the updated Level.
     */
    Level increaseXp();

    /**
     * Levels up by 1.
     * @return the new instance of the updated Level.
     */
    Level upLevel();

    /**
     * Checks if avatar can reach the next level.
     * @return true if avatar can gain a level; false if otherwise.
     */
    boolean canLevel();

    /**
     * The simple algorithm to calculate the xp needed for the
     * next level.
     * @param level the level of the avatar.
     * @return the xp needed to hit the next level.
     */
    int xpNeeded(int level);
}
