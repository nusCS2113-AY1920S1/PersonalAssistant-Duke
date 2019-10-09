package owlmoney.logic.parser.transaction.deposit;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.print.DocFlavor;

import owlmoney.logic.command.Command;
import owlmoney.logic.parser.ParseRawData;
import owlmoney.logic.parser.exception.ParserException;
import owlmoney.logic.regex.RegexUtil;

public abstract class ParseDeposit {
    HashMap<String, String> expendituresParameters = new HashMap<String, String>();
    private ParseRawData parseRawData = new ParseRawData();
    private String rawData;
    private static final String[] EXPENDITURE_KEYWORD = new String[] {
            "/amount", "/date", "/desc", "/category", "/to", "/transno","/from", "/num"
    };
    private static final List<String> EXPENDITURE_KEYWORD_LISTS = Arrays.asList(EXPENDITURE_KEYWORD);
    static final String AMOUNT = "/amount";
    static final String DATE = "/date";
    static final String DESCRIPTION = "/desc";
    static final String TO = "/to";
    static final String TRANSNO = "/transno";
    static final String FROM = "/from";
    static final String NUM = "/num";

    ParseDeposit(String data) {
        this.rawData = data;
    }

    void checkRedundantParameter(String parameter, String command) throws ParserException {
        if (rawData.contains(parameter)) {
            throw new ParserException(command + " /deposit should not contain " + parameter);
        }
    }

    void checkFirstParameter() throws ParserException {
        String[] rawDateSplit = rawData.split(" ", 2);
        if(!EXPENDITURE_KEYWORD_LISTS.contains(rawDateSplit[0])) {
            throw new ParserException("Incorrect command syntax");
        }
    }

    public void fillHashTable() throws ParserException {
        expendituresParameters.put(AMOUNT,
                parseRawData.extractParameter(rawData, AMOUNT, EXPENDITURE_KEYWORD));
        expendituresParameters.put(DATE,
                parseRawData.extractParameter(rawData, DATE, EXPENDITURE_KEYWORD));
        expendituresParameters.put(DESCRIPTION,
                parseRawData.extractParameter(rawData, DESCRIPTION, EXPENDITURE_KEYWORD));
        expendituresParameters.put(TO,
                parseRawData.extractParameter(rawData, TO, EXPENDITURE_KEYWORD));
        expendituresParameters.put(TRANSNO,
                parseRawData.extractParameter(rawData, TRANSNO, EXPENDITURE_KEYWORD));
        expendituresParameters.put(FROM,
                parseRawData.extractParameter(rawData, FROM, EXPENDITURE_KEYWORD));
        expendituresParameters.put(NUM,
                parseRawData.extractParameter(rawData, NUM, EXPENDITURE_KEYWORD));
    }

    void checkAmount(String valueString) throws ParserException {
        if(!RegexUtil.regexCheckMoney(valueString)) {
            throw new ParserException("/amount can only be numbers with at most 9 digits and 2 decimal places");
        }
    }

    void checkInt(String variable, String valueString) throws ParserException {
        if(!RegexUtil.regexCheckListNumber(valueString)) {
            throw new ParserException(variable + " can only be a positive number with at most 9 digits");
        }
    }

    void checkDescription(String descString) throws ParserException {
        if(!RegexUtil.regexCheckDescription(descString)) {
            throw new ParserException("/desc can only contain numbers and letters and at most 50 characters");
        }
    }

    void checkName(String nameString, String variable) throws ParserException {
        if(!RegexUtil.regexCheckName(nameString)) {
            throw new ParserException(variable + " can only contain letters and at most 30 characters");
        }
    }

    Date checkDate(String dateString) throws ParserException {
        if(RegexUtil.regexCheckDateFormat(dateString)) {
            DateFormat temp = new SimpleDateFormat("dd/MM/yyyy");
            temp.setLenient(false);
            Date date;
            try {
                date = temp.parse(dateString);
                if(date.compareTo(new Date()) > 0) {
                    throw new ParserException("/date cannot be after today");
                }
                return date;
            } catch (ParseException e) {
                throw new ParserException("Incorrect date format."
                        + " Date format is dd/mm/yyyy in year range of 1900-2099");
            }
        }
        throw new ParserException("Incorrect date format." + " Date format is dd/mm/yyyy in year range of 1900-2099");
    }

    public abstract void checkParameter() throws ParserException;

    public abstract Command getCommand();
}
