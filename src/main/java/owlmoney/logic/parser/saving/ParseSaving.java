package owlmoney.logic.parser.saving;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import owlmoney.logic.command.Command;
import owlmoney.logic.parser.ParseRawData;
import owlmoney.logic.parser.exception.ParserException;
import owlmoney.logic.regex.RegexUtil;

public abstract class ParseSaving {
    HashMap<String, String> savingsParameters = new HashMap<String, String>();
    private ParseRawData parseRawData = new ParseRawData();
    private String rawData;
    private static final String[] SAVINGS_KEYWORD = new String[] {"/amount", "/income", "/name", "/newname"};
    static final String AMOUNT = "/amount";
    static final String INCOME = "/income";
    static final String NAME = "/name";
    static final String NEW_NAME = "/newname";
    private static final List<String> SAVINGS_KEYWORD_LISTS = Arrays.asList(SAVINGS_KEYWORD);

    ParseSaving(String data) {
        this.rawData = data;
    }

    void checkRedundantParameter(String parameter, String command) throws ParserException {
        if (rawData.contains(parameter)) {
            throw new ParserException(command + "/savings should not contain " + parameter);
        }
    }

    void checkFirstParameter() throws ParserException {
        String[] rawDateSplit = rawData.split(" ", 2);
        if (!SAVINGS_KEYWORD_LISTS.contains(rawDateSplit[0])) {
            throw new ParserException("Incorrect command syntax");
        }
    }

    public void fillHashTable() throws ParserException {
        savingsParameters.put(AMOUNT,
                parseRawData.extractParameter(rawData, AMOUNT, SAVINGS_KEYWORD));
        savingsParameters.put(INCOME,
                parseRawData.extractParameter(rawData, INCOME, SAVINGS_KEYWORD));
        savingsParameters.put(NAME,
                parseRawData.extractParameter(rawData, NAME, SAVINGS_KEYWORD));
        savingsParameters.put(NEW_NAME,
                parseRawData.extractParameter(rawData, NEW_NAME, SAVINGS_KEYWORD));
    }

    void checkAmount(String valueString) throws ParserException {
        if (!RegexUtil.regexCheckBankAmount(valueString)) {
            throw new ParserException("/amount can only be numbers with at most 9 digits and 2 decimal places");
        }
    }

    void checkIncome(String valueString) throws ParserException {
        if (!RegexUtil.regexCheckBankAmount(valueString)) {
            throw new ParserException("/income can only be numbers with at most 9 digits and 2 decimal places");
        }
    }

    void checkName(String key, String nameString) throws ParserException {
        if (!RegexUtil.regexCheckName(nameString)) {
            throw new ParserException(key + " can only contain letters and at most 50 characters");
        }
    }

    public abstract void checkParameter() throws ParserException;

    public abstract Command getCommand();
}
