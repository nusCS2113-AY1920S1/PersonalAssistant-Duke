package payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


/**
 * PaymentManager for managing Payments objects and PaymentForms from the PaymentsList.
 */
public abstract class PaymentManager {
    static ArrayList<Payments> paymentsArrayList = new ArrayList<Payments>();

    /**
     * Finds the Payments objects containing a payee name and returns a list of Payments.
     *
     * @param payee Payee of the item.
     */

    public static ArrayList<Payments> findPayee(String payee, HashMap<String, Payee> managermap) {

        for (Payments payment : managermap.get(payee).payments) {
            paymentsArrayList.add(payment);
            //TODO Output payment to UI
        }
        return paymentsArrayList;
    }

    /**
     * Edits the Payments object details, may overload string to take different ways of inputs.
     */
    public void editPayee(String payee, String inv, Field field, String replace, HashMap<String, Payee> managermap) {
        if (inv.isEmpty()) {
            if (field == Field.PAYEE) {
                managermap.get(payee).payee = replace;
            } else if (field == Field.EMAIL) {
                managermap.get(payee).email = replace;
            } else if (field == Field.MATRIC) {
                managermap.get(payee).matricNum = replace;
            } else if (field == Field.PHONE) {
                managermap.get(payee).phoneNum = replace;
            }
        } else {
            for (Payments payment : managermap.get(payee).payments) {
                if (payment.inv.equals(inv)) {
                    if (field == Field.ITEM) {
                        payment.item = replace;
                    } else if (field == Field.COST) {
                        payment.cost = Double.parseDouble(replace);
                    } else if (field == Field.INV) {
                        payment.inv = replace;
                    }
                    break;
                }
                assert (false); //Invalid invoice number <-- TODO : Raise error
            }
        }
    }

    /**
     * List the Payments object details, may extend to generate statement of accounts.
     */
    public static void listPayments(HashMap<String, Payee> managermap) {
        ArrayList<Payments> overdue = new ArrayList<>();
        ArrayList<Payments> pending = new ArrayList<>();
        ArrayList<Payments> approved = new ArrayList<>();
        Date currDate = new Date();
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
        // printList(); <-- TODO : Modify implementation in UI
    }

    /**
     * Deletes the Payments object details.
     */
    public static Payments deletePayments(String payee, String item, HashMap<String, Payee> managermap) {
        int i = 0;
        while (i < managermap.get(payee).payments.size()) {
            if (managermap.get(payee).payments.get(i++).item.equals(item)) {
                Payments deleted = new Payments(item, managermap.get(payee).payments.get(--i).cost,
                        managermap.get(payee).payments.get(i).inv);
                managermap.get(payee).payments.remove(i);
                return deleted;
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * Add the Payments object details to PaymentsList.
     */
    public static Payments addPayments(String payee, String item, double cost, String inv,
                                       HashMap<String, Payee> managermap) {
        Payments pay = new Payments(item, cost, inv);
        managermap.get(payee).payments.add(pay);
        return pay;
    }

    /**
     * Add Payee object to managermap.
     */
    public static Payee addPayee(String payee, String email, String matricNum, String phoneNum,
                                 HashMap<String, Payee> managermap) {
        Payee payeeNew = new Payee(payee, email, matricNum, phoneNum);
        managermap.put(payee, payeeNew);
        return payeeNew;
    }

    /**
     * @param payee payee name.
     * @param email email id of payee.
     * @param matricNum matriculation number of payee.
     * @param phoneNum phone number of payee.
     * @param managermap Hashmap where the key is a payee's name and the value is the details(given above) of that payee.
     * @return payeeDeleted Payee object of the payee being deleted.
     */
    public static Payee deletePayee(String payee, String email, String matricNum, String phoneNum,
                                    HashMap<String, Payee> managermap) {
        Payee payeeDeleted = new Payee(payee, email, matricNum, phoneNum);
        managermap.remove(payee);
        return payeeDeleted;
    }
}
