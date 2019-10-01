package owlmoney.logic.parser.expenditure;

import java.util.HashMap;

import owlmoney.logic.parser.ParseRawData;
import owlmoney.logic.parser.exception.ParserException;

public abstract class ParseExpenditure {
    HashMap<String, String> expendituresParameters = new HashMap<String, String>();
    ParseRawData parseRawData = new ParseRawData();
    String rawData;
    static final String[] SAVINGS_KEYWORD = new String[] {"/name", "/amount", "/date", "/description", "/category",
            "/from", "/expno"};
    static final String AMOUNT = "/amount";
    static final String DATE = "/date";
    static final String DESCRIPTION = "/description";
    static final String CATEGORY = "/category";
    static final String FROM = "/from";
    static final String EXPNO = "/expno";

    ParseExpenditure(String data) {
        this.rawData = data;
    }

    public void fillHashTable() throws ParserException {
        expendituresParameters.put(AMOUNT,
                parseRawData.extractParameter(rawData,AMOUNT, SAVINGS_KEYWORD));
        expendituresParameters.put(DATE,
                parseRawData.extractParameter(rawData,DATE, SAVINGS_KEYWORD));
        expendituresParameters.put(DESCRIPTION,
                parseRawData.extractParameter(rawData,DESCRIPTION, SAVINGS_KEYWORD));
        expendituresParameters.put(CATEGORY,
                parseRawData.extractParameter(rawData,CATEGORY, SAVINGS_KEYWORD));
        expendituresParameters.put(FROM,
                parseRawData.extractParameter(rawData,FROM, SAVINGS_KEYWORD));
        expendituresParameters.put(EXPNO,
                parseRawData.extractParameter(rawData,EXPNO, SAVINGS_KEYWORD));
    }

    void checkIfDouble(String valueString) throws ParserException {
        try {
            Double value = Double.parseDouble(valueString);
        } catch (NumberFormatException e) {
            throw new ParserException("Amount can only be numbers with at most 2 decimal places");
        }
    }

    public abstract void checkParameter() throws ParserException;
}
