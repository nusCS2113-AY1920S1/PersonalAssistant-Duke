package duke.parser.utilities;

public class Token {
    private final String token;

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return token;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Token)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Token otherPrefix = (Token) obj;
        return otherPrefix.getToken().equals(getToken());
    }

    @Override
    public int hashCode() {
        return token == null ? 0 : token.hashCode();
    }
}

