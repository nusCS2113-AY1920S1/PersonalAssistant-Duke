package entertainment.pro.commons.exceptions;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entertainment.pro.model.GenreId;
import entertainment.pro.model.UserProfile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;

public class SetExceptions extends Exceptions {
    public SetExceptions(String message) {
        super(message);
    }

    /**
     * check whether user input a number for age.
     * command: age
     * @throws SetExceptions when user input characters instead
     */
    public static void checkAgeInput(String input) throws SetExceptions {
        boolean flag = false;
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            flag = true;
        }
        if (flag) {
            throw new SetExceptions("ohno u did not enter a number :( pls try again with a number");
        }
    }

    /**
     * to check whether the user input a valid genre name.
     * command: preference -g / restriction -g
     * @throws SetExceptions when genre does not exist
     */
    public static void checkValidGenre(String inputGenre) throws SetExceptions {
        String[] genres = {"Action", "Adventure", "Animation", "Comedy", "Crime", "Documentary", "Drama", "Family", "Fantasy", "History", "Horror", "Music", "Mystery", "Romance", "Science Fiction", "TV Movie", "Thriller", "War", "Western"};
        boolean flag = true;
        for (String log : genres) {
            if (inputGenre.equalsIgnoreCase(log)) {
                flag = false;
                break;
            }
        }
        if (flag) {
            throw new SetExceptions("ohno the genre <" + inputGenre + "> does not exist :( pls try again with another genre");
        }
    }

    /**
     * to check whether a genre id can be added to user's preferences/restrictions.
     * command: preference -g / restriction -g
     * @throws SetExceptions when id already belongs to preferences or when id already belongs to restrictions
     */
    public static void checkForSetGenre(int id, ArrayList<Integer> preferences, ArrayList<Integer> restrictions) throws SetExceptions, IOException {
        for (int log : preferences) {
            if (id == log) {
                throw new SetExceptions("ohno <" + findGenreName(id) + "> is already in your preferences :( pls try again with another genre");
            }
        }
        for (int log : restrictions) {
            if (id == log) {
                throw new SetExceptions("ohno <" + findGenreName(id) + "> is already in your restrictions :( pls try again with another genre");
            }
        }
    }

    /**
     * to check whether a genre id can be set to user's preferences.
     * command: preference -g
     * @throws SetExceptions when id already belongs to restrictions
     */
    public static void checkForSetPreference(int id, ArrayList<Integer> restrictions) throws SetExceptions, IOException {
        for (int log : restrictions) {
            if (id == log) {
                throw new SetExceptions("ohno <" + findGenreName(id) + "> is already in your restrictions :( pls try again with another genre");
            }
        }
    }

    /**
     * to check whether a genre id can be set to user's restriction.
     * command: restriction -g
     * @throws SetExceptions when id already belongs to preferences
     */
    public static void checkForSetRestriction(int id, ArrayList<Integer> preferences) throws SetExceptions, IOException {
        for (int log : preferences) {
            if (id == log) {
                throw new SetExceptions("ohno <" + findGenreName(id) + "> is already in your preferences :( pls try again with another genre");
            }
        }
    }

    /**
     * check whether user can edit their adult restriction.
     * command: preference -a
     * @throws SetExceptions when user's age is below 21
     */
    public static void checkForSetAdult(UserProfile userProfile) throws SetExceptions {
        if (userProfile.getUserAge() < 21) {
            throw new SetExceptions("ohno u are below 21 yrs old and are not allowed to set your adult restriction :(");
        }
    }

    private static String findGenreName(int id) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = new FileInputStream("EPdata/GenreId.json");
        TypeReference<ArrayList<GenreId>> typeReference = new TypeReference<ArrayList<GenreId>>() {
        };
        ArrayList<GenreId> genreIds = mapper.readValue(inputStream, typeReference);
        for (GenreId log : genreIds) {
            if (log.getId() == id) {
                inputStream.close();
                return log.getGenre();
            }
        }
        inputStream.close();
        return "0";
    }
}
