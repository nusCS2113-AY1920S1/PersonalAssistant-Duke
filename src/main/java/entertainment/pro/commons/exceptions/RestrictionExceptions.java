package entertainment.pro.commons.exceptions;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entertainment.pro.model.GenreId;
import entertainment.pro.model.UserProfile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * This class handles exceptions related to setting genre restrictions.
 * @@author nwenhui
 */
public class RestrictionExceptions extends Exceptions {
    public RestrictionExceptions(String message) {
        super(message);
    }

    /**
     * to check whether a genre id can be added to user's restrictions.
     * command: add -g
     * @throws RestrictionExceptions when id already belongs to preferences or when id already belongs to restrictions
     */
    public static void checkForAddGenre(int id, ArrayList<Integer> preferences,
                                        ArrayList<Integer> restrictions) throws RestrictionExceptions, IOException {
        for (int log : preferences) {
            if (id == log) {
                throw new RestrictionExceptions("ohno <" + findGenreName(id)
                        + "> is already in your preferences :( pls try again with another genre");
            }
        }
        for (int log : restrictions) {
            if (id == log) {
                throw new RestrictionExceptions("ohno <" + findGenreName(id)
                        + "> is already in your restrictions :( pls try again with another genre");
            }
        }
    }

    /**
     * to check whether a genre id can be removed from user's restrictions.
     * command: remove -g
     * @throws RestrictionExceptions when id don't belong to preferences
     */
    public static void checkForRemoveGenre(int id, ArrayList<Integer> restrictions)
            throws RestrictionExceptions, IOException {
        boolean flag = true;
        for (int log : restrictions) {
            if (id == log) {
                flag = false;
                break;
            }
        }
        if (flag) {
            throw new RestrictionExceptions("ohno <" + findGenreName(id)
                    + "> does not belong to your restrictions :( pls try again with another genre");
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
