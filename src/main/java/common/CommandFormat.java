package common;

public class CommandFormat {

    public String loadBackupFormat() {
        return "load";
    }

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
        return "goto PROJECT_INDEX_IN_LIST";
    }

    public String addPayeeFormat() {
        return "add payee p/PAYEE e/EMAIL m/MATRICNUM ph/PHONENUM";
    }

    public String addPaymentFormat() {
        return "add payment p/PAYEE i/ITEM c/COST v/INVOICE";
    }

    public String deletePaymentFormat() {
        return "delete payment p/PAYEE i/ITEM";
    }

    public String editPayeeFormat() {
        return "edit p/PAYEE f/FIELD r/REPLACEMENT";
    }
    
    public String editPaymentFormat() {
        return "edit p/PAYEE i/ITEM f/FIELD r/REPLACEMENT";
    }

    public String listPaymentCurrFormat() {
        return "list payments";
    }

    public String listPaymentProjectFormat() {
        return "list payments pr/PROJECT";
    }

    public String listPaymentPayeeFormat() {
        return "list payments p/PAYEE";
    }

    public String findPayeeFormat() {
        return "find payee p/PAYEE";
    }

    public String findPaymentFormat() {
        return "find payment p/PAYEE i/ITEM";
    }

    public String deletePayeeFormat() {
        return "delete payee p/PAYEE";
    }

    public String totalCostFormat() {
        return "total cost p/PAYEE_NAME";
    }

    public String setFundFormat() {
        return "set fund am/AMOUNT";
    }

    public String addFundFormat() {
        return "add fund add/AMOUNT";
    }

    public String assignFundFormat() {
        return "assign budget pr/PROJECT_NAME am/AMOUNT";
    }

    public String reducebudgetFormat() {
        return "reduce budget pr/PROJECT_NAME am/AMOUNT";
    }

    public String resetFundFormat() {
        return "change fund new/AMOUNT";
    }

    public String reminderFormat() {
        return "reminder";
    }

    public String showFundFormat() {
        return "show fund";
    }

    public String showBudgetFormat() {
        return "show budget pr/PROJECT_NAME";
    }

    public String addTodoFormat() {
        return "add todo d/DESCRIPTION";
    }

    public String addDeadlineFormat() {
        return "add deadline d/DESCRIPTION by/DATE.";
    }

    public String doneTaskFormat() {
        return "done id/ID";
    }

    public String deleteTaskFormat() {
        return "delete task id/ID";
    }

    public String findTaskFormat() {
        return "find task key/KEY_WORD";
    }

    public String listTasksFormat() {
        return "list tasks";
    }

    public String viewScheduleFormat() {
        return "view schedule d/DATE";
    }

    public String snoozeFormat() {
        return "snooze id/ID";
    }

    public String postponeFormat() {
        return "postpone id/ID n/DAYS";
    }

    public String rescheduleFormat() {
        return "reschedule id/ID d/DATE";
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
