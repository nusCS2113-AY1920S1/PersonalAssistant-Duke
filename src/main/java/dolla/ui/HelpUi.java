package dolla.ui;

//@@author Weng-Kexin
public class HelpUi extends Ui {

    private static final String HEADER =
          "\t~~~~~~~~~~~~~~~~~~~~~~~~~Help Manual~~~~~~~~~~~~~~~~~~~~~~~~~\n\n"
        + "\tThese commands are defined internally. Some commands are mode specific.\n"
        + "\tPlease refer to Dolla UG for a more comprehensive explanation of commands.\n"
        + "\tType `help` to see this list.\n\n"
        + "\tdescription                             command\n"
        + "\t-----------                             --------";

    private static final String SWITCH_MODE_CMD =
          "\tswitch to dolla mode                    dolla \n"
        + "\tswitch to entry mode                    entry \n"
        + "\tswitch to limit mode                    limit \n"
        + "\tswitch to debt  mode                    debt  \n"
        + "\tswitch to shortcut mode                 shortcut";

    private static final String VIEW_LIST_CMD =
          "\tshow list of records in each mode       entries, limits, debts, shortcuts";

    private static final String UNDO_REDO_CMD =
          "\tundo previous add command               undo\n"
        + "\tredo the undo command                   redo";

    private static final String REMOVE_CMD =
          "\tremove record from list                 remove [LIST NO.]";

    private static final String SEARCH_CMD =
          "\tsearch description                      search [KEYWORD(S)]\n"
        + "\tsearch date                             search [KEYWORD(S)]\n"
        + "\tsearch name                             search [KEYWORD(S)]\n"
        + "\tsearch duration                         search [KEYWORD(S)]";

    private static final String MODIFY_CMD =
          "\tfully modify input                      modify [LIST NO.]\n"
        + "\tpartially modify input                  modify [LIST NO.] /COMPONENT [NEW DATA]";

    private static final String SORT_CMD =
          "\tsort by date                            sort date\n"
        + "\tsort by amount                          sort amount\n"
        + "\tsort by description                     sort description\n"
        + "\tsort by name                            sort name";

    private static final String VIEW_CMD =
          "\toverview of today's finances            view today\n"
        + "\toverview of a specific day's finances   view [DATE]";

    private static final String EXPENSES_CMD =
          "\tadd expense                             add expense [AMOUNT] [DESCRIPTION] /on [DATE]\n"
        + "\tadd income                              add income  [AMOUNT] [DESCRIPTION] /on [DATE]";

    private static final String SHORTCUT_CMD =
          "\tadd new entry to shortcut               create shortcut expense [DESCRIPTION] [AMOUNT]\n"
        + "\tadd new income to shortcut              create shortcut income  [DESCRIPTION] [AMOUNT]\n"
        + "\tcreate shortcut from entry list         cs [LIST NO.]\n"
        + "\texecute shortcut from shortcut list     es [SHORTCUT NO.]";

    private static final String LIMITS_CMD =
          "\tset duration based budget               set budget [AMOUNT] [DURATION]\n"
        + "\tset duration based saving               set saving [AMOUNT] [DURATION]\n"
        + "\tview remaining budget for the duration  remaining budget [DURATION]\n"
        + "\tview remaining saving for the duration  remaining saving [DURATION]";

    private static final String DEBTS_CMD =
          "\towe                                     owe [FRIEND] [AMOUNT] [DESCRIPTION] /due [DATE]\n"
        + "\tborrow                                  borrow [FRIEND] [AMOUNT] [DESCRIPTION] /due [DATE]\n"
        + "\tadd new bill                            bill [NUM OF PEOPLE] [TOTAL AMOUNT] [NAME1] [NAME2]...\n"
        + "\tremove name from a bill                 paid [BILL NUM] [NAME]\n"
        + "\tshow bill list                          bills";

    private static final String HELP_CMD =
          "\tviewing help                            help";

    private static final String BYE_CMD =
          "\texit the app                            bye";

    public static void helpCommandPrinter() {

        System.out.println(HEADER);
        System.out.println(SWITCH_MODE_CMD);
        System.out.println(VIEW_LIST_CMD);
        System.out.println(UNDO_REDO_CMD);
        System.out.println(REMOVE_CMD);
        System.out.println(SEARCH_CMD);
        System.out.println(MODIFY_CMD);
        System.out.println(SORT_CMD);
        System.out.println(VIEW_CMD);
        System.out.println(EXPENSES_CMD);
        System.out.println(SHORTCUT_CMD);
        System.out.println(LIMITS_CMD);
        System.out.println(DEBTS_CMD);
        System.out.println(HELP_CMD);
        System.out.println(BYE_CMD);
        System.out.println(line);
    }
}
