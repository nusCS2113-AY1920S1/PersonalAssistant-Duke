package exception;

public class NoSynonymFoundException extends WordUpException {
    public NoSynonymFoundException(String searchTag) {
        super(" OOPS: No Synonyms found for : " + searchTag + "\n");
    }
}
