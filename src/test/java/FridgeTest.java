//package duke.test;

import duke.exception.DukeException;
import duke.fridge.Fridge;
import duke.ingredient.Ingredient;
import duke.parser.Convert;
import duke.storage.FridgeStorage;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Represents a test class, used to test the methods implemented in the {@link Fridge} class
 * @author Sara Djambazovska
 */
class FridgeTest {
    Fridge fridge = new Fridge();


    @Test
    void testAddIngredient() throws DukeException {
        fridge = new Fridge();
        Ingredient beef=new Ingredient("Beef", 500,  "03/11/2020");
        fridge.addIngredient(beef);
        assertTrue( fridge.hasEnough(beef));
        fridge.addIngredient(beef);
        assertEquals(1000, fridge.getIngredient(beef).getAmount());
        Ingredient newBeef=new Ingredient("Beef", 29,  "04/11/2020");
        fridge.addIngredient(newBeef);
        assertEquals(1000, fridge.getIngredient(beef).getAmount());
        assertEquals(29, fridge.getIngredient(newBeef).getAmount());
    }
    @Test
    void testAddExpiredIngredient() throws DukeException {
        fridge = new Fridge();
        Ingredient expiredBeef=new Ingredient("ExpiredBeef", 500,  "03/11/2019");
        fridge.addIngredient(expiredBeef);
        assertFalse( fridge.hasEnough(expiredBeef));
    }

    @Test
    void hasEnough() throws DukeException {
        fridge=new Fridge();
        fridge.addIngredient(new Ingredient("Pepper", 2, "03/11/2020"));
        assertTrue(fridge.hasEnough(new Ingredient("Pepper",1,"03/11/2020")));
    }

    @Test
    void useIngredient()throws DukeException {
        fridge=new Fridge();
        Ingredient pepper =new Ingredient("Pepper", 3, "03/11/2020");
        Ingredient onePepper =new Ingredient("Pepper", 1, "03/11/2020");
        fridge.addIngredient(pepper);
        fridge.useIngredient(onePepper);
        assertEquals(2, fridge.getIngredient(pepper).getAmount());
        fridge.useIngredient(onePepper);
        assertEquals(1, fridge.getIngredient(pepper).getAmount());
        fridge.useIngredient(onePepper);
        assertFalse(fridge.hasEnough(onePepper));
    }

    @Test
    void hasExpiredIngredients() throws DukeException {
        fridge=new Fridge();
        Ingredient expiredBeef = new Ingredient("ExpiredBeef", 500,  "03/11/2019");
        assertFalse(fridge.hasExpiredIngredients());
        fridge.addIngredient(expiredBeef);
        assertTrue(fridge.hasExpiredIngredients());
        fridge.removeExpired();
        assertFalse(fridge.hasExpiredIngredients());
    }


    @Test
    void removeExpiring() throws DukeException {
        fridge=new Fridge();
        Ingredient almostExpiredBeef = new Ingredient("Beef", 500,  "10/11/2020");
        assertTrue(fridge.isEmpty());
        fridge.addIngredient(almostExpiredBeef);
        fridge.addIngredient(almostExpiredBeef);
        assertFalse(fridge.isEmpty());
        fridge.removeExpiring(Convert.stringToDate("11/11/2020"));
        assertTrue(fridge.isEmpty());
    }

}