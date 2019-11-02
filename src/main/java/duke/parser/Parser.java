package duke.parser;

import duke.Duke;
import duke.command.Cmd;
import duke.command.ingredientCommand.AddCommand;
import duke.command.ingredientCommand.DeleteCommand;
import duke.command.ingredientCommand.UseCommand;
import duke.command.orderCommand.*;
import duke.dish.Dish;
import duke.exception.DukeException;
import duke.ingredient.Ingredient;
import duke.order.Order;
import duke.command.dishesCommand.*;
import duke.command.ingredientCommand.*;
import duke.command.dishesCommand.InitCommand;

import java.util.Date;

/**
 * Represents a parser used to parse the input String from the user into a Duke understandable Command.
 * It should deals with making sense of the user command.
 */
public class Parser {

    //There is no constructor method for all others are static.

    /**
     * Returns a {@link Cmd} that can be understood by {@link Duke} and executed after.
     * We first split the fullCommand into 2, the keyword, followed by everything else.
     * Then we perform switching based on the keyword.
     *
     * @param fullCommand The String command entered by the user
     * @return Command The Command to be executed
     * @throws DukeException for any invalid input
     */
    public static Cmd parse(String fullCommand, Duke.Type type) throws DukeException {
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
                    return new DeleteCommand<Ingredient>(Integer.parseInt(splitted[1]));
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
                        return new InitCommand();
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
                else if (splitted[0].equals("add")){ //add a new order
                    String[] split = splitted[1].split(" /on ",2);
                    Order newOrder = null;
                    Date orderDate;
                    if (split.length==1) { newOrder = new Order(); }
                    else if (split.length==2) {
                        orderDate = Convert.stringToDate(split[1]);
                        newOrder = new Order(orderDate);
                    } else { throw new DukeException("must enter a valid date"); }
                    String[] orderDishes = split[0].split(", ");
                    for (String dishes: orderDishes) {
                        String[] dishesSplit = dishes.split("\\*", 2);
                        newOrder.addDish(dishesSplit[0], Integer.parseInt(dishesSplit[1]));
                    }
                    return new AddOrderCommand(newOrder);
                } else if (splitted[0].equals("alter")) { //alter order date
                    if (splitted.length==1) { throw new DukeException("Must specify order number! *starting from 1*"); }
                    Date orderDate;
                    int orderIndex;
                    String[] split2 = splitted[1].split(" ",2);
                    if (split2.length==1) {
                        orderIndex = Integer.parseInt(split2[0]);
                        orderDate = new Date();
                    }
                    else {
                        orderIndex = Integer.parseInt(split2[0]);
                        orderDate = Convert.stringToDate(split2[1]);
                    }
                    return new AlterDateCommand(orderIndex,orderDate);

                } else if (splitted[0].equals("done")) {//done an order
                    if (splitted.length==1) { throw new DukeException("Must specify order number! *starting from 1*"); }
                    try {
                        int orderIndex = Integer.parseInt(splitted[1]);
                        return new DoneOrderCommand(orderIndex-1);
                    } catch (NumberFormatException e) {
                        throw new DukeException("enter a valid index");
                    }
                } else if (splitted[0].equals("remove")) {//remove an order
                    if (splitted.length==1) { throw new DukeException("Must specify order number! *starting from 1*"); }
                    try {
                        int orderIndex = Integer.parseInt(splitted[1]);
                        return new DeleteOrderCommand(orderIndex-1);
                    } catch (NumberFormatException e) {
                        throw new DukeException("enter a valid index");
                    }
                } else if (splitted[0].equals("list")) {//list orders
                    if (splitted.length==1) { return new ListOrderCommand("all"); }
                    String listType = splitted[1];
                    String[] split2 = listType.split(" ",2);
                    if (split2.length==1) { return new ListOrderCommand(split2[0]); }
                    else { return new ListOrderCommand(split2[1]); }
                } else if (splitted[0].equals("init")) {
                    return new InitOrderListCommand();
                } else throw new DukeException("not a valid command for an order");
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
}
