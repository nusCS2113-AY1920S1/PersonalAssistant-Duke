package seedu.duke.email.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * A pair of keyword with its possible expressions.
 */
public class KeywordPair {
    private String keyword;
    private ArrayList<String> expressions;

    /**
     * Constructs a keyword pair.
     *
     * @param keyword     the value of keyword looked for
     * @param expressions the possible expressions of that keyword
     */
    public KeywordPair(String keyword, ArrayList<String> expressions) {
        this.keyword = keyword;
        this.expressions = expressions;
    }

    /**
     * Constructs a keyword pair with only keyword. Expression will be the same as the keyword by
     * default.
     *
     * @param keyword the value of keyword looked for
     */
    public KeywordPair(String keyword) {
        this.keyword = keyword;
        this.expressions = new ArrayList<>(List.of(keyword));
    }

    public String getKeyword() {
        return this.keyword;
    }

    public ArrayList<String> getExpressions() {
        return this.expressions;
    }
}
