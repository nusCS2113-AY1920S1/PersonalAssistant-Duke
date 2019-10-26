package duke.parser;

public class KeywordAndField {

    private String keyword;
    private String field;

    public KeywordAndField(String keyword, String field) {
        this.field = field;
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getField() {
        return field;
    }
}
