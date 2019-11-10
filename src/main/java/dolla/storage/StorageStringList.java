package dolla.storage;

//@@author yetong1895
public interface StorageStringList {
    String PATH = "./data/dolla.txt";
    String DATA = "data";
    String PRELOAD_PATH = "/dolla-preload.txt";
    String DELIMITER = " \\| ";

    String INCOME_TYPE = "I";
    String EXPENSE_TYPE = "E";
    String BUDGET_TYPE = "BU";
    String SAVING_TYPE = "S";
    String OWE_TYPE = "O";
    String BORROW_TYPE = "B";
    String RECURRING_INCOME_TYPE = "RI";
    String RECURRING_EXPENSE_TYPE = "RE";
    String BILL_TYPE = "BI";

    String SHORTCUT = "shortcut";
    String INCOME = "income";
    String EXPENSE = "expense";
    String BUDGET = "budget";
    String SAVING = "saving";
    String OWE = "owe";
    String BORROW = "borrow";
    String BILL = "bill";
}
