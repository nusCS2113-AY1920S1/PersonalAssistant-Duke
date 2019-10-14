package payment;

/**
 * Payments Class to manage the payment details of an item.
 */
public class Payments {
    String item;
    double cost;
    String payee;
    String email;
    String matricNum;
    String phoneNum;

    public Payments(String item, double cost, String payee, String email, String matricNum, String phoneNum){
        this.item = item;
        this.cost = cost;
        this.payee = payee;
        this.email = email;
        this.matricNum = matricNum;
        this.phoneNum = phoneNum;
    }

}
