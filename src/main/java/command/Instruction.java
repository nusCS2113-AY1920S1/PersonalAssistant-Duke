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

    public boolean isDelete(String input) {
        return input.startsWith("delete");
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

    public boolean isPayee(String input) {
        return input.startsWith("payee");
    }

    public boolean isgetpayee(String input)  {
        return input.startsWith("getpayee");
    }
}