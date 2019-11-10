package duke.parser.utilities;

/**
 * Stores the starting position for a given token.
 */
public class PositionOfToken {
    private int startPosition;
    private final Token token;

    PositionOfToken(Token token, int startPosition) {
        this.token = token;
        this.startPosition = startPosition;
    }

    int getStartPosition() {
        return startPosition;
    }

    Token getToken() {
        return token;
    }
}
