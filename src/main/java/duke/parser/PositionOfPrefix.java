package duke.parser;

public class PositionOfPrefix {
    private int startPosition;
    private Prefix prefix;

    public PositionOfPrefix(Prefix prefix,int startPosition) {
        this.prefix = prefix;
        this.startPosition = startPosition;
    }

    int getStartPosition() {
        return startPosition;
    }

    Prefix getPrefix() {
        return prefix;
    }
}
