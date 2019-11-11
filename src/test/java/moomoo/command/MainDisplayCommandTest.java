package moomoo.command;

import moomoo.feature.Budget;
import moomoo.feature.MooMooException;
import moomoo.feature.category.Expenditure;
import moomoo.feature.category.CategoryList;
import moomoo.feature.category.Category;
import moomoo.feature.Ui;
import moomoo.stubs.ScheduleListStub;
import moomoo.stubs.CategoryListStub;
import moomoo.stubs.StorageStub;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainDisplayCommandTest {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    @Test
    void testMainDisplayMiscCommand() throws MooMooException {
        ScheduleListStub newCalendar = new ScheduleListStub();
        Budget newBudget = new Budget();
        StorageStub newStorage = new StorageStub();
        CategoryListStub newCatList = new CategoryListStub();

        MainDisplayCommand newMainDisplay = new MainDisplayCommand(0, 0);
        newMainDisplay.execute(newCalendar, newBudget, newCatList, newStorage);


        assertEquals(
                ".-------------------.--------------------------.\n"
                        + "|Month: All         |       <" + ANSI_BLUE + "Categories" + ANSI_RESET + ">       |\n"
                        + "|Year: All          |--------------------------.\n"
                        + "|                   |misc                      |\n"
                        + "|                   |--------------------------.\n"
                        + "|                   |              |           |\n"
                        + ".-------------------.--------------------------.\n"
                        + "|" + ANSI_GREEN + "Total" + ANSI_RESET + ":             |                          |\n"
                        + ".-------------------.--------------------------.\n"
                        + "|" + ANSI_CYAN + "Budget" + ANSI_RESET + ":            |                          |\n"
                        + ".-------------------.--------------------------.\n"
                        + "|" + ANSI_YELLOW + "Savings" + ANSI_RESET + ":           |                          |\n"
                        + ".-------------------.--------------------------.\n", Ui.getOutput());

        /*
        assertEquals(
                ".-------------------.--------------------------.\n"
                        + "|Month: All         |       <" + "Categories" + ">       |\n"
                        + "|Year: All          |--------------------------.\n"
                        + "|                   |misc                      |\n"
                        + "|                   |--------------------------.\n"
                        + "|                   |              |           |\n"
                        + ".-------------------.--------------------------.\n"
                        + "|" + "Total" + ":             |                          |\n"
                        + ".-------------------.--------------------------.\n"
                        + "|" + "Budget" + ":            |                          |\n"
                        + ".-------------------.--------------------------.\n"
                        + "|" + "Savings" + ":           |                          |\n"
                        + ".-------------------.--------------------------.\n", Ui.getOutput());

         */
    }

    @Test
    void testMainDisplayCommand() throws MooMooException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate date = LocalDate.parse("25/10/2019", formatter);

        Category shoes = new Category("shoes");
        shoes.add(new Expenditure("Value 6", 50.00,  date, "shoes"));

        Category food = new Category("food");
        food.add(new Expenditure("Value 6", 50.00,  date, "food"));

        Category window = new Category("window");
        window.add(new Expenditure("Value 6", 50.00,  date, "window"));

        Category places = new Category("places to go");
        places.add(new Expenditure("Value 6", 50.00,  date, "places"));

        Category sweets = new Category("sweets");
        sweets.add(new Expenditure("Value 6", 50.00,  date, "sweets"));

        Category laptop = new Category("laptop");
        laptop.add(new Expenditure("Value 6", 50.00,  date, "laptop"));

        CategoryList newCatList = new CategoryList();

        newCatList.add(shoes);
        newCatList.add(food);
        newCatList.add(window);
        newCatList.add(places);
        newCatList.add(sweets);
        newCatList.add(laptop);

        Budget newBudget = new Budget();

        newBudget.addNewBudget("shoes", 50.00);
        newBudget.addNewBudget("food", 60.00);
        newBudget.addNewBudget("window", 70.00);
        newBudget.addNewBudget("places to go", 80.00);
        newBudget.addNewBudget("sweets", 90.00);
        newBudget.addNewBudget("laptop", 100.00);

        ScheduleListStub newCalendar = new ScheduleListStub();
        StorageStub newStorage = new StorageStub();

        MainDisplayCommand newMainDisplay = new MainDisplayCommand(10, 2019);
        newMainDisplay.execute(newCalendar, newBudget, newCatList, newStorage);

        assertEquals(
                ".-------------------.------------------------------------------------------"
                        + "-----------------------------------------------------------------------------"
                        + "------------------------------.\n"
                        + "|Month: October     |                                                      "
                        + "                     <" + ANSI_BLUE + "Categories" + ANSI_RESET + ">          "
                        + "                                   "
                        + "                             |\n"
                        + "|Year: 2019         |--------------------------.--------------------------.-"
                        + "-------------------------.--------------------------.-------------------------"
                        + "-.--------------------------.\n"
                        + "|                   |" + ANSI_PURPLE + "shoes" + ANSI_RESET + "                     "
                        + "|" + ANSI_PURPLE + "food" + ANSI_RESET + "                      "
                        + "|" + ANSI_PURPLE + "window" + ANSI_RESET + "                    "
                        + "|" + ANSI_PURPLE + "places to go" + ANSI_RESET + "              "
                        + "|" + ANSI_PURPLE + "sweets" + ANSI_RESET + "                    "
                        + "|" + ANSI_PURPLE + "laptop" + ANSI_RESET + "                    |\n"
                        + "|                   |--------------------------------------------------------"
                        + "------------------------------------------------------------------------------"
                        + "---------------------------.\n"
                        + "|                   |Value 6       "
                        + "|$50.00     |Value 6       "
                        + "|$50.00     |Value 6       "
                        + "|$50.00     |Value 6       "
                        + "|$50.00     |Value 6       "
                        + "|$50.00     |Value 6       "
                        + "|$50.00     |\n"
                        + ".-------------------.-----------------------------------------------------"
                        + "-----------------------------------------------------------------------------"
                        + "-------------------------------.\n"
                        + "|" + ANSI_GREEN + "Total" + ANSI_RESET + ":             "
                        + "|$50.00                    "
                        + "|$50.00                    "
                        + "|$50.00                    "
                        + "|$50.00                    "
                        + "|$50.00                    "
                        + "|$50.00                    |\n"
                        + ".-------------------.----------------------------------------------------"
                        + "---------------------------------------------------------------------------"
                        + "----------------------------------.\n"
                        + "|" + ANSI_CYAN + "Budget" + ANSI_RESET + ":            "
                        + "|$50.00                    "
                        + "|$60.00                    "
                        + "|$70.00                    "
                        + "|$80.00                    "
                        + "|$90.00                    "
                        + "|$100.00                   |\n"
                        + ".-------------------.----------------------------------------------------"
                        + "---------------------------------------------------------------------------"
                        + "----------------------------------.\n"
                        + "|" + ANSI_YELLOW + "Savings" + ANSI_RESET + ":           "
                        + "|$0.00                     "
                        + "|$10.00                    "
                        + "|$20.00                    "
                        + "|$30.00                    "
                        + "|$40.00                    "
                        + "|$50.00                    |\n"
                        + ".-------------------.----------------------------------------------------"
                        + "----------------------------------------------------------------------------"
                        + "---------------------------------.\n", Ui.getOutput());
    }
}
