package owlmoney.logic.parser;

import java.util.Arrays;
import java.util.List;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.PlaceHolderEmptyCommand;
import owlmoney.logic.command.bank.ListInvestmentCommand;
import owlmoney.logic.command.bank.ListSavingsCommand;
import owlmoney.logic.command.goals.ListGoalsCommand;
import owlmoney.logic.parser.exception.ParserException;
import owlmoney.logic.command.card.ListCardCommand;
import owlmoney.logic.parser.bond.ParseAddBond;
import owlmoney.logic.parser.bond.ParseBond;
import owlmoney.logic.parser.bond.ParseDeleteBond;
import owlmoney.logic.parser.bond.ParseEditBond;
import owlmoney.logic.parser.bond.ParseListBond;
import owlmoney.logic.parser.card.ParseAddCard;
import owlmoney.logic.parser.card.ParseCard;
import owlmoney.logic.parser.card.ParseDeleteCard;
import owlmoney.logic.parser.card.ParseEditCard;
import owlmoney.logic.parser.goals.ParseAddGoals;
import owlmoney.logic.parser.goals.ParseDeleteGoals;
import owlmoney.logic.parser.goals.ParseEditGoals;
import owlmoney.logic.parser.goals.ParseGoals;
import owlmoney.logic.parser.investment.ParseAddInvestment;
import owlmoney.logic.parser.investment.ParseDeleteInvestment;
import owlmoney.logic.parser.investment.ParseEditInvestment;
import owlmoney.logic.parser.investment.ParseInvestment;
import owlmoney.logic.parser.saving.ParseAddSaving;
import owlmoney.logic.parser.saving.ParseDeleteSaving;
import owlmoney.logic.parser.saving.ParseEditSaving;
import owlmoney.logic.parser.saving.ParseSaving;
import owlmoney.logic.parser.transaction.deposit.ParseAddDeposit;
import owlmoney.logic.parser.transaction.deposit.ParseDeleteDeposit;
import owlmoney.logic.parser.transaction.deposit.ParseDeposit;
import owlmoney.logic.parser.transaction.deposit.ParseEditDeposit;
import owlmoney.logic.parser.transaction.deposit.ParseListDeposit;
import owlmoney.logic.parser.transaction.expenditure.ParseAddExpenditure;
import owlmoney.logic.parser.transaction.expenditure.ParseAddRecurringExpenditure;
import owlmoney.logic.parser.transaction.expenditure.ParseDeleteExpenditure;
import owlmoney.logic.parser.transaction.expenditure.ParseDeleteRecurringExpenditure;
import owlmoney.logic.parser.transaction.expenditure.ParseEditExpenditure;
import owlmoney.logic.parser.transaction.expenditure.ParseEditRecurringExpenditure;
import owlmoney.logic.parser.transaction.expenditure.ParseExpenditure;
import owlmoney.logic.parser.transaction.expenditure.ParseListExpenditure;
import owlmoney.logic.parser.transaction.expenditure.ParseListRecurringExpenditure;
import owlmoney.logic.parser.transaction.expenditure.ParseRecurringExpenditure;
import owlmoney.logic.parser.transfer.ParseTransfer;

/**
 * Represents the second layer of parsing for secondary category of command.
 * This determines what type of command the user desires after specifying the command.
 */

class ParseType extends Parser {

    /**
     * List of whitelisted keywords that the user can use.
     */
    private static final String[] TYPE_KEYWORDS = new String[] {
        "/savings", "/investment", "/cardexpenditure", "/bankexpenditure", "/goals", "/card",
        "/recurbankexp", "/bonds", "/profile", "/deposit", "/fund"
    };
    private static final List<String> TYPE_KEYWORD_LISTS = Arrays.asList(TYPE_KEYWORDS);
    private static final String BANK = "bank";
    private static final String CARD = "card";
    private static final String BOND = "bonds";
    private static final String GOALS = "goals";

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
        if ("/list".equals(command)) {
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
        if ("/delete".equals(command)) {
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
            throw new ParserException("You entered an invalid type for savings");
        case "/investment":
            if ("/add".equals(command)) {
                ParseInvestment parseAddInvestment = new ParseAddInvestment(rawData);
                parseAddInvestment.fillHashTable();
                parseAddInvestment.checkParameter();
                return parseAddInvestment.getCommand();
            } else if ("/edit".equals(command)) {
                ParseInvestment parseEditInvestment = new ParseEditInvestment(rawData);
                parseEditInvestment.fillHashTable();
                parseEditInvestment.checkParameter();
                return parseEditInvestment.getCommand();
            } else if ("/delete".equals(command)) {
                ParseInvestment parseDeleteInvestment = new ParseDeleteInvestment(rawData);
                parseDeleteInvestment.fillHashTable();
                parseDeleteInvestment.checkParameter();
                return parseDeleteInvestment.getCommand();
            } else if ("/list".equals(command)) {
                return new ListInvestmentCommand();
            }
            throw new ParserException("You entered an invalid type for investment");
        case "/bonds":
            if ("/add".equals(command)) {
                ParseBond parseAddBond = new ParseAddBond(rawData, BOND);
                parseAddBond.fillHashTable();
                parseAddBond.checkParameter();
                return parseAddBond.getCommand();
            } else if ("/edit".equals(command)) {
                ParseBond parseEditBond = new ParseEditBond(rawData, BOND);
                parseEditBond.fillHashTable();
                parseEditBond.checkParameter();
                return parseEditBond.getCommand();
            } else if ("/delete".equals(command)) {
                ParseBond parseDeleteBond = new ParseDeleteBond(rawData, BOND);
                parseDeleteBond.fillHashTable();
                parseDeleteBond.checkParameter();
                return parseDeleteBond.getCommand();
            } else if ("/list".equals(command)) {
                ParseBond parseListBond = new ParseListBond(rawData, BOND);
                parseListBond.fillHashTable();
                parseListBond.checkParameter();
                return parseListBond.getCommand();
            }
            throw new ParserException("You entered an invalid type for bond");
        case "/bankexpenditure":
            if ("/add".equals(command)) {
                ParseExpenditure addExp = new ParseAddExpenditure(rawData, BANK);
                addExp.fillHashTable();
                addExp.checkParameter();
                return addExp.getCommand();
            } else if ("/list".equals(command)) {
                ParseExpenditure listExp = new ParseListExpenditure(rawData, BANK);
                listExp.fillHashTable();
                listExp.checkParameter();
                return listExp.getCommand();
            } else if ("/delete".equals(command)) {
                ParseExpenditure deleteExp = new ParseDeleteExpenditure(rawData, BANK);
                deleteExp.fillHashTable();
                deleteExp.checkParameter();
                return deleteExp.getCommand();
            } else if ("/edit".equals(command)) {
                ParseExpenditure editExp = new ParseEditExpenditure(rawData, BANK);
                editExp.fillHashTable();
                editExp.checkParameter();
                return editExp.getCommand();
            }
            throw new ParserException("You entered an invalid type for bank expenditure");
        case "/cardexpenditure":
            if ("/add".equals(command)) {
                ParseExpenditure addExp = new ParseAddExpenditure(rawData, CARD);
                addExp.fillHashTable();
                addExp.checkParameter();
                return addExp.getCommand();
            } else if ("/list".equals(command)) {
                ParseExpenditure listExp = new ParseListExpenditure(rawData, CARD);
                listExp.fillHashTable();
                listExp.checkParameter();
                return listExp.getCommand();
            } else if ("/delete".equals(command)) {
                ParseExpenditure deleteExp = new ParseDeleteExpenditure(rawData, CARD);
                deleteExp.fillHashTable();
                deleteExp.checkParameter();
                return deleteExp.getCommand();
            } else if ("/edit".equals(command)) {
                ParseExpenditure editExp = new ParseEditExpenditure(rawData, CARD);
                editExp.fillHashTable();
                editExp.checkParameter();
                return editExp.getCommand();
            }
            throw new ParserException("You entered an invalid type for card expenditure");
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
            throw new ParserException("You entered an invalid type for deposit");
        case "/card":
            if ("/add".equals(command)) {
                ParseCard addCard = new ParseAddCard(rawData);
                addCard.fillHashTable();
                addCard.checkParameter();
                return addCard.getCommand();
            } else if ("/delete".equals(command)) {
                ParseCard deleteCard = new ParseDeleteCard(rawData);
                deleteCard.fillHashTable();
                deleteCard.checkParameter();
                return deleteCard.getCommand();
            } else if ("/list".equals(command)) {
                return new ListCardCommand();
            } else if ("/edit".equals(command)) {
                ParseCard editCard = new ParseEditCard(rawData);
                editCard.fillHashTable();
                editCard.checkParameter();
                return editCard.getCommand();
            }
            throw new ParserException("You entered an invalid type for card");
        case "/goals":
            if ("/add".equals(command)) {
                ParseGoals addGoals = new ParseAddGoals(rawData);
                addGoals.fillHashTable();
                addGoals.checkParameter();
                return addGoals.getCommand();
            } else if ("/delete".equals(command)) {
                ParseGoals deleteGoals = new ParseDeleteGoals(rawData);
                deleteGoals.fillHashTable();
                deleteGoals.checkParameter();
                return deleteGoals.getCommand();
            } else if ("/edit".equals(command)) {
                ParseGoals editGoals = new ParseEditGoals(rawData);
                editGoals.fillHashTable();
                editGoals.checkParameter();
                return editGoals.getCommand();
            } else if ("/list".equals(command)) {
                return new ListGoalsCommand();
            }
            throw new ParserException("You entered an invalid type for goals");
        case "/recurbankexp":
            if ("/add".equals(command)) {
                ParseRecurringExpenditure addRecurringExpenditure = new ParseAddRecurringExpenditure(rawData, BANK);
                addRecurringExpenditure.fillHashTable();
                addRecurringExpenditure.checkParameter();
                return addRecurringExpenditure.getCommand();
            } else if ("/delete".equals(command)) {
                ParseDeleteRecurringExpenditure
                        deleteRecurringExpenditure = new ParseDeleteRecurringExpenditure(rawData, BANK);
                deleteRecurringExpenditure.fillHashTable();
                deleteRecurringExpenditure.checkParameter();
                return deleteRecurringExpenditure.getCommand();
            } else if ("/edit".equals(command)) {
                ParseRecurringExpenditure editRecurringExpenditure = new ParseEditRecurringExpenditure(rawData, BANK);
                editRecurringExpenditure.fillHashTable();
                editRecurringExpenditure.checkParameter();
                return editRecurringExpenditure.getCommand();
            } else if ("/list".equals(command)) {
                ParseRecurringExpenditure listRecurringExpenditure = new ParseListRecurringExpenditure(rawData, BANK);
                listRecurringExpenditure.fillHashTable();
                listRecurringExpenditure.checkParameter();
                return listRecurringExpenditure.getCommand();
            }
            throw new ParserException("You entered an invalid type for recurbankexpenditure");
        case "/fund":
            if ("/transfer".equals(command)) {
                ParseTransfer parseTransfer = new ParseTransfer(rawData);
                parseTransfer.fillHashTable();
                parseTransfer.checkParameter();
                return parseTransfer.getCommand();
            }
            throw new ParserException("You entered an invalid type for transfer");
        default:
            throw new ParserException("You entered an invalid type");
        }
    }
}
