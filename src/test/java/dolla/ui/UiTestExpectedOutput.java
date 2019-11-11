package dolla.ui;

public interface UiTestExpectedOutput {

    String LINE_SEPARATOR = "line.separator";

    String ACTION_UI_EXPECTED_1 =
            "____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tI have undone the command for you!" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tI have redone the command for you!" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________";

    String ACTION_UI_EXPECTED_2 =
            "____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tSorry, you do not have any command to undo." + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tSorry, you do not have any command to redo." + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________";

    String DEBT_UI_EXPECTED_1 =
            "____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tplease follow the format 'owe(/borrow) [NAME] [AMOUNT] [DESCRIPTION] /due [DURATION]'"
            + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________";

    String DEBT_UI_EXPECTED_2 =
            "____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tGot it! Total amount: $10.0 Number of people: 2" + System.getProperty(LINE_SEPARATOR)
            + "\tHere is the bill per person after splitting: $5.0" + System.getProperty(LINE_SEPARATOR)
            + "\tHere is the name list: " + System.getProperty(LINE_SEPARATOR)
            + "\t1. tata" + System.getProperty(LINE_SEPARATOR)
            + "\t2. yuyu" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________";

    String DEBT_UI_EXPECTED_3 =
            "____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tplease follow the format 'bill [NUMBER OF PEOPLE] [TOTAL AMOUNT] [NAME 1] [NAME 2]...[NAME N]"
            + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________";

    String DEBT_UI_EXPECTED_4 =
            "____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tGot it! yaya has paid the bill." + System.getProperty(LINE_SEPARATOR)
            + "\tHere is the updated name list: [tata, yuyu]" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________";

    String DEBT_UI_EXPECTED_5 =
            "____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tPlease follow the format: paid [LIST NUMBER] [NAME]" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________";

    String DEBT_UI_EXPECTED_6 =
            "____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tPlease input a valid bill number." + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________";

    String DEBT_UI_EXPECTED_7 =
            "____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tOne bill has cleared" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________";

    String DEBT_UI_EXPECTED_8 =
            "____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tYou don't have any bills yet." + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________";

    String DEBT_UI_EXPECTED_9 =
            "____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tPlease enter 5 names." + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________";

    String DEBT_UI_EXPECTED_10 =
            "____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tPlease input a valid name." + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________";

    String DEBT_UI_EXPECTED_11 =
            "____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tThis name is not found in the list." + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________";

    String DEBT_UI_EXPECTED_12 =
            "____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tYou can also add bills under this mode :)" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________";

    String DEBT_UI_EXPECTED_13 =
            "____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tGot it! This bill is removed." + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________";

    String DEBT_UI_EXPECTED_14 =
            "____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tIf you want to remove a bill, Please follow the format: remove bill [BILL NUMBER]"
            + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________";

    String DEBT_UI_EXPECTED_15 =
            "____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tPlease enter 3 names." + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________";

    String DEBT_UI_EXPECTED_16 =
            "____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tThe type can only be 'owe' or 'borrow'." + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________";

    String ENTRY_UI_EXPECTED_1 =
            "____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tPlease specify the type of entry you want to add: income or expense."
            + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________";

    String ENTRY_UI_EXPECTED_2 =
            "____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tPlease follow the format 'add income(/expense) [AMOUNT] [DESCRIPTION] /on [DATE]'"
            + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________";

    String LIMIT_UI_EXPECTED_1 =
            "____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tOOPS! Please specify the limit type: budget/saving." + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________";

    String LIMIT_UI_EXPECTED_2 =
            "____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tOOPS! Please specify the limit duration: daily/weekly/monthly." + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________";

    String LIMIT_UI_EXPECTED_3 =
            "____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tOOPS! Please follow the format:" + System.getProperty(LINE_SEPARATOR)
            + "\t'set [limitType] [AMOUNT] [DURATION]' to set a new limit." + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________";

    String LIMIT_UI_EXPECTED_4 =
            "____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tOOPS! Please follow the format:" + System.getProperty(LINE_SEPARATOR)
            + "\t'remaining [DURATION] [limitType]' to view your limit goals!" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________";

    String LIMIT_UI_EXPECTED_5 =
            "____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tOOPS! There is no budget set for the duration you specified." + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tOOPS! There is no saving set for the duration you specified." + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________";

    String LIMIT_UI_EXPECTED_6 =
            "____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tOh no! You have exceeded your daily budget by: $10.0" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tOh no! You have exceeded your weekly budget by: $20.0" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tOh no! You have exceeded your monthly budget by: $30.0" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________";

    String LIMIT_UI_EXPECTED_7 =
            "____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tOh no! You have reached your weekly budget. Time to cut down!" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tOh no! You have reached your daily budget. Time to cut down!" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tOh no! You have reached your monthly budget. Time to cut down!" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________";

    String LIMIT_UI_EXPECTED_8 =
            "____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tOh no! You have not saved any money. Work harder!" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________";

    String LIMIT_UI_EXPECTED_9 =
            "____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tCongratulations! You have reached your daily saving goal!" + System.getProperty(LINE_SEPARATOR)
            + "\tKeep up the good work!" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tCongratulations! You have reached your weekly saving goal!" + System.getProperty(LINE_SEPARATOR)
            + "\tKeep up the good work!" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tCongratulations! You have reached your monthly saving goal!" + System.getProperty(LINE_SEPARATOR)
            + "\tKeep up the good work!" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________";

    String LIMIT_UI_EXPECTED_10 =
            "____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tCongratulations! You have exceeded your daily saving goal by $45.0"
            + System.getProperty(LINE_SEPARATOR)
            + "\tKeep up the good work!" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tCongratulations! You have exceeded your weekly saving goal by $188.0"
            + System.getProperty(LINE_SEPARATOR)
            + "\tKeep up the good work!" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tCongratulations! You have exceeded your monthly saving goal by $288.0"
            + System.getProperty(LINE_SEPARATOR)
            + "\tKeep up the good work!" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________";

    String LIMIT_UI_EXPECTED_11 =
            "____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tYour remaining weekly budget is: $25.0" + System.getProperty(LINE_SEPARATOR)
            + "\tHere is a visual representation of your current goal:" + System.getProperty(LINE_SEPARATOR)
            + "\t[xxxxxxxxxxxxxxx                                           ]" + System.getProperty(LINE_SEPARATOR)
            + "\tKeep up the good work!" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________";

    String LIMIT_UI_EXPECTED_12 =
            "____________________________________________________________" + System.getProperty(LINE_SEPARATOR)
            + "\tYou still have $40.0 more to go before you reach your weekly saving goal. Keep it up!"
            + System.getProperty(LINE_SEPARATOR)
            + "\tHere is a visual representation of your current goal:" + System.getProperty(LINE_SEPARATOR)
            + "\t[xxxxxxxxxxxxxxxxxxxxxxxx                                  ]"
            + System.getProperty(LINE_SEPARATOR)
            + "\tKeep up the good work!" + System.getProperty(LINE_SEPARATOR)
            + "\t____________________________________________________________";
}
