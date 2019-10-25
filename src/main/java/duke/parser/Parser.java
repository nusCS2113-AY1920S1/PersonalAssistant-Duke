package duke.parser;

import duke.command.Cmd;
import duke.command.dishesCommand.*;
import duke.command.ingredientCommand.*;
import duke.command.orderCommand.*;
import duke.command.dishesCommand.InitCommand;
import duke.dish.Dish;
import duke.exception.DukeException;
import duke.ingredient.Ingredient;
import duke.order.Order;
import duke.task.Deadline;
import duke.task.DoWithinPeriodTasks;
import duke.task.Event;
import duke.task.Todo;
import java.util.Date;

/**
 * Represents a parser used to parse the input String from the user into a Duke understandable Command.
 * It should deals with making sense of the user command.
 */
public class Parser {

    //There is no constructor method for all others are static.

    /**
     * Checks the length of a String array is of size 2.
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
     * For commands starting with "dish".
     * @param command string
     * @return a dish command
     * @throws DukeException when cannot
     */
    public static Cmd dish(String command) throws DukeException {
        String[] split = splitAndCheck(command, " ");
        switch (split[0]) {
            case "add":
                //Example: dish add cake /num 1
                String[] getnum = splitAndCheck(split[1], " /num ");
                int amount = Integer.parseInt(getnum[1]);
                return new AddDishCommand(new Dish(getnum[0]), amount);
            case "list":
                return new ListDishCommand();
            case "delete" :
                int orderNb = Integer.parseInt(split[1]);
                return new DeleteDishCommand(orderNb);
            case "ingr" :
                //Example: dish ingr milk /add 2
                String[] getIng = splitAndCheck(split[1], " /add ");
                int listNum = Integer.parseInt(getIng[1]);
                return new AddIngredient(new Ingredient(getIng[0], listNum, new Date()) , listNum);
            case "init" :
                return new InitCommand();
            default:
                throw new DukeException("I'm sorry, but I don't know what that means :-(");
        }
    }

    /**
     * For commands starting with "order".
     * @param command string
     * @return an order command
     * @throws DukeException when cannot
     */
    public static Cmd order(String command) throws DukeException {
        String[] split = splitAndCheck(command, " ");
        switch (split[0]) {
            case "add":
                return new AddOrderCommand(new Order(), split[1]);
            case "list":
                //split[1] can be order list all, order list undone,
                //                order list today, order list undoneToday,
                //                order list date <xxxx/xx/xx>,
                //                order list dish <dishname>
                return new ListOrderCommand(split[1]);
            case "done":
                return new DoneOrderCommand(Integer.parseInt(split[1]));
            case "delete":
                return new DeleteOrderCommand(Integer.parseInt(split[1]));
            case "alterDate":
                //getDate[0] is the order index, getDate[1] is the newly set date
                //Example: order alterDate 2 /to 09/09/09
                String[] getDate = splitAndCheck(split[1], " /to ");
                return new AlterDateCommand(Integer.parseInt(getDate[0]), getDate[1]);
            default:
                throw new DukeException("I'm sorry, but I don't know what that means :-(");
        }
    }

    /**
     * For all commands.
     * @param fullCommand string
     * @return any suitable command
     * @throws DukeException when really cannot
     */
    public static Cmd parse(String fullCommand) throws DukeException {
        String[] split = splitAndCheck(fullCommand, " ");
        switch (split[0]) {
            case "dish":
                return dish(split[1]);
            case "order":
                return order(split[1]);
            default:
                throw new DukeException("I'm sorry, but I don't know what that means :-(");
        }
    }
}
