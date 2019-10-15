package payment;

import java.lang.reflect.GenericDeclaration;
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
    public void findPayee(String payee, HashMap<String, Payee> ManagerMap) {
        for (Payments payment : ManagerMap.get(payee).payments) {
            // <-- TODO : Output payment to UI
        }
    }

    /**
     * Edits the Payments object details, may overload string to take different ways of inputs.
     */
    public void editPayee(String payee, String inv, Field field, String replace, HashMap<String, Payee> ManagerMap) {
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
                assert(false); //Invalid invoice number <-- TODO : Raise error
            }
        }
    }

    /**
     * List the Payments object details, may extend to generate statement of accounts
     */
    public static void listPayments(HashMap<String, Payee> ManagerMap){
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
    public static Payments deletePayments(String payee, String item, HashMap<String, Payee> ManagerMap){
        int i = 0;
        while (i < ManagerMap.get(payee).payments.size()) {
            if (ManagerMap.get(payee).payments.get(i++).item.equals(item)) {
                Payments deleted = new Payments(payee, ManagerMap.get(payee).payments.get(--i).cost, ManagerMap.get(payee).payments.get(i).inv);
                ManagerMap.get(payee).payments.remove(i);
                return deleted;
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * Add the Payments object details to PaymentsList
     */
    public static Payments addPayments(String payee, String item, double cost, String inv, HashMap<String, Payee> ManagerMap){
        Payments pay = new Payments(item, cost, inv);
        ManagerMap.get(payee).payments.add(pay);
        return pay;
    }

    /**
     * Add Payee object to ManagerMap
     */
    public static Payee addPayee(String payee, String email, String matricNum, String phoneNum, HashMap<String, Payee> ManagerMap){
        Payee payeeNew = new Payee(payee, email, matricNum, phoneNum);
        ManagerMap.put(payee, payeeNew);
        return payeeNew;
    }
}
