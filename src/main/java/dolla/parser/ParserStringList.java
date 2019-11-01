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
    static final String DEBT_COMMAND_OWE = "owe";
    static final String DEBT_COMMAND_BORROW = "borrow";
    static final String DEBT_COMMAND_LIST = "debts";
    static final String BILL_COMMAND_BILL = "bill";
    static final String BILL_COMMAND_LIST = "bills";

    // Shared Commands
    static final String COMMAND_MODIFY = "modify";
    static final String COMMAND_SORT = "sort";
    static final String COMMAND_SEARCH = "search";
    static final String COMMAND_REMOVE = "remove";

    // Components used in modify
    static final String COMPONENT_TYPE = "/type";
    static final String COMPONENT_DESC = "/desc";
    static final String COMPONENT_AMOUNT = "/amount";
    static final String COMPONENT_DATE = "/on";
    static final String COMPONENT_TAG = "/tag";
}
