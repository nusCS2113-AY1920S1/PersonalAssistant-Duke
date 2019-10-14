package owlmoney.logic.parser.goals;

import owlmoney.logic.command.Command;
import owlmoney.logic.parser.ParseRawData;
import owlmoney.logic.parser.exception.ParserException;
import owlmoney.logic.regex.RegexUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public abstract class ParseGoals {
    HashMap<String, String> goalsParameters = new HashMap<>();
    ParseRawData parseRawData = new ParseRawData();
    String rawData;

    private static final String[] GOALS_KEYWORD = new String[] {"/name", "income", "/amount"};
    private static final List<String> GOALS_KEYWORD_LISTS = Arrays.asList(GOALS_KEYWORD);
    static final String NAME = "/name";
    static final String AMOUNT = "/amount";
    static final String DATE = "/date";
    static final String BY = "/by";
    static final String NEW_NAME = "/newname";
    static final String FROM = "/from";
    static final String NUM = "/num";

    ParseGoals(String data) {
        this.rawData = data;
    }

    void checkRedundantParameter(String parameter, String command) throws ParserException {
        if (rawData.contains(parameter)) {
            throw new ParserException(command + " /goals should not contain " + parameter);
        }
    }

    void checkFirstParameter() throws ParserException {
        String[] rawDataSplit = rawData.split(" ", 2);
        if (!GOALS_KEYWORD_LISTS.contains(rawDataSplit[0])) {
            throw new ParserException("Incorrect parameter" + rawDataSplit[0]);
        }
    }
    public void fillHashTable() throws ParserException {
        goalsParameters.put(NAME,
                parseRawData.extractParameter(rawData,NAME, GOALS_KEYWORD));
        goalsParameters.put(AMOUNT,
                parseRawData.extractParameter(rawData,AMOUNT,GOALS_KEYWORD));
        goalsParameters.put(BY,
                parseRawData.extractParameter(rawData,BY,GOALS_KEYWORD));
    }

    void checkAmount(String value) throws ParserException {
        if (!RegexUtil.regexCheckMoney(value)) {
            throw new ParserException("/amount can only be positive numbers"
                    + " with at most 9 digits and 2 decimal places");
        }
    }

    void checkName(String key, String name ) throws ParserException {
        if (!RegexUtil.regexCheckName(name)) {
            throw new ParserException("/name can only contain letters and at most 30 characters");
        }
    }

    Date checkDate(String dateString) throws ParserException {
        if (RegexUtil.regexCheckDateFormat(dateString)) {
            DateFormat temp = new SimpleDateFormat("dd/MM/yyyy");
            temp.setLenient(false);
            Date date;
            try {
                date = temp.parse(dateString);
                if (date.compareTo(new Date()) < 0) {
                    throw new ParserException("/date has already passed");
                }
                return date;
            } catch (ParseException e) {
                throw new ParserException("Incorrect date format."
                        + " Date format is dd/mm/yyyy in year range of 1900-2099");
            }
        }
        throw new ParserException("Incorrect date format." + " Date format is dd/mm/yyyy in year range of 1900-2099");
    }

    void checkInt(String variable, String valueString) throws ParserException {
        if (!RegexUtil.regexCheckListNumber(valueString)) {
            throw new ParserException(variable + " can only be a positive number with at most 9 digits");
        }
    }

    public abstract void checkParameter() throws ParserException;

    public abstract Command getCommand();
}
