package seedu.hustler.game.achievement;

/**
 * Achievements that the user can achieve in Hustler.
 */
public abstract class Achievements implements Write,Achievement {
    /**
     * Total points the user has.
     */
    public static int totalPoints;

    /**
     * Format which the achievement will be printed out.
     * @return
     */
    public String toString() {
        return "Gained:";
    }
}
