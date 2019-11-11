package diyeats.logic.command;

import diyeats.logic.commands.Command;
import diyeats.logic.commands.SuggestExerciseCommand;
import diyeats.logic.dummy.DummyMealList;
import diyeats.logic.dummy.DummyStorage;
import diyeats.logic.dummy.DummyUser;
import diyeats.logic.dummy.DummyWallet;
import diyeats.model.meal.ExerciseList;
import diyeats.model.undo.Undo;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static diyeats.model.user.Gender.MALE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SuggestExerciseTest {
    @Test
    public void executeTest() {
        DummyMealList dummyMealList = new DummyMealList(new ExerciseList());
        DummyStorage dummyStorage = new DummyStorage();
        DummyUser dummyUser = new DummyUser("Hashir", 22, 175, MALE,
                3, 70, null);
        DummyWallet dummyWallet = new DummyWallet();
        Undo undo = new Undo();

        try {
            Command command = new SuggestExerciseCommand(LocalDate.now(), null);
            command.execute(dummyMealList, dummyStorage, dummyUser, dummyWallet, undo);
            assertEquals(false, command.isDone());
        } catch (Exception e) {
            fail();
        }

        try {
            Command command = new SuggestExerciseCommand(LocalDate.now(), null);
            command.execute(dummyMealList, dummyStorage, dummyUser, dummyWallet, undo);
            assertEquals(false, command.isDone());
            //Should have 8 entries
            command.setResponseStr("8");
            command.execute(dummyMealList, dummyStorage, dummyUser, dummyWallet, undo);
            assertEquals(true, command.isDone());
        } catch (Exception e) {
            fail();
        }

        try {
            Command command = new SuggestExerciseCommand(LocalDate.now(), "cycling");
            command.execute(dummyMealList, dummyStorage, dummyUser, dummyWallet, undo);
            assertEquals(false, command.isDone());
            //should have 3 entries
            command.setResponseStr("3");
            command.execute(dummyMealList, dummyStorage, dummyUser, dummyWallet, undo);
            assertEquals(true, command.isDone());
        } catch (Exception e) {
            fail();
        }
    }
}
