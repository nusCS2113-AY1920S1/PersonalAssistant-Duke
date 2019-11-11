//@@author LL-Pengfei
/**
 * FoodTest.java
 * Test whether the Food Class is functioning as expected.
 */

package cube.model.food;

import cube.exception.CubeException;
import cube.logic.parser.ParserUtil;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Date;

/**
 * This class tests whether the Food Class is functioning as expected.
 */
public class FoodTest extends Food {
    /**
     * The default constructor.
     *
     * @param name The food name.
     */
    public FoodTest(String name) {
        super(name);
    }

    /**
     * This method tests the constructor of the Food Class.
     *
     */
    @Test
    public void testConstructor() {
        final String food1Name = "Food1NameWithoutSpace";
        final String food2Name = "Food2 Name With Spaces";
        final Food food1 = new Food(food1Name);
        final Food food2 = new Food(food2Name);
        final String food1Type = "";
        final String food2Type = "";
        final double food1Price = 0.0;
        final double food2Price = 0.0;
        final double food1Cost = 0.0;
        final double food2Cost = 0.0;
        final int food1Stock = 0;
        final int food2Stock = 0;

        assertEquals(food1Name, food1.getName(), "Food 1 name is wrong.");
        assertEquals(food1Type, food1.getType(), "Food1 Type is not initialized properly.");
        assertEquals(food1Price, food1.getPrice(), "Food1 Price is not initialized properly.");
        assertEquals(food1Cost, food1.getCost(), "Food1 Cost is not initialized properly.");
        assertEquals(food1Stock, food1.getStock(), "Food1 Stock is not initialized properly.");

        assertEquals(food2Name, food2.getName(), "Food 2 name is wrong.");
        assertEquals(food2Type, food2.getType(), "Food2 Type is not initialized properly.");
        assertEquals(food2Price, food2.getPrice(), "Food2 Price is not initialized properly.");
        assertEquals(food2Cost, food2.getCost(), "Food2 Cost is not initialized properly.");
        assertEquals(food2Stock, food2.getStock(), "Food2 Stock is not initialized properly.");

        //try some illegal inputs
        Food food3 = new Food(food1Name);
        fail("Constructor allows duplicate food names.");

        Food food4 = new Food(null);
        fail("Constructor allows null food name.");
    }

    /**
     * This method tests the Setters and Getters for food variables.
     * @throws CubeException if test fails.
     */
    @Test
    public void testSettersAndGetters() throws CubeException {
        final Food food1 = new Food("Food1NameWithoutSpaces");
        final Food food2 = new Food("Food 2 Name With Spaces");
        final Food food3 = new Food("Food3NameWithoutSpaces");
        final Food food4 = new Food("Food 4 Name With Spaces");

        //testing setters and getters for type of the food.
        String food12Type = "TypeA";
        String food34Type = "TypeB";
        food1.setType(food12Type);
        food2.setType(food12Type);
        food3.setType(food34Type);
        food4.setType(food34Type);
        assertSame("TypeA", food1.getType(), "Fail to set Food Type for food1.");
        assertSame("TypeA", food2.getType(), "Fail to set Food Type for food2.");
        assertSame("TypeB", food3.getType(), "Fail to set Food Type for food3.");
        assertSame("TypeB", food4.getType(), "Fail to set Food Type for food4.");
        final String food1Type = food1.getType();
        final String food2Type = food2.getType();
        final String food3Type = food3.getType();
        final String food4Type = food4.getType();
        assertSame(food12Type, food1Type, "Fail to get Food Type for food1.");
        assertSame(food12Type, food2Type, "Fail to get Food Type for food2.");
        assertSame(food34Type, food3Type, "Fail to get Food Type for food3.");
        assertSame(food34Type, food4Type, "Fail to get Food Type for food4.");

        //try some illegal inputs
        String illegalType = null;
        food1.setType(illegalType);
        fail("Setters for Type allows null type.");


        //testing setters and getters for price of the food.
        double food12Price = 30.00;
        double food34Price = 50.00;
        food1.setPrice(food12Price);
        food2.setPrice(food12Price);
        food3.setPrice(food34Price);
        food4.setPrice(food34Price);
        assertEquals(food12Price, food1.getPrice(), "Fail to set Food Price for food1.");
        assertEquals(food12Price, food2.getPrice(), "Fail to set Food Price for food2.");
        assertEquals(food34Price, food3.getPrice(), "Fail to set Food Price for food3.");
        assertEquals(food34Price, food4.getPrice(), "Fail to set Food Price for food4.");
        final double food1Price = food1.getPrice();
        final double food2Price = food2.getPrice();
        final double food3Price = food3.getPrice();
        final double food4Price = food4.getPrice();
        assertEquals(food12Price, food1Price, "Fail to get Food Price for food1.");
        assertEquals(food12Price, food2Price, "Fail to get Food Price for food2.");
        assertEquals(food34Price, food3Price, "Fail to get Food Price for food3.");
        assertEquals(food34Price, food4Price, "Fail to get Food Price for food4.");

        //try some illegal inputs
        double illegalPrice = -1;
        food1.setPrice(illegalPrice);
        fail("Setters for Price allows negative inputs.");


        //testing setters and getters for Expiry Date of the Food.
        Date food12Date = ParserUtil.parseStringToDate("12/11/2019"); //near future
        Date food34Date = ParserUtil.parseStringToDate("12/11/2025"); //far future
        food1.setExpiryDate(food12Date);
        food2.setExpiryDate(food12Date);
        food3.setExpiryDate(food34Date);
        food4.setExpiryDate(food34Date);
        assertEquals(food12Date, food1.getExpiryDate(), "Fail to set Expiry Date for food1.");
        assertEquals(food12Date, food2.getExpiryDate(), "Fail to set Expiry Date for food2.");
        assertEquals(food34Date, food3.getExpiryDate(), "Fail to set Expiry Date for food3.");
        assertEquals(food34Date, food4.getExpiryDate(), "Fail to set Expiry Date for food4.");
        final Date food1Date = food1.getExpiryDate();
        final Date food2Date = food2.getExpiryDate();
        final Date food3Date = food3.getExpiryDate();
        final Date food4Date = food4.getExpiryDate();
        assertEquals(food1Date.compareTo(food12Date), 0, "Fail to get Food Expiry Date for food1.");
        assertEquals(food2Date.compareTo(food12Date), 0, "Fail to get Food Expiry Date for food2.");
        assertEquals(food3Date.compareTo(food34Date), 0, "Fail to get Food Expiry Date for food3.");
        assertEquals(food4Date.compareTo(food34Date), 0, "Fail to get Food Expiry Date for food4.");

        //try some illegal inputs
        Date illegalExpiryDate = ParserUtil.parseStringToDate("23/03/1998"); //in the past
        food1.setExpiryDate(illegalExpiryDate);
        fail("Setters for Expiry Date allows Date in the past.");


        //testing setters and getters for Stock of the Food.
        int food12Stock = 50; //high
        int food34Stock = 5; //low
        food1.setStock(food12Stock);
        food2.setStock(food12Stock);
        food3.setStock(food34Stock);
        food4.setStock(food34Stock);
        assertEquals(food12Stock, food1.getStock(), "Fail to set Food Stock for food1.");
        assertEquals(food12Stock, food2.getStock(), "Fail to set Food Stock for food2.");
        assertEquals(food34Stock, food3.getStock(), "Fail to set Food Stock for food3.");
        assertEquals(food34Stock, food4.getStock(), "Fail to set Food Stock for food4.");
        final int food1Stock = food1.getStock();
        final int food2Stock = food2.getStock();
        final int food3Stock = food3.getStock();
        final int food4Stock = food4.getStock();
        assertEquals(food12Stock, food1Stock, "Fail to get Food Stock for food1.");
        assertEquals(food12Stock, food2Stock, "Fail to get Food Stock for food2.");
        assertEquals(food34Stock, food3Stock, "Fail to get Food Stock for food3.");
        assertEquals(food34Stock, food4Stock, "Fail to get Food Stock for food4.");

        //try some illegal inputs
        int illegalStock = -5;
        food1.setStock(illegalStock);
        fail("Setters for Food Stock allows negative stock.");


        //testing setters and getters for Cost of the Food.
        double food12Cost = 15.00;
        double food34Cost = 25.00;
        food1.setCost(food12Cost);
        food2.setCost(food12Cost);
        food3.setCost(food34Cost);
        food4.setCost(food34Cost);
        assertEquals(food12Cost, food1.getCost(), "Fail to set Food Cost for food1.");
        assertEquals(food12Cost, food2.getCost(), "Fail to set Food Cost for food2.");
        assertEquals(food34Cost, food3.getCost(), "Fail to set Food Cost for food3.");
        assertEquals(food34Cost, food4.getCost(), "Fail to set Food Cost for food4.");
        final double food1Cost = food1.getCost();
        final double food2Cost = food2.getCost();
        final double food3Cost = food3.getCost();
        final double food4Cost = food4.getCost();
        assertEquals(food12Cost, food1Cost, "Fail to get Food Cost for food1.");
        assertEquals(food12Cost, food2Cost, "Fail to get Food Cost for food2.");
        assertEquals(food34Cost, food3Cost, "Fail to get Food Cost for food3.");
        assertEquals(food34Cost, food4Cost, "Fail to get Food Cost for food4.");

        //try some illegal inputs
        double illegalCost = -1.00;
        food1.setCost(illegalCost);
        fail("Setters for Food Cost allows negative cost.");


        //testing exists, i.e. the function to check if the food already exists in the inventory.
        String food5Name = "foodNameExistsCheck [arbitrary name]";
        Food food5 = new Food(food5Name);
        assertEquals(exists(food5Name), true, "Exists function that checking on whether a food"
                + "already exists in the inventory is incorrect.");
        String arbitraryRandomName = "23i54c283hjsqecwr3mudfs";
        assertEquals(exists(arbitraryRandomName), false, "Exists function that checking on"
                + "whether a food already exists in the inventory is incorrect.");
    }
}
