package diyeats.logic.parsers;

import diyeats.logic.commands.AddExerciseCommand;

//@@author Fractalisk

public class AddExerciseCommandParser implements ParserInterface<AddExerciseCommand> {

    /**
     * Parses user input and returns an AddCommand encapsulating a breakfast object.
     * @param userInputStr String input by user.
     * @return <code>AddCommand</code> Command object encapsulating a breakfast object
     */
    @Override
    public AddExerciseCommand parse(String userInputStr) {
        if (!userInputStr.contains("/value")) {
            return new AddExerciseCommand(false, "Theres no /value tag provided. "
                    + "Please include the /value tag");
        }

        String[] exerciseNameAndValue = ArgumentSplitter.splitArguments(userInputStr, "/value");
        String exerciseNameStr = exerciseNameAndValue[0];
        String exerciseValueStr = exerciseNameAndValue[1];

        if (exerciseNameStr.trim().length() == 0) {
            return new AddExerciseCommand(false, "No description of "
                    + "the exercise has been provided.");
        }

        int exerciseValueInt;
        try {
            exerciseValueInt = Integer.parseInt(exerciseValueStr);
        } catch (Exception e) {
            return new AddExerciseCommand(false, "Unable to parse " + exerciseValueStr + " as a number. "
                    + "Please enter a valid integer.");
        }

        if (exerciseValueInt <= 0) {
            return new AddExerciseCommand(false, "Please enter a valid integer greater than 0.");
        }

        return new AddExerciseCommand(exerciseNameStr, exerciseValueInt);
    }
}
