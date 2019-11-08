package exception;

public class NoTagFoundException extends WordUpException {
    public NoTagFoundException(String searchTag) {
        super(" OOPS: I could not find your tag: " + searchTag + "\n");
    }
}