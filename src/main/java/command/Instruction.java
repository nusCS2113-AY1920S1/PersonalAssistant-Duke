package command;

public class Instruction {

    public boolean isBye(String input) {
        return input.equals("bye");
    }

    public boolean isList(String input) {
        return input.equals("list");
    }

    public boolean isDone(String input) {
        return input.startsWith("done");
    }

    public boolean isDeadline(String input) {
        return input.startsWith("deadline");
    }

    public boolean isDoAfter(String input) {
        return input.startsWith("DoAfter");
    }

    public boolean isDeletePayment(String input) {
        return input.startsWith("delete payment");
    }

    public boolean isFind(String input) {
        return input.startsWith("find");
    }

    public boolean isWithinPeriodTask(String input) {
        return input.startsWith("within");
    }

    public boolean isSnooze(String input) {
        return input.startsWith("snooze");
    }

    /*
    public boolean isPostpone(String input) {
        return input.startsWith("postpone");
    }
    */

    public boolean isReschedule(String input) {
        return input.startsWith("reschedule");
    }

    public boolean isViewSchedule(String input) {
        return input.startsWith("View Schedule");
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

    public boolean isDeletePayee(String input) {
        return input.startsWith("delete payee");
    }

    public boolean isgetpayee(String input)  {
        return input.startsWith("getpayee");
    }

    public boolean isInvoice(String input) {
        return input.startsWith("invoice");
    }

    public boolean isHistory(String input) {
        return input.startsWith("history");
    }

    public boolean isAddProject(String input) {
        return input.startsWith("add project");
    }

    public boolean isDeleteProject(String input) {
        return input.startsWith("delete project");
    }

    public boolean isGoToProject(String input) {
        return input.startsWith("goto project");
    }

}