package common;

public class CommandFormat {

    public String addProjectFormat() {
        return "add project pr/PROJECT_NAME am/AMOUNT_OF_FUND";
    }

    public String deleteProjectFormat() {
        return "delete project pr/PROJECT_NAME";
    }

    public String listProjectFormat() {
        return "list projects";
    }

    public String gotoProjectFormat() {
        return "goto project pr/PROJECT_NAME";
    }

    public String addPayeeFormat() {
        return "add payee p/PAYEE e/EMAIL m/MATRICNUM ph/PHONENUM";
    }

    public String addPaymentFormat() {
        return "add payment p/PAYEE i/ITEM c/COST v/INVOICE";
    }

    public String editPaymentFormat() {
        return "edit p/PAYEE v/INVOICE f/FIELD r/REPLACEMENT";
    }

    public String deletePayeeFormat() {
        return "delete payee p/PAYEE";
    }

    public String setFundFormat() {
        return "set fund am/AMOUNT";
    }

    public String addFundFormat() {
        return "add fund add/AMOUNT";
    }

    public String assignFundFormat() {
        return "assign fund pr/PROJECT_NAME am/AMOUNT";
    }

    public String resetFundFormat() {
        return "reset fund am/AMOUNT";
    }

    public String reminderFormat() {
        return "reminder";
    }

    public String historyFormat() {
        return "history";
    }

    public String viewhistoryFormat() {
        return "view history h/DATE_1 to DATE_2";
    }

    public String exitFormat() {
        return "bye";
    }
}
