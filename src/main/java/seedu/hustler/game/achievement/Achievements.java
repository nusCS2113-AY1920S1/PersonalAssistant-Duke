package seedu.hustler.game.achievement;


import java.util.ArrayList;

/**
 * Achievements that the user can achieve in Hustler.
 */
public abstract class Achievements {

    /**
     * List of achievement that the user unlocked.
     */
    private ArrayList<String> achievementUnlocked = new ArrayList<>();

    /**
     * List of achievement that can be unlocked.
     */
    private ArrayList<String> achievementList = new ArrayList<>();

    /**
     * User's current point.
     */
    private int points = 0;

}
