package owlmoney.logic.command.help;

import owlmoney.logic.command.Command;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

public class HelpCommand extends Command {
    private static final String NEWLINE = System.lineSeparator();
    private static final String HEADER_PROFILE = NEWLINE + "====================" + NEWLINE
            + "/profile" + NEWLINE + "--------------------" + NEWLINE;
    private static final String HEADER_SAVINGS = NEWLINE + "====================" + NEWLINE
            + "/savings" + NEWLINE + "--------------------" + NEWLINE;
    private static final String HEADER_BANKEXPENDITURE = NEWLINE + "====================" + NEWLINE
            + "/bankexpenditure" + NEWLINE + "--------------------" + NEWLINE;
    private static final String HEADER_RECURBANKEXP = NEWLINE + "====================" + NEWLINE
            + "/recurbankexp" + NEWLINE + "--------------------" + NEWLINE;
    private static final String HEADER_DEPOSIT = NEWLINE + "====================" + NEWLINE
            + "/deposit" + NEWLINE + "--------------------" + NEWLINE;
    private static final String HEADER_CARD = NEWLINE + "====================" + NEWLINE
            + "/card" + NEWLINE + "--------------------" + NEWLINE;
    private static final String HEADER_CARDEXPENDITURE = NEWLINE + "====================" + NEWLINE
            + "/cardexpenditure" + NEWLINE + "--------------------" + NEWLINE;
    private static final String HEADER_CARDBILL = NEWLINE + "====================" + NEWLINE
            + "/cardbill" + NEWLINE + "--------------------" + NEWLINE;
    private static final String HEADER_INVESTMENT = NEWLINE + "====================" + NEWLINE
            + "/investment" + NEWLINE + "--------------------" + NEWLINE;
    private static final String HEADER_BONDS = NEWLINE + "====================" + NEWLINE
            + "/bonds" + NEWLINE + "--------------------" + NEWLINE;
    private static final String HEADER_GOALS = NEWLINE + "====================" + NEWLINE
            + "/goals" + NEWLINE + "--------------------" + NEWLINE;
    private static final String PRINT_MOREINFO = NEWLINE + "====================" + NEWLINE
            + "For more information, please visit our User Guide on our Github at:"
            + NEWLINE
            + "https://bit.ly/OwlMoney"
            + NEWLINE
            + "===================="
            + NEWLINE;
    private static final String PRINT_PROFILE = HEADER_PROFILE + "/edit /profile /name NAME /newname NAME"
            + NEWLINE;
    private static final String PRINT_SAVINGS = HEADER_SAVINGS
            + "/add /savings /name ACCOUNT_NAME /amount AMOUNT /income INCOME"
            + NEWLINE
            + "/edit /savings /name ACCOUNT_NAME [/newname ACCOUNT_NAME] [/amount AMOUNT] [/income INCOME]"
            + NEWLINE
            + "/delete /savings /name ACCOUNT_NAME"
            + NEWLINE
            + "/list /savings"
            + NEWLINE
            + "/find /savings /name ACCOUNT_NAME"
            + NEWLINE
            + "/transfer /fund /from ACCOUNT_NAME /to ACCOUNT_NAME /amount AMOUNT /date DATE"
            + NEWLINE;
    private static final String PRINT_BANKEXPENDITURE = HEADER_BANKEXPENDITURE
            + "/add /bankexpenditure /amount AMOUNT /from ACCOUNT_NAME /date DATE /desc DESCRIPTION "
            + "[/category CATEGORY]"
            + NEWLINE
            + "/edit /bankexpenditure /from ACCOUNT_NAME /transno TRANSACTION_NUMBER [/desc DESCRIPTION] "
            + "[/category CATEGORY] [/amount AMOUNT] [/date DATE]"
            + NEWLINE
            + "/delete /bankexpenditure /from ACCOUNT_NAME /transno TRANSACTION_NUMBER"
            + NEWLINE
            + "/list /bankexpenditure /from ACCOUNT_NAME [/num NUMBER]"
            + NEWLINE
            + "/find /banktransaction /name ACCOUNT_NAME [/desc DESCRIPTION] [/from DATE /to DATE] "
            + "[/category CATEGORY]"
            + NEWLINE;
    private static final String PRINT_RECURBANKEXP = HEADER_RECURBANKEXP
            + "/add /recurbankexp /amount AMOUNT /from ACCOUNT_NAME /desc DESCRIPTION [/category CATEGORY]"
            + NEWLINE
            + "/edit /recurbankexp /from ACCOUNT_NAME /transno TRANSACTION_NUMBER [/desc DESCRIPTION] "
            + "[/category CATEGORY] [/amount AMOUNT]"
            + NEWLINE
            + "/delete /recurbankexp /from ACCOUNT_NAME /transno TRANSACTION_NUMBER"
            + NEWLINE
            + "/list /recurbankexp /from ACCOUNT_NAME"
            + NEWLINE
            + "/find /recurbankexp /name ACCOUNT_NAME [/desc DESCRIPTION] [/category CATEGORY]"
            + NEWLINE
            + "/update"
            + NEWLINE;
    private static final String PRINT_DEPOSIT = HEADER_DEPOSIT
            + "/add /deposit /to ACCOUNT_NAME /amount AMOUNT /desc DESCRIPTION /date DATE"
            + NEWLINE
            + "/edit /deposit /from ACCOUNT_NAME /transno TRANSACTION_NUMBER [/desc DESCRIPTION] "
            + "[/amount AMOUNT] [/date DATE]"
            + NEWLINE
            + "/delete /deposit /from ACCOUNT_NAME /transno TRANSACTION_NUMBER"
            + NEWLINE
            + "/list /deposit /from ACCOUNT_NAME [/num NUMBER]"
            + NEWLINE
            + "/find /banktransaction /name ACCOUNT_NAME [/desc DESCRIPTION] [/from DATE /to DATE] "
            + "[/category CATEGORY]"
            + NEWLINE;
    private static final String PRINT_CARD = HEADER_CARD
            + "/add /card /name CARD_NAME /limit CARD_LIMIT /rebate CASHBACK_RATE"
            + NEWLINE
            + "/edit /card /name CARD_NAME [/newname CARD_NAME] [/limit CARD_LIMIT] [/rebate CASHBACK_RATE]"
            + NEWLINE
            + "/delete /card /name CARD_NAME"
            + NEWLINE
            + "/list /card"
            + NEWLINE
            + "/find /card /name CARD_NAME"
            + NEWLINE;
    private static final String PRINT_CARDEXPENDITURE = HEADER_CARDEXPENDITURE
            + "/add /cardexpenditure /amount AMOUNT /from CARD_NAME /date DATE /desc DESCRIPTION "
            + "[/category CATEGORY]"
            + NEWLINE
            + "/edit /cardexpenditure /from CARD_NAME /transno TRANSACTION_NUMBER [/desc DESCRIPTION] "
            + "[/category CATEGORY] [/amount AMOUNT] [/date DATE]"
            + NEWLINE
            + "/delete /cardexpenditure /from CARD_NAME /transno TRANSACTION_NUMBER"
            + NEWLINE
            + "/list /cardexpenditure /from CARD_NAME [/num NUMBER]"
            + NEWLINE
            + "/find /cardtransaction /name CARD_NAME [/desc DESCRIPTION] [/from DATE /to DATE] "
            + "[/category CATEGORY]"
            + NEWLINE;
    private static final String PRINT_CARDBILL = HEADER_CARDBILL
            + "/add /cardbill /card POBB Tomorrow Card /date 10/2019 /bank JunBank Savings Account"
            + NEWLINE
            + "/delete /cardbill /card CARD_NAME /date YEARMONTH /bank ACCOUNT_NAME /expno"
            + NEWLINE;
    private static final String PRINT_INVESTMENT = HEADER_INVESTMENT
            + "/add /investment /name ACCOUNT_NAME /amount AMOUNT"
            + NEWLINE
            + "/edit /investment /name ACCOUNT_NAME [/newname ACCOUNT_NAME] [/amount AMOUNT]"
            + NEWLINE
            + "/delete /investment /name ACCOUNT_NAME"
            + NEWLINE
            + "/list /investment"
            + NEWLINE
            + "/find /investment /name ACCOUNT_NAME"
            + NEWLINE
            + "/transfer /fund /from ACCOUNT_NAME /to ACCOUNT_NAME /amount AMOUNT /date DATE"
            + NEWLINE
            + "/update"
            + NEWLINE;
    private static final String PRINT_BONDS = HEADER_BONDS
            + "/add /bonds /from ACCOUNT_NAME /name BOND_NAME /amount AMOUNT /rate BOND_RATE "
            + "/date DATE /year YEARS"
            + NEWLINE
            + "/edit /bonds /from ACCOUNT_NAME /name BOND_NAME [/rate BOND_RATE] [/year YEARS]"
            + NEWLINE
            + "/delete /bonds /from ACCOUNT_NAME /name BOND_NAME"
            + NEWLINE
            + "/list /bonds /from ACCOUNT_NAME [/num NUMBER]"
            + NEWLINE
            + "/find /bonds /name BOND_NAME /from ACCOUNT_NAME"
            + NEWLINE
            + "/update"
            + NEWLINE;
    private static final String PRINT_GOALS = HEADER_GOALS
            + "/add /goals /name GOAL_NAME /amount TARGET_AMOUNT /by DATE [/from ACCOUNT_NAME]"
            + NEWLINE
            + "or"
            + NEWLINE
            + "/add /goals /name GOAL_NAME /amount TARGET_AMOUNT /in DAYS [/from ACCOUNT_NAME]"
            + NEWLINE
            + "/edit /goals /name GOAL_NAME [/newname GOAL_NAME] [/amount AMOUNT] [/in DAYS] [/by DATE] "
            + "[/from ACCOUNT_NAME] [/mark 1]"
            + NEWLINE
            + "/delete /goals /name GOAL_NAME"
            + NEWLINE
            + "/list /goals"
            + NEWLINE
            + "/list /achievement"
            + NEWLINE;

    /**
     * Executes the function to print help command.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return True if OwlMoney should terminate after execution.
     */
    public boolean execute(Profile profile, Ui ui) {
        ui.printMessage(PRINT_PROFILE + PRINT_SAVINGS + PRINT_BANKEXPENDITURE + PRINT_RECURBANKEXP
                + PRINT_DEPOSIT + PRINT_CARD + PRINT_CARDEXPENDITURE + PRINT_CARDBILL + PRINT_INVESTMENT
                + PRINT_BONDS + PRINT_GOALS + PRINT_MOREINFO);
        return this.isExit;
    }
}
