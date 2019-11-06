package entertainment.pro.commons.exceptions;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entertainment.pro.model.GenreId;
import entertainment.pro.model.UserProfile;
import entertainment.pro.storage.utils.EditProfileJson;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class PreferenceExceptions extends Exceptions {
    public PreferenceExceptions(String message) {
        super(message);
    }

    /**
     * check whether user can clear their adult restriction.
     * command: clear -a
     * @throws PreferenceExceptions when user's age is below 21
     */
    public static void checkForSetAdult(UserProfile userProfile) throws PreferenceExceptions {
        if (userProfile.getUserAge() < 21) {
            throw new PreferenceExceptions("ohno u are below 21 yrs old and "
                    + "are not allowed to clear your adult restriction :(");
        }
    }

    /**
     * to check whether a genre id can be added to user's preferences.
     * command: add -g
     * @throws PreferenceExceptions when id already belongs to preferences or when id already belongs to restrictions
     */
    public static void checkForAddGenre(int id, ArrayList<Integer> preferences,
                                        ArrayList<Integer> restrictions) throws PreferenceExceptions, IOException {
        for (int log : preferences) {
            if (id == log) {
                throw new PreferenceExceptions("ohno <" + findGenreName(id)
                        + "> is already in your preferences :( pls try again with another genre");
            }
        }
        for (int log : restrictions) {
            if (id == log) {
                throw new PreferenceExceptions("ohno <" + findGenreName(id)
                        + "> is already in your restrictions :( pls try again with another genre");
            }
        }
    }

    /**
     * to check whether a genre id can be removed from user's preferences.
     * command: remove -g
     * @throws PreferenceExceptions when id don't belong to preferences
     */
    public static void checkForRemoveGenre(int id, ArrayList<Integer> preferences)
            throws PreferenceExceptions, IOException {
        boolean flag = true;
        for (int log : preferences) {
            if (id == log) {
                flag = false;
                break;
            }
        }
        if (flag) {
            throw new PreferenceExceptions("ohno <" + findGenreName(id)
                    + "> does not belong to your preferences :( pls try again with another genre");
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
