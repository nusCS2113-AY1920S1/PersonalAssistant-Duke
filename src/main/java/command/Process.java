package command;

import common.AlphaNUSException;
import common.TaskList;
import payment.Payee;
import payment.PaymentManager;
import payment.Payments;
import project.Fund;
import project.Project;
import project.ProjectManager;
import task.Deadline;
import task.DoAfterTasks;
import task.Task;
import task.WithinPeriodTask;
import ui.Ui;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Process {
    public SimpleDateFormat dataformat = new SimpleDateFormat("dd/MM/yyyy HHmm");
    ProjectManager projectmanager = new ProjectManager();

    public Process() throws AlphaNUSException {
    }

    /**
     * Trims leading and trailing whitespace of an array of strings.
     *
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
     * Processes the homepage messages to be displayed.
     *
     * @param currentprojectname Current project that the treasurer is working on.
     * @param projectsize        Number of projects in the record.
     * @param ui                 Ui that interacts with the user.
     */
    public void homePageMessage(String currentprojectname, int projectsize, Ui ui) {
        ui.printProjectStatus(currentprojectname, projectsize); //TODO to change to reflect homepage contents
    }

    //===========================* Project *================================

    /**
     * Processes the list project command to list all existing projects in the projectmap.
     *
     * @param ui Ui that interacts with the user.
     * @return
     */
    public void listProjects(Ui ui) throws AlphaNUSException {
        ArrayList<Project> projectslist = projectmanager.listProjects();
        if (projectslist.isEmpty()) {
            ui.printNoProjectMessage();
            return;
        }
        ui.printProjectsList(projectslist);
    }

    /**
     * Processes the add project command to add a new project to the projectmap.
     *
     * @param input Input from the user.
     * @param ui    Ui that interacts with the user.
     * @return
     */
    public void addProject(String input, Ui ui, Storage storage) throws AlphaNUSException {
        beforeAftercommand.beforedoCommand(storage);
        String[] split = input.split("pr/", 2);
        split = cleanStrStr(split);
        if (split.length != 2) {
            System.out.println("\t" + "Incorrect input");
            System.out.println("\t" + "Correct Format: add project pr/PROJECT_NAME");
            return;
        } //TODO refactor

        String projectname = split[1];
        if (projectname.isEmpty()) {
            System.out.println("\t" + "Project name cannot be empty!");
            System.out.println("\t" + "Correct Format: add project pr/PROJECT_NAME");
            return;
        } //TODO refactor

        if (projectmanager.projectmap.containsKey(projectname)) {
            System.out.println("\t" + "Project already exists!");
            return;
        } //TODO refactor

        Project newProject = projectmanager.addProject(projectname);
        storage.writeToProjectsFile(projectmanager.projectmap);
        int projectsize = projectmanager.projectmap.size();
        ui.printAddProject(newProject, projectsize);
        beforeAftercommand.afterCommand(storage);
    }

    /**
     * Processes the delete project command to delete a project from the projectmap.
     *
     * @param input Input from the user.
     * @param ui    Ui that interacts with the user.
     */
    public void deleteProject(String input, Ui ui, Storage storage) throws AlphaNUSException {
        beforeAftercommand.beforedoCommand(storage);
        String[] split = input.split("pr/", 2);
        split = cleanStrStr(split);
        if (split.length != 2) {
            System.out.println("\t" + "Incorrect input");
            System.out.println("\t" + "Correct Format: delete project pr/PROJECT_NAME");
            return;
        } //TODO refactor

        String projectname = split[1];
        if (projectname.isEmpty()) {
            System.out.println("\t" + "Project name cannot be empty!");
            System.out.println("\t" + "Correct Format: delete project pr/PROJECT_NAME");
            return;
        } //TODO refactor

        if (!projectmanager.projectmap.containsKey(projectname)) {
            System.out.println("\t" + "Project does not exist!");
            return;
        } //TODO refactor

        Project deletedProject = projectmanager.deleteProject(projectname);
        int projectsize = projectmanager.projectmap.size();
        storage.writeToProjectsFile(projectmanager.projectmap);
        ui.printDeleteProject(deletedProject, projectsize);
        beforeAftercommand.afterCommand(storage);
    }

    /**
     * Processes the goto project command to set a project in the projectmap
     * as the current project that the user is working on.
     *
     * @param input Input from the user.
     * @param ui    Ui that interacts with the user.
     */
    //@return Returns the Project object of the project that was gone to.
    public void goToProject(String input, Ui ui) {
        String[] split = input.split("pr/", 2);
        split = cleanStrStr(split);
        if (split.length != 2) {
            System.out.println("\t" + "Incorrect input");
            System.out.println("\t" + "Correct Format: goto project pr/PROJECT_NAME");
            return;
        } //TODO refactor

        String projectname = split[1];
        if (projectname.isEmpty()) {
            System.out.println("\t" + "Project name cannot be empty!");
            System.out.println("\t" + "Correct Format: goto project pr/PROJECT_NAME");
            return;
        } else if (projectmanager.projectmap.isEmpty()) { //TODO refactor
            ui.printNoProjectMessage();
            return;
        } else if (!projectmanager.projectmap.containsKey(projectname)) { //TODO refactor
            System.out.println("\t" + "Project does not exist!");
            return;
        } //TODO refactor

        projectmanager.gotoProject(projectname);
        ui.printGoToProject(projectname);
    }

    /**
     * Process the set fund command to set a fund to all projects.
     * Command format: set fund am/AMOUNT_OF_FUND.
     *
     * @param input Input from the user.
     * @param ui    Ui that interacts with the user.
     * @param fund  the total fund the that the organisation owns
     */
    public void setFund(String input, Ui ui, Fund fund) {
        try {
            String[] split = input.split("am/", 2);
            Double amount = Double.parseDouble(split[1]);
            if (fund.getFund() == 0.0) {
                fund.setFund(amount);
                ui.printSetFundMessage(fund);
            } else {
                ui.exceptionMessage("     ☹ OOPS!!! The fund id set already.");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! Please input the correct command format (refer to user guide)");
        }
    }

    /**
     * Process the add fund command to add fund value to all projects
     * Command format: add fund add/AMOUNT_OF_FUND.
     *
     * @param input Input from the user.
     * @param ui    Ui that interacts with the user.
     * @param fund  the total fund the that the organisation owns
     */
    public void addFund(String input, Ui ui, Fund fund) {
        try {
            String[] split = input.split("add/", 2);
            Double amount = Double.parseDouble(split[1]);
            fund.addFund(amount);
            ui.printAddFundMessage(fund, amount);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! Please input the correct command format (refer to user guide)");
        }
    }

    /**
     * Process the add fund command to add fund value to specific project
     * Command Format: assign fund p/PROJECT_NAME am/AMOUNT_OF_FUND.
     *
     * @param input Input from the user.
     * @param ui    Ui that interacts with the user.
     * @param fund  the total fund the that the organisation owns
     */
    public void assignFund(String input, Ui ui, Fund fund) {
        try {
            String[] split = input.split("pr/| am/");
            String projectname = split[1];
            Double amount = Double.parseDouble(split[2]);
            if (!projectmanager.projectmap.containsKey(projectname)) {
                System.out.println("\t" + "Project does not exist!");
                return;
            } else {
                if (fund.getFundRemaining() >= amount) {
                    fund.takeFund(amount);
                    projectmanager.assignBudget(projectname, amount);
                    ui.printAssignFundMessage(fund, amount, projectmanager.projectmap.get(projectname));
                } else {
                    ui.exceptionMessage("     ☹ OOPS!!! There is not enough fund. "
                            + "Please decrease the amount of fund assigned)");
                    System.out.print(fund.giveFund());
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! Please input the correct command format (refer to user guide)");
        } catch (NullPointerException e) {
            ui.exceptionMessage("     ☹ OOPS!!! There is no project with that name yet, please add the project first!");
        }
    }

    /**
     * Show the current fund status.
     *
     * @param input Input from the user.
     * @param ui    Ui that interacts with the user.
     * @param fund  the total fund the that the organisation owns
     */
    public void showFund(String input, Ui ui, Fund fund) {
        System.out.println(Ui.line);
        System.out.print(fund.giveFund());
        System.out.println(Ui.line);
    }
    //===========================* Deadline *================================

    /**
     * Processes the View Schedule command and outputs the schedule for the specific date entered in the input.
     *
     * @param input    Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui       Ui that interacts with the user.
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
     * Processes the done command and sets the task specified as done.
     *
     * @param input    Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui       Ui that interacts with the user.
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
     *
     * @param input    Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui       Ui that interacts with the user.
     */
    public void deadline(String input, TaskList tasklist, Ui ui) {
        try {
            String[] splitspace = input.split("d/|by/");
            String taskDescription = splitspace[1];
            String date = splitspace[2];
            Deadline deadline = new Deadline(taskDescription, date);
            tasklist.addTask(deadline);
            ui.printAddedMessage(deadline, tasklist);
        } catch (ArrayIndexOutOfBoundsException | ParseException e) {
            ui.exceptionMessage("     ☹ OOPS!!! The description of a deadline cannot be empty.");
        }
    }

    /**
     * Processes the delete task command and removes task from tasklist.
     *
     * @param input    Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui       Ui that interacts with the user.
     */
    public void deleteTask(String input, TaskList tasklist, Ui ui) {
        try {
            String[] splitspace = input.split("id/", 2);
            int id = Integer.parseInt(splitspace[1]) - 1;
            tasklist.deleteTask(id);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! The id of a deadline cannot be empty.");
        }
    }

    /**
     * Processes the DoAfter command and adds a task,
     * which has to be done after another task or a specific date and time,
     * to the user's Tasklist.
     *
     * @param input    Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui       Ui that interacts with the user.
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
     *
     * @param input    Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui       Ui that interacts with the user.
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
     *
     * @param input    Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui       Ui that interacts with the user.
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
     *
     * @param input    Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui       Ui that interacts with the user.
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

    /**
     * process the invoice command, set invoice status as true, update invoice value and set the deadline.
     * INPUT FORMAT: invoice id i/invoice_num
     *
     * @param input    Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui       Ui that interacts with the user.
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
*/

    /**
     * Processes the edit command, amends the data of a payee or payment already exisiting in the records.
     * INPUT FORMAT: edit p/PAYEE v/INVOICE f/FIELD r/REPLACEMENT
     *
     * @param input Input from the user.
     * @param ui    Ui that interacts with the user.
     */
    public void edit(String input, Ui ui) {
        try {
            HashMap<String, Payee> managermap = projectmanager.getCurrentProjectManagerMap();
            String[] splitspace = input.split("edit ", 2);
            String[] splitpayments = splitspace[1].split("p/|v/|f/|r/");
            splitpayments = cleanStrStr(splitpayments);
            PaymentManager.editPayee(splitpayments[1], splitpayments[2],
                    splitpayments[3], splitpayments[4], managermap, ui);
        } catch (IllegalArgumentException e) {
            ui.exceptionMessage("     ☹ OOPS!!! Please input the correct command format (refer to user guide)");
        }
    }


    //===========================* Payments *================================

    /**
     * Processes the delete command.
     * INPUT FORMAT: delete payment p/payee i/item
     *
     * @param input Input from the user.
     * @param ui    Ui that interacts with the user.
     */
    public void deletePayment(String input, Ui ui) {
        HashMap<String, Payee> managermap = projectmanager.getCurrentProjectManagerMap();
        String currentProjectName = projectmanager.currentProject.projectname;
        String[] arr = input.split("payment ", 2);
        String[] split = arr[1].split("p/|i/");
        split = cleanStrStr(split);
        Payments deleted = PaymentManager.deletePayments(split[1], split[2], managermap);
        ui.printDeletePaymentMessage(split[1], deleted, managermap.get(split[1]).payments.size(), currentProjectName);
    }

    /**
     * Processes the add payment command, saves a new payment under a specified payee.
     * INPUT FORMAT: add payment p/payee i/item c/111 v/invoice
     *
     * @param input Input from the user.
     * @param ui    Ui that interacts with the user.
     */
    public void addPayment(String input, Ui ui) {
        try {
            HashMap<String, Payee> managermap = projectmanager.getCurrentProjectManagerMap();
            String currentProjectName = projectmanager.currentProject.projectname;
            String[] splitspace = input.split("payment ", 2);
            String[] splitpayments = splitspace[1].split("p/|i/|c/|v/");
            splitpayments = cleanStrStr(splitpayments);
            String payee = splitpayments[1];
            String item = splitpayments[2];
            double cost = Double.parseDouble(splitpayments[3]);
            String invoice = splitpayments[4];
            Payments payment = PaymentManager.addPayments(payee, item, cost, invoice, managermap);
            int paymentsSize = managermap.get(payee).payments.size();
            ui.printAddPaymentMessage(splitpayments[1], payment, paymentsSize, currentProjectName);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! Please input the correct command format (refer to user guide)");
        } catch (NullPointerException e) {
            ui.exceptionMessage("     ☹ OOPS!!! There is no payee with that name yet, please add the payee first!");
        }
    }

    /**
     * Processes the add payee command, saves a new payee inside managermap.
     * INPUT FORMAT: add payee p/payee e/email m/matricNum ph/phoneNum
     *
     * @param input Input from the user.
     * @param ui    Ui that interacts with the user.
     */
    public void addPayee(String input, Ui ui) {
        try {
            HashMap<String, Payee> managermap = projectmanager.getCurrentProjectManagerMap();
            String currentProjectName = projectmanager.currentProject.projectname;
            String[] splitspace = input.split("payee ", 2);
            String[] splitpayments = splitspace[1].split("p/|e/|m/|ph/");
            splitpayments = cleanStrStr(splitpayments);
            String payeename = splitpayments[1];
            String email = splitpayments[2];
            String matricNum = splitpayments[3];
            String phoneNum = splitpayments[4];
            Payee payee = PaymentManager.addPayee(payeename, email, matricNum, phoneNum, managermap);
            int payeesize = managermap.size();
            ui.printAddPayeeMessage(splitpayments[1], payee, payeesize, currentProjectName);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! Please input the correct command format (refer to user guide)");
        } catch (NullPointerException e) {
            ui.exceptionMessage("     ☹ OOPS!!! There is no payee with that name yet, please add the payee first!");
        }
    }

    /**
     * Processes the delete payee command, saves a new payee inside managermap.
     * INPUT FORMAT: delete payee p/payee
     *
     * @param input Input from the user.
     * @param ui    Ui that interacts with the user.
     */
    public void deletePayee(String input, Ui ui) {
        try {
            HashMap<String, Payee> managermap = projectmanager.getCurrentProjectManagerMap();
            String currentProjectName = projectmanager.currentProject.projectname;
            String[] splitspace = input.split("payee ", 2);
            String[] splitpayments = splitspace[1].split("p/");
            splitpayments = cleanStrStr(splitpayments);
            String payeename = splitpayments[1];
            Payee payee = PaymentManager.deletePayee(payeename, managermap);
            int payeesize = managermap.size();
            ui.printdeletePayeeMessage(splitpayments[1], payee, payeesize, currentProjectName);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! Please input the correct command format (refer to user guide)");
        } catch (NullPointerException e) {
            ui.exceptionMessage("     ☹ OOPS!!! There is no payee with that name yet, please add the payee first!");
        }
    }

    /**
     * Processes the find command and outputs a list of payments from the payee name given.
     *
     * @param input Input from the user.
     * @param ui    Ui that interacts with the user.
     */
    public void findPayee(String input, Ui ui) {
        try {
            HashMap<String, Payee> managermap = projectmanager.getCurrentProjectManagerMap();
            String[] splitspace = input.split(" ", 2);
            ArrayList<Payments> paymentsArrayList = PaymentManager.findPayee(splitspace[1], managermap);
            ui.printPaymentList(paymentsArrayList);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! The content to find cannot be empty.");
        }
    }

    //===========================* Command History *================================

    /**
     * processes the input command and stores it in a text file.
     *
     * @param input   Input from the user.
     * @param ui      Ui that interacts with the user.
     * @param storage command.Storage that stores the input commands entered by the user.
     */

    public void commandHistory(String input, Ui ui, Storage storage) throws AlphaNUSException {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String formattedDate = dateFormat.format(date);
        String commandTime = input + " ~ " + formattedDate;
        storage.writeToCommandsFile(commandTime);
    }

    /**
     * TODO: Update this.
     *
     * @param ui          Ui that interacts with the user.
     * @param commandList ArrayList of commands.
     * @param storage     command.Storage that stores the input commands entered by the user.
     */
    public void history(Ui ui, Storage storage) throws AlphaNUSException {
        ArrayList<String> commandList = new ArrayList<String>();
        commandList = storage.readFromCommandsFile();
        ui.printArrayList(commandList);
    }

    /**
     * TODO: Update this.
     *
     * @param input       Input from the user.
     * @param ui          Ui that interacts with the user.
     * @param commandList ArrayList of commands.
     * @param storage     command.Storage that stores the input commands entered by the user.
     */
    public void viewhistory(String input, Ui ui , Storage storage) throws ParseException, AlphaNUSException {
        ArrayList<String> commandList = new ArrayList<String>();
        String[] splitspace = input.split(" ", 3);
        String[] splitslash = splitspace[2].split("/", 2);
        String[] splitdates = splitslash[1].split(" ", 3);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String date1 = splitdates[0];
        Date dateFirst = sdf.parse(date1);
        String date2 = splitdates[2];
        Date dateSecond = sdf.parse(date2);
        commandList = storage.readFromCommandsFile();
        ArrayList<String> viewhistory = new ArrayList<String>();
            for (int i = 0; i < commandList.size(); i = i + 1) {
                String token = null;
                String token1 = null;
                String[] splitDateCommand = commandList.get(i).split("~", 2);
                for (int j = 0; j < splitDateCommand.length; j = j + 1) {
                    token = splitDateCommand[j];
                }
                String[] splitDateTime = token.split(" ", 3);
                for (int k = 0; k < splitDateTime.length; k = k + 1) {
                    if (k == 1) {
                        token1 = splitDateTime[k];
                    }
                }
                Date dateCommand = sdf.parse(token1);
                if ((dateCommand.compareTo(dateFirst)) >= 0) {
                    if ((dateCommand.compareTo(dateSecond)) <= 0) {
                        viewhistory.add(commandList.get(i));
                    }
                }
            }
            ui.printArrayList(viewhistory);
        }
    public void undo(Storage storage, Ui ui) throws AlphaNUSException {
        storage.writeToProjectsFile(storage.readFromUndoFile());
        ui.undoMessage();
    }
    public void redo(Storage storage, Ui ui) throws AlphaNUSException {
        storage.writeToProjectsFile(storage.readFromRedoFile());
        ui.redoMessage();
    }
}
