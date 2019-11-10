package entertainment.pro.commons.exceptions.logic;

import entertainment.pro.commons.strings.PromptMessages;
import entertainment.pro.commons.exceptions.InvalidFormatCommandException;
import entertainment.pro.commons.exceptions.InvalidParameterException;
import entertainment.pro.commons.exceptions.NoPermissionException;
import entertainment.pro.model.UserProfile;
import entertainment.pro.storage.user.ProfileCommands;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

/**
 * logic for handling exceptions in set commands.
 */
public class SetExceptions {
    /**
     * to check if name command is in correct format.
     * command: set name USER_NAME
     * checks: whether user input payload
     */
    public static void checkNameCommand(String payload) throws InvalidFormatCommandException {
        checkPayload(payload);
    }

    /**
     * to check if age command is in correct format.
     * command: set age USER_AGE
     * checks: whether user input payload
     * whether payload is a number
     * whether payload is a positive number
     */
    public static void checkAgeCommand(String payload) throws InvalidParameterException, InvalidFormatCommandException {
        checkPayload(payload);
        checkAgeInput(payload);
        checkPositiveNumber(payload);
    }

    /**
     * to check if preference command is in correct format.
     * command: set preference [-a YES / NO] [-g GENRE...]
     * checks: whether user use valid flags
     * for [-g]:
     * whether GENRE is valid
     * whether GENRE belongs to preference / restriction
     * for [-a]:
     * whether user's age is >= 21
     * whether flagging input is valid
     */
    public static void checkPreferenceCommand(Map<String, ArrayList<String>> flagMap, UserProfile userProfile)
            throws InvalidFormatCommandException, InvalidParameterException, IOException, NoPermissionException {
        int count = 0;
        for (Map.Entry<String, ArrayList<String>> flag : flagMap.entrySet()) {
            System.out.println(count++);
            checkPreferenceFlag(flag);
            if (flag.getKey().equals("-g")) {
                for (String log : flag.getValue()) {
                    checkValidGenre(log);
                    checkForSetPreference(findGenreID(log.trim()), userProfile.getGenreIdRestriction());
                }
            } else if (flag.getKey().equals("-a")) {
                checkAgeForSetAdult(userProfile);
                checkForSetAdult(flag.getValue().get(0));
            }
        }
    }

    /**
     * to check if restriction command is in correct format.
     * command: set restriction [-g GENRE...]
     * checks: whether user use valid flags
     * whether GENRE is valid
     * whether GENRE belongs to preference / restriction
     */
    public static void checkRestrictionCommand(Map<String, ArrayList<String>> flagMap, UserProfile userProfile)
            throws IOException, InvalidParameterException, InvalidFormatCommandException {
        for (Map.Entry<String, ArrayList<String>> flag : flagMap.entrySet()) {
            checkRestrictionFlag(flag);
            for (String log : flag.getValue()) {
                checkValidGenre(log);
                checkForSetRestriction(findGenreID(log.trim()), userProfile.getGenreIdPreference());
            }
        }
    }

    /**
     * to check if sort command is in correct format.
     * command: set sort 1 / 2 / 3
     * checks: whether user valid input
     */
    public static void checkSortCommand(String payload) throws InvalidParameterException {
        checkSortPayload(payload);
    }

    /**
     * to check whether that command's payload is empty.
     * command: all set commands
     *
     * @throws InvalidFormatCommandException when payload is empty
     */
    private static void checkPayload(String payload) throws InvalidFormatCommandException {
        if (payload.isEmpty()) {
            throw new InvalidFormatCommandException(PromptMessages.SET_PAYLOAD_EMPTY);
        }
    }

    /**
     * check whether user input a number for age.
     * command: age
     *
     * @throws InvalidParameterException when user input characters instead
     */
    private static void checkAgeInput(String input) throws InvalidParameterException {
        boolean flag = false;
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            flag = true;
        }
        if (flag) {
            throw new InvalidParameterException(PromptMessages.INDEX_NOT_NUMBER_START + input
                    + PromptMessages.INDEX_NOT_NUMBER_END);
        }
    }

    /**
     * check whether user input a positive integer for age.
     * command: age
     *
     * @throws InvalidParameterException when user input negative sign
     */
    private static void checkPositiveNumber(String age) throws InvalidParameterException {
        if (age.contains("-")) {
            throw new InvalidParameterException(PromptMessages.AGE_IS_NEGATIVE);
        }
    }

    /**
     * check whether flag used is valid for this command.
     * command: preference
     *
     * @throws InvalidFormatCommandException when flag is not [-g] or [-a]
     */
    private static void checkPreferenceFlag(Map.Entry<String, ArrayList<String>> log)
            throws InvalidFormatCommandException {
        boolean flag = true;
        if (log.getKey().equals("-g") || log.getKey().equals("-a")) {
            flag = false;
        }
        if (flag) {
            throw new InvalidFormatCommandException(PromptMessages.INVALID_FLAG_START + log.getKey()
                    + PromptMessages.INVALID_FLAG_END);
        }
    }

    /**
     * check whether user input adult options correctly.
     * command: preference -a
     *
     * @throws InvalidFormatCommandException when user input options != yes or no
     */
    private static void checkForSetAdult(String option) throws InvalidFormatCommandException {
        boolean flag = true;
        if (option.equalsIgnoreCase("yes") || option.equalsIgnoreCase("no")) {
            flag = false;
        }
        if (flag) {
            throw new InvalidFormatCommandException(PromptMessages.INVALID_ADULT_OPTION_START + option
                    + PromptMessages.INVALID_ADULT_OPTION_END);
        }
    }

    /**
     * check whether flag used is valid for this command.
     * command: restriction
     *
     * @throws InvalidFormatCommandException when flag is not [-g]
     */
    private static void checkRestrictionFlag(Map.Entry<String, ArrayList<String>> log)
            throws InvalidFormatCommandException {
        if (!log.getKey().equals("-g")) {
            throw new InvalidFormatCommandException(PromptMessages.INVALID_FLAG_START + log.getKey()
                    + PromptMessages.INVALID_FLAG_END);
        }
    }

    /**
     * to check whether the user input a valid genre name.
     * command: preference -g / restriction -g
     *
     * @throws InvalidParameterException when genre does not exist
     */
    private static void checkValidGenre(String inputGenre) throws InvalidParameterException {
        String[] genres = {
            "Action", "Adventure", "Animation", "Comedy", "Crime",
            "Documentary", "Drama", "Family", "Fantasy", "History",
            "Horror", "Music", "Mystery", "Romance", "Science Fiction",
            "TV Movie", "Thriller", "War", "Western"
        };
        boolean flag = true;
        for (String log : genres) {
            if (inputGenre.equalsIgnoreCase(log)) {
                flag = false;
                break;
            }
        }
        if (flag) {
            throw new InvalidParameterException(PromptMessages.INVALID_GENRE_NAME_START + inputGenre
                    + PromptMessages.INVALID_GENRE_NAME_END);
        }
    }

    //    /**
    //     * to check whether a genre id can be added to user's preferences/restrictions.
    //     * command: preference -g / restriction -g
    //     * @throws InvalidParameterException when id already belongs to preferences or
    //     *                                   when id already belongs to restrictions
    //     */
    //    private static void checkForSetGenre(int id, ArrayList<Integer> preferences, ArrayList<Integer> restrictions)
    //            throws InvalidParameterException, IOException {
    //        for (int log : preferences) {
    //            if (id == log) {
    //                throw new InvalidParameterException(PromptMessages.GENRE_IN_PREFERENCE_START + findGenreName(id)
    //                        + PromptMessages.GENRE_IN_PREFERENCE_END);
    //            }
    //        }
    //        for (int log : restrictions) {
    //            if (id == log) {
    //                throw new InvalidParameterException(PromptMessages.GENRE_IN_RESTRICTION_START + findGenreName(id)
    //                        + PromptMessages.GENRE_IN_RESTRICTION_END);
    //            }
    //        }
    //    }

    /**
     * to check whether a genre id can be set to user's preferences.
     * command: preference -g
     *
     * @throws InvalidParameterException when id already belongs to restrictions
     */

    private static void checkForSetPreference(int id, ArrayList<Integer> restrictions) throws IOException,
            InvalidParameterException {
        for (int log : restrictions) {
            if (id == log) {
                throw new InvalidParameterException(PromptMessages.GENRE_IN_RESTRICTION_START + findGenreName(id)
                        + PromptMessages.GENRE_IN_RESTRICTION_END);
            }
        }
    }

    /**
     * to check whether a genre id can be set to user's restriction.
     * command: restriction -g
     *
     * @throws InvalidParameterException when id already belongs to preferences
     */

    private static void checkForSetRestriction(int id, ArrayList<Integer> preferences) throws InvalidParameterException,
                IOException {
        for (int log : preferences) {
            if (id == log) {
                throw new InvalidParameterException(PromptMessages.GENRE_IN_PREFERENCE_START + findGenreName(id)
                        + PromptMessages.GENRE_IN_PREFERENCE_END);
            }
        }
    }

    /**
     * check whether user can edit their adult restriction.
     * command: preference -a
     *
     * @throws NoPermissionException when user's age is below 21
     */
    private static void checkAgeForSetAdult(UserProfile userProfile) throws NoPermissionException {
        if (userProfile.getUserAge() < 21) {
            throw new NoPermissionException(PromptMessages.AGE_RESTRICTED);
        }
    }

    /**
     * to check if user use correct options for sort.
     * command: sort
     *
     * @throws InvalidParameterException when user input != 1 or 2 or 3
     */
    private static void checkSortPayload(String payload) throws InvalidParameterException {
        boolean flag = true;
        if (payload.equals("1") || payload.equals("2") || payload.equals("3")) {
            flag = false;
        }
        if (flag) {
            throw new InvalidParameterException(PromptMessages.INVALID_SORT_OPTION_START + payload
                    + PromptMessages.INVALID_SORT_OPTION_END);
        }
    }

    /**
     * to find genreId for corresponding genre name.
     */
    private static Integer findGenreID(String genreName) throws IOException {
        InputStream inputStream = ProfileCommands.class.getResourceAsStream("/data/GenreId.json");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String genreListString = "";
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            genreListString += line;
        }
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
        return Integer.parseInt(parseToId(genreListString, genreName.trim()));
    }

    /**
     * to find genre name for corresponding id.
     */
    private static String findGenreName(int id) throws IOException {
        InputStream inputStream = ProfileCommands.class.getResourceAsStream("/data/GenreId.json");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String genreListString = "";
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            genreListString += line;
        }
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
        return parseToName(genreListString, Integer.toString(id));
    }

    private static String parseToName(String genreListString, String id) {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = (JSONArray) parser.parse(genreListString);
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        for (Object o : jsonArray) {
            JSONObject jsonObject = (JSONObject) o;
            String genreId = (String) jsonObject.get("id");
            if (genreId.equals(id)) {
                return (String) jsonObject.get("genre");
            }
        }
        return "0";
    }

    private static String parseToId(String genreListString, String name) {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = (JSONArray) parser.parse(genreListString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (Object o : jsonArray) {
            JSONObject jsonObject = (JSONObject) o;
            String genreName = (String) jsonObject.get("genre");
            if (genreName.equalsIgnoreCase(name)) {
                return (String) jsonObject.get("id");
            }
        }
        return "0";
    }
}