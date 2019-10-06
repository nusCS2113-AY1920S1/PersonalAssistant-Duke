package seedu.hustler.game.achievement;


import java.util.ArrayList;

/**
 * Achievements that the user can achieve in Hustler.
 */
public abstract class Achievements {

    /**
     *
     */
    private String achievementDescription;


    /**
     *
     */
    public Achievements(){
        this.achievementDescription = "Gained: ";
    }

    /**
     *
     * @return
     */
    public String ToString(){
        return "You have attained ";
    }




}
