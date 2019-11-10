package owlmoney.logic.parser;

import static owlmoney.commons.log.LogsCenter.getLogger;

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
import java.util.logging.Logger;

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
    private static final Logger logger = getLogger(ParseType.class);

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
            logger.warning(type + " is an invalid type");
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
    private void checkDeleteProfile(String command) throws ParserException {
        if (DELETE_COMMAND.equals(command)) {
            logger.warning("Profile cannot be deleted");
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
            checkDeleteProfile(command);
        }
        logger.warning("You entered an invalid type for profile");
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
        logger.warning("You entered an invalid type for savings");
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
        logger.warning("You entered an invalid type for investment");
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
        logger.warning("You entered an invalid type for bond");
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
            ParseExpenditure parseAddExpenditure = new ParseAddExpenditure(rawData, BANK);
            parseAddExpenditure.fillHashTable();
            parseAddExpenditure.checkParameter();
            return parseAddExpenditure.getCommand();
        } else if (LIST_COMMAND.equals(command)) {
            ParseExpenditure parseListExpenditure = new ParseListExpenditure(rawData, BANK);
            parseListExpenditure.fillHashTable();
            parseListExpenditure.checkParameter();
            return parseListExpenditure.getCommand();
        } else if (DELETE_COMMAND.equals(command)) {
            ParseExpenditure parseDeleteExpenditure = new ParseDeleteExpenditure(rawData, BANK);
            parseDeleteExpenditure.fillHashTable();
            parseDeleteExpenditure.checkParameter();
            return parseDeleteExpenditure.getCommand();
        } else if (EDIT_COMMAND.equals(command)) {
            ParseExpenditure parseEditExpenditure = new ParseEditExpenditure(rawData, BANK);
            parseEditExpenditure.fillHashTable();
            parseEditExpenditure.checkParameter();
            return parseEditExpenditure.getCommand();
        }
        logger.warning("You entered an invalid type for bank expenditure");
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
            ParseExpenditure parseAddExpenditure = new ParseAddExpenditure(rawData, CARD);
            parseAddExpenditure.fillHashTable();
            parseAddExpenditure.checkParameter();
            return parseAddExpenditure.getCommand();
        } else if (LIST_COMMAND.equals(command)) {
            ParseExpenditure parseListExpenditure = new ParseListExpenditure(rawData, CARD);
            parseListExpenditure.fillHashTable();
            parseListExpenditure.checkParameter();
            return parseListExpenditure.getCommand();
        } else if (DELETE_COMMAND.equals(command)) {
            ParseExpenditure parseDeleteExpenditure = new ParseDeleteExpenditure(rawData, CARD);
            parseDeleteExpenditure.fillHashTable();
            parseDeleteExpenditure.checkParameter();
            return parseDeleteExpenditure.getCommand();
        } else if (EDIT_COMMAND.equals(command)) {
            ParseExpenditure parseEditExpenditure = new ParseEditExpenditure(rawData, CARD);
            parseEditExpenditure.fillHashTable();
            parseEditExpenditure.checkParameter();
            return parseEditExpenditure.getCommand();
        }
        logger.warning("You entered an invalid type for card expenditure");
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
            ParseDeposit parseAddDeposit = new ParseAddDeposit(rawData);
            parseAddDeposit.fillHashTable();
            parseAddDeposit.checkParameter();
            return parseAddDeposit.getCommand();
        } else if (LIST_COMMAND.equals(command)) {
            ParseDeposit parseListDeposit = new ParseListDeposit(rawData);
            parseListDeposit.fillHashTable();
            parseListDeposit.checkParameter();
            return parseListDeposit.getCommand();
        } else if (DELETE_COMMAND.equals(command)) {
            ParseDeposit parseDeleteDeposit = new ParseDeleteDeposit(rawData);
            parseDeleteDeposit.fillHashTable();
            parseDeleteDeposit.checkParameter();
            return parseDeleteDeposit.getCommand();
        } else if (EDIT_COMMAND.equals(command)) {
            ParseDeposit parseEditDeposit = new ParseEditDeposit(rawData);
            parseEditDeposit.fillHashTable();
            parseEditDeposit.checkParameter();
            return parseEditDeposit.getCommand();
        }
        logger.warning("You entered an invalid type for deposit");
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
            ParseCard parseAddCard = new ParseAddCard(rawData);
            parseAddCard.fillHashTable();
            parseAddCard.checkParameter();
            return parseAddCard.getCommand();
        } else if (DELETE_COMMAND.equals(command)) {
            ParseCard parseDeleteCard = new ParseDeleteCard(rawData);
            parseDeleteCard.fillHashTable();
            parseDeleteCard.checkParameter();
            return parseDeleteCard.getCommand();
        } else if (LIST_COMMAND.equals(command)) {
            return new ListCardCommand();
        } else if (EDIT_COMMAND.equals(command)) {
            ParseCard parseEditCard = new ParseEditCard(rawData);
            parseEditCard.fillHashTable();
            parseEditCard.checkParameter();
            return parseEditCard.getCommand();
        } else if (FIND_COMMAND.equals(command)) {
            ParseFindBankOrCard parseFindCard = new ParseFindBankOrCard(rawData, CARD);
            parseFindCard.fillHashTable();
            parseFindCard.checkParameter();
            return parseFindCard.getCommand();
        }
        logger.warning("You entered an invalid type for card");
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
            ParseGoals parseAddGoals = new ParseAddGoals(rawData);
            parseAddGoals.fillHashTable();
            parseAddGoals.checkParameter();
            return parseAddGoals.getCommand();
        } else if (DELETE_COMMAND.equals(command)) {
            ParseGoals parseDeleteGoals = new ParseDeleteGoals(rawData);
            parseDeleteGoals.fillHashTable();
            parseDeleteGoals.checkParameter();
            return parseDeleteGoals.getCommand();
        } else if (EDIT_COMMAND.equals(command)) {
            ParseGoals parseEditGoals = new ParseEditGoals(rawData);
            parseEditGoals.fillHashTable();
            parseEditGoals.checkParameter();
            return parseEditGoals.getCommand();
        } else if (LIST_COMMAND.equals(command)) {
            return new ListGoalsCommand();
        }
        logger.warning("You entered an invalid type for goals");
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
            ParseRecurringExpenditure parseAddRecurringExpenditure = new ParseAddRecurringExpenditure(rawData, BANK);
            parseAddRecurringExpenditure.fillHashTable();
            parseAddRecurringExpenditure.checkParameter();
            return parseAddRecurringExpenditure.getCommand();
        } else if (DELETE_COMMAND.equals(command)) {
            ParseDeleteRecurringExpenditure
                    parseDeleteRecurringExpenditure = new ParseDeleteRecurringExpenditure(rawData, BANK);
            parseDeleteRecurringExpenditure.fillHashTable();
            parseDeleteRecurringExpenditure.checkParameter();
            return parseDeleteRecurringExpenditure.getCommand();
        } else if (EDIT_COMMAND.equals(command)) {
            ParseRecurringExpenditure parseEditRecurringExpenditure = new ParseEditRecurringExpenditure(rawData, BANK);
            parseEditRecurringExpenditure.fillHashTable();
            parseEditRecurringExpenditure.checkParameter();
            return parseEditRecurringExpenditure.getCommand();
        } else if (LIST_COMMAND.equals(command)) {
            ParseRecurringExpenditure parseListRecurringExpenditure = new ParseListRecurringExpenditure(rawData, BANK);
            parseListRecurringExpenditure.fillHashTable();
            parseListRecurringExpenditure.checkParameter();
            return parseListRecurringExpenditure.getCommand();
        } else if (FIND_COMMAND.equals(command)) {
            ParseFindRecurring parseFindRecurringExpenditure = new ParseFindRecurring(rawData, RECURRING);
            parseFindRecurringExpenditure.fillHashTable();
            parseFindRecurringExpenditure.checkParameter();
            return parseFindRecurringExpenditure.getCommand();
        }
        logger.warning("You entered an invalid type for recurbankexp");
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
        logger.warning("You entered an invalid type for fund");
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
        logger.warning("You entered an invalid type for banktransaction");
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
        logger.warning("You entered an invalid type for cardtransaction");
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
        logger.warning("You entered an invalid type for cardbill");
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
        logger.warning("You entered an invalid type for achievements");
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
            logger.warning("You entered an invalid type");
            throw new ParserException("You entered an invalid type");
        }
    }
}
