package seedu.hustler.game.achievement;

/**
 * The interface that all classes in achievement must inherit to
 * convert them to proper format before storing them in a txtfile.
 */
public interface Write {

    /**
     * Convert object to text format to be written into achievement.txt.
     * @return String format of achievement.
     */
    public String toTxt();

}
