package owlmoney.logic.parser;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.bank.ListInvestmentCommand;
import owlmoney.logic.command.bank.ListSavingsCommand;
import owlmoney.logic.command.card.ListCardCommand;
import owlmoney.logic.command.goals.ListAchievementCommand;
import owlmoney.logic.command.goals.ListGoalsCommand;
import owlmoney.logic.parser.bond.ParseAddBond;
import owlmoney.logic.parser.bond.ParseBond;
import owlmoney.logic.parser.bond.ParseDeleteBond;
import owlmoney.logic.parser.bond.ParseEditBond;
import owlmoney.logic.parser.bond.ParseListBond;
import owlmoney.logic.parser.card.ParseAddCard;
import owlmoney.logic.parser.card.ParseCard;
import owlmoney.logic.parser.card.ParseDeleteCard;
import owlmoney.logic.parser.card.ParseEditCard;
import owlmoney.logic.parser.cardbill.ParseAddCardBill;
import owlmoney.logic.parser.cardbill.ParseCardBill;
import owlmoney.logic.parser.cardbill.ParseDeleteCardBill;
import owlmoney.logic.parser.exception.ParserException;
import owlmoney.logic.parser.find.ParseFindBankOrCard;
import owlmoney.logic.parser.find.ParseFindBond;
import owlmoney.logic.parser.find.ParseFindRecurring;
import owlmoney.logic.parser.find.ParseFindTransaction;
import owlmoney.logic.parser.goals.ParseAddGoals;
import owlmoney.logic.parser.goals.ParseDeleteGoals;
import owlmoney.logic.parser.goals.ParseEditGoals;
import owlmoney.logic.parser.goals.ParseGoals;
import owlmoney.logic.parser.investment.ParseAddInvestment;
import owlmoney.logic.parser.investment.ParseDeleteInvestment;
import owlmoney.logic.parser.investment.ParseEditInvestment;
import owlmoney.logic.parser.investment.ParseInvestment;
import owlmoney.logic.parser.profile.ParseEditProfile;
import owlmoney.logic.parser.saving.ParseAddSaving;
import owlmoney.logic.parser.saving.ParseDeleteSaving;
import owlmoney.logic.parser.saving.ParseEditSaving;
import owlmoney.logic.parser.saving.ParseSaving;
import owlmoney.logic.parser.transaction.deposit.ParseAddDeposit;
import owlmoney.logic.parser.transaction.deposit.ParseDeleteDeposit;
import owlmoney.logic.parser.transaction.deposit.ParseEditDeposit;
import owlmoney.logic.parser.transaction.deposit.ParseDeposit;
import owlmoney.logic.parser.transaction.deposit.ParseListDeposit;
import owlmoney.logic.parser.transaction.expenditure.ParseRecurringExpenditure;
import owlmoney.logic.parser.transaction.expenditure.ParseAddRecurringExpenditure;
import owlmoney.logic.parser.transaction.expenditure.ParseDeleteRecurringExpenditure;
import owlmoney.logic.parser.transaction.expenditure.ParseEditRecurringExpenditure;
import owlmoney.logic.parser.transaction.expenditure.ParseListRecurringExpenditure;
import owlmoney.logic.parser.transaction.expenditure.ParseExpenditure;
import owlmoney.logic.parser.transaction.expenditure.ParseAddExpenditure;
import owlmoney.logic.parser.transaction.expenditure.ParseEditExpenditure;
import owlmoney.logic.parser.transaction.expenditure.ParseDeleteExpenditure;
import owlmoney.logic.parser.transaction.expenditure.ParseListExpenditure;
import owlmoney.logic.parser.transfer.ParseTransfer;

import java.util.Arrays;
import java.util.List;

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
        "/recurbankexp", "/bonds", "/profile", "/deposit", "/fund", "/banktransaction", "/cardtransaction", "/cardbill",
        "/achievement"
    };
    private static final List<String> TYPE_KEYWORD_LISTS = Arrays.asList(TYPE_KEYWORDS);
    private static final String BANK = "bank";
    private static final String CARD = "card";
    private static final String SAVING = "saving";
    private static final String INVESTMENT = "investment";
    private static final String BOND = "bonds";
    private static final String RECURRING = "recurring";
    private static final String ADD_COMMAND = "/add";
    private static final String EDIT_COMMAND = "/edit";
    private static final String DELETE_COMMAND = "/delete";
    private static final String LIST_COMMAND = "/list";
    private static final String FIND_COMMAND = "/find";
    private static final String TRANSFER_COMMAND = "/transfer";
    private static final String PROFILE_COMMANDTYPE = "/profile";
    private static final String SAVINGS_COMMANDTYPE = "/savings";
    private static final String INVESTMENT_COMMANDTYPE = "/investment";
    private static final String BONDS_COMMANDTYPE = "/bonds";
    private static final String BANKEXPENDITURE_COMMANDTYPE = "/bankexpenditure";
    private static final String CARDEXPENDITURE_COMMANDTYPE = "/cardexpenditure";
    private static final String DEPOSIT_COMMANDTYPE = "/deposit";
    private static final String CARD_COMMANDTYPE = "/card";
    private static final String GOALS_COMMANDTYPE = "/goals";
    private static final String RECURRINGEXPENDITURE_COMMANDTYPE = "/recurbankexp";
    private static final String FUND_COMMANDTYPE = "/fund";
    private static final String BANKTRANSACTION_COMMANDTYPE = "/banktransaction";
    private static final String CARDTRANSACTION_COMMANDTYPE = "/cardtransaction";
    private static final String CARDBILL_COMMANDTYPE = "/cardbill";
    private static final String ACHIEVEMENT_COMMANDTYPE = "/achievement";

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
        if (LIST_COMMAND.equals(command)) {
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
        if (DELETE_COMMAND.equals(command)) {
            throw new ParserException("Profile cannot be deleted");
        }
    }

    /**
     * Checks which operation to be performed for Profile.
     *
     * @param command The command extracted from the initial first field.
     * @param rawData The remaining data after removing command and type.
     * @return The command object that is required to be executed.
     * @throws ParserException when an invalid type is specified.
     */
    private Command menuForProfile(String command, String rawData) throws ParserException {
        if (EDIT_COMMAND.equals(command)) {
            ParseEditProfile parseEditProfile = new ParseEditProfile(rawData);
            parseEditProfile.fillHashTable();
            parseEditProfile.checkParameter();
            return parseEditProfile.getCommand();
        } else if (DELETE_COMMAND.equals(command)) {
            isDeleteProfile(command);
        }
        throw new ParserException("You entered an invalid type for profile");
    }

    /**
     * Checks which operation to be performed for Savings.
     *
     * @param command The command extracted from the initial first field.
     * @param rawData The remaining data after removing command and type.
     * @return The command object that is required to be executed.
     * @throws ParserException when an invalid type is specified.
     */
    private Command menuForSavings(String command, String rawData) throws ParserException {
        if (ADD_COMMAND.equals(command)) {
            ParseSaving parseAddSaving = new ParseAddSaving(rawData);
            parseAddSaving.fillHashTable();
            parseAddSaving.checkParameter();
            return parseAddSaving.getCommand();
        } else if (EDIT_COMMAND.equals(command)) {
            ParseSaving parseEditSaving = new ParseEditSaving(rawData);
            parseEditSaving.fillHashTable();
            parseEditSaving.checkParameter();
            return parseEditSaving.getCommand();
        } else if (DELETE_COMMAND.equals(command)) {
            ParseSaving parseDeleteSaving = new ParseDeleteSaving(rawData);
            parseDeleteSaving.fillHashTable();
            parseDeleteSaving.checkParameter();
            return parseDeleteSaving.getCommand();
        } else if (LIST_COMMAND.equals(command)) {
            return new ListSavingsCommand();
        } else if (FIND_COMMAND.equals(command)) {
            ParseFindBankOrCard parseFindSaving = new ParseFindBankOrCard(rawData, SAVING);
            parseFindSaving.fillHashTable();
            parseFindSaving.checkParameter();
            return parseFindSaving.getCommand();
        }
        throw new ParserException("You entered an invalid type for savings");
    }

    /**
     * Checks which operation to be performed for Investment.
     *
     * @param command The command extracted from the initial first field.
     * @param rawData The remaining data after removing command and type.
     * @return The command object that is required to be executed.
     * @throws ParserException when an invalid type is specified.
     */
    private Command menuForInvestment(String command, String rawData) throws ParserException {
        if (ADD_COMMAND.equals(command)) {
            ParseInvestment parseAddInvestment = new ParseAddInvestment(rawData);
            parseAddInvestment.fillHashTable();
            parseAddInvestment.checkParameter();
            return parseAddInvestment.getCommand();
        } else if (EDIT_COMMAND.equals(command)) {
            ParseInvestment parseEditInvestment = new ParseEditInvestment(rawData);
            parseEditInvestment.fillHashTable();
            parseEditInvestment.checkParameter();
            return parseEditInvestment.getCommand();
        } else if (DELETE_COMMAND.equals(command)) {
            ParseInvestment parseDeleteInvestment = new ParseDeleteInvestment(rawData);
            parseDeleteInvestment.fillHashTable();
            parseDeleteInvestment.checkParameter();
            return parseDeleteInvestment.getCommand();
        } else if (LIST_COMMAND.equals(command)) {
            return new ListInvestmentCommand();
        } else if (FIND_COMMAND.equals(command)) {
            ParseFindBankOrCard parseFindInvestment = new ParseFindBankOrCard(rawData, INVESTMENT);
            parseFindInvestment.fillHashTable();
            parseFindInvestment.checkParameter();
            return parseFindInvestment.getCommand();
        }
        throw new ParserException("You entered an invalid type for investment");
    }

    /**
     * Checks which operation to be performed for Bonds.
     *
     * @param command The command extracted from the initial first field.
     * @param rawData The remaining data after removing command and type.
     * @return The command object that is required to be executed.
     * @throws ParserException when an invalid type is specified.
     */
    private Command menuForBonds(String command, String rawData) throws ParserException {
        if (ADD_COMMAND.equals(command)) {
            ParseBond parseAddBond = new ParseAddBond(rawData, BOND);
            parseAddBond.fillHashTable();
            parseAddBond.checkParameter();
            return parseAddBond.getCommand();
        } else if (EDIT_COMMAND.equals(command)) {
            ParseBond parseEditBond = new ParseEditBond(rawData, BOND);
            parseEditBond.fillHashTable();
            parseEditBond.checkParameter();
            return parseEditBond.getCommand();
        } else if (DELETE_COMMAND.equals(command)) {
            ParseBond parseDeleteBond = new ParseDeleteBond(rawData, BOND);
            parseDeleteBond.fillHashTable();
            parseDeleteBond.checkParameter();
            return parseDeleteBond.getCommand();
        } else if (LIST_COMMAND.equals(command)) {
            ParseBond parseListBond = new ParseListBond(rawData, BOND);
            parseListBond.fillHashTable();
            parseListBond.checkParameter();
            return parseListBond.getCommand();
        } else if (FIND_COMMAND.equals(command)) {
            ParseFindBond parseFindBond = new ParseFindBond(rawData, BOND);
            parseFindBond.fillHashTable();
            parseFindBond.checkParameter();
            return parseFindBond.getCommand();
        }
        throw new ParserException("You entered an invalid type for bond");
    }

    /**
     * Checks which operation to be performed for Bank Expenditure.
     *
     * @param command The command extracted from the initial first field.
     * @param rawData The remaining data after removing command and type.
     * @return The command object that is required to be executed.
     * @throws ParserException when an invalid type is specified.
     */
    private Command menuForBankExpenditure(String command, String rawData) throws ParserException {
        if (ADD_COMMAND.equals(command)) {
            ParseExpenditure addExp = new ParseAddExpenditure(rawData, BANK);
            addExp.fillHashTable();
            addExp.checkParameter();
            return addExp.getCommand();
        } else if (LIST_COMMAND.equals(command)) {
            ParseExpenditure listExp = new ParseListExpenditure(rawData, BANK);
            listExp.fillHashTable();
            listExp.checkParameter();
            return listExp.getCommand();
        } else if (DELETE_COMMAND.equals(command)) {
            ParseExpenditure deleteExp = new ParseDeleteExpenditure(rawData, BANK);
            deleteExp.fillHashTable();
            deleteExp.checkParameter();
            return deleteExp.getCommand();
        } else if (EDIT_COMMAND.equals(command)) {
            ParseExpenditure editExp = new ParseEditExpenditure(rawData, BANK);
            editExp.fillHashTable();
            editExp.checkParameter();
            return editExp.getCommand();
        }
        throw new ParserException("You entered an invalid type for bank expenditure");
    }

    /**
     * Checks which operation to be performed for Card Expenditure.
     *
     * @param command The command extracted from the initial first field.
     * @param rawData The remaining data after removing command and type.
     * @return The command object that is required to be executed.
     * @throws ParserException when an invalid type is specified.
     */
    private Command menuForCardExpenditure(String command, String rawData) throws ParserException {
        if (ADD_COMMAND.equals(command)) {
            ParseExpenditure addExp = new ParseAddExpenditure(rawData, CARD);
            addExp.fillHashTable();
            addExp.checkParameter();
            return addExp.getCommand();
        } else if (LIST_COMMAND.equals(command)) {
            ParseExpenditure listExp = new ParseListExpenditure(rawData, CARD);
            listExp.fillHashTable();
            listExp.checkParameter();
            return listExp.getCommand();
        } else if (DELETE_COMMAND.equals(command)) {
            ParseExpenditure deleteExp = new ParseDeleteExpenditure(rawData, CARD);
            deleteExp.fillHashTable();
            deleteExp.checkParameter();
            return deleteExp.getCommand();
        } else if (EDIT_COMMAND.equals(command)) {
            ParseExpenditure editExp = new ParseEditExpenditure(rawData, CARD);
            editExp.fillHashTable();
            editExp.checkParameter();
            return editExp.getCommand();
        }
        throw new ParserException("You entered an invalid type for card expenditure");
    }

    /**
     * Checks which operation to be performed for Deposit.
     *
     * @param command The command extracted from the initial first field.
     * @param rawData The remaining data after removing command and type.
     * @return The command object that is required to be executed.
     * @throws ParserException when an invalid type is specified.
     */
    private Command menuForDeposit(String command, String rawData) throws ParserException {
        if (ADD_COMMAND.equals(command)) {
            ParseDeposit addDep = new ParseAddDeposit(rawData);
            addDep.fillHashTable();
            addDep.checkParameter();
            return addDep.getCommand();
        } else if (LIST_COMMAND.equals(command)) {
            ParseDeposit listDep = new ParseListDeposit(rawData);
            listDep.fillHashTable();
            listDep.checkParameter();
            return listDep.getCommand();
        } else if (DELETE_COMMAND.equals(command)) {
            ParseDeposit deleteDep = new ParseDeleteDeposit(rawData);
            deleteDep.fillHashTable();
            deleteDep.checkParameter();
            return deleteDep.getCommand();
        } else if (EDIT_COMMAND.equals(command)) {
            ParseDeposit editDep = new ParseEditDeposit(rawData);
            editDep.fillHashTable();
            editDep.checkParameter();
            return editDep.getCommand();
        }
        throw new ParserException("You entered an invalid type for deposit");
    }

    /**
     * Checks which operation to be performed for Card.
     *
     * @param command The command extracted from the initial first field.
     * @param rawData The remaining data after removing command and type.
     * @return The command object that is required to be executed.
     * @throws ParserException when an invalid type is specified.
     */
    private Command menuForCard(String command, String rawData) throws ParserException {
        if (ADD_COMMAND.equals(command)) {
            ParseCard addCard = new ParseAddCard(rawData);
            addCard.fillHashTable();
            addCard.checkParameter();
            return addCard.getCommand();
        } else if (DELETE_COMMAND.equals(command)) {
            ParseCard deleteCard = new ParseDeleteCard(rawData);
            deleteCard.fillHashTable();
            deleteCard.checkParameter();
            return deleteCard.getCommand();
        } else if (LIST_COMMAND.equals(command)) {
            return new ListCardCommand();
        } else if (EDIT_COMMAND.equals(command)) {
            ParseCard editCard = new ParseEditCard(rawData);
            editCard.fillHashTable();
            editCard.checkParameter();
            return editCard.getCommand();
        } else if (FIND_COMMAND.equals(command)) {
            ParseFindBankOrCard parseFindCard = new ParseFindBankOrCard(rawData, CARD);
            parseFindCard.fillHashTable();
            parseFindCard.checkParameter();
            return parseFindCard.getCommand();
        }
        throw new ParserException("You entered an invalid type for card");
    }

    /**
     * Checks which operation to be performed for Goals.
     *
     * @param command The command extracted from the initial first field.
     * @param rawData The remaining data after removing command and type.
     * @return The command object that is required to be executed.
     * @throws ParserException when an invalid type is specified.
     */
    private Command menuForGoals(String command, String rawData) throws ParserException {
        if (ADD_COMMAND.equals(command)) {
            ParseGoals addGoals = new ParseAddGoals(rawData);
            addGoals.fillHashTable();
            addGoals.checkParameter();
            return addGoals.getCommand();
        } else if (DELETE_COMMAND.equals(command)) {
            ParseGoals deleteGoals = new ParseDeleteGoals(rawData);
            deleteGoals.fillHashTable();
            deleteGoals.checkParameter();
            return deleteGoals.getCommand();
        } else if (EDIT_COMMAND.equals(command)) {
            ParseGoals editGoals = new ParseEditGoals(rawData);
            editGoals.fillHashTable();
            editGoals.checkParameter();
            return editGoals.getCommand();
        } else if (LIST_COMMAND.equals(command)) {
            return new ListGoalsCommand();
        }
        throw new ParserException("You entered an invalid type for goals");
    }

    /**
     * Checks which operation to be performed for Recurring Expenditure.
     *
     * @param command The command extracted from the initial first field.
     * @param rawData The remaining data after removing command and type.
     * @return The command object that is required to be executed.
     * @throws ParserException when an invalid type is specified.
     */
    private Command menuForRecurringExpenditure(String command, String rawData) throws ParserException {
        if (ADD_COMMAND.equals(command)) {
            ParseRecurringExpenditure addRecurringExpenditure = new ParseAddRecurringExpenditure(rawData, BANK);
            addRecurringExpenditure.fillHashTable();
            addRecurringExpenditure.checkParameter();
            return addRecurringExpenditure.getCommand();
        } else if (DELETE_COMMAND.equals(command)) {
            ParseDeleteRecurringExpenditure
                    deleteRecurringExpenditure = new ParseDeleteRecurringExpenditure(rawData, BANK);
            deleteRecurringExpenditure.fillHashTable();
            deleteRecurringExpenditure.checkParameter();
            return deleteRecurringExpenditure.getCommand();
        } else if (EDIT_COMMAND.equals(command)) {
            ParseRecurringExpenditure editRecurringExpenditure = new ParseEditRecurringExpenditure(rawData, BANK);
            editRecurringExpenditure.fillHashTable();
            editRecurringExpenditure.checkParameter();
            return editRecurringExpenditure.getCommand();
        } else if (LIST_COMMAND.equals(command)) {
            ParseRecurringExpenditure listRecurringExpenditure = new ParseListRecurringExpenditure(rawData, BANK);
            listRecurringExpenditure.fillHashTable();
            listRecurringExpenditure.checkParameter();
            return listRecurringExpenditure.getCommand();
        } else if (FIND_COMMAND.equals(command)) {
            ParseFindRecurring parseFindRecurringExpenditure = new ParseFindRecurring(rawData, RECURRING);
            parseFindRecurringExpenditure.fillHashTable();
            parseFindRecurringExpenditure.checkParameter();
            return parseFindRecurringExpenditure.getCommand();
        }
        throw new ParserException("You entered an invalid type for recurbankexp");
    }

    /**
     * Checks which operation to be performed for Fund.
     *
     * @param command The command extracted from the initial first field.
     * @param rawData The remaining data after removing command and type.
     * @return The command object that is required to be executed.
     * @throws ParserException when an invalid type is specified.
     */
    private Command menuForFund(String command, String rawData) throws ParserException {
        if (TRANSFER_COMMAND.equals(command)) {
            ParseTransfer parseTransfer = new ParseTransfer(rawData);
            parseTransfer.fillHashTable();
            parseTransfer.checkParameter();
            return parseTransfer.getCommand();
        }
        throw new ParserException("You entered an invalid type for fund");
    }

    /**
     * Checks which operation to be performed for Bank Transaction.
     *
     * @param command The command extracted from the initial first field.
     * @param rawData The remaining data after removing command and type.
     * @return The command object that is required to be executed.
     * @throws ParserException when an invalid type is specified.
     */
    private Command menuForBankTransaction(String command, String rawData) throws ParserException {
        if (FIND_COMMAND.equals(command)) {
            ParseFindTransaction parseFindBankTransaction = new ParseFindTransaction(rawData, BANK);
            parseFindBankTransaction.fillHashTable();
            parseFindBankTransaction.checkParameter();
            return parseFindBankTransaction.getCommand();
        }
        throw new ParserException("You entered an invalid type for banktransaction");
    }

    /**
     * Checks which operation to be performed for Card Transaction.
     *
     * @param command The command extracted from the initial first field.
     * @param rawData The remaining data after removing command and type.
     * @return The command object that is required to be executed.
     * @throws ParserException when an invalid type is specified.
     */
    private Command menuForCardTransaction(String command, String rawData) throws ParserException {
        if (FIND_COMMAND.equals(command)) {
            ParseFindTransaction parseFindCardTransaction = new ParseFindTransaction(rawData, CARD);
            parseFindCardTransaction.fillHashTable();
            parseFindCardTransaction.checkParameter();
            return parseFindCardTransaction.getCommand();
        }
        throw new ParserException("You entered an invalid type for cardtransaction");
    }

    /**
     * Checks which operation to be performed for Card Bill.
     *
     * @param command The command extracted from the initial first field.
     * @param rawData The remaining data after removing command and type.
     * @return The command object that is required to be executed.
     * @throws ParserException when an invalid type is specified.
     */
    private Command menuForCardBill(String command, String rawData) throws ParserException {
        if (ADD_COMMAND.equals(command)) {
            ParseCardBill parseAddCardBill = new ParseAddCardBill(rawData);
            parseAddCardBill.fillHashTable();
            parseAddCardBill.checkParameter();
            return parseAddCardBill.getCommand();
        } else if (DELETE_COMMAND.equals(command)) {
            ParseCardBill parseDeleteCardBill = new ParseDeleteCardBill(rawData);
            parseDeleteCardBill.fillHashTable();
            parseDeleteCardBill.checkParameter();
            return parseDeleteCardBill.getCommand();
        }
        throw new ParserException("You entered an invalid type for cardbill");
    }

    /**
     * Checks which operation to be performed for Achievement.
     *
     * @param command The command extracted from the initial first field.
     * @return The command object that is required to be executed.
     * @throws ParserException when an invalid type is specified.
     */
    private Command menuForAchievement(String command) throws ParserException {
        if (LIST_COMMAND.equals(command)) {
            return new ListAchievementCommand();
        }
        throw new ParserException("You entered an invalid type for achievements");
    }

    /**
     * The parseTypeMenu determines what type of command object to generate based on the command and type.
     *
     * @param command The command extracted from the initial first field.
     * @param type    The type of command extracted from the subsequent first field after first layer of parsing.
     * @param rawData The remaining data after removing command and type.
     * @return The command object that is required to be executed.
     * @throws ParserException when an invalid type is specified.
     */
    private Command parseTypeMenu(String command, String type, String rawData) throws ParserException {
        switch (type) {
        case PROFILE_COMMANDTYPE:
            return menuForProfile(command, rawData);
        case SAVINGS_COMMANDTYPE:
            return menuForSavings(command, rawData);
        case INVESTMENT_COMMANDTYPE:
            return menuForInvestment(command, rawData);
        case BONDS_COMMANDTYPE:
            return menuForBonds(command, rawData);
        case BANKEXPENDITURE_COMMANDTYPE:
            return menuForBankExpenditure(command, rawData);
        case CARDEXPENDITURE_COMMANDTYPE:
            return menuForCardExpenditure(command, rawData);
        case DEPOSIT_COMMANDTYPE:
            return menuForDeposit(command, rawData);
        case CARD_COMMANDTYPE:
            return menuForCard(command, rawData);
        case GOALS_COMMANDTYPE:
            return menuForGoals(command, rawData);
        case RECURRINGEXPENDITURE_COMMANDTYPE:
            return menuForRecurringExpenditure(command, rawData);
        case FUND_COMMANDTYPE:
            return menuForFund(command, rawData);
        case BANKTRANSACTION_COMMANDTYPE:
            return menuForBankTransaction(command, rawData);
        case CARDTRANSACTION_COMMANDTYPE:
            return menuForCardTransaction(command, rawData);
        case CARDBILL_COMMANDTYPE:
            return menuForCardBill(command, rawData);
        case ACHIEVEMENT_COMMANDTYPE:
            return menuForAchievement(command);
        default:
            throw new ParserException("You entered an invalid type");
        }
    }
}
