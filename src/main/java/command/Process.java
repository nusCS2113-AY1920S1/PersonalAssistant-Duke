package command;

import common.TaskList;
import payment.Payee;
import payment.PaymentManager;
import payment.Payments;
import task.Deadline;
import task.DoAfterTasks;
import task.Task;
import task.WithinPeriodTask;
import ui.Ui;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Calendar;

public class Process {
    public SimpleDateFormat dataformat = new SimpleDateFormat("dd/MM/yyyy HHmm");

    /**
     * Trims leading and trailing whitespace of an array of strings.
     * @param arr The array of Strings to clean.
     * @return cleanArr The array of Strings after cleaning.
     */

    private String[] cleanStrStr(String[] arr) {
        String[] cleanArr = arr.clone();
        for (int i = 0; i < arr.length; i++) {
            cleanArr[i] = arr[i].trim();
        }
        return cleanArr;
    }

    /**
     * Processes the find command and outputs a list of payments from the payee name given.
     * @param input Input from the user.
     * @param managermap HashMap containing all Payees and their Payments.
     * @param ui Ui that interacts with the user.
     */
    public void findPayee(String input, Ui ui, HashMap<String, Payee> managermap) {
        try {
            String[] splitspace = input.split(" ", 2);
            ArrayList<Payments> paymentsArrayList = PaymentManager.findPayee(splitspace[1], managermap);
            ui.printPaymentList(paymentsArrayList);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! The content to find cannot be empty.");
        }
    }

    /**
     * Processes the View Schedule command and outputs the schedule for the specific date entered in the input.
     * @param input Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui Ui that interacts with the user.
     */
    public void viewSchedule(String input, TaskList tasklist, Ui ui) {
        try {
            TaskList findlist = new TaskList();
            String[] splitspace = input.split(" ", 3);
            for (Task tasks : tasklist.returnArrayList()) {
                if (tasks.giveTask().contains(splitspace[2])) {
                    findlist.addTask(tasks);
                }
            }
            ArrayList<String> time = new ArrayList<String>();
            for (Task tasks : findlist.returnArrayList()) {
                String[] splitcolon = tasks.giveTask().split(":");
                String[] splitspaces = splitcolon[1].split(" ");
                time.add(splitspaces[2]);
            }
            Collections.sort(time);
            TaskList finalList = new TaskList();
            for (int i = 0; i < time.size(); i = i + 1) {
                for (Task tasks : findlist.returnArrayList()) {
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
     * INPUT FORMAT: delete payment p/payee i/item
     * @param input Input from the user.
     * @param managermap HashMap containing all Payees and their Payments.
     * @param ui Ui that interacts with the user.
     */
    public void deletePayment(String input, HashMap<String, Payee> managermap, Ui ui) {
        String[] arr = input.split("payment ", 2);
        String[] split = arr[1].split("p/|i/");
        split = cleanStrStr(split);
        Payments deleted = PaymentManager.deletePayments(split[1], split[2], managermap);
        ui.printDeletePaymentMessage(split[1], deleted, managermap.get(split[1]).payments.size());
    }

    /**
     * Processes the done command and sets the task specified as done.
     * @param input Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui Ui that interacts with the user.
     */
    public void done(String input, TaskList tasklist, Ui ui) {
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
    public void deadline(String input, TaskList tasklist, Ui ui) {
        try {
            String[] splitspace = input.split(" ", 2);
            String taskDescription = splitspace[1];
            Deadline deadline = new Deadline(taskDescription);
            tasklist.addTask(deadline);
            ui.printAddedMessage(deadline, tasklist);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! The description of a deadline cannot be empty.");
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
    public void doAfter(String input, TaskList tasklist, Ui ui) {
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
    public void within(String input, TaskList tasklist, Ui ui) {
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
    public void snooze(String input, TaskList tasklist, Ui ui) {
        try {
            String[] arr = input.split(" ", 2);
            int nsnooze = Integer.parseInt(arr[1]) - 1;
            if (tasklist.get(nsnooze).getType().equals("D")) {
                String taskTime = tasklist.get(nsnooze).getBy();
                Date formattedtime = dataformat.parse(taskTime);
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                calendar.setTime(formattedtime);
                calendar.add(Calendar.HOUR_OF_DAY, 1);
                Date newDate = calendar.getTime();
                tasklist.get(nsnooze).setBy(tasklist.get(nsnooze).getIsInVoice());
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
    public void postpone(String input, TaskList tasklist, Ui ui) {
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
                calendar.add(Calendar.HOUR_OF_DAY, delaytime);
                Date newDate = calendar.getTime();
                tasklist.get(npostpone).setBy(tasklist.get(npostpone).getIsInVoice());
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
    /*
    public void reschedule(String input, TaskList tasklist, Ui ui) {
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

    public void edit(String input, TaskList tasklist, Ui ui) {
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
*/

    /**
     * Processes the add payment command, saves a new payment under a specified payee.
     * INPUT FORMAT: add payment p/payee i/item c/111 v/invoice
     * @param input Input from the user.
     * @param managermap HashMap containing all Payees and their Payments.
     * @param ui Ui that interacts with the user.
     */
    public void addPayment(String input, HashMap<String, Payee> managermap, Ui ui) {
        try {
            String[] splitspace = input.split("payment ", 2);
            String[] splitpayments = splitspace[1].split("p/|i/|c/|v/");
            splitpayments = cleanStrStr(splitpayments);
            String payee = splitpayments[1];
            String item = splitpayments[2];
            double cost = Double.parseDouble(splitpayments[3]);
            String invoice = splitpayments[4];
            Payments payment = PaymentManager.addPayments(payee, item, cost, invoice, managermap);
            int paymentsSize = managermap.get(payee).payments.size();
            ui.printAddPaymentMessage(splitpayments[1], payment, paymentsSize);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! Please input the correct command format (refer to user guide)");
        } catch (NullPointerException e) {
            ui.exceptionMessage("     ☹ OOPS!!! There is no payee with that name yet, please add the payee first!");
        }
    }

    /**
     * Processes the add payee command, saves a new payee inside managermap.
     * INPUT FORMAT: add payee p/payee e/email m/matricNum ph/phoneNum
     * @param input Input from the user.
     * @param managermap HashMap containing all Payees and their Payments.
     * @param ui Ui that interacts with the user.
     */
    public void addPayee(String input, HashMap<String, Payee> managermap, Ui ui) {
        try {
            String[] splitspace = input.split("payee ", 2);
            String[] splitpayments = splitspace[1].split("p/|e/|m/|ph/");
            splitpayments = cleanStrStr(splitpayments);
            String payeename = splitpayments[1];
            String email = splitpayments[2];
            String matricNum = splitpayments[3];
            String phoneNum = splitpayments[4];
            Payee payee = PaymentManager.addPayee(payeename, email, matricNum, phoneNum, managermap);
            int payeesize = managermap.size();
            ui.printAddPayeeMessage(splitpayments[1], payee, payeesize);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! Please input the correct command format (refer to user guide)");
        } catch (NullPointerException e) {
            ui.exceptionMessage("     ☹ OOPS!!! There is no payee with that name yet, please add the payee first!");
        }
    }

    /**
     * Processes the delete payee command, saves a new payee inside managermap.
     * INPUT FORMAT: delete payee p/payee
     * @param input Input from the user.
     * @param managermap HashMap containing all Payees and their Payments.
     * @param ui Ui that interacts with the user.
     */
    public void deletePayee(String input, HashMap<String, Payee> managermap, Ui ui) {
        try {
            String[] splitspace = input.split("payee ", 2);
            String[] splitpayments = splitspace[1].split("p/");
            splitpayments = cleanStrStr(splitpayments);
            String payeename = splitpayments[1];
            Payee payee = PaymentManager.deletePayee(payeename, managermap);
            int payeesize = managermap.size();
            ui.printdeletePayeeMessage(splitpayments[1], payee, payeesize);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! Please input the correct command format (refer to user guide)");
        } catch (NullPointerException e) {
            ui.exceptionMessage("     ☹ OOPS!!! There is no payee with that name yet, please add the payee first!");
        }
    }

    /**
     * process the invoice command, set invoice status as true, update invoice value and set the deadline.
     * INPUT FORMAT: invoice id i/invoice_num
     * @param input Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui Ui that interacts with the user.
     */
    public void inVoice(String input, TaskList tasklist, Ui ui) {
        try {
            String[] splitspace = input.split(" ", 2);
            String[] splitInvoice = splitspace[1].split(" i/");
            int id = Integer.parseInt(splitInvoice[0]) - 1;
            if (tasklist.get(id).getType().equals("D")) {
                String invoice = splitInvoice[1];
                tasklist.get(id).setInVoice(invoice);
                ui.printAddInvoiceMessage(tasklist.get(id));
            } else {
                ui.exceptionMessage("     ☹ OOPS!!! Please select a deadline instead!");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! Please input a valid ID!");
        } catch (NumberFormatException e) {
            ui.exceptionMessage("     ☹ OOPS!!! Please input the correct command format (refer to user guide)");
        }
    }

    public void commandHistory(String input, Ui ui, ArrayList<String> commandList, Storage storage) throws IOException {
        if (!input.equals("history")) {
                Calendar cal = Calendar.getInstance();
                Date date = cal.getTime();
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                String formattedDate = dateFormat.format(date);
                String command_time = input + " at " + formattedDate;
                //commandList.add(command_time);
                FileWriter fw = new FileWriter(String.valueOf(storage));
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(command_time);
            }
            else{
                //ui.printArrayList(commandList);
                ui.printTxtfile(String.valueOf(storage));
            }
        }
}