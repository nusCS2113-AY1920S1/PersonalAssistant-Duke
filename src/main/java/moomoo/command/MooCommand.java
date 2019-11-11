package moomoo.command;

import java.util.ArrayList;
import java.util.Random;

import moomoo.feature.Budget;
import moomoo.feature.MooMooException;
import moomoo.feature.ScheduleList;
import moomoo.feature.Ui;
import moomoo.feature.storage.Storage;
import moomoo.feature.category.CategoryList;

public class MooCommand extends Command {
    private String[] mooText = new String[] {"MooOoO!", "MOOOooo!", "MOoOoO...", "MoOoOoO?", "MOOOoooOOO!!",
        "Moo!", "Moo?", "Moo.", "Moo...", "Moo :D",
        "Moooooooooooooo!", "Mooooooooooo...", "Mooooooooo??", "Moooooooooo^2", "Moooooo :D",
        "MUUUUUU", "Muuuuu!", "Muuuuu?", "Muuuuuu...", "Muuuuuu :D",
        "Huh?", "MIIIIIII!?!?", "Quack", "Woof :D", "YEEET *dabs rapidly*",
        "There's a 2% chance to obtain this Moo! Here you go!    ***MOO***",
        "Q: If a cat goes meow and a cow goes moo, what does the dog say?     A: Ed...ward..",
        "How much Moo can a Moo Wa Wa if a Woo Ma Coo Ma Woo",
        "Moo! Fun fact, I hardcoded all these Moos here, not sure if there was a better way but oh wells",
        "To Moo, or not to Moo. That is the question.       -Milkshakespeare, 2019",
        "MooOoO!", "MOOOooo!", "MOoOoO...", "MoOoOoO?", "MOOOoooOOO!!",
        "Moo!", "Moo?", "Moo.", "Moo...", "Moo :D", "Have enough Moo yet?",
        "Two cows in a field, one turns to the other and says MOO. The other cow replied, \"I was going to say that!\"",
        "Q: Why are cows so complicated?    A: They've got a lot of moooving parts!",
        "Q: How do cows pay for things?    A: With Mooolah", "Q: What's a coward?    A: Moooooo! Get it? A cow-word!",
        "Q: Where did the cow go?    A: It mooooved!", "I'm getting tired, moo",
        "Q: Why did the cow jump over the moon?   A: To get to the milky way!",
        "Fun fact: you have a 1 in 50 chance of getting this message!",
        "Q: What do you call a cow after she has given birth?    A: Decaffeinated!",
    };
    
    public MooCommand() {
        super(false, "");
    }
    
    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList,
                        Storage storage)
            throws MooMooException {
        try {
            Random randomGenerator = new Random();
            int messageNo = randomGenerator.nextInt(50);
            ArrayList<String> output = new ArrayList<>();
            output.add(mooText[messageNo]);
            Ui.showInCowBox(output);
        } catch (Exception e) {
            throw new MooMooException("Hmmm.. Moo?");
        }
    }
}

