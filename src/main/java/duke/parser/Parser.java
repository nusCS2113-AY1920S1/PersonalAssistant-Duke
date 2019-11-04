package duke.parser;

import duke.Duke;
import duke.command.Command;
import duke.command.dishesCommand.*;
import duke.command.ingredientCommand.*;
import duke.command.orderCommand.*;
import duke.dish.Dish;
import duke.exception.DukeException;
import duke.ingredient.Ingredient;
import duke.order.Order;

import java.util.Date;

/**
 * Represents a parser used to parse the input String from the user into a Duke understandable Command.
 * It should deals with making sense of the user command.
 */
public class Parser {

    //There is no constructor method for all others are static.

    /**
     * Returns a {@link Command} that can be understood by {@link Duke} and executed after.
     * We first split the fullCommand into 2, the keyword, followed by everything else.
     * Then we perform switching based on the keyword.
     *
     * @param fullCommand The String command entered by the user
     * @return Command The Command to be executed
     * @throws DukeException for any invalid input
     */
    public static Command parse(String fullCommand, Duke.Type type) throws DukeException {
        String[] splitted;

        switch (type) {
            case INGREDIENT: {
                splitted = fullCommand.split(" ");

                if (splitted[0].equals("add")) {
                    if (splitted.length != 4)
                        throw new DukeException("must specify ingredient name, amount and/or expiry date");
                    return new AddCommand(new Ingredient(splitted[1], Integer.parseInt(splitted[2]), splitted[3]));
                }
                if (splitted[0].equals("remove")) {
                    if (splitted.length != 2)
                        throw new DukeException("must specify a index");
                    return new DeleteCommand(Integer.parseInt(splitted[1]));
                } else if (splitted[0].equals("use")) {
                    if (splitted.length != 3)
                        throw new DukeException("follow the template: use <ingredient name> <amount>");
                    return new UseCommand(new Ingredient(splitted[1], Integer.parseInt(splitted[2]), new Date()));
                } else if (splitted[0].equals("listtoday")) {
                    if (splitted.length != 1)
                        throw new DukeException("follow the template: listtoday");
                    return new FindToday();
                } else if(splitted[0].equals("find")) {
                    if(splitted.length != 2)
                        throw new DukeException("follow the template: find <ingredient name>");
                    return new FindIngredientCommand(splitted[1]);
                }
                else
                    throw new DukeException("not a valid command for an Ingredient");
            }
            case DISH: {
                splitted = fullCommand.split(" ", 2);
                switch (splitted[0]) {
                    case "add":
                        if(splitted.length < 2) {
                            throw new DukeException("specify dish name");
                        }
                        else
                            splitted[1] = splitted[1].replaceAll("\\s+", " ");
                        return new AddDishCommand(new Dish(splitted[1]));
                    case "remove":
                        try {
                            return new DeleteDishCommand(Integer.parseInt(splitted[1]));
                        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                            throw new DukeException("enter a valid index");
                        }
                    case "list":
                        return new ListDishCommand();
                    case "initialize":
                        return new ResetDishCommand();
                    case "ingredient":
                        String[] getIng = splitted[1].split(" ", 3);
                        int amount = 0;
                        int index = 0;
                        try {
                            amount = Integer.parseInt(getIng[1]);
                            index = Integer.parseInt(getIng[2]);
                        } catch (NumberFormatException e) {
                            throw new DukeException("enter a valid amount/index");
                        }
                        return new AddIngredient(new Ingredient(getIng[0], amount, new Date()), index);
                    default:
                        throw new DukeException("not a valid command for a Dish");
                }

            }
            case ORDER: {
                splitted = fullCommand.split(" ", 2);
                if (splitted.length > 4)
                    throw new DukeException("must specify ordered dishes and order date");
                else if (splitted[0].equals("add")){  //add a new order
                    return addOrderParser(splitted);
                } else if (splitted[0].equals("alter")) { //alter order date
                    return alterOrderDateParser(splitted);
                } else if (splitted[0].equals("remove") ||splitted[0].equals("done")) { //remove or done an order
                    return removeOrDoneOrderParser(splitted);
                } else if (splitted[0].equals("init")) {
                    return new InitOrderListCommand();
                } else if (splitted[0].equals("list")) {//list orders
                    String[] listType;
                    if (splitted.length == 1) {
                        listType = "-l all".split(" ",2);
                    } else listType = splitted[1].split(" ",2);
                    if (listType.length==1) {throw new DukeException("Must enter a list type, dishes name, or order date");}
                    return new ListOrderCommand(listType);
                } else throw new DukeException("Not a valid command for an order");
            }
            default:
                throw new DukeException("not a valid type");
        }
    }

    /**
     * Checks the length of a String array is of size 2.
     *
     * @throws DukeException when array is not of size 2.
     */
    public static void checkLength(String[] str) throws DukeException {
        if (str.length != 2) {
            throw new DukeException("The description cannot be empty.");
        }
    }

    /**
     * Split a string and check its length.
     */
    public static String[] splitAndCheck(String str, String regex) throws DukeException {
        String[] part = str.split(regex, 2);
        checkLength(part); //Throws DukeException
        return part;
    }

    /**
     * Converts a string into a number, and checks if it is out of bounds.
     *
     * @return Returns a valid integer
     * @throws DukeException when it is invalid
     */
    public static int checkNumber(String str, int size) throws DukeException {
        int x;
        try {
            //Minus one because index starts from zero.
            //Throws NumberFormatException
            x = Integer.parseInt(str) - 1;
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
        if (x < 0 || x >= size) {
            //Index is out of bounds
            throw new DukeException("FUCK YOU JOEY!");
        }
        return x;
    }

    public static Command addOrderParser(String[] splitter) throws DukeException {
        Order newOrder;
        Date orderDate;
        String[] orderedDishes;
        if (splitter[1].startsWith("-n ")) {
            if (splitter[1].length()<4) { throw new DukeException("Must specify dishes name"); }
            newOrder = new Order();
            orderedDishes = splitter[1].substring(3).split(", ");
        } else if (splitter[1].startsWith("-d ")&&splitter[1].length()>20) {
            String[] dateAndDish = splitter[1].substring(3).split(" -n ",2);
            if (dateAndDish[0].length()!=10) { throw new DukeException("Must enter a valid order date: dd/mm/yyyy"); }
            orderDate = Convert.stringToDate(dateAndDish[0]);
            newOrder = new Order(orderDate);
            orderedDishes = dateAndDish[1].split(", ");
        } else { throw new DukeException("must enter a valid order date or specify dishes"); }
        for (String dishes: orderedDishes) {
            String[] dishesSplit = dishes.split("\\*", 2);
            if (dishesSplit.length==1) newOrder.addDish(dishesSplit[0], 1);
            else {
                int dishAmount;
                try{
                    dishAmount = Integer.parseInt(dishesSplit[1]);
                    if (dishAmount<=0) {throw new DukeException("Must enter a dishes amount larger than 1");}
                } catch (NumberFormatException e) {
                    throw new DukeException("cannot resolve non-integer or too large dishes amount");
                }
                newOrder.addDish(dishesSplit[0], dishAmount);
            }
        }
        return new AddOrderCommand(newOrder);
    }

    public static Command alterOrderDateParser(String[] splitter) throws DukeException {
        if (splitter.length == 1) {
            throw new DukeException("Must enter an order index.\n\t Note that ORDER_INDEX starts from 1");
        }
        String[] indexAndDate = splitter[1].split(" ", 2);
        int orderIndex;
        Date orderDate;
        try {
            orderIndex = Integer.parseInt(indexAndDate[0]);
            if (orderIndex <= 0) throw new DukeException("Must enter a positive order index");
        } catch (NumberFormatException e) {
            throw new DukeException("Must enter a valid order index");
        }
        if (indexAndDate.length == 1) { orderDate = new Date(); }
        else {
            orderDate = Convert.stringToDate(indexAndDate[1]);
            if (orderDate==null) {throw new DukeException("Error when converting order date");}
        }
        return new AlterDateCommand(orderIndex-1, orderDate);
    }

    public static Command removeOrDoneOrderParser(String[] splitter) throws DukeException {
        if (splitter.length == 1) {
            throw new DukeException("Must enter an order index.\n\t Note that ORDER_INDEX starts from 1");
        }
        try {
            int orderIndex = Integer.parseInt(splitter[1]);
            if (orderIndex <= 0) throw new DukeException("Must enter a positive order index");
            if (splitter[0].equals("remove")) return new DeleteOrderCommand(orderIndex - 1);
            else return new DoneOrderCommand(orderIndex - 1);
        } catch (NumberFormatException e) {
            throw new DukeException("Must enter a valid order index");
        }
    }
}