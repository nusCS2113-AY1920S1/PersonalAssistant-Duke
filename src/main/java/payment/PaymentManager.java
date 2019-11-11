package payment;

import ui.Ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import common.AlphaNUSException;
import project.Project;
import payment.Status;

//@@author karansarat
/**
 * PaymentManager for managing Payments objects and PaymentForms from the
 * PaymentsList.
 */
public abstract class PaymentManager {

    /**
     * Takes in a string describing a field of payee/payment objects.
     * Returns a Field enum object that describes the field.
     * @param str string describing field
     * @return a Field object corresponding to str.
     */
    private static Field strToField(String str) {
        switch (str.toUpperCase()) {
        case ("PAYEE"):
            return Field.PAYEE;
        case ("EMAIL"):
            return Field.EMAIL;
        case ("MATRIC"):
            return Field.MATRIC;
        case ("PHONE"):
            return Field.PHONE;
        case ("ITEM"):
            return Field.ITEM;
        case ("COST"):
            return Field.COST;
        case ("INVOICE"):
            return Field.INV;
        case ("STATUS"):
            return Field.STATUS;
        case ("DEADLINE"):
            return Field.DEADLINE;
        default:
            throw new IllegalArgumentException();
        }
    }

    /**
     * Finds a Payee object from any project using the payee name.
     * User does not have to goto each project to try to find payee.
     * @param projectMap LinkedHashMap containing all the projects in AlphaNUS.
     * @param curr the name of the current project user is using.
     * @param name name of the payee to find
     * @return the Payee object required to find
     */
    public static Payee findPayee(LinkedHashMap<String, Project> projectMap, String curr, String name) {
        Set<String> projectnames = projectMap.keySet();
        String currProject = curr;
        while (!projectMap.get(currProject).managermap.containsKey(name)) {
            projectnames.remove(currProject);
            if (projectnames.isEmpty()) {
                throw new IllegalArgumentException();
            }
            currProject = projectnames.iterator().next();
        }
        Payee found = projectMap.get(currProject).managermap.get(name);
        checkStatus(found);
        return found;
    }

    /**
     * Modifies the specified field of a Payee object.
     * @param payee Name of payee
     * @param fieldToAmend field of payee to amend
     * @param replace what to write to the field
     * @param managermap Hashmap of all payees in project
     * @param ui Print messages to show payee has been edited.
     */
    public static void editPayee(String payee, String fieldToAmend, String replace,
            HashMap<String, Payee> managermap, Ui ui) {
        Field field = strToField(fieldToAmend);
        checkStatus(managermap.get(payee));
        if (field == Field.PAYEE) {
            managermap.get(payee).payee = replace;
        } else if (field == Field.EMAIL) {
            managermap.get(payee).email = replace;
        } else if (field == Field.MATRIC) {
            managermap.get(payee).matricNum = replace;
        } else if (field == Field.PHONE) {
            managermap.get(payee).phoneNum = replace;
        }
        ui.printEditMessage(managermap.get(payee));
    }

    /**
     * Modifies the specified field of a Payment object.
     * @param payee name of payee
     * @param item description of payment 
     * @param fieldToAmend which field to amend
     * @param replace what to replace in that field
     * @param managermap Hashmap of all payees in project
     * @param ui Print messages to show payment has been edited.
     */
    public static void editPayment(String payee, String item, String fieldToAmend, String replace,
        HashMap<String, Payee> managermap, Ui ui) {
        Field field = strToField(fieldToAmend);
        for (Payments payment : managermap.get(payee).payments) {
            if (payment.item.equals(item)) {
                checkStatus(payment);
                if (field == Field.ITEM) {
                    payment.item = replace;
                } else if (field == Field.COST) {
                    payment.cost = Double.parseDouble(replace);
                } else if (field == Field.INV) {
                    payment.inv = replace;
                } else if (field == Field.STATUS) {
                    if (replace.equalsIgnoreCase("pending")) {
                        payment.status = Status.PENDING;
                    } else if (replace.equalsIgnoreCase("approved")) {
                        payment.status = Status.APPROVED;
                    } else if (replace.equalsIgnoreCase("overdue")) {
                        payment.status = Status.OVERDUE;
                    }
                } else if (field == Field.DEADLINE) {
                    SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
                    Date newDeadline = new Date();
                    try {
                        newDeadline = ft.parse(replace);
                        payment.deadline = newDeadline;
                    } catch (ParseException e) {
                        ui.exceptionMessage("\t☹ OOPS!!! Unable to parse date," 
                            + " use dd-mm-yyyy format");
                    }
                }
                ui.printEditMessage(payment, payee);
                break;
            }
        }
    }

    /**
     * Organises all payments in a managermap, output according to status.
     * @param managermap Hashmap of all payees in project
     * @return a list containing a list each for pending, overdue and approved payments.
     */
    public static ArrayList<ArrayList<Payments>> listOfPayments(HashMap<String, Payee> managermap) {
        checkStatus(managermap);
        ArrayList<ArrayList<Payments>> listOfPayments = new ArrayList<>();
        ArrayList<Payments> overdue = new ArrayList<>();
        ArrayList<Payments> pending = new ArrayList<>();
        ArrayList<Payments> approved = new ArrayList<>();
        for (Payee payee : managermap.values()) {
            for (Payments payment : payee.payments) {
                if (payment.status == Status.PENDING) {
                    pending.add(payment);
                } else if (payment.status == Status.OVERDUE) {
                    overdue.add(payment);
                } else {
                    approved.add(payment);
                }
            }
        }
        listOfPayments.add(pending);
        listOfPayments.add(overdue);
        listOfPayments.add(approved);
        return listOfPayments;
    }

    /**
     * Add the Payments object details to PaymentsList.
     * @param project Name of project
     * @param payee Name of payee
     * @param item Description of item
     * @param cost Amount billed in invoice
     * @param inv Invoice ID
     * @param managermap Hashmap containing all payees in project
     * @param dict set of strings containing the vocab used by user
     * @return returns a Payment object representing the newly created payment
     * @throws AlphaNUSException when error is found in writing to dict.json
     */
    public static Payments addPayments(String project, String payee, String item, double cost, String inv, 
            HashMap<String, Payee> managermap, Set<String> dict) {
        for (Payments payment : managermap.get(payee).payments) {
            if (payment.item.equals(item)) {
                throw new IllegalAccessError();
            }
        }
        Payments pay = new Payments(project, payee, item, cost, inv);
        pay.paymentToDict(dict);
        managermap.get(payee).payments.add(pay);
        return pay;
    }

    /**
     * Deletes an existing payment object.
     * @param payee Payee name.
     * @param item Description of payment item
     * @param managermap Hashmap containing all payees in project.
     * @return the payee object that was deleted from managermap to print out a confirmation.
     */
    public static Payments deletePayments(String payee, String item, 
        HashMap<String, Payee> managermap, Set<String> dict) {
        if (!managermap.containsKey(payee)) {
            throw new IllegalAccessError();
        }
        int i = 0;
        while (i < managermap.get(payee).payments.size()) {
            if (managermap.get(payee).payments.get(i++).item.equals(item)) {
                Double cost = managermap.get(payee).payments.get(--i).cost;
                String inv = managermap.get(payee).payments.get(i).inv;
                String prName = managermap.get(payee).payments.get(i).project;
                Payments deleted = new Payments(prName, payee, item, cost, inv);
                managermap.get(payee).payments.remove(i);
                checkStatus(deleted);
                deleted.cleanDict(dict);
                return deleted;
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * Add the Payee object details to managermap.
     * @param project Name of project
     * @param payee Name of payee
     * @param email Email address of payee
     * @param matricNum Matriculation number of payee
     * @param phoneNum Phone number of payee
     * @param managermap Hashmap containing all payees in project
     * @return Payee object that user created
     */
    public static Payee addPayee(String project, String payee, String email, String matricNum, String phoneNum,
                                 HashMap<String, Payee> managermap) {
        if (managermap.keySet().contains(payee)) {
            throw new IllegalArgumentException();
        }
        Payee payeeNew = new Payee(project, payee, email, matricNum, phoneNum);
        managermap.put(payee, payeeNew);
        return payeeNew;
    }

    /**
     * Deletes an existing payee object.
     * @param payee Payee name.
     * @param managermap Hashmap containing all payees in project.
     * @return the payee object that was deleted from managermap to print out a confirmation.
     */
    public static Payee deletePayee(String payee, HashMap<String, Payee> managermap) {
        if (!managermap.containsKey(payee)) {
            throw new IllegalArgumentException();
        }
        Payee payeeDeleted = managermap.get(payee);
        checkStatus(payeeDeleted);
        managermap.remove(payee);
        return payeeDeleted;
    }

    /**
     * This function scans through every payment in managermap and changes its status if needed.
     * @param managermap The managermap to scan through.
     */
    public static void checkStatus(HashMap<String, Payee> managermap) {
        for (Payee payee : managermap.values()) {
            checkStatus(payee);
        }
    }

    /**
     * This function scans through every payment in a payee and changes its status if needed.
     * @param payee The payee to scan through.
     */
    public static void checkStatus(Payee payee) {
        for (Payments payment : payee.payments) {
            checkStatus(payment);
        }
    }

    /**
     * This function checks a payment and changes its status if needed.
     * @param payment The payment object to check through.
     */
    public static void checkStatus(Payments payment) {
        Date currDate = new Date();
        if (payment.status != Status.APPROVED && currDate.compareTo(payment.deadline) > 0) {
            payment.status = Status.OVERDUE;
        }
    }
}
