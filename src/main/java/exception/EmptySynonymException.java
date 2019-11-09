package exception;

public class EmptySynonymException extends WordUpException {
    public EmptySynonymException() {
        super(" OOPS: One of your input synonyms is empty");
    }
}
