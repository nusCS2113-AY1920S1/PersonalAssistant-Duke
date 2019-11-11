package dolla.ui;

public interface UiTestExpectedOutput {

    String LINE_SEPARATOR = "line.separator";
    String REGREX = "\\n|\\r\\n";

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


}
