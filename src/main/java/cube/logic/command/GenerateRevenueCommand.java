//@@author LL-Pengfei
/**
 * GenerateRevenueCommand.java
 * Support commands related to generating revenue.
 */
package cube.logic.command;

// need to add support for adding revenue at the start and update revenue later (cmd)
// need to change the name to revenue for user friendliness
// updated by LL-Pengfei

import cube.logic.command.exception.CommandErrorMessage;
import cube.logic.command.exception.CommandException;
import cube.model.Food;
import cube.model.FoodList;
import cube.storage.StorageManager;

/**
 * This class supports commands related to generating revenue.
 */
public class GenerateRevenueCommand extends Command {
    /**
     * Use enums to specify the states of the object whose revenue is to be generated.
     */
    public enum GenerateRevenueBy {
        INDEX, NAME, TYPE, ALL
    }

    private int generateRevenueIndex;
    private String generateRevenueDescription;
    private GenerateRevenueCommand.GenerateRevenueBy param;
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
    public GenerateRevenueCommand() {
    }


    /**
     * The constructor for generating the total revenue.
     *
     * @param param The parameter is used to specify the type of generating revenue.
     */
    public GenerateRevenueCommand(String param) {
        this.param = GenerateRevenueCommand.GenerateRevenueBy.valueOf(param);
    }

    /**
     * The constructor for generating revenue using index.
     *
     * @param index The index of the food whose revenue is to be generated.
     * @param param The parameter is used to specify the type of generating revenue.
     */
    public GenerateRevenueCommand(int index, String param) {
        this.generateRevenueIndex = index - 1;
        this.param = GenerateRevenueCommand.GenerateRevenueBy.valueOf(param);
    }
    /**
     * The constructor for generating revenue using food name or food type.
     *
     * @param GenerateRevenueDescription The food name or food type whose revenue is to be generated.
     * @param param The parameter is used to specify the type of generating revenue.
     */
    public GenerateRevenueCommand(String GenerateRevenueDescription, String param) {
        this.generateRevenueDescription = GenerateRevenueDescription;
        this.param = GenerateRevenueCommand.GenerateRevenueBy.valueOf(param);
    }

    /**
     * The class checks whether a given index is valid or not.
     *
     * @param list The food list.
     * @throws CommandException If the given index is invalid.
     */
    private void checkValidIndex(FoodList list) throws CommandException {
        if (generateRevenueIndex < 0 || generateRevenueIndex >= list.size()) {
            throw new CommandException(CommandErrorMessage.FOOD_NOT_EXISTS);
        }
    }

    /**
     * The class checks whether a given food name is in the food list or not.
     *
     * @param list The food list.
     * @throws CommandException If the given food name is not inside the food list.
     */
    private void checkValidName(FoodList list) throws CommandException {
        if (!list.existsName(generateRevenueDescription)) {
            throw new CommandException(CommandErrorMessage.FOOD_NOT_EXISTS);
        }
    }

    /**
     * The class checks whether a given food type is in the food list or not.
     * @param list The food list.
     * @throws CommandException If the given food type is not inside the food list.
     */
    private void checkValidType(FoodList list) throws CommandException {
        if (!list.existsType(generateRevenueDescription)) {
            throw new CommandException(CommandErrorMessage.FOOD_NOT_EXISTS);
        }
    }

    /**
     * The class generates the revenue for food whose revenue the user wishes to generate.
     *
     * @param list The food list.
     * @param storage The storage we have.
     * @return The Feedback to User for Generate Revenue Command.
     * @throws CommandException If Generating Revenue is unsuccessful.
     */
    @Override
    public CommandResult execute(FoodList list, StorageManager storage) throws CommandException {
        Food toGenerateRevenue;
        switch (param) {
            case ALL:
                return new CommandResult(String.format(MESSAGE_SUCCESS_ALL, Food.getRevenue(), list.size()));
            case INDEX:
                checkValidIndex(list);
                toGenerateRevenue = list.get(generateRevenueIndex);
                return new CommandResult(String.format(MESSAGE_SUCCESS_SINGLE, toGenerateRevenue.getFoodRevenue(), list.size()));
            case NAME:
                checkValidName(list);
                toGenerateRevenue = list.get(generateRevenueDescription);
                return new CommandResult(String.format(MESSAGE_SUCCESS_SINGLE, toGenerateRevenue.getFoodRevenue(), list.size()));
            case TYPE:
                checkValidType(list);
                double totalRevenue = 0;
                int count = 0, listSize = list.size(); //listSize stored in a variable to speed up the loop below (one time access)
                for (int i = 0; i < listSize; ++i) {
                    if ((list.get(i).getType() != null) && (list.get(i).getType().equals(generateRevenueDescription))) {
                        totalRevenue = totalRevenue + list.get(i).getFoodRevenue();
                        ++count;
                    }
                }
                return new CommandResult(String.format(MESSAGE_SUCCESS_MULTIPLE, totalRevenue, count, listSize));
        }
        return null;
    }
}
