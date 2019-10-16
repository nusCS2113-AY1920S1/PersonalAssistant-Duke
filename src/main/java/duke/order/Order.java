package duke.order;

import duke.Duke;
import duke.exception.DukeException;
import duke.parser.Convert;
import duke.task.Dish;

import java.util.*;

/**
 * Represents a general Order to be added by {@link Duke}.
 */
public class Order {
    private Map<Integer, Integer> content;  //first int is dishNb, second is dishAmount
    private boolean isDone;
    private Date date;
    private String name;

    /**
     * The constructor method for {@link Order}.
     */
    public Order() {
        this.isDone = false;
        this.date = new Date();
        this.content = new HashMap<>();
        this.name = "";
    }

    /**
     * The constructor method for the {@link Order} in reservation case.
     * @param date date of serving the {@link Order}.
     */
    public Order(String date, String name) {
        this.date = (date=="")? new Date():Convert.stringToDate(date);
        this.isDone = false;
        this.content = new HashMap<>();
        this.name = name;
    }

    /**
     * Used to get the serving date of the {@link Order}.
     */
    public Date getDate() { return this.date;}

    /**
     * Used to get the customer name of the {@link Order}.
     */
    public String getCustomerName() { return this.name;}

    /**
     * Used to alter the serving date of the {@link Order}.
     * @param date reset date of the {@link Order}.
     */
    public void setDate(String date) {
        this.date = Convert.stringToDate(date);
    }

    /**
     * Used to alter the serving date of the {@link Order}.
     * @param name reset customer name of the reservation {@link Order}.
     */
    public void setCustomerName(String name) {
        this.name = name;
    }

    /**
     * Returns a boolean indicating whether the {@link Order} was completed.
     * @return boolean true if the order was marked as done, false otherwise
     */
    public boolean isDone() { return isDone; }

    /**
     * Returns a String representation of the status icon, indicating whether the {@link Order} was done.
     * @return a tick or a cross
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    /**
     * Returns the content of the {@link Order}.
     * Write the content into file.
     * @return content of the Order
     */
    public Map<Integer, Integer> getOrder() { return this.content; }

    public String printInFile() {
        String desc;
        if (this.isDone()) { desc = "O|1|" + this.date + "|" + this.name; }
        else { desc = "O|0|" + this.date + "|" + this.name; }
        for (Map.Entry<Integer, Integer> entry : content.entrySet()) {
            int DishNb = entry.getKey();
            int DishAmount = entry.getValue();
            desc += "\nD|" + DishAmount + "|" + DishNb;
        }
        return desc;
    }

    /**
     * Returns the content of the {@link Order}.
     * Show the content to the user/customer.
     * @return content of the Order
     */
    public String toString() {
        String description = "[" + getStatusIcon() + "] ";
        if (this.date!=new Date()) {
            description += "Preorder under "+ this.name + " on " + this.date;
        }else {
            description += "Order Today";
        }
        for (Map.Entry<Integer, Integer> entry : content.entrySet()) {
            try {
                int DishNb = entry.getKey();
                String DishName = getDishName(DishNb);
                int DishAmount = entry.getValue();
                description += "\n" + DishAmount + DishName;
            } catch (DukeException e) {
                e.printStackTrace();
            }
        }
        return description;
    }

    public double getTotalPrice() {
        //To do
        //link the price with DishNb s.t. can calculate total price
        int unitPrice = 1;

        double total = 0;
        for (Map.Entry<Integer, Integer> entry : content.entrySet()) {
            int DishNb = entry.getKey();
            int DishAmount = entry.getValue();
            total += DishAmount*unitPrice;
        }
        return total;
    }

    public void addDish(int dishNb, int amount) {
        this.content.put(dishNb, amount);
    }

    /**
     * Used to mark the {@link Order} as finished.
     */
    public void markAsDone() {
        this.isDone = true;

        //To do
        // trigger get feedback here

    }

    public String getDishName(int dishNb) throws DukeException {
        try {
            switch (dishNb) {
                case 1: return "Beef Noodle";
                case 2: return "Pork Dumplings";
                case 3: return "Chili Crab";
                case 4: return "Cereal Prawn";
                case 5: return "Laksa";
                case 6: return "Chicken Rice";
                case 7: return "Seasonal Vegetables";
                default:
                    throw new DukeException("No corresponding dishes in today's menu!");
            }
        }catch (Exception e){
            e.getMessage();
        }
        return "";
    }

}
