package duke.parser;

public class KeywordAndEdit {

    private String keyword;
    private String edit;

    public KeywordAndEdit(String keyword, String edit) {
        this.edit = edit;
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getEdit() {
        return edit;
    }
}
