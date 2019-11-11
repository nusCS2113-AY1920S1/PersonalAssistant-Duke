package command;

public class Instruction {

    public boolean isBye(String input) {
        return input.equals("bye");
    }

    public boolean isList(String input) {
        return input.equals("list");
    }

    public boolean isDone(String input) {
        return input.startsWith("done task");
    }

    public boolean isTodo(String input) {
        return input.startsWith("add todo");
    }

    public boolean isDeadline(String input) {
        return input.startsWith("add deadline");
    }

    public boolean isDoAfter(String input) {
        return input.startsWith("DoAfter");
    }

    public boolean isDeletePayment(String input) {
        return input.startsWith("delete payment");
    }

    public boolean isFindPayee(String input) {
        return input.startsWith("find payee");
    }

    public boolean isFindPayment(String input) {
        return input.startsWith("find payment");
    }

    public boolean isFindTask(String input) {
        return input.startsWith("find task");
    }

    public  boolean isListTasks(String input) {
        return input.startsWith("list tasks");
    }

    public boolean isWithinPeriodTask(String input) {
        return input.startsWith("within");
    }

    public boolean isSnooze(String input) {
        return input.startsWith("snooze");
    }

    public boolean isPostpone(String input) {
        return input.startsWith("postpone");
    }

    public  boolean isDeleteTask(String input) {
        return input.startsWith("delete task");
    }

    public boolean isReschedule(String input) {
        return input.startsWith("reschedule");
    }

    public boolean isViewSchedule(String input) {
        return input.startsWith("view schedule");
    }

    public boolean isReminder(String input) {
        return input.startsWith("reminder");
    }

    public boolean isEdit(String input) {
        return input.startsWith("edit");
    }

    public boolean isAddPayment(String input) {
        return input.startsWith("add payment");
    }

    public boolean isAddPayee(String input) {
        return input.startsWith("add payee");
    }

    public boolean isListPayments(String input) {
        return input.startsWith("list payments");
    }

    public boolean isListPayees(String input) {
        return input.startsWith("list payees");
    }

    public boolean isDeletePayee(String input) {
        return input.startsWith("delete payee");
    }

    public boolean istotalcost(String input)  {
        return input.startsWith("total cost");
    }

    public boolean isHistory(String input) {
        return input.startsWith("history");
    }

    public boolean isListProjects(String input) {
        return input.equals("list projects");
    }

    public boolean isAddProject(String input) {
        return input.startsWith("add project");
    }

    public boolean isDeleteProject(String input) {
        return input.startsWith("delete project");
    }

    public boolean isGoToProject(String input) {
        return input.startsWith("goto");
    }

    public boolean isSetFund(String input) {
        return input.startsWith("set fund");
    }

    public boolean isAddFund(String input) {
        return input.startsWith("add fund");
    }

    public boolean isAssignFund(String input) {
        return input.startsWith("assign budget");
    }

    public boolean isReduceBudget(String input) {
        return input.startsWith("reduce budget");
    }

    public boolean isShowFund(String input) {
        return input.startsWith("show fund");
    }

    public boolean isResetFund(String input) {
        return input.startsWith("change fund");
    }

    public boolean isShowBudget(String input) {
        return input.startsWith("show budget");
    }

    public boolean isViewhistory(String input) {
        return input.startsWith("view history");
    }

    public boolean isUndo(String input) {
        return input.startsWith("undo");
    }

    public boolean isRedo(String input) {
        return input.startsWith("redo");
    }

    public boolean isHelp(String input) {
        return input.equals("help");
    }

    public boolean isLoad(String input) {
        return input.equals("load");
    }

}