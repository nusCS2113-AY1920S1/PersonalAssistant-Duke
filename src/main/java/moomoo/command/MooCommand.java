package moomoo.command;

import java.util.Random;

import moomoo.feature.Budget;
import moomoo.feature.MooMooException;
import moomoo.feature.ScheduleList;
import moomoo.feature.storage.Storage;
import moomoo.feature.Ui;
import moomoo.feature.category.CategoryList;

public class MooCommand extends Command {
    private String[] mooText = new String[] {"MooOoO!", "MOOOooo!", "MOoOoO...", "MoOoOoO?", "MOOOoooOOO!!",
        "Moo!", "Moo?", "Moo.", "Moo...", "Moo :D",
        "Moooooooooooooo!", "Mooooooooooo...", "Mooooooooo??", "Moooooooooo^2", "Moooooo :D",
        "MUUUUUU", "Muuuuu!", "Muuuuu?", "Muuuuuu...", "Muuuuuu :D",
        "Huh?", "MIIIIIII!?!?", "Quack", "Woof :D", "YEEET *dabs rapidly*",
        "There's a 2% chance to obtain this Moo! Here you go! \n ***MOO***",
        "If a cat goes meow and a cow goes moo, what does the dog say? \n Ed...ward..",
        "How much Moo can a Moo Wa Wa if a Woo Ma Coo Ma Woo",
        "Moo! \n Fun fact, I hardcoded all these Moos here, not sure if there was a better way but oh wells",
        "To Moo, or not to Moo. That is the question. \n -Milkshakespeare, 2019",
        "MooOoO!", "MOOOooo!", "MOoOoO...", "MoOoOoO?", "MOOOoooOOO!!",
        "Moo!", "Moo?", "Moo.", "Moo...", "Moo :D",
        "Moooooooooooooo!", "Mooooooooooo...", "Mooooooooo??", "Moooooooooo^2", "Moooooo :D",
        "MUUUUUU", "Muuuuu!", "Muuuuu?", "Muuuuuu...", "Muuuuuu :D",
    };
    
    public MooCommand() {
        super(false, "");
    }
    
    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryListList,
                        Ui ui, Storage storage)
            throws MooMooException {
        try {
            Random randomGenerator = new Random();
            int messageNo = randomGenerator.nextInt(50);
            String output = mooText[messageNo];
            ui.setOutput(output);
        } catch (Exception e) {
            throw new MooMooException("Hmmm.. Moo?");
        }
    }
}

