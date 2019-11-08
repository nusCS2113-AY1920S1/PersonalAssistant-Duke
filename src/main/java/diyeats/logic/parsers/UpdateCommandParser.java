package diyeats.logic.parsers;

import diyeats.logic.commands.UpdateCommand;

import java.util.HashMap;

/**
 * Parser class to handle addition of breakfast item to model.
 */
public class UpdateCommandParser implements ParserInterface<UpdateCommand> {

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
        } else {
            age = null;
        }
        if (map.containsKey("weight")) {
            weight = map.get("weight");
        } else {
            weight = null;
        }
        if (map.containsKey("date")) {
            date = map.get("date");
        } else {
            date = null;
        }
        if (map.containsKey("height")) {
            height = map.get("height");
        } else {
            height = null;
        }
        if (map.containsKey("name")) {
            name = map.get("name");
        } else {
            name = null;
        }
        if (map.containsKey("activity")) {
            activity = map.get("activity");
        } else {
            activity = null;
        }
        return new UpdateCommand(age, weight, date, height, name, activity);
    }
}
