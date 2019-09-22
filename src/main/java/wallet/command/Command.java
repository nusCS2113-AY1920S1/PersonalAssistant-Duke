package wallet.command;

import wallet.contact.Contact;
import wallet.contact.ContactList;
import wallet.record.*;
import wallet.storage.Storage;
import wallet.task.Deadline;
import wallet.task.Event;
import wallet.task.ScheduleList;
import wallet.task.Task;
import wallet.task.TaskList;
import wallet.task.Tentative;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Command {
    /**
     * This method parses the input command of the user and execute the different functions based on the given command.
     *
     * @param fullCommand The full command input by the user
     * @param taskList    The current list of task
     * @param fileIO      The class object that handles file IO
     * @return true if the command given is bye
     */
    public static boolean parse(String fullCommand, TaskList taskList, Storage fileIO, ScheduleList scheduleList,
                                ContactList contactList, RecordList recordList, ExpenseList expenseList) {
        boolean isExit = false;

        String[] command = fullCommand.split(" ", 2);
        if (command[0].equals("list")) {
            if (command[1].equals("task")) {
                int count = 1;
                System.out.println("Here are the tasks in your list:");
                for (Task t : taskList.getTaskList()) {
                    System.out.println(count + "." + t.toString());
                    count++;
                }
            } else if (command[1].equals("contact")) {
                int count = 1;
                System.out.println("Here are the contacts in your list:");
                for (Contact c : contactList.getContactList()) {
                    System.out.println(count + "." + c.toString());
                    count++;
                }
            } else if (command[1].equals("expense")) {
                int count = 1;
                System.out.println("Here are the expenses in your list:");
                for (Expense e : expenseList.getExpenseList()) {
                    System.out.println(count + ". " + e.toString());
                    count++;
                }
            }
        } else if (command[0].equals("find")) {
            int count = 1;
            try {
                String keyword = command[1];
                System.out.println("Here are the matching tasks in your list:");
                for (Task t : taskList.getTaskList()) {
                    if (t.getDescription().contains(keyword)) {
                        System.out.println(count + "." + t.toString());
                        count++;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("☹ OOPS!!! Please specify a keyword to search for.");
            }
        } else if (command[0].equals("done")) {
            try {
                int num = Integer.parseInt(command[1]) - 1;
                Task task = taskList.getTask(num);
                if (task.getStatus()) {
                    System.out.println("This task is already done.");
                    System.out.println(task.toString());
                } else {
                    task.markAsDone();
                    taskList.modifyTask(num, task);
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(task.toString());
                    fileIO.updateFile(task, num);
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("☹ OOPS!!! I'm sorry, but this task does not exist");
            }
        } else if (command[0].equals("todo") || command[0].equals("deadline")
                || command[0].equals("event") || command[0].equals("dowithin")) {
            try {
                System.out.println("command[1]: " + command[1]);
                Task task = taskList.createTask(command[0], command[1]);
                if (task != null) {
                    taskList.addTask(task);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(task.toString());
                    System.out.println("Now you have " + taskList.getTaskListSize() + " tasks in the list.");
                    fileIO.writeFile(task, command[0]);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("☹ OOPS!!! The description of " + command[0] + " cannot be empty");
            }
        } else if (command[0].equals("view")) {
            try {
                scheduleList = new ScheduleList();

                if (command[1].equals("all")) {
                    for (Record r : recordList.getRecordList()) {
                        if (r instanceof Expense || r instanceof Loan) {
                            scheduleList.addSchedule(r);
                        }
                    }
                    scheduleList.printScheduleList();
                } else {
                    LocalDate date = LocalDate.parse(command[1].trim());
                    for (Record r : recordList.getRecordList()) {
                        if (r instanceof Expense) {
                            LocalDate expensesDate = ((Expense) r).getDate();
                            if (date.equals(expensesDate)) {
                                scheduleList.addSchedule(r);
                            }
                        } else if (r instanceof Loan) {
                            LocalDate loanDate = ((Loan) r).getDate();
                            if (date.equals(loanDate)) {
                                scheduleList.addSchedule(r);
                            }
                        }
                    }
                    if (scheduleList.getScheduleListSize() == 0) {
                        System.out.println("There is nothing due for that date/time!");
                    } else {
                        scheduleList.printScheduleList();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (command[0].equals("delete")) {
            try {
                int index = Integer.parseInt(command[1]) - 1;
                Task task = taskList.getTask(index);
                taskList.deleteTask(index);
                fileIO.removeTask(taskList.getTaskList(), index);
                System.out.println("Noted. I've removed this task:");
                System.out.println(task.toString());
                System.out.println("Now you have " + taskList.getTaskListSize() + " tasks in the list.");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("☹ OOPS!!! I'm sorry, but this task does not exist");
            } catch (NumberFormatException e) {
                System.out.println("☹ OOPS!!! Please use input the index of the task to delete");
            }
        } else if (command[0].equals("bye")) {

            isExit = true;
        } else if (command[0].equals("tentative")) {
            //B-Tentative Scheduling: Create Tentative Event Entry
            try {
                Task task = taskList.createTentativeEvent(command[1]);
                if (task != null) {
                    taskList.addTask(task);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(task.toString());
                    System.out.println("Now you have " + taskList.getTaskListSize() + " tasks in the list.");
                    fileIO.writeFile(task, command[0]);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("☹ OOPS!!! The description of " + command[0] + " cannot be empty");
            }

        } else if (command[0].equals("schedule")) {
            //B-Tentative Scheduling: Choose date
            try {
                int num = Integer.parseInt(command[1]) - 1;
                Task task = taskList.getTask(num);
                String outputString = task.toString();
                String type = outputString.substring(1, 3);
                if (type.equals("*E")) {
                    Tentative notSet = (Tentative) task;
                    Task newEvent = taskList.updateTentative(notSet);
                    if (newEvent != null) {
                        taskList.addTask(newEvent);
                        taskList.deleteTask(num);
                        fileIO.removeTask(taskList.getTaskList(), num);
                        System.out.println("Got it. I've updated it into an event:");
                        System.out.println(newEvent.toString());
                        System.out.println("Now you have " + taskList.getTaskListSize() + " tasks in the list.");
                        fileIO.writeFile(newEvent, "event");
                        fileIO.removeTask(taskList.getTaskList(), num);
                    }
                } else {
                    System.out.println("☹ OOPS!!! I'm sorry, but this task is not a tentative schedule");
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("☹ OOPS!!! I'm sorry, but this task does not exist");
            }
        } else if (command[0].equals("contact")) {
            try {
                String[] info = command[1].split(" ", 3);
                Contact contact = contactList.createContact(info[0], info[1], info[2]);
                if (contact != null) {
                    contactList.addContact(contact);
                    System.out.println("Got it. I've added this contact:");
                    System.out.println(contact.toString());
                    System.out.println("Now you have " + contactList.getContactListSize()
                            + " contacts in your contact list.");
                    //TODO: write the updated contactList into a file.
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("☹ OOPS!!! The description of " + command[0] + " cannot be empty");
            }
        } else if (command[0].equals("expense")) {
            try {
                Expense expense = ExpenseParser.parseInput(command[1]);
                if (expense != null) {
                    expenseList.addExpense(expense);
                    System.out.println("Got it. I've added this expense:");
                    System.out.println(expense.toString());
                    ExpenseParser.populateRecurringRecords(expenseList);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("☹ OOPS!!! The format of adding expense is "
                                    + "\"expense lunch $5 /on 01/01/2019 /cat Food /r daily\"");
            }
        } else if (command[0].equals("recurring")) {
            ArrayList<Expense> recList = ExpenseParser.getRecurringRecords(expenseList);
            System.out.println("Here are your recurring records: ");
            int index = 1;
            for (Expense e : recList) {
                System.out.println(index + "." + e.toString());
                index++;
            }
        } else {
            System.out.println("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
        }

        return isExit;
    }
}
