package duke.test;

import duke.exception.DukeException;
import duke.fridge.Fridge;
import duke.ingredient.Ingredient;
import duke.parser.Convert;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FridgeTest {

    Fridge fridge = new Fridge();
    @Test
    void testPutIngredient() throws DukeException {
        fridge = new Fridge();
        Date expiryDate= Convert.stringToDate( "03/11/2019");
        Ingredient beef=new Ingredient("Beef", 500, expiryDate);
        fridge.putIngredient(beef);
        assertTrue( fridge.hasEnough(beef));
    }

    @Test
    void hasEnough() throws DukeException {
        fridge=new Fridge();
        Date expiryDate= Convert.stringToDate( "03/11/2019");
        fridge.putIngredient(new Ingredient("Pepper", 2, expiryDate));
        assertTrue(fridge.hasEnough(new Ingredient("Pepper",1,expiryDate)));
    }

    @org.junit.jupiter.api.Test
    void useIngredient() {

    }

    @org.junit.jupiter.api.Test
    void hasExpiredIngredients() {
    }

    @org.junit.jupiter.api.Test
    void getExpiredIngredients() {
    }

    @org.junit.jupiter.api.Test
    void removeExpiring() {
    }

    @org.junit.jupiter.api.Test
    void removeExpired() {
    }

    @org.junit.jupiter.api.Test
    void testGetExpiredIngredients() {
    }

    @org.junit.jupiter.api.Test
    void getMostRecentlyExpiring() {
    }

    @Test
    void putIngredient() {
    }

    @Test
    void getAllIngredients() {
    }
}