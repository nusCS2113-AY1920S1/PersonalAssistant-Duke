package moomoo.feature;

import moomoo.command.Command;
import moomoo.feature.parser.Parser;
import moomoo.stubs.CategoryListStub;
import moomoo.stubs.ScheduleListStub;
import moomoo.stubs.StorageStub;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserTest {
    @Test
    public void testParseSavingsBudgetCommand() {
        CategoryListStub newCatList = new CategoryListStub();
        newCatList.add(null);

        ArrayList<String> categories = new ArrayList<>();

        categories.add("slippers");
        categories.add("window");
        categories.add("laptop");
        categories.add("Dogs");

        ArrayList<Double> budgets = new ArrayList<>();

        budgets.add(875.0);
        budgets.add(938.0);
        budgets.add(75.89);
        budgets.add(100.58);

        for (int i = 0; i < newCatList.getCategoryList().size(); ++i) {
            newCatList.getCategoryList().get(i).add(null);
        }

        ScheduleListStub newCalendar = new ScheduleListStub();
        StorageStub newStorage = new StorageStub();
        Budget newBudget = new Budget();
        Parser newParser = new Parser();

        Throwable thrown;
        thrown = assertThrows(MooMooException.class, () -> {
            Command c = newParser.parse("budget savings s/01/2019 e/14/2019");
            c.execute(newCalendar, newBudget, newCatList, newStorage);
        });

        assertEquals("Please set an end month and year in this format \"e/01/2019\"\n", thrown.getMessage());

        thrown = assertThrows(MooMooException.class, () -> {
            Command c = newParser.parse("budget savings s/13/2019 e/14/2019");
            c.execute(newCalendar, newBudget, newCatList, newStorage);
        });

        assertEquals("Please set a start month and year in this format \"s/01/2019\"\n", thrown.getMessage());

        thrown = assertThrows(MooMooException.class, () -> {
            Command c = newParser.parse("budget savings s/invalid input");
            c.execute(newCalendar, newBudget, newCatList, newStorage);
        });
        assertEquals("Please input in this format \"c/CATEGORY s/STARTMONTHYEAR e/ENDMONTHYEAR\"\n",
                thrown.getMessage());

        thrown = assertThrows(MooMooException.class, () -> {
            Command c = newParser.parse("budget savings s/invalid");
            c.execute(newCalendar, newBudget, newCatList, newStorage);
        });
        assertEquals("Please set a start month and year in this format \"s/01/2019\"\n", thrown.getMessage());

    }

    @Test
    public void testParseSetBudgetCommand() throws MooMooException {
        ScheduleListStub newCalendar = new ScheduleListStub();
        StorageStub newStorage = new StorageStub();
        Budget newBudget = new Budget();
        Parser newParser = new Parser();
        CategoryListStub newCatList = new CategoryListStub();
        newCatList.add(null);

        Command c = newParser.parse("budget set c/food c/laptop b/123.45 c/places to go b/150");
        c.execute(newCalendar, newBudget, newCatList, newStorage);
        assertEquals(
                ".__________________________________.\n"
                        + "| ___ _   _ ___   ___ ___ _____    |\n"
                        + "|| _ ) | | |   \\ / __| _ |_   _|   |\n"
                        + "|| _ \\ |_| | |) | (_ | _|  | |     |\n"
                        + "||___/\\___/|___/ \\___|___| |_|     |\n"
                        + "|                                  |\n"
                        + "|Category : food                   |\n"
                        + "|$123.45                           |\n"
                        + ".----------------------------------.\n"
                        + "        \\   ^__^\n"
                        + "         \\  (oo)\\_______\n"
                        + "            (__)\\       )\\/\\\n"
                        + "                ||----w |\n"
                        + "                ||     ||\n"
                        + ".__________________________________.\n"
                        + "| ___ _   _ ___   ___ ___ _____    |\n"
                        + "|| _ ) | | |   \\ / __| _ |_   _|   |\n"
                        + "|| _ \\ |_| | |) | (_ | _|  | |     |\n"
                        + "||___/\\___/|___/ \\___|___| |_|     |\n"
                        + "|                                  |\n"
                        + "|Category : laptop                 |\n"
                        + "|$123.45                           |\n"
                        + ".----------------------------------.\n"
                        + "        \\   ^__^\n"
                        + "         \\  (oo)\\_______\n"
                        + "            (__)\\       )\\/\\\n"
                        + "                ||----w |\n"
                        + "                ||     ||\n"
                        + ".__________________________________.\n"
                        + "| ___ _   _ ___   ___ ___ _____    |\n"
                        + "|| _ ) | | |   \\ / __| _ |_   _|   |\n"
                        + "|| _ \\ |_| | |) | (_ | _|  | |     |\n"
                        + "||___/\\___/|___/ \\___|___| |_|     |\n"
                        + "|                                  |\n"
                        + "|Category : places to go           |\n"
                        + "|$150.00                           |\n"
                        + ".----------------------------------.\n"
                        + "        \\   ^__^\n"
                        + "         \\  (oo)\\_______\n"
                        + "            (__)\\       )\\/\\\n"
                        + "                ||----w |\n"
                        + "                ||     ||\n", Ui.getOutput());


        Throwable thrown = assertThrows(MooMooException.class, () -> {
            Command thrownC = newParser.parse("budget set b/100 c/places to go b/150");
            thrownC.execute(newCalendar, newBudget, newCatList, newStorage);
        });
        assertEquals("Please input in this format \"c/CATEGORY b/BUDGET\"", thrown.getMessage());
    }

    @Test
    public void testParseListBudgetCommand() throws MooMooException {
        CategoryListStub newCatList = new CategoryListStub();
        newCatList.add(null);
        Budget newBudget = new Budget();
        Parser newParser = new Parser();
        ScheduleListStub newCalendar = new ScheduleListStub();
        StorageStub newStorage = new StorageStub();

        Command c = newParser.parse("budget set c/food b/100 c/laptop b/125 c/places to go b/123");
        c.execute(newCalendar, newBudget, newCatList, newStorage);

        c = newParser.parse("budget list");
        c.execute(newCalendar, newBudget, newCatList, newStorage);
        assertEquals(".__________________________________________________.\n"
                + "|Budget for shoes has not been set                 |\n"
                + "|Budget for food is $100.00                        |\n"
                + "|Budget for window has not been set                |\n"
                + "|Budget for places to go is $123.00                |\n"
                + "|Budget for sweets has not been set                |\n"
                + "|Budget for laptop is $125.00                      |\n"
                + ".--------------------------------------------------.\n"
                + "        \\   ^__^\n"
                + "         \\  (oo)\\_______\n"
                + "            (__)\\       )\\/\\\n"
                + "                ||----w |\n"
                + "                ||     ||\n\n", Ui.getOutput());

        c = newParser.parse("budget list c/food c/hello c/shoes");
        c.execute(newCalendar, newBudget, newCatList, newStorage);
        assertEquals(".__________________________________________________.\n"
                + "|Budget for food is $100.00                        |\n"
                + "|Budget for shoes has not been set.                |\n"
                + ".--------------------------------------------------.\n"
                + "        \\   ^__^\n"
                + "         \\  (oo)\\_______\n"
                + "            (__)\\       )\\/\\\n"
                + "                ||----w |\n"
                + "                ||     ||\n"
                + "\n", Ui.getOutput());

        c = newParser.parse("budget list c/food s/food");
        c.execute(newCalendar, newBudget, newCatList, newStorage);

        assertEquals("food s/food category does not exist. Please add it first.\n", Ui.getOutput());

    }

    @Test
    public void testParseEditBudgetCommand() throws MooMooException {
        CategoryListStub newCatList = new CategoryListStub();
        newCatList.add(null);
        Budget newBudget = new Budget();
        Parser newParser = new Parser();
        ScheduleListStub newCalendar = new ScheduleListStub();
        StorageStub newStorage = new StorageStub();

        Command c = newParser.parse("budget set c/food b/100 c/laptop b/125 c/places to go b/123");
        c.execute(newCalendar, newBudget, newCatList, newStorage);

        c = newParser.parse("budget edit c/food c/laptop b/100 c/places to go b/150");
        c.execute(newCalendar, newBudget, newCatList, newStorage);
        assertEquals("._________________________________________________________________________.\n"
                + "|You have changed the budget for laptop from $125.00 to $100.00           |\n"
                + "|You have changed the budget for places to go from $123.00 to $150.00     |\n"
                + ".-------------------------------------------------------------------------.\n"
                + "        \\   ^__^\n"
                + "         \\  (oo)\\_______\n"
                + "            (__)\\       )\\/\\\n"
                + "                ||----w |\n"
                + "                ||     ||\n"
                + "\n", Ui.getOutput());

        Throwable thrown = assertThrows(MooMooException.class, () -> {
            Command thrownC = newParser.parse("budget edit b/100 c/places to go b/150");
            thrownC.execute(newCalendar, newBudget, newCatList, newStorage);
        });
        assertEquals("Please input in this format \"c/CATEGORY b/BUDGET\"", thrown.getMessage());

    }

}