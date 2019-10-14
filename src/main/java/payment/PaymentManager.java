package payment;

import java.util.*;

/**
 * PaymentManager for managing Payments objects and PaymentForms from the PaymentsList
 */
public abstract class PaymentManager {


    /**
     * Finds the Payments objects containing a payee name and returns a list of Payments.
     * @param payee Payee of the item.
     * @return Payments object with payee name.
     */
    public static void findPayee(String payee, ArrayList<Payments> paymentslist){
        //TODO
    }

    /**
     * Edits the Payments object details, may overload string to take different ways of inputs.
     */
    public static void editPayments(String payee){
        //TODO
    }

    /**
     * List the Payments object details, may extend to generate statement of accounts
     */
    public static void listPayments(HashMap<String, Payee> managermap){
        ArrayList<Payments> overdue = new ArrayList<>();
        ArrayList<Payments> pending = new ArrayList<>();
        ArrayList<Payments> approved = new ArrayList<>();
        Date currDate = new Date();
        for (Payee payee : managermap.values()) {
            for (Payments payment : payee.payments) {
                if (payment.status == Status.PENDING) pending.add(payment);
                else if (payment.status == Status.OVERDUE) overdue.add(payment);
                else approved.add(payment);
            }
        }
        // printList(); <-- TODO : Modify implementation in UI
    }

    /**
     * Deletes the Payments object details
     */
    public static void deletePayments(String payee, String item, HashMap<String, Payee> managermap){
        int i = 0;
        while (i < managermap.get(payee).payments.size()) {
            if (managermap.get(payee).payments.get(i++).equals(item)) {
                managermap.get(payee).payments.remove(--i);
            }
        }
        // printDeleteMessage(); <-- TODO : Modify implementation in UI
    }

    /**
     * Add the Payments object details to PaymentsList
     */
    public static Payments addPayments(String payee, String item, double cost, String inv, HashMap<String, Payee> managermap){
        Payments pay = new Payments(item, cost, inv);
        managermap.get(payee).payments.add(pay);
        return pay;
    }

    /**
     * Add Payee object to managermap
     */
    public static Payee addPayee(String payee, String email, String matricNum, String phoneNum, HashMap<String, Payee> managermap){
        Payee payeeNew = new Payee(payee, email, matricNum, phoneNum);
        managermap.put(payee, payeeNew);
        return payeeNew;
    }


}
