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

    // Commands specific to Debt mode
    String DEBT_COMMAND_OWE = "owe";
    String DEBT_COMMAND_BORROW = "borrow";

    // Shared Commands
    String COMMAND_MODIFY = "modify";
    String COMMAND_SORT = "sort";
    String COMMAND_SEARCH = "search";
    String COMMAND_REMOVE = "remove";
}
