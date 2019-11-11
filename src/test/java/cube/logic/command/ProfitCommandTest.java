//@@author LL-Pengfei
/**
 * ProfitCommandTest.java
 * Tests on the functionality of ProfitCommand.
 */

package cube.logic.command;

import cube.logic.command.exception.CommandException;
import cube.logic.command.util.CommandResult;
import cube.logic.parser.ParserUtil;
import cube.logic.parser.exception.ParserException;
import cube.model.ModelManager;
import cube.model.food.Food;
import cube.model.food.FoodList;
import cube.model.sale.Sale;
import cube.model.sale.SalesHistory;
import cube.storage.StorageManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class test on the functionality of the ProfitCommand.
 */
public class ProfitCommandTest extends ProfitCommand {
    /**
     * Tests whether Profit Command behaviour is as expected.
     *
     * @throws CommandException If exception occurs when using command.
     * @throws ParserException If exception occurs when using parser.
     */
    @Test
    public void testProfitCommand() throws CommandException, ParserException {
        ModelManager model = new ModelManager();
        StorageManager storage = new StorageManager();
        SalesHistory salesHistory = new SalesHistory();
        FoodList list = new FoodList();


        //create a new dummy sales record
        String foodName = "food1Name";
        int quantitySold = 20;
        double revenue = 100.00;
        double profit = 40.00;
        Date soldDate = ParserUtil.parseStringToDate("23/03/2018");
        Sale saleRecord = new Sale(foodName, quantitySold, revenue, profit, soldDate);
        salesHistory.add(saleRecord);

        model.setSalesHistory(salesHistory);
        Food food = new Food(foodName);
        list.add(food);
        model.setFoodList(list);

        //specify the period to search for in the saleshistory, inclusive of the sold date of
        //the dummy sales record created above
        Date dateI = ParserUtil.parseStringToDate("23/03/1998");
        Date dateF = ParserUtil.parseStringToDate("23/03/2028");

        String pattern = "MM/dd/yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        String dateIStr = df.format(dateI);
        String dateFStr = df.format(dateF);

        //search all
        //should only have the one sales record created above
        ProfitCommand commandAll = new ProfitCommand(dateI, dateF, "ALL");
        CommandResult resultAll = commandAll.execute(model, storage);

        CommandResult expectedResultAll = new CommandResult(String.format(ProfitCommand.MESSAGE_SUCCESS_ALL,
                profit, revenue, dateIStr, dateFStr));
        
        assertEquals(resultAll, expectedResultAll);

        //search by name
        ProfitCommand commandName = new ProfitCommand(dateI, dateF, "food1Name", "NAME");
        CommandResult resultName = commandName.execute(model, storage);
        CommandResult expectedResultName = new CommandResult(String.format(ProfitCommand.MESSAGE_SUCCESS_SINGLE,
                profit, revenue, dateIStr, dateFStr));
        assertEquals(resultName, expectedResultName);


        //search by index/type, now requiring the FoodList
        //for testing purposes, only initializing the food name && set its type will do
        Food dummyFood = new Food(foodName);
        dummyFood.setType("dummyType");
        list.add(dummyFood);

        //search by index
        //since it is the only one in the list, it is indexed 0
        ProfitCommand commandIndex = new ProfitCommand(dateI, dateF, 1, "INDEX");
        CommandResult resultIndex = commandIndex.execute(model, storage);
        CommandResult expectedResultIndex = new CommandResult(String.format(ProfitCommand.MESSAGE_SUCCESS_SINGLE,
                profit, revenue, dateIStr, dateFStr)); //now list has one entry
        assertEquals(resultIndex, expectedResultIndex);

        //search by type
        ProfitCommand commandType = new ProfitCommand(dateI, dateF, "dummyType", "TYPE");
        CommandResult resultType = commandType.execute(model, storage);
        CommandResult expectedResultType = new CommandResult(String.format(ProfitCommand.MESSAGE_SUCCESS_MULTIPLE,
                profit, revenue, 1, dateIStr, dateFStr)); //now list has one entry
        assertEquals(resultType, expectedResultType);
    }
}
