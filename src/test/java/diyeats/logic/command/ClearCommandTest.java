package diyeats.logic.command;

import diyeats.logic.commands.ClearCommand;
import diyeats.logic.dummy.DummyMealList;
import diyeats.logic.dummy.DummyStorage;
import diyeats.logic.dummy.DummyUser;
import diyeats.logic.dummy.DummyWallet;
import diyeats.logic.parsers.ClearCommandParser;
import diyeats.model.undo.Undo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClearCommandTest {
    private ClearCommand clearCommand;
    private ClearCommandParser clearCommandParser;
    private DummyMealList meals;
    private DummyStorage storage;
    private DummyUser user;
    private DummyWallet wallet;
    private Undo undo;
    private String[] inputStrs = {"", "/startdate 1/11/2019 /enddate 5/11/2019",
                                  "/startdate 3/11/2019 /enddate 2/11/2019",
                                  "/startdate 1/1/2020 /enddate 1/1/2020"  };
    private int clearedSize = 0;
    private int defaultSize = 4;

    @BeforeEach
    public void setupClearCommand() {
        meals = new DummyMealList();
        storage = new DummyStorage();
        wallet = new DummyWallet();
        undo = new Undo();
        user = new DummyUser();
        clearCommandParser = new ClearCommandParser();
    }

    @Test
    public void ClearCommandEmptyTest() {
        clearCommand = clearCommandParser.parse(inputStrs[0]);
        assertTrue(clearCommand.isFail());
    }

    @Test
    public void ClearCommandRangeTest() {
        clearCommand = clearCommandParser.parse(inputStrs[1]);
        assertTrue(!clearCommand.isFail());
        assertTrue(clearCommand.startDate.equals(LocalDate.of(2019,11,1)));
        clearCommand.execute(meals, storage, user, wallet, undo);
        System.out.println(meals.getMealsList(LocalDate.of(2019,1,11)));
        meals.deleteAllMealsOnDate(LocalDate.of(2019,11,1));
        System.out.println(meals.getMealsList(LocalDate.of(2019,1,11)));

        assertTrue(meals.getMealsList(LocalDate.of(2019,11,1)).size() == clearedSize);
        assertTrue(meals.getMealsList(LocalDate.of(2019,11,2)).size() == clearedSize);
        assertTrue(meals.getMealsList(LocalDate.of(2019,11,3)).size() == clearedSize);
        assertTrue(meals.getMealsList(LocalDate.of(2019,11,4)).size() == clearedSize);
        assertTrue(meals.getMealsList(LocalDate.of(2019,11,5)).size() == clearedSize);
        assertTrue(meals.getMealsList(LocalDate.of(2019,11,10)).size() == defaultSize);
        assertTrue(meals.getMealsList(LocalDate.of(2019,12,31)).size() == defaultSize);
        assertTrue(meals.getMealsList(LocalDate.of(2020,1,1)).size() == defaultSize);
    }

    @Test
    public void ClearCommandAfterBeforeTest() {
        clearCommand = clearCommandParser.parse(inputStrs[2]);
        assertTrue(clearCommand.isFail());
    }

    @Test
    public void ClearCommandOnDay() {
        clearCommand = clearCommandParser.parse(inputStrs[3]);
        assertTrue(!clearCommand.isFail());

        clearCommand.execute(meals, storage, user, wallet, undo);
        assertTrue(meals.getMealsList(LocalDate.of(2019,11,1)).size() == defaultSize);
        assertTrue(meals.getMealsList(LocalDate.of(2019,11,2)).size() == defaultSize);
        assertTrue(meals.getMealsList(LocalDate.of(2019,11,3)).size() == defaultSize);
        assertTrue(meals.getMealsList(LocalDate.of(2019,11,4)).size() == defaultSize);
        assertTrue(meals.getMealsList(LocalDate.of(2019,11,5)).size() == defaultSize);
        assertTrue(meals.getMealsList(LocalDate.of(2019,11,10)).size() == defaultSize);
        assertTrue(meals.getMealsList(LocalDate.of(2019,12,31)).size() == defaultSize);
        assertTrue(meals.getMealsList(LocalDate.of(2020,1,1)).size() == clearedSize);
    }


}
