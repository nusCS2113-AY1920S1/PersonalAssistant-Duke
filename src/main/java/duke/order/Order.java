package duke.order;

import duke.Duke;
import duke.exception.DukeException;
import duke.storage.Printable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents a general Order to be added by {@link Duke}.
 */
public class Order implements Printable {
    private Map<String, Integer> content;
    private boolean isDone;
    private Date date;
    private String dateToString;

    /**
     * The constructor method for {@link Order}.
     */
    public Order() {
        this.isDone = false;
        this.date = new Date();
        this.content = new LinkedHashMap<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.dateToString = simpleDateFormat.format(this.date);
    }

    /**
     * The constructor method for the {@link Order} in reservation case.
     * @param date date of serving the {@link Order}.
     */
    public Order(Date date) {
        if (date.before(new Date())) {date = new Date();}
        this.date = date;
        this.isDone = false;
        this.content = new LinkedHashMap<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.dateToString = simpleDateFormat.format(this.date);

    }

    /**
     * Used to get the serving date of the {@link Order}.
     */
    public String getDate() { return this.dateToString;}

    /**
     * Used to alter the serving date of the {@link Order}.
     * @param date reset date of the {@link Order}.
     */
    public void setDate(Date date) throws DukeException {
        Date setDate = date;
        Date todayDate = new Date();
        if (setDate.before(todayDate)) { throw new DukeException("Must set date equal or after today"); }
        this.date = setDate;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String setDateToString = simpleDateFormat.format(setDate);
        this.dateToString = setDateToString;
    }

    /**
     * Returns a boolean indicating whether the serving date of the {@link Order}
     * is today or not.
     * @return boolean true if it is today's order, false otherwise (i.e., pre-order)
     */
    public boolean isToday() {
        Date today = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String todayToString = simpleDateFormat.format(today);
        if (this.dateToString.equals(todayToString)) { return true;}
        else return false;
    }

    /**
     * Returns a boolean indicating whether the {@link Order} was completed.
     * @return boolean true if the order was marked as done, false otherwise
     */
    public boolean isDone() { return isDone; }

    /**
     * Used to mark the {@link Order} as finished.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Returns a String representation of the status icon, indicating whether the {@link Order} was done.
     * @return a tick or a cross
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    /**
     * Returns the content of the {@link Order}.
     * @return content of the Order
     */
    public Map<String, Integer> getOrderContent() { return this.content; }

    /**
     * Returns the content of the {@link Order}
     * that will be print out as a msg.
     * @return content of the Order as a string
     */
    public String toString() {
        String description;
        description = "["+this.getStatusIcon()+"] ";
        if (this.isToday()) { description += "Order today "; }
        else { description += "Order /on " + dateToString + " "; }
        int count=0;
        for (Map.Entry<String, Integer> entry : content.entrySet()) {
            String dishName = entry.getKey();
            int amount = entry.getValue();
            count++;
            description += "\n\t"+"    (" + count + ") " + dishName + " " + amount;
        }
        return description;
    }

    /**
     * Returns the content of the {@link Order}
     * that will be write into a txt file.
     * @return content of the Order as a string
     */
    public String printInFile() {
        String description;
        if (this.isDone()) { description = "1|" + dateToString; }
        else { description = "0|" + dateToString; }
        for (Map.Entry<String, Integer> entry : content.entrySet()) {
            String dishName = entry.getKey();
            int amount = entry.getValue();
            description += "|D|" + dishName + "|" + amount;
        }
        return description;
    }

    /**
     * Returns a boolean indicating whether the {@link Order} has that dishes.
     * @return boolean true if the order has that dishes, false otherwise.
     */
    public boolean hasDishes(String dishes) { return content.containsKey(dishes); }

    /**
     * Returns the amount of the dishes ordered in the {@link Order}
     * @param dishes dishes
     * @return dishes amount
     */
    public int getDishesAmount(String dishes) {
        if (this.hasDishes(dishes)) {
            return content.get(dishes);
        } else {return 0; }
    }

    /**
     * Add dishes to the undone {@link Order}. By default,
     * add one more if not specifying the amount.
     * If the dishes is not found in the {@link Order},
     * simply add a new element in the content map.
     * If the order is done, do nothing.
     */
    public void addDish(String dishes){
        if (!this.isDone())
            if (!this.hasDishes(dishes)) {
                content.put(dishes, 1);
            } else {
                int oldAmount = this.getDishesAmount(dishes);
                content.put(dishes, oldAmount+1);
            }
    }

    /**
     * Add dishes to the undone {@link Order}
     * If the dishes is not found in the {@link Order},
     * simply add a new element in the content map.
     * If the order is done, do nothing.
     */
    public void addDish(String dishes, int addAmount){
        if (!this.isDone())
            if (!content.containsKey(dishes)) {
                content.put(dishes, addAmount);
            } else {
                int oldAmount = content.get(dishes);
                content.put(dishes, oldAmount+addAmount);
            }
    }

}




