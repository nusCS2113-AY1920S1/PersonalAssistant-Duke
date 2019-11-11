package moomoo.command;

import moomoo.command.category.AddCategoryCommand;
import moomoo.command.category.AddExpenditureCommand;
import moomoo.command.category.DeleteCategoryCommand;
import moomoo.command.category.DeleteExpenditureCommand;
import moomoo.command.category.ListCategoryCommand;
import moomoo.command.category.SortCategoryCommand;
import moomoo.feature.Budget;
import moomoo.feature.MooMooException;
import moomoo.feature.Ui;
import moomoo.feature.category.Category;
import moomoo.feature.category.CategoryList;
import moomoo.feature.category.Expenditure;
import moomoo.feature.storage.CategoryStorage;
import moomoo.feature.storage.ExpenditureStorage;
import moomoo.feature.storage.Storage;
import moomoo.stubs.CategoryListStub;
import moomoo.stubs.ScheduleListStub;
import moomoo.stubs.StorageStub;
import moomoo.stubs.UiStub;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GraphCategoryCommandTest {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";
    
    private final String fullBlock = "\u2588"; //"H";
    private final String halfBlock = "\u258c"; //"l";
    private final String topBorder = "\u252c";//"v";
    private final String bottomBorder = "\u2534";//"^";
    private String horizontalAxisTop = "";
    private String horizontalAxisBottom = "";
    private String output = "";
    
    
    @Test
    void testGraphCategoryCommandEmpty() throws MooMooException {
        ScheduleListStub newCalendar = new ScheduleListStub();
        Budget newBudget = new Budget();
        StorageStub newStorage = new StorageStub();
        CategoryListStub newCatList = new CategoryListStub();
        
        Command testGraph = new GraphCategoryCommand("test", 1, 1);
        Throwable thrown = assertThrows(MooMooException.class, () -> {
            testGraph.execute(newCalendar, newBudget, newCatList, newStorage);
        });
        assertEquals("Please enter an existing category!! Moohoohoo", thrown.getMessage());
    }
}