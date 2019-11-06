package dolla.parser;

//@@author omupenguin
public interface ParserStringList {

    // Modify specific modes
    String MODE_MODIFY_ENTRY = "modify entry";
    String MODE_MODIFY_LIMIT = "modify limit";
    String MODE_MODIFY_DEBT = "modify debt";
    String MODE_MODIFY_SHORTCUT = "modify shortcut";

    // Commands specific to Entry mode
    String ENTRY_COMMAND_ADD = "add";
    String ENTRY_COMMAND_LIST = "entries";

    // Commands specific to Limit mode
    String LIMIT_COMMAND_LIST = "limits";
    String LIMIT_COMMAND_SET = "set";

    String LIMIT_TYPE_S = "saving";
    String LIMIT_TYPE_B = "budget";

    String LIMIT_DURATION_D = "daily";
    String LIMIT_DURATION_W = "weekly";
    String LIMIT_DURATION_M = "monthly";

    // Commands specific to Debt mode
    String DEBT_COMMAND_OWE = "owe";
    String DEBT_COMMAND_BORROW = "borrow";
    String DEBT_COMMAND_LIST = "debts";
    String BILL_COMMAND_BILL = "bill";
    String BILL_COMMAND_LIST = "bills";
    String BILL_COMMAND_PAID = "paid";

    //Commands specific to shortcut
    String SHORTCUT_COMMAND_CREATE = "cs";
    String SHORTCUT_COMMAND_EXECUTE = "es";
    String SHORTCUT_COMMAND_LIST = "shortcuts";

    // Shared Commands
    String COMMAND_MODIFY = "modify";
    String COMMAND_SORT = "sort";
    String COMMAND_SEARCH = "search";
    String COMMAND_REMOVE = "remove";

    // Components used in modify
    String COMPONENT_TYPE = "/type";
    String COMPONENT_DESC = "/desc";
    String COMPONENT_AMOUNT = "/amount";
    String COMPONENT_DATE = "/on";
    String COMPONENT_TAG = "/tag";

    //Components used in sort
    String SORT_TYPE_AMOUNT = "amount";
    String SORT_TYPE_DATE = "date";
    String SORT_TYPE_DESC = "description";
    String SORT_TYPE_NAME = "name";

    //Search Components
    String SEARCH_DESCRIPTION = "description";
    String SEARCH_DATE = "date";
    String SEARCH_NAME = "name";
    String SEARCH_DURATION = "duration";

    //Components used in Action
    String COMMAND_REDO = "redo";
    String COMMAND_UNDO = "undo";
    String COMMAND_REPEAT = "repeat";

    //Commons
    String SPACE = " ";
    String COMMAND_BYE = "bye";
    String EMPTY_STR = "";
}
