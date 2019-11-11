package diyeats.logic.parsers;

import diyeats.commons.exceptions.ProgramException;
import diyeats.logic.autocorrect.Autocorrect;
import diyeats.logic.commands.AddCommand;
import diyeats.logic.commands.AddDefaultValueCommand;
import diyeats.logic.commands.AddExerciseCommand;
import diyeats.logic.commands.AddTransactionCommand;
import diyeats.logic.commands.DeleteDefaultValueCommand;
import diyeats.logic.commands.DeleteExerciseCommand;
import diyeats.logic.commands.ExitCommand;
import diyeats.logic.commands.HelpCommand;
import diyeats.logic.commands.ListCommand;
import diyeats.logic.commands.MarkDoneCommand;
import diyeats.logic.commands.StatsCommand;
import diyeats.logic.commands.SuggestExerciseCommand;
import org.junit.jupiter.api.Test;

import java.beans.Expression;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ParserTest {
    private Autocorrect autocorrect = new Autocorrect();

    @Test
    void parse() {
        try {
            Parser parser = new Parser(autocorrect);
            assertTrue(parser.parse("bye") instanceof ExitCommand);

            assertTrue(parser.parse("breakfast burger") instanceof AddCommand);
            assertTrue(parser.parse("breakfast burger /calories 100") instanceof AddCommand);
            assertTrue(parser.parse("breakfast burger /calories 100 /date 1/1/2019")
                    instanceof AddCommand);

            assertTrue(parser.parse("lunch burger") instanceof AddCommand);
            assertTrue(parser.parse("lunch burger /calories 100") instanceof AddCommand);
            assertTrue(parser.parse("lunch burger /calories 100 /date 1/1/2019")
                    instanceof AddCommand);

            assertTrue(parser.parse("dinner burger") instanceof AddCommand);
            assertTrue(parser.parse("dinner burger /calories 100") instanceof AddCommand);
            assertTrue(parser.parse("dinner burger /calories 100 /date 1/1/2019")
                    instanceof AddCommand);
            assertTrue(parser.parse("default burger /calories 100") instanceof AddDefaultValueCommand);
            assertTrue(parser.parse("default burger /calories 100 /sodium 20")
                    instanceof AddDefaultValueCommand);
            assertTrue(parser.parse("         default       burger /calorie 100 /sodium 20")
                    instanceof AddDefaultValueCommand);
            assertTrue(parser.parse("default burger") instanceof AddDefaultValueCommand);
            assertTrue(parser.parse("default") instanceof AddDefaultValueCommand);


            assertTrue(parser.parse("deldefault burger") instanceof DeleteDefaultValueCommand);
            assertTrue(parser.parse("       deldefault    burger") instanceof DeleteDefaultValueCommand);

            assertTrue(parser.parse("addexercise running /value 20") instanceof AddExerciseCommand);
            assertTrue(parser.parse("     addexercise running /value 20") instanceof AddExerciseCommand);

            assertTrue(parser.parse("delexercise running") instanceof DeleteExerciseCommand);
            assertTrue(parser.parse("     delexercise running") instanceof DeleteExerciseCommand);

            assertTrue(parser.parse("suggestexercise") instanceof SuggestExerciseCommand);
            assertTrue(parser.parse("        suggestexercise      ") instanceof SuggestExerciseCommand);
            assertTrue(parser.parse("suggestexercise /date 1/1/2019") instanceof SuggestExerciseCommand);
            assertTrue(parser.parse("suggestexercise /find cycling") instanceof SuggestExerciseCommand);
            assertTrue(parser.parse("suggestexercise /find cycling /date 1/1/2019")
                    instanceof SuggestExerciseCommand);

            assertTrue(parser.parse("stats") instanceof StatsCommand);
            assertTrue(parser.parse("    stats") instanceof StatsCommand);

            assertTrue(parser.parse("list") instanceof ListCommand);
            assertTrue(parser.parse("list /sort cost /date 10/10/2019") instanceof ListCommand);
            assertTrue(parser.parse("list /sort calorie /date 10/10/2019") instanceof ListCommand);

            assertTrue(parser.parse("done 1") instanceof MarkDoneCommand);

            assertTrue(parser.parse("help") instanceof HelpCommand);
            assertTrue(parser.parse("help breakfast") instanceof HelpCommand);

            assertTrue(parser.parse("pay 200") instanceof AddTransactionCommand);
            assertTrue(parser.parse("pay 80 /date 1/1/2019") instanceof AddTransactionCommand);
            assertTrue(parser.parse("deposit 200") instanceof  AddTransactionCommand);
            assertTrue(parser.parse("deposit 100 /date 1/1/2019") instanceof AddTransactionCommand);
        } catch (ProgramException e) {
            System.out.println("Something is wrong with the parser");
        }
    }
}

