package command;

import common.DukeException;
import common.TaskList;
import payment.Payee;
import payment.PaymentManager;
import payment.Payments;
import task.Deadline;
import task.DoAfterTasks;
import task.Task;
import task.WithinPeriodTask;
import ui.Ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Calendar;

/**
 * Parser that parses input from the user.
 */
public class Parser {

    private static SimpleDateFormat dataformat = new SimpleDateFormat("dd/MM/yyyy HHmm");

    /**
     * Method that parses input from the user and executes processes based on the input.
     * @param input Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui Ui that interacts with the user.
     * @param storage Storage for the Tasklist.
     * @return Returns boolean variable to indicate when to stop parsing for input.
     * @throws DukeException if input is not valid.
     */
    public static boolean parse(String input, TaskList tasklist, Ui ui,
                                Storage storage, HashMap<String, Payee> managermap) {
        try {
            if (isBye(input)) {
                //print bye message
                ui.byeMessage();
                ui.getIn().close();
                return true;

            } else if (isList(input)) {
                //print out current list
                ui.printList(tasklist, "list");
            } else if (isDone(input)) {
                processDone(input, tasklist, ui);

            } else if (isDeadline(input)) {
                processDeadline(input, tasklist, ui);
                storage.save(tasklist.returnArrayList());

            } else if (isDoAfter(input)) {
                processDoAfter(input, tasklist, ui);
                Storage.save(tasklist.returnArrayList());
            } else if (isDelete(input)) {
                processDelete(input, managermap, ui);
                storage.save(tasklist.returnArrayList());

            } else if (isFind(input)) {
                processFind(input, tasklist, ui);
            } else if (isWithinPeriodTask(input)) {
                processWithin(input, tasklist, ui);
                storage.save(tasklist.returnArrayList());
            } else if (isSnooze(input)) {
                processSnooze(input, tasklist, ui);
                storage.save(tasklist.returnArrayList());
            /*
            `} else if (isPostpone(input)) {
                processPostpone(input, tasklist, ui);
                storage.save(tasklist.returnArrayList());`
            */
            } else if (isReschedule(input)) {
                processReschedule(input, tasklist, ui);
                storage.save(tasklist.returnArrayList());
            } else if (isViewSchedule(input)) {
                processViewSchedule(input, tasklist, ui);
                //storage.save(tasklist.returnArrayList());
            } else if (isReminder(input)) {
                //processReminder(input, tasklist, ui);
            } else if (isEdit(input)) {
                processEdit(input,tasklist,ui);
            } else if (isPayment(input)) {
                processPayment(input, managermap, ui);
            } else if(isgetpayee(input)){
                processfindpayee(input, ui, managermap);
            }
            else if (isPayee(input)) {
                processPayee(input, managermap, ui);
            } else {
                throw new DukeException("     ☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        } catch (DukeException e) {
            ui.exceptionMessage(e.getMessage());
        }
        return false;
    }

    /**
     * Processes the find command and outputs a list of tasks containing the word.
     * @param input Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui Ui that interacts with the user.
     */
    private static void processFind(String input, TaskList tasklist, Ui ui) {
        try {
            TaskList findlist = new TaskList();
            String[] splitspace = input.split(" ", 2);
            for (Task tasks : tasklist.returnArrayList()) {
                if (tasks.getDescription().contains(splitspace[1])) {
                    findlist.addTask(tasks);
                }
            }
            ui.printList(findlist, "find");
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! The content to find cannot be empty.");
        }
    }

    private static void processfindpayee(String input, Ui ui, HashMap<String, Payee> managermap) {
        try {
            String[] splitspace = input.split(" ", 2);
            ArrayList<Payments> paymentsArrayList = new ArrayList<Payments>();
            paymentsArrayList  = PaymentManager.findPayee(splitspace[1], managermap);
            ui.printPaymentList(paymentsArrayList);
        }catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! The content to find cannot be empty.");
        }
    }
    /**
     * Processes the View Schedule command and outputs the schedule for the specific date entered in the input.
     * @param input Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui Ui that interacts with the user.
     */
    private static void processViewSchedule(String input, TaskList tasklist, Ui ui) {
        try {
            TaskList findlist = new TaskList();
            String[] splitspace = input.split(" ", 3);
            for (Task tasks : tasklist.returnArrayList()) {
                if (tasks.giveTask().contains(splitspace[2])) {
                    findlist.addTask(tasks);
                }
            }
            ArrayList<String> time = new ArrayList<String>();
            for (Task tasks: findlist.returnArrayList()) {
                String[] splitcolon = tasks.giveTask().split(":");
                String[] splitspaces = splitcolon[1].split(" ");
                time.add(splitspaces[2]);
            }
            Collections.sort(time);
            TaskList finalList = new TaskList();
            for (int i = 0; i < time.size(); i = i + 1) {
                for (Task tasks: findlist.returnArrayList()) {
                    if (tasks.giveTask().contains(time.get(i))) {
                        finalList.addTask(tasks);
                    }
                }
            }
            ui.printList(finalList, "View Schedule");
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! The content to find cannot be empty.");
        }
    }

    /**
     * Processes the delete command.
     * INPUT FORMAT: delete p/payee i/item
     * @param input Input from the user.
     * @param managermap HashMap containing all Payees and their Payments.
     * @param ui Ui that interacts with the user.
     */
    private static void processDelete(String input, HashMap<String, Payee> managermap, Ui ui) {
        String[] arr = input.split(" ", 1);
        String[] split = arr[1].split("p/|i/");
        Payments deleted = PaymentManager.deletePayments(split[0], split[1], managermap);
        ui.printDeleteMessage(split[0], deleted, managermap.get(split[0]).payments.size());
    }

    /**
     * Processes the done command and sets the task specified as done.
     * @param input Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui Ui that interacts with the user.
     */
    private static void processDone(String input, TaskList tasklist, Ui ui) {
        try {
            String[] arr = input.split(" ", 2);
            int numdone = Integer.parseInt(arr[1]) - 1;
            tasklist.get(numdone).setDone();
            ui.printDoneMessage(numdone, tasklist);

        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! Please input the list number to indicate as done.");
        }
    }

    /**
     * Processes the deadline command and adds a deadline to the user's Tasklist.
     * @param input Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui Ui that interacts with the user.
     */
    private static void processDeadline(String input, TaskList tasklist, Ui ui) {
        try {
            String[] splitspace = input.split(" ", 2);
            String[] splitslash = splitspace[1].split("/", 2);
            String taskDescription = splitslash[0];
            String[] splittime = splitslash[1].split(" ", 2);
            String taskTime = splittime[1];
            Date formattedtime = dataformat.parse(taskTime);
            Deadline deadline = new Deadline(taskDescription, dataformat.format(formattedtime));
            tasklist.addTask(deadline);
            ui.printAddedMessage(deadline, tasklist);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! The description of a deadline cannot be empty.");
        } catch (ParseException e) {
            ui.exceptionMessage("     ☹ OOPS!!! Format of time is wrong.");
        }
    }

    /**
     * Processes the DoAfter command and adds a task,
     * which has to be done after another task or a specific date and time,
     * to the user's Tasklist.
     * @param input Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui Ui that interacts with the user.
     */
    private static void processDoAfter(String input, TaskList tasklist, Ui ui) {
        try {
            String[] splitspace = input.split(" ", 2);
            String[] splitslash = splitspace[1].split("/", 2);
            String taskDescription = splitslash[0];
            String[] splittime = splitslash[1].split(" ", 2);
            String taskTime = splittime[1];
            if (taskTime.contains("/")) {
                Date formattedtime = dataformat.parse(taskTime);
                DoAfterTasks after = new DoAfterTasks(taskDescription, dataformat.format(formattedtime));
                tasklist.addTask(after);
                ui.printAddedMessage(after, tasklist);
            } else {
                DoAfterTasks after = new DoAfterTasks(taskDescription, taskTime);
                tasklist.addTask(after);
                ui.printAddedMessage(after, tasklist);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! The description of a DoAfter cannot be empty.");
        } catch (ParseException e) {
            ui.exceptionMessage("     ☹ OOPS!!! Format of time is wrong.");
        }
    }

    /**
     * Processes the within command and adds a withinPeriodTask to the user's Tasklist.
     * @param input Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui Ui that interacts with the user.
     */
    private static void processWithin(String input, TaskList tasklist, Ui ui) {
        try {
            String[] splitspace = input.split(" ", 2);
            String[] splitslash = splitspace[1].split("/", 2);
            String taskDescription = splitslash[0];
            String[] splittime = splitslash[1].split(" ", 2);
            String[] splitand = splittime[1].split("and ", 2);
            String taskstart = splitand[0];
            String taskend = splitand[1];
            Date formattedtimestart = dataformat.parse(taskstart);
            Date formattedtimeend = dataformat.parse(taskend);
            WithinPeriodTask withinPeriodTask = new WithinPeriodTask(taskDescription,
                    dataformat.format(formattedtimestart), dataformat.format(formattedtimeend));
            tasklist.addTask(withinPeriodTask);
            ui.printAddedMessage(withinPeriodTask, tasklist);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! The description of a withinPeriodTask cannot be empty.");
        } catch (ParseException e) {
            ui.exceptionMessage("     ☹ OOPS!!! Format of time is wrong.");
        }
    }



    /**
     * Process the snooze command and automatically postpone the selected deadline task by 1 hour.
     * @param input Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui Ui that interacts with the user.
     */
    private static void processSnooze(String input, TaskList tasklist, Ui ui) {
        try {
            String[] arr = input.split(" ", 2);
            int nsnooze = Integer.parseInt(arr[1]) - 1;
            if (tasklist.get(nsnooze).getType().equals("D")) {
                String taskTime = tasklist.get(nsnooze).getBy();
                Date formattedtime = dataformat.parse(taskTime);
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                calendar.setTime(formattedtime);
                calendar.add(Calendar.HOUR_OF_DAY,1);
                Date newDate = calendar.getTime();
                tasklist.get(nsnooze).setBy(tasklist.get(nsnooze).getInVoice());
                ui.printSnoozeMessage(tasklist.get(nsnooze));
            } else {
                ui.exceptionMessage("     ☹ OOPS!!! Please select a deadline type task to snooze.");
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! Please input the list number to snooze.");
        } catch (ParseException e) {
            ui.exceptionMessage("     ☹ OOPS!!! Format of time is wrong.");
        }
    }

    /**
     * Process the postpone command and postpone the selected deadline task by required number of hours.
     * @param input Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui Ui that interacts with the user.
     */
    private static void processPostpone(String input, TaskList tasklist, Ui ui) {
        try {
            String[] splitspace = input.split(" ", 2);
            String[] splittime = splitspace[1].split(" ", 2);
            int npostpone = Integer.parseInt(splittime[0]) - 1;
            int delaytime = Integer.parseInt(splittime[1]);
            if (tasklist.get(npostpone).getType().equals("D")) {
                String taskTime = tasklist.get(npostpone).getBy();
                Date formattedtime = dataformat.parse(taskTime);
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                calendar.setTime(formattedtime);
                calendar.add(Calendar.HOUR_OF_DAY,delaytime);
                Date newDate = calendar.getTime();
                tasklist.get(npostpone).setBy(tasklist.get(npostpone).getInVoice());
                ui.printPostponeMessage(tasklist.get(npostpone));
            } else {
                ui.exceptionMessage("     ☹ OOPS!!! Please select a deadline type task to postpone.");
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! Please input the list number to postpone. "
                    + "Format:'postpone <index> <no.of hours to postpone>'");
        } catch (ParseException e) {
            ui.exceptionMessage("     ☹ OOPS!!! Format of time is wrong. "
                    + "Format:'postpone <index> <no.of hours to postpone>");
        }
    }

    private static void processReschedule(String input, TaskList tasklist, Ui ui) {
        try {
            String[] splitspace = input.split(" ", 2);
            String[] splittime = splitspace[1].split(" ", 2);
            int nreschedule = Integer.parseInt(splittime[0]) - 1;
            String delay = splittime[1];
            if (tasklist.get(nreschedule).getType().equals("D")) {
                Date formattedtime = dataformat.parse(delay);
                String newschedule = dataformat.format(formattedtime);
                tasklist.get(nreschedule).setBy(tasklist.get(nreschedule).getInVoice());
                ui.printRescheduleMessage(tasklist.get(nreschedule));
            } else if (tasklist.get(nreschedule).getType().equals("E")) {
                Date formattedtime = dataformat.parse(delay);
                String newschedule = dataformat.format(formattedtime);
                tasklist.get(nreschedule).setAt(newschedule);
                ui.printRescheduleMessage(tasklist.get(nreschedule));
            } else {
                ui.exceptionMessage("     ☹ OOPS!!! Please select a deadline type task to reschedule.");
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! Please input the list number to reschedule. "
                    + "Format:'postpone <index> <the new scheduled time in dd/mm/yyyy HHmm>'");
        } catch (ParseException e) {
            ui.exceptionMessage("     ☹ OOPS!!! Format of time is wrong. "
                    + "Format:'postpone <index> <the new scheduled time in dd/mm/yyyy HHmm>");
        }
    }

    private static void processEdit(String input, TaskList tasklist, Ui ui) {
        try {
            String[] splitspace = input.split(" ", 2);
            String[] splitedit = splitspace[1].split(" d/", 2);
            int nedit = Integer.parseInt(splitedit[0]) - 1;
            String description = splitedit[1];
            tasklist.get(nedit).setDescription(description);
            ui.printEditMessage(tasklist.get(nedit));
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! Please input the correct command format (refer to user guide)");
        } catch (NumberFormatException e) {
            ui.exceptionMessage("     ☹ OOPS!!! Please input the correct command format (refer to user guide)");
        }
    }

    //INPUT FORMAT: payment add p/payee i/item c/111 v/invoice
    private static void processPayment(String input, HashMap<String, Payee> managermap, Ui ui) {
        try {
            String[] splitspace = input.split(" ", 2);
            if (splitspace[1].startsWith("add")) {
                String[] splitpayments = splitspace[1].split("p/|i/|c/|v/");
                String payee = splitpayments[1];
                String item = splitpayments[2];
                Double cost = Double.parseDouble(splitpayments[3]);
                String invoice = splitpayments[4];
                Payments payment = PaymentManager.addPayments(payee, item, cost, invoice, managermap);
                ui.printAddPaymentMessage(splitpayments[1], payment);
            }
            //TODO --> delete payment
            //TODO --> edit payment

        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! Please input the correct command format (refer to user guide)");
        } catch (NullPointerException e) {
            ui.exceptionMessage("     ☹ OOPS!!! There is no payee with that name yet, please add the payee first!");
        }
    }

    private static void processPayee(String input, HashMap<String, Payee> managermap, Ui ui) {
        try {
            String[] splitspace = input.split(" ", 2);
            if (splitspace[1].startsWith("add")) {
                String[] splitpayments = splitspace[1].split("p/|e/|m/|ph/");
                String payeename = splitpayments[1];
                String email = splitpayments[2];
                String matricNum = splitpayments[3];
                String phoneNum = splitpayments[4];
                Payee payee = PaymentManager.addPayee(payeename, email, matricNum, phoneNum, managermap);
                ui.printAddPayeeMessage(splitpayments[1], payee);
            }
            //TODO --> delete payee
            //TODO --> edit payee

        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! Please input the correct command format (refer to user guide)");
        } catch (NullPointerException e) {
            ui.exceptionMessage("     ☹ OOPS!!! There is no payee with that name yet, please add the payee first!");
        }
    }

    private static boolean isBye(String input) {
        return input.equals("bye");
    }

    private static boolean isList(String input) {
        return input.equals("list");
    }

    private static boolean isDone(String input) {
        return input.startsWith("done");
    }

    private static boolean isDeadline(String input) {
        return input.startsWith("deadline");
    }

    private static boolean isDoAfter(String input) {
        return input.startsWith("DoAfter");
    }

    private static boolean isDelete(String input) {
        return input.startsWith("delete");
    }

    private static boolean isFind(String input) {
        return input.startsWith("find");
    }

    private static boolean isWithinPeriodTask(String input) {
        return input.startsWith("within");
    }

    private static boolean isSnooze(String input) {
        return input.startsWith("snooze");
    }

    /*
    private static boolean isPostpone(String input) {
        return input.startsWith("postpone");
    }
    */

    private static boolean isReschedule(String input) {
        return input.startsWith("reschedule");
    }

    private static boolean isViewSchedule(String input) {
        return input.startsWith("View Schedule");
    }

    private static boolean isReminder(String input) {
        return input.startsWith("reminder");
    }

    private static boolean isEdit(String input) {
        return input.startsWith("edit");
    }

    private static boolean isPayment(String input) {
        return input.startsWith("payment");
    }

    private static boolean isPayee(String input) {
        return input.startsWith("payee");
    }

    private static boolean isgetpayee(String input)  {
        return input.startsWith("getpayee");
    }
}
