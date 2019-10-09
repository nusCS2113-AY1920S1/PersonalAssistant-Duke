package owlmoney.logic.parser;

import java.util.Arrays;
import java.util.List;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.PlaceHolderEmptyCommand;
import owlmoney.logic.command.bank.ListSavingsCommand;
import owlmoney.logic.parser.exception.ParserException;
import owlmoney.logic.parser.saving.ParseSaving;
import owlmoney.logic.parser.transaction.deposit.ParseAddDeposit;
import owlmoney.logic.parser.transaction.deposit.ParseDeleteDeposit;
import owlmoney.logic.parser.transaction.deposit.ParseDeposit;
import owlmoney.logic.parser.transaction.deposit.ParseEditDeposit;
import owlmoney.logic.parser.transaction.deposit.ParseListDeposit;
import owlmoney.logic.parser.transaction.expenditure.ParseAddExpenditure;
import owlmoney.logic.parser.transaction.expenditure.ParseDeleteExpenditure;
import owlmoney.logic.parser.transaction.expenditure.ParseEditExpenditure;
import owlmoney.logic.parser.transaction.expenditure.ParseExpenditure;
import owlmoney.logic.parser.transaction.expenditure.ParseListExpenditure;
import owlmoney.logic.parser.saving.ParseAddSaving;
import owlmoney.logic.parser.saving.ParseDeleteSaving;
import owlmoney.logic.parser.saving.ParseEditSaving;

/**
 * Represents the second layer of parsing for secondary category of command.
 * This determines what type of command the user desires after specifying the command.
 */

class ParseType extends Parser {

    /**
     * List of whitelisted keywords that the user can use.
     */
    private static final String[] TYPE_KEYWORDS = new String[] {
            "/savings",
            "/investment",
            "/expenditure",
            "/goals",
            "/card",
            "/recurexpenditure",
            "/bonds",
            "/profile",
            "/deposit"
    };
    private static final List<String> TYPE_KEYWORD_LISTS = Arrays.asList(TYPE_KEYWORDS);

    /**
     * Determines the type of command and checks if it is of valid type.
     * After determining that it is of a legal type.
     * The type is extracted just like how the first field was extracted when extracting command.
     *
     * @param command The command previously extracted from the first field of user input.
     * @param data    The remaining user input string with command removed.
     * @return The raw data left with command and type removed.
     * @throws ParserException if the user specified an invalid type.
     */
    Command parseData(String command, String data) throws ParserException {
        String type = parseFirstField(data);
        if (!TYPE_KEYWORD_LISTS.contains(type)) {
            throw new ParserException(type + " is an invalid type");
        }
        String rawData;
        if (command.equals("/list")) {
            rawData = removeListFirstField(data, type);
        } else {
            rawData = removeFirstField(data, type);
        }
        return parseTypeMenu(command, type, rawData);
    }

    /**
     * Checks if the user wants to delete profile.
     *
     * @param command The extracted first field from the initial user input that determines the command.
     * @throws ParserException if the user wants to delete his profile.
     */
    private void isDeleteProfile(String command) throws ParserException {
        if (command.equals("/delete")) {
            throw new ParserException("Profile cannot be deleted");
        }
    }

    /**
     * The parseTypeMenu determines what type of command object to generate based on the command and type.
     *
     * @param command The command extracted from the initial first field.
     * @param type    The type of command extracted from the subsequent first field after first layer of parsing.
     * @param rawData The remaining data after removing command and type.
     * @return The command object that is required to be executed.
     * @throws ParserException when an invalid type if specified.
     */
    private Command parseTypeMenu(String command, String type, String rawData) throws ParserException {
        switch (type) {
        case "/profile":
            isDeleteProfile(command);
            return new PlaceHolderEmptyCommand();
        case "/savings":
            if ("/add".equals(command)) {
                ParseSaving parseAddSaving = new ParseAddSaving(rawData);
                parseAddSaving.fillHashTable();
                parseAddSaving.checkParameter();
                return parseAddSaving.getCommand();
            } else if ("/edit".equals(command)) {
                ParseSaving parseEditSaving = new ParseEditSaving(rawData);
                parseEditSaving.fillHashTable();
                parseEditSaving.checkParameter();
                return parseEditSaving.getCommand();
            } else if ("/delete".equals(command)) {
                ParseSaving parseDeleteSaving = new ParseDeleteSaving(rawData);
                parseDeleteSaving.fillHashTable();
                parseDeleteSaving.checkParameter();
                return parseDeleteSaving.getCommand();
            } else if ("/list".equals(command)) {
                return new ListSavingsCommand();
            }
            throw new ParserException("You entered an invalid type");
        case "/investment":
            return new PlaceHolderEmptyCommand();
        case "/expenditure":
            if ("/add".equals(command)) {
                ParseExpenditure addExp = new ParseAddExpenditure(rawData);
                addExp.fillHashTable();
                addExp.checkParameter();
                return addExp.getCommand();
            } else if ("/list".equals(command)) {
                ParseExpenditure listExp = new ParseListExpenditure(rawData);
                listExp.fillHashTable();
                listExp.checkParameter();
                return listExp.getCommand();
            } else if ("/delete".equals(command)) {
                ParseExpenditure deleteExp = new ParseDeleteExpenditure(rawData);
                deleteExp.fillHashTable();
                deleteExp.checkParameter();
                return deleteExp.getCommand();
            } else if ("/edit".equals(command)) {
                ParseExpenditure editExp = new ParseEditExpenditure(rawData);
                editExp.fillHashTable();
                editExp.checkParameter();
                return editExp.getCommand();
            }
            throw new ParserException("You entered an invalid type");
        case "/deposit":
            if ("/add".equals(command)) {
                ParseDeposit addDep = new ParseAddDeposit(rawData);
                addDep.fillHashTable();
                addDep.checkParameter();
                return addDep.getCommand();
            } else if ("/list".equals(command)) {
                ParseDeposit listDep = new ParseListDeposit(rawData);
                listDep.fillHashTable();
                listDep.checkParameter();
                return listDep.getCommand();
            } else if ("/delete".equals(command)) {
                ParseDeposit deleteDep = new ParseDeleteDeposit(rawData);
                deleteDep.fillHashTable();
                deleteDep.checkParameter();
                return deleteDep.getCommand();
            } else if ("/edit".equals(command)) {
                ParseDeposit editDep = new ParseEditDeposit(rawData);
                editDep.fillHashTable();
                editDep.checkParameter();
                return editDep.getCommand();
            }
            throw new ParserException("You entered an invalid type");
        case "/card":
            return new PlaceHolderEmptyCommand();
        default:
            throw new ParserException("You entered an invalid type");
        }
    }
}
