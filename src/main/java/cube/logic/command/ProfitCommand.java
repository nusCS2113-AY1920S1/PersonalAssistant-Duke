//@@author LL-Pengfei
/**
 * ProfitCommand.java
 * Support commands related to generating profits and revenue.
 */
package cube.logic.command;

import cube.logic.command.exception.CommandException;
import cube.model.food.FoodList;
import cube.model.food.Food;
import cube.model.ModelManager;
import cube.storage.StorageManager;
import cube.logic.command.util.CommandResult;
import cube.logic.command.util.CommandUtil;

import java.util.Date;

/**
 * This class supports commands related to generating revenue.
 */
public class ProfitCommand extends Command {
    /**
     * Use enums to specify the states of the object whose revenue is to be generated.
     */
    public enum ProfitBy {
        INDEX, NAME, TYPE, ALL
    }

    private int profitIndex;
    private String profitDescription;
    private ProfitCommand.ProfitBy param;
    private final String MESSAGE_SUCCESS_ALL = "Nice! I've generated the revenue for all the stocks:\n"
            + "$ %1$s\n"
            + "In total, you have %2$s food in the list.\n";
    private final String MESSAGE_SUCCESS_SINGLE = "Nice! I've generated the revenue for this food:\n"
            + "$ %1$s\n"
            + "In total, you have %2$s food in the list.\n";
    private final String MESSAGE_SUCCESS_MULTIPLE = "Nice! I've generated the revenue for this type:\n"
            + "$ %1$s\n"
            + "This type contains "
            + "%2$s food items\n"
            + "In total, you have %3$s food in the list.\n";

    /**
     * The default constructor, empty since parameters are required to perform generating revenue command.
     */
    public ProfitCommand() {
    }


    /**
     * The constructor for generating the total revenue.
     *
     * @param param The parameter is used to specify the type of generating revenue.
     */
    public ProfitCommand(Date date1, Date date2, String param) {
        this.param = ProfitCommand.ProfitBy.valueOf(param);
    }

    /**
     * The constructor for generating revenue using index.
     *
     * @param index The index of the food whose revenue is to be generated.
     * @param param The parameter is used to specify the type of generating revenue.
     */
    public ProfitCommand(Date date1, Date date2, int index, String param) {
        this.profitIndex = index - 1;
        this.param = ProfitCommand.ProfitBy.valueOf(param);
    }
    /**
     * The constructor for generating revenue using food name or food type.
     *
     * @param ProfitDescription The food name or food type whose revenue is to be generated.
     * @param param The parameter is used to specify the type of generating revenue.
     */
    public ProfitCommand(Date date1, Date date2, String ProfitDescription, String param) {
        this.profitDescription = ProfitDescription;
        this.param = ProfitCommand.ProfitBy.valueOf(param);
    }


    /**
     * The class generates the revenue for food whose revenue the user wishes to generate.
     *
     * @param storage The storage we have.
     * @return The Feedback to User for Generate Revenue Command.
     * @throws CommandException If Generating Revenue is unsuccessful.
     */
    @Override
    public CommandResult execute(ModelManager model, StorageManager storage) throws CommandException {
        FoodList list = model.getFoodList();
        Food toGenerateRevenue;
        switch (param) {
            case ALL:
                return new CommandResult(String.format(MESSAGE_SUCCESS_ALL, Food.getRevenue(), list.size()));
            case INDEX:
                CommandUtil.requireValidIndex(list, profitIndex);
                toGenerateRevenue = list.get(profitIndex);
                return new CommandResult(String.format(MESSAGE_SUCCESS_SINGLE, toGenerateRevenue.getFoodRevenue(), list.size()));
            case NAME:
                CommandUtil.requireValidName(list, profitDescription);
                toGenerateRevenue = list.get(profitDescription);
                return new CommandResult(String.format(MESSAGE_SUCCESS_SINGLE, toGenerateRevenue.getFoodRevenue(), list.size()));
            case TYPE:
                CommandUtil.requireValidType(list, profitDescription);
                double totalRevenue = 0;
                int count = 0, listSize = list.size(); //listSize stored in a variable to speed up the loop below (one time access)
                for (int i = 0; i < listSize; ++i) {
                    if ((list.get(i).getType() != null) && (list.get(i).getType().equals(profitDescription))) {
                        totalRevenue = totalRevenue + list.get(i).getFoodRevenue();
                        ++count;
                    }
                }
                return new CommandResult(String.format(MESSAGE_SUCCESS_MULTIPLE, totalRevenue, count, listSize));
        }
        return null;
    }
}
