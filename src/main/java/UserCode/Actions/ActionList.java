package UserCode.Actions;

import java.util.Set;

public class ActionList {
    protected static final Set<String> level1 = Set.of("buySeeds", "plantSeeds");

    public static boolean validateAction(int level, String userInput) {
        boolean valid = false;
        switch (level) {
            case 1:
                valid =  level1.contains(userInput);
                break;
        }
        return valid;
    }
}
