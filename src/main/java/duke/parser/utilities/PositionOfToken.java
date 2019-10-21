package duke.parser.utilities;

public class PositionOfToken {
    private int startPosition;
    private Token token;

    public PositionOfToken(Token token, int startPosition) {
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
