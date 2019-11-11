package diyeats.logic.parsers;

import diyeats.logic.commands.UpdateCommand;

import java.util.HashMap;

//@@author koushireo

/**
 * Parser class to handle update command parameters.
 */
public class UpdateCommandParser implements ParserInterface<UpdateCommand> {
    private boolean flag = false;
    /**
     * Parses user input and returns an UpdateCommand encapsulating an object containing information to be updated.
     * @param userInputStr String input by user.
     * @return <code>UpdateCommand</code> Command object encapsulating information to be updated
     */

    @Override
    public UpdateCommand parse(String userInputStr) {
        String age;
        String weight;
        String date;
        String height;
        String name;
        String activity;
        String[] temp = {"age", "weight", "date", "height", "name", "activity"};
        HashMap<String, String> map = ArgumentSplitter.splitForwardSlashArguments(userInputStr);
        if (map.containsKey("age")) {
            age = map.get("age");
            flag = true;
        } else {
            age = null;
        }
        if (map.containsKey("weight")) {
            weight = map.get("weight");
            flag = true;
        } else {
            weight = null;
        }
        if (map.containsKey("date")) {
            date = map.get("date");
            flag = true;
        } else {
            date = null;
        }
        if (map.containsKey("height")) {
            height = map.get("height");
            flag = true;
        } else {
            height = null;
        }
        if (map.containsKey("name")) {
            name = map.get("name");
            flag = true;
        } else {
            name = null;
        }
        if (map.containsKey("activity")) {
            activity = map.get("activity");
            flag = true;
        } else {
            activity = null;
        }
        if (flag == false) {
            return new UpdateCommand(false, "Please enter a parameter using /name /age /weight /height /activity!");
        }
        return new UpdateCommand(age, weight, date, height, name, activity);
    }
}
