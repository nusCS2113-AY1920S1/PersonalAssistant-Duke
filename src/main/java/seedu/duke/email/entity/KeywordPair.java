package seedu.duke.email.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    /**
     * Construct kewordPair from a json object.
     *
     * @param json a json object containing full information of a keyword pair
     */
    public KeywordPair(JSONObject json) throws JSONException {
        keyword = json.getString("keyword");
        expressions = new ArrayList<>();
        JSONArray expressionArray = json.getJSONArray("expressions");
        for (int i = 0; i < expressionArray.length(); i++) {
            expressions.add(expressionArray.getString(i));
        }
    }

    public String getKeyword() {
        return this.keyword;
    }

    public ArrayList<String> getExpressions() {
        return this.expressions;
    }

    /**
     * Adds the expression to the expression list if not already exists.
     *
     * @param newExpression the new expression to be added
     */
    public void addExpression(String newExpression) {
        for (String expression: expressions) {
            if (expression.equals(newExpression)) {
                return;
            }
        }
        this.expressions.add(newExpression);
    }

    public JSONObject toJsonObject() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("keyword", this.keyword);
        JSONArray expressionArray = new JSONArray();
        for (String expression : expressions) {
            expressionArray.put(expression);
        }
        json.put("expressions", expressionArray);
        return json;
    }
}
