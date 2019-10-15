package payment;

import java.lang.reflect.GenericDeclaration;
import java.util.*;

/**
 * PaymentManager for managing Payments objects and PaymentForms from the PaymentsList
 */
public class PaymentManager {
    HashMap<String, Payee> ManagerMap;

    /**
     * Finds the Payments objects containing a payee name and returns a list of Payments.
     * @param payee Payee of the item.
     * @return Payments object with payee name.
     */
    public void findPayee(String payee) {
        for (Payments payment : ManagerMap.get(payee).payments) {
            //TODO Output payment to UI
        }
    }

    /**
     * Edits the Payments object details, may overload string to take different ways of inputs.
     */
    public void editPayee(String payee, String inv, Field field, String replace) {
        if (inv.isEmpty()) {
            if (field == Field.PAYEE) {
                ManagerMap.get(payee).payee = replace;
            } else if (field == Field.EMAIL) {
                ManagerMap.get(payee).email = replace;
            } else if (field == Field.MATRIC) {
                ManagerMap.get(payee).matricNum = replace;
            } else if (field == Field.PHONE) {
                ManagerMap.get(payee).phoneNum = replace;
            }
        }
        else {
            for (Payments payment : ManagerMap.get(payee).payments) {
                if (payment.inv.equals(inv)) {
                    if (field == Field.ITEM) {
                        payment.item = replace;
                    }
                    else if (field == Field.COST) {
                        payment.cost = Double.parseDouble(replace);
                    }
                    else if (field == Field.INV) {
                        payment.inv = replace;
                    }
                    break;
                }
                assert(false); //Invalid invoice number
            }
        }
    }

    /**
     * List the Payments object details, may extend to generate statement of accounts
     */
    public void listPayments(){
        ArrayList<Payments> overdue = new ArrayList<>();
        ArrayList<Payments> pending = new ArrayList<>();
        ArrayList<Payments> approved = new ArrayList<>();
        Date currDate = new Date();
        for (Payee payee : ManagerMap.values()) {
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
    public void deletePayments(String payee, String item){
        int i = 0;
        while (i < ManagerMap.get(payee).payments.size()) {
            if (ManagerMap.get(payee).payments.get(i++).equals(item)) {
                ManagerMap.get(payee).payments.remove(--i);
            }
        }
        // printDeleteMessage(); <-- TODO : Modify implementation in UI
    }

    /**
     * Add the Payments object details to PaymentsList
     */
    public void addPayments(String payee, String item, double cost, String inv){
        Payments pay = new Payments(item, cost, inv);
        ManagerMap.get(payee).payments.add(pay);
    }
    // printAddedMessage(); <-- TODO : Modify implementation in UI
}
