package seedu.hustler.game.avatar;

public interface Level extends Convertible {
    int getLevel();
    int getXp();
    Level increaseXp();
    Level upLevel();
    boolean canLevel();
    int xpNeeded(int level);
}
