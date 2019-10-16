package seedu.hustler.task.variables;

/**
 * The class that stores the difficulty of the task.
 */
public class Difficulty {

    /**
     * Enumerator that separates the level of the difficulty.
     */
    enum Level {
        L,
        M,
        H
    }

    /**
     * The level of the difficulty.
     */
    private Level level;

    /**
     * Constructs a difficulty level based on user input.
     * Will automatically set default difficulty as M (medium)
     * if user input cannot be parsed.
     * @param difficulty the level of the difficulty.
     */
    public Difficulty(String difficulty) {
        switch (difficulty.toLowerCase()) {
        case "l":
        case "low":
            this.level = Level.L;
            break;
        case "m":
        case "medium":
        default:
            this.level = Level.M;
            break;
        case "h":
        case "high":
            this.level = Level.H;
        }
    }

    public String toString() {
        return "[" + this.level.toString() + "]";
    }

    public String toSaveFormat() {
        return this.level.toString();
    }

    public Level getLevel() {
        return this.level;
    }
}
