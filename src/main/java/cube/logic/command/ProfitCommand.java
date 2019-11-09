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
import cube.model.sale.Sale;
import cube.model.sale.SalesHistory;
import cube.storage.StorageManager;
import cube.logic.command.util.CommandResult;
import cube.logic.command.util.CommandUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * This class supports commands related to generating profits and revenue.
 */
public class ProfitCommand extends Command {
    /**
     * Use enums to specify the states of the object whose profits and revenue is to be generated.
     */
    public enum ProfitBy {
        INDEX, NAME, TYPE, ALL
    }

    private int profitIndex;
    private String profitDescription;
    private Date date_i; //start date (initial)
    private Date date_f; //end date (final)
    private ProfitCommand.ProfitBy param;
    private final String MESSAGE_SUCCESS_ALL = "Nice! I've generated the profits and revenue for all the stocks:\n"
            + "profit:  $ %1$s\n"
            + "revenue: $ %2$s\n"
            + "In total, you have %3$s food in the list.\n";
    private final String MESSAGE_SUCCESS_SINGLE = "Nice! I've generated the profits and revenue for this food:\n"
            + "profit:  $ %1$s\n"
            + "revenue: $ %2$s\n"
            + "In total, you have %3$s food in the list.\n";
    private final String MESSAGE_SUCCESS_MULTIPLE = "Nice! I've generated the profits and revenue for this type:\n"
            + "profit:  $ %1$s\n"
            + "revenue: $ %2$s\n"
            + "This type contains "
            + "%3$s food items\n"
            + "In total, you have %4$s food in the list.\n";

    /**
     * The default constructor, empty since parameters are required to perform generating profits and revenue command.
     */
    public ProfitCommand() {
    }

    /**
     * The constructor for generating the total profits and revenue.
     *
     * @param date_i The start date of the period where generating profits and revenue is concerned.
     * @param date_f The end date of the period where generating profits and revenue is concerned.
     * @param param The parameter is used to specify the type of generating profits and revenue.
     */
    public ProfitCommand(Date date_i, Date date_f, String param) {
        this.date_i = date_i;
        this.date_f = date_f;
        this.param = ProfitCommand.ProfitBy.valueOf(param);
    }

    /**
     * The constructor for generating the total profits and revenue using index.
     *
     * @param date_i The start date of the period where generating profits and revenue is concerned.
     * @param date_f The end date of the period where generating profits and revenue is concerned.
     * @param index The index of the food whose profits and revenue are to be generated.
     * @param param The parameter is used to specify the type of generating profits and revenue.
     */
    public ProfitCommand(Date date_i, Date date_f, int index, String param) {
        this.date_i = date_i;
        this.date_f = date_f;
        this.profitIndex = index - 1;
        this.param = ProfitCommand.ProfitBy.valueOf(param);
    }

    /**
     * The constructor for generating the total profits and revenue using food name or food type.
     *
     * @param date_i The start date of the period where generating profits and revenue is concerned.
     * @param date_f The end date of the period where generating profits and revenue is concerned.
     * @param ProfitDescription The food name or food type whose profits and revenue are to be generated.
     * @param param The parameter is used to specify the type of generating profits and revenue.
     */
    public ProfitCommand(Date date_i, Date date_f, String ProfitDescription, String param) {
        this.date_i = date_i;
        this.date_f = date_f;
        this.profitDescription = ProfitDescription;
        this.param = ProfitCommand.ProfitBy.valueOf(param);
    }


    /**
     * The class generates the profits and revenue for food whose profits and revenue the user wishes to generate.
     *
     * @param storage The storage we have.
     * @return The Feedback to User for Generate Revenue Command.
     * @throws CommandException If Generating Revenue is unsuccessful.
     */
    @Override
    public CommandResult execute(ModelManager model, StorageManager storage) throws CommandException {
        FoodList list = ModelManager.getFoodList();
        SalesHistory saleSet = ModelManager.getSalesHistory();
        double toGenerateProfit = 0;
        double toGenerateRevenue = 0;
        String tempFoodName;
        switch (param) {
            case ALL:
                //generating the profits and revenue for all food in the given period from date_i to date_f,
                //simply iterating though the sales history will do.
                Iterator<Sale> it = saleSet.iterator();
                while (it.hasNext()) {
                    Sale tempSale = it.next();
                    Date tempDate = tempSale.getDate();
                    if (tempDate.compareTo(date_i) >= 0 && tempDate.compareTo(date_f) <= 0) {
                        double tempRevenue = tempSale.getRevenue();
                        double tempProfit = tempSale.getProfit();
                        toGenerateRevenue += tempRevenue;
                        toGenerateProfit += tempProfit;
                    }
                }
                return new CommandResult(String.format(MESSAGE_SUCCESS_ALL, toGenerateProfit,
                        toGenerateRevenue, list.size()));
            case INDEX:
                //generating the profits and revenue for the food with given index in the list, in the given period
                //from date_i to date_f, slightly more involved.
                //get the food name based on indexing from the food list, then iterate through sales history to find
                //matching description.
                CommandUtil.requireValidIndex(list, profitIndex);
                Food foodToGenerate = list.get(profitIndex);
                tempFoodName = foodToGenerate.getName(); //possible to do this because we disallow duplicate food names

                Iterator<Sale> it = saleSet.iterator();
                while (it.hasNext()) {
                    Sale tempSale = it.next();
                    Date tempDate = tempSale.getDate();
                    String tempName = tempSale.getName();
                    if (tempDate.compareTo(date_i) >= 0 && tempDate.compareTo(date_f) <= 0
                            && tempFoodName.equals(tempName)) {
                        double tempRevenue = tempSale.getRevenue();
                        double tempProfit = tempSale.getProfit();
                        toGenerateRevenue += tempRevenue;
                        toGenerateProfit += tempProfit;
                    }
                }
                return new CommandResult(String.format(MESSAGE_SUCCESS_SINGLE, toGenerateProfit,
                        toGenerateRevenue, list.size()));
            case NAME:
                //generating the profits and revenue for the food with given food name, in the given period
                //from date_i to date_f, rather straightforward.
                //since we disallow duplicate food names in this project, iterate through sales history will do.
                CommandUtil.requireValidName(list, profitDescription);
                tempFoodName = profitDescription; //for consistency

                Iterator<Sale> it = saleSet.iterator();
                while (it.hasNext()) {
                    Sale tempSale = it.next();
                    Date tempDate = tempSale.getDate();
                    String tempName = tempSale.getName();
                    if (tempDate.compareTo(date_i) >= 0 && tempDate.compareTo(date_f) <= 0
                            && tempFoodName.equals(tempName)) {
                        double tempRevenue = tempSale.getRevenue();
                        double tempProfit = tempSale.getProfit();
                        toGenerateRevenue += tempRevenue;
                        toGenerateProfit += tempProfit;
                    }
                }
                return new CommandResult(String.format(MESSAGE_SUCCESS_SINGLE, toGenerateProfit,
                        toGenerateRevenue, list.size()));
            case TYPE:
                //generating the profits and revenue for the food with given food type, in the given period
                //from date_i to date_f, most involved in all cases here.
                //get all the food names of the food of the associated food type from the food list,
                //then iterate through sales history to find possible matching descriptions.

                //store all the food names of the food with the food type the user is looking for into an arraylist.
                CommandUtil.requireValidType(list, profitDescription);
                ArrayList<String> tempFoodNames = new ArrayList<String>();
                int count = 0, listSize = list.size();
                for (int i = 0; i < listSize; ++i) {
                    if ((list.get(i).getType() != null) && (list.get(i).getType().equals(profitDescription))) {
                        tempFoodNames.add(list.get(i).getName());
                        ++count;
                    }
                }

                //iterate through the sales history to look for matching food names, identification
                //with only food names is possible since we disallow duplicate food names in this project.
                Iterator<Sale> it = saleSet.iterator();
                while (it.hasNext()) {
                    Sale tempSale = it.next();
                    Date tempDate = tempSale.getDate();
                    String tempName = tempSale.getName();
                    if (tempDate.compareTo(date_i) >= 0 && tempDate.compareTo(date_f) <= 0) {
                        for (int i = 0; i < count; ++i) {
                            if (tempName.equals(tempFoodNames.get(i))) {
                                double tempRevenue = tempSale.getRevenue();
                                double tempProfit = tempSale.getProfit();
                                toGenerateRevenue += tempRevenue;
                                toGenerateProfit += tempProfit;
                                break;
                            }
                        }
                    }
                }
                return new CommandResult(String.format(MESSAGE_SUCCESS_MULTIPLE, toGenerateProfit,
                        toGenerateRevenue, count, listSize));
        }
        return null;
    }
}
