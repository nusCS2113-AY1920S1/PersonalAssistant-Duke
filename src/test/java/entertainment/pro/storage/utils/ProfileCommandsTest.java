package entertainment.pro.storage.utils;



import entertainment.pro.commons.exceptions.DuplicateGenreException;
import entertainment.pro.commons.exceptions.GenreDoesNotExistException;
import entertainment.pro.commons.exceptions.InvalidFormatCommandException;
import entertainment.pro.commons.exceptions.InvalidGenreNameEnteredException;
import entertainment.pro.model.UserProfile;
import entertainment.pro.storage.user.ProfileCommands;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ProfileCommandsTest {

    static ProfileCommands profileCommands;
    static UserProfile userProfile;

    {
        try {
            userProfile = new EditProfileJson().load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            profileCommands = new ProfileCommands(userProfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addPreferenceTest_valid_genre_pref() {
        profileCommands.clearGenrePreference();
        profileCommands.clearGenreRestrict();
        TreeMap<String, ArrayList<String>> flagMap = new TreeMap<>();
        ArrayList<String> genreOption = new ArrayList<>();
        genreOption.add("action");
        flagMap.put("-g", genreOption);
        try {
            profileCommands.addPreference(flagMap, "-g");
        } catch (IOException | InvalidFormatCommandException | InvalidGenreNameEnteredException
                | DuplicateGenreException e) {
            e.printStackTrace();
        }
        ArrayList<Integer> getUserProfileGenre = userProfile.getGenreIdPreference();
        assertEquals(1, getUserProfileGenre.size());
        assertEquals(28, getUserProfileGenre.get(0));
        TreeMap<String, ArrayList<String>> newFlagMap = new TreeMap<>();
        ArrayList<String> newGenreOption = new ArrayList<>();
        newGenreOption.add("horror");
        newGenreOption.add("comedy");
        newFlagMap.put("-g", newGenreOption);
        try {
            profileCommands.addPreference(newFlagMap, "-g");
        } catch (IOException | InvalidFormatCommandException | InvalidGenreNameEnteredException
                | DuplicateGenreException e) {
            e.printStackTrace();
        }
        ArrayList<Integer> getNewUserProfileGenre = userProfile.getGenreIdPreference();
        assertEquals(3, getNewUserProfileGenre.size());
        assertEquals(28, getNewUserProfileGenre.get(0));
        assertEquals(27, getNewUserProfileGenre.get(1));
        assertEquals(35, getNewUserProfileGenre.get(2));
        profileCommands.clearGenreRestrict();
    }

    @Test
    public void addPreferenceTest_genrePref_return_invalid() throws InvalidFormatCommandException, IOException {
        profileCommands.clearGenrePreference();
        profileCommands.clearGenreRestrict();
        TreeMap<String, ArrayList<String>> flagMap = new TreeMap<>();
        ArrayList<String> genreOption = new ArrayList<>();
        genreOption.add("actio");
        flagMap.put("-g", genreOption);
        assertThrows(InvalidGenreNameEnteredException.class, () -> {
            profileCommands.addPreference(flagMap, "-g");
        });
    }

    @Test
    public void addPreferenceTest_genrePref_return_dup() throws InvalidFormatCommandException, IOException {
        profileCommands.clearGenrePreference();
        profileCommands.clearGenreRestrict();
        TreeMap<String, ArrayList<String>> flagMap = new TreeMap<>();
        ArrayList<String> genreOption = new ArrayList<>();
        genreOption.add("action");
        flagMap.put("-r", genreOption);
        try {
            profileCommands.addPreference(flagMap, "-r");
        } catch (IOException | InvalidFormatCommandException | InvalidGenreNameEnteredException
                | DuplicateGenreException e) {
            e.printStackTrace();
        }
        TreeMap<String, ArrayList<String>> newFlagMap = new TreeMap<>();
        ArrayList<String> newGenreOption = new ArrayList<>();
        newGenreOption.add("action");
        newGenreOption.add("comedy");
        newFlagMap.put("-g", newGenreOption);
        assertThrows(DuplicateGenreException.class, () -> {
            profileCommands.addPreference(newFlagMap, "-g");
        });
    }


    @Test
    public void addPreferenceTest_valid_genre_restrict() throws InvalidFormatCommandException, IOException {
        profileCommands.clearGenrePreference();
        profileCommands.clearGenreRestrict();
        TreeMap<String, ArrayList<String>> flagMap = new TreeMap<>();
        ArrayList<String> genreOption = new ArrayList<>();
        genreOption.add("action");
        flagMap.put("-r", genreOption);
        try {
            profileCommands.addPreference(flagMap, "-r");
        } catch (IOException | InvalidFormatCommandException | InvalidGenreNameEnteredException
                | DuplicateGenreException e) {
            e.printStackTrace();
        }
        ArrayList<Integer> getUserProfileGenre = userProfile.getGenreIdRestriction();
        assertEquals(1, getUserProfileGenre.size());
        assertEquals(28, getUserProfileGenre.get(0));
        TreeMap<String, ArrayList<String>> newFlagMap = new TreeMap<>();
        ArrayList<String> newGenreOption = new ArrayList<>();
        newGenreOption.add("horror");
        newGenreOption.add("comedy");
        newFlagMap.put("-r", newGenreOption);
        try {
            profileCommands.addPreference(newFlagMap, "-r");
        } catch (IOException | InvalidFormatCommandException | InvalidGenreNameEnteredException
                | DuplicateGenreException e) {
            e.printStackTrace();
        }
        ArrayList<Integer> getNewUserProfileGenre = userProfile.getGenreIdRestriction();
        assertEquals(3, getNewUserProfileGenre.size());
        assertEquals(28, getNewUserProfileGenre.get(0));
        assertEquals(27, getNewUserProfileGenre.get(1));
        assertEquals(35, getNewUserProfileGenre.get(2));
        profileCommands.clearGenreRestrict();
    }

    @Test
    public void addPreferenceTest_genreRestrict_return_invalid() throws InvalidFormatCommandException, IOException {
        profileCommands.clearGenrePreference();
        profileCommands.clearGenreRestrict();
        TreeMap<String, ArrayList<String>> flagMap = new TreeMap<>();
        ArrayList<String> genreOption = new ArrayList<>();
        genreOption.add("actio");
        flagMap.put("-r", genreOption);
        assertThrows(InvalidGenreNameEnteredException.class, () -> {
            profileCommands.addPreference(flagMap, "-r");
        });
    }

    @Test
    public void addPreferenceTest_genreRestrict_return_dup() throws InvalidFormatCommandException, IOException {
        profileCommands.clearGenrePreference();
        profileCommands.clearGenreRestrict();
        TreeMap<String, ArrayList<String>> flagMap = new TreeMap<>();
        ArrayList<String> genreOption = new ArrayList<>();
        genreOption.add("action");
        flagMap.put("-g", genreOption);
        try {
            profileCommands.addPreference(flagMap, "-g");
        } catch (IOException | InvalidFormatCommandException | InvalidGenreNameEnteredException
                | DuplicateGenreException e) {
            e.printStackTrace();
        }
        TreeMap<String, ArrayList<String>> newFlagMap = new TreeMap<>();
        ArrayList<String> newGenreOption = new ArrayList<>();
        newGenreOption.add("action");
        newGenreOption.add("comedy");
        newFlagMap.put("-r", newGenreOption);
        assertThrows(DuplicateGenreException.class, () -> {
            profileCommands.addPreference(newFlagMap, "-r");
        });
    }


    @Test
    public void addPreferenceTest_valid_sort_input() throws IOException {
        TreeMap<String, ArrayList<String>> flagMap = new TreeMap<>();
        ArrayList<String> sortOption = new ArrayList<>();
        sortOption.add("1");
        flagMap.put("-s", sortOption);
        try {
            UserProfile newUserProfile = profileCommands.addPreference(flagMap, "-s");
            assertTrue(newUserProfile.isSortByAlphabetical(), "Test failed");
            assertFalse(newUserProfile.isSortByHighestRating(), "Test failed");
            assertFalse(newUserProfile.isSortByLatestRelease(), "Test failed");
        } catch (IOException | InvalidFormatCommandException | InvalidGenreNameEnteredException
                | DuplicateGenreException e) {
            e.printStackTrace();
        }
        profileCommands.clearSortPreference();
    }

    @Test
    public void addPreferenceTest_invalid_sort_input() throws IOException, InvalidFormatCommandException {
        TreeMap<String, ArrayList<String>> testFlagMap1 = new TreeMap<>();
        ArrayList<String> sortOption = new ArrayList<>();
        sortOption.add("4");
        testFlagMap1.put("-s", sortOption);
        assertThrows(InvalidFormatCommandException.class, () -> {
            profileCommands.addPreference(testFlagMap1, "-s");
        });
        testFlagMap1.get("-s").add("4");
        assertThrows(InvalidFormatCommandException.class, () -> {
            profileCommands.addPreference(testFlagMap1, "-s");
        });
        sortOption.clear();
        sortOption.add("0");
        TreeMap<String, ArrayList<String>> testFlagMap2 = new TreeMap<>();
        testFlagMap2.put("-s", sortOption);
        assertThrows(InvalidFormatCommandException.class, () -> {
            profileCommands.addPreference(testFlagMap2, "-s");
        });
    }

    @Test
    public void addPreferenceTest_valid_adult_input() throws InvalidFormatCommandException, IOException,
            InvalidGenreNameEnteredException, DuplicateGenreException {
        TreeMap<String, ArrayList<String>> flagMap = new TreeMap<>();
        ArrayList<String> adultOption = new ArrayList<>();
        adultOption.add("true");
        flagMap.put("-a", adultOption);
        try {
            UserProfile newUserProfile = profileCommands.addPreference(flagMap, "-a");
            assertTrue(newUserProfile.isAdult(), "Test has failed");
        } catch (IOException e) {
            e.printStackTrace();
        }
        profileCommands.clearAdultPreference();
    }

    @Test
    public void addPreferenceTest_invalid_adult_input() throws IOException, InvalidFormatCommandException {
        TreeMap<String, ArrayList<String>> testFlagMap1 = new TreeMap<>();
        ArrayList<String> adultOption = new ArrayList<>();
        adultOption.add("yes");
        testFlagMap1.put("-a", adultOption);
        assertThrows(InvalidFormatCommandException.class, () -> {
            profileCommands.addPreference(testFlagMap1, "-a");
        });
        testFlagMap1.get("-a").add("true");
        assertThrows(InvalidFormatCommandException.class, () -> {
            profileCommands.addPreference(testFlagMap1, "-a");
        });
        adultOption.clear();
        adultOption.add("allow");
        TreeMap<String, ArrayList<String>> testFlagMap2 = new TreeMap<>();
        testFlagMap2.put("-a", adultOption);
        assertThrows(InvalidFormatCommandException.class, () -> {
            profileCommands.addPreference(testFlagMap2, "-a");
        });
    }

    @Test
    public void clearPreferenceTest_valid_sort_input() throws InvalidFormatCommandException, IOException {
        TreeMap<String, ArrayList<String>> flagMap = new TreeMap<>();
        ArrayList<String> sortOption = new ArrayList<>();
        flagMap.put("-s", sortOption);
        try {
            profileCommands.clearPreference(flagMap, "-s");
        } catch (IOException | InvalidFormatCommandException e) {
            e.printStackTrace();
        }
        UserProfile newUserProfile = new EditProfileJson().load();
        assertFalse(newUserProfile.isSortByAlphabetical(), "Test failed");
        assertFalse(newUserProfile.isSortByHighestRating(), "Test failed");
        assertFalse(newUserProfile.isSortByLatestRelease(), "Test failed");
        profileCommands.clearSortPreference();
    }

    @Test
    public void clearPreferenceTest_valid_adult_content() throws InvalidFormatCommandException, IOException {
        TreeMap<String, ArrayList<String>> flagMap = new TreeMap<>();
        ArrayList<String> adultOption = new ArrayList<>();
        flagMap.put("-a", adultOption);
        try {
            UserProfile newUserProfile = profileCommands.clearPreference(flagMap, "-a");
            assertFalse(newUserProfile.isAdult(), "Test failed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void clearPreferenceTest_valid_genre_pref() throws InvalidFormatCommandException, IOException {
        profileCommands.clearGenrePreference();
        profileCommands.clearGenreRestrict();
        TreeMap<String, ArrayList<String>> flagMap = new TreeMap<>();
        ArrayList<String> genreOption = new ArrayList<>();
        genreOption.add("action");
        genreOption.add("adventure");
        flagMap.put("-g", genreOption);


        ArrayList<String> newGenreOption = new ArrayList<>();
        TreeMap<String, ArrayList<String>> clearFlagMap = new TreeMap<>();
        clearFlagMap.put("-g", newGenreOption);
        try {
            profileCommands.addPreference(flagMap, "-g");
            profileCommands.clearPreference(clearFlagMap, "-g");
        } catch (IOException | InvalidFormatCommandException | InvalidGenreNameEnteredException
                | DuplicateGenreException e) {
            e.printStackTrace();
        }
        assertEquals(0, userProfile.getGenreIdPreference().size());
    }

    @Test
    public void clearPreferenceTest_valid_genre_restrict() throws InvalidFormatCommandException, IOException {
        profileCommands.clearGenrePreference();
        profileCommands.clearGenreRestrict();
        TreeMap<String, ArrayList<String>> flagMap = new TreeMap<>();
        ArrayList<String> genreOption = new ArrayList<>();
        genreOption.add("action");
        genreOption.add("adventure");
        flagMap.put("-r", genreOption);

        TreeMap<String, ArrayList<String>> clearFlagMap = new TreeMap<>();
        ArrayList<String> newGenreOption = new ArrayList<>();
        clearFlagMap.put("-r", newGenreOption);
        try {
            profileCommands.addPreference(flagMap, "-r");
            profileCommands.clearPreference(clearFlagMap, "-r");
        } catch (IOException | InvalidFormatCommandException | InvalidGenreNameEnteredException
                | DuplicateGenreException e) {
            e.printStackTrace();
        }
        assertEquals(0, userProfile.getGenreIdPreference().size());
    }

    @Test
    public void clearPreferenceTest_invalid_sort_input() throws IOException, InvalidFormatCommandException {
        TreeMap<String, ArrayList<String>> testFlagMap1 = new TreeMap<>();
        ArrayList<String> sortOption = new ArrayList<>();
        sortOption.add("4");
        testFlagMap1.put("-s", sortOption);
        assertThrows(InvalidFormatCommandException.class, () -> {
            profileCommands.clearPreference(testFlagMap1, "-s");
        });
        testFlagMap1.get("-s").add("0");
        assertThrows(InvalidFormatCommandException.class, () -> {
            profileCommands.clearPreference(testFlagMap1, "-s");
        });
        sortOption.clear();
        sortOption.add("0");

        TreeMap<String, ArrayList<String>> testFlagMap2 = new TreeMap<>();
        testFlagMap2.put("-s", sortOption);
        assertThrows(InvalidFormatCommandException.class, () -> {
            profileCommands.clearPreference(testFlagMap2, "-s");
        });
    }

    @Test
    public void removePreferenceTest_valid_genre_pref() throws InvalidFormatCommandException, IOException {
        profileCommands.clearGenrePreference();
        profileCommands.clearGenreRestrict();
        TreeMap<String, ArrayList<String>> flagMap = new TreeMap<>();
        ArrayList<String> genreOption = new ArrayList<>();
        genreOption.add("action");
        genreOption.add("horror");
        flagMap.put("-g", genreOption);
        TreeMap<String, ArrayList<String>> removeFlagMap = new TreeMap<>();
        ArrayList<String> removeGenreOption = new ArrayList<>();
        removeGenreOption.add("action");
        removeFlagMap.put("-g", removeGenreOption);
        try {
            profileCommands.addPreference(flagMap, "-g");
            profileCommands.removePreference(removeFlagMap, "-g");
        } catch (IOException | InvalidFormatCommandException | InvalidGenreNameEnteredException
                | DuplicateGenreException | GenreDoesNotExistException e) {
            e.printStackTrace();
        }
        ArrayList<Integer> getUserProfileGenre = userProfile.getGenreIdPreference();
        assertEquals(1, getUserProfileGenre.size());
        assertEquals(27, getUserProfileGenre.get(0));
    }

    @Test
    public void removePreferenceTest_valid_genre_restrict() throws InvalidFormatCommandException, IOException {
        profileCommands.clearGenrePreference();
        profileCommands.clearGenreRestrict();
        TreeMap<String, ArrayList<String>> flagMap = new TreeMap<>();
        ArrayList<String> genreOption = new ArrayList<>();
        genreOption.add("action");
        genreOption.add("horror");
        flagMap.put("-r", genreOption);
        TreeMap<String, ArrayList<String>> removeFlagMap = new TreeMap<>();
        ArrayList<String> removeGenreOption = new ArrayList<>();
        removeGenreOption.add("action");
        removeFlagMap.put("-r", removeGenreOption);
        try {
            profileCommands.addPreference(flagMap, "-r");
            profileCommands.removePreference(removeFlagMap, "-r");
        } catch (IOException | InvalidFormatCommandException | InvalidGenreNameEnteredException
                | DuplicateGenreException | GenreDoesNotExistException e) {
            e.printStackTrace();
        }
        ArrayList<Integer> getUserProfileGenre = userProfile.getGenreIdRestriction();
        assertEquals(1, getUserProfileGenre.size());
        assertEquals(27, getUserProfileGenre.get(0));
    }

    @Test
    public void removePreferenceTest_genre_pref_returns_invalid() throws InvalidFormatCommandException, IOException {
        profileCommands.clearGenrePreference();
        profileCommands.clearGenreRestrict();
        TreeMap<String, ArrayList<String>> flagMap = new TreeMap<>();
        ArrayList<String> genreOption = new ArrayList<>();
        genreOption.add("action");
        genreOption.add("horror");
        flagMap.put("-g", genreOption);
        TreeMap<String, ArrayList<String>> removeFlagMap = new TreeMap<>();
        ArrayList<String> removeGenreOption = new ArrayList<>();
        removeGenreOption.add("actio");
        removeFlagMap.put("-g", removeGenreOption);
        try {
            profileCommands.addPreference(flagMap, "-g");
            assertThrows(InvalidGenreNameEnteredException.class, () -> {
                profileCommands.removePreference(removeFlagMap, "-g");
            });
        } catch (IOException | InvalidFormatCommandException | InvalidGenreNameEnteredException
                | DuplicateGenreException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void removePreferenceTest_genre_pref_returns_does_not_exists()
            throws InvalidFormatCommandException, IOException {
        profileCommands.clearGenrePreference();
        profileCommands.clearGenreRestrict();
        TreeMap<String, ArrayList<String>> flagMap = new TreeMap<>();
        ArrayList<String> genreOption = new ArrayList<>();
        genreOption.add("action");
        genreOption.add("horror");
        flagMap.put("-g", genreOption);
        TreeMap<String, ArrayList<String>> removeFlagMap = new TreeMap<>();
        ArrayList<String> removeGenreOption = new ArrayList<>();
        removeGenreOption.add("comedy");
        removeFlagMap.put("-g", removeGenreOption);
        try {
            profileCommands.addPreference(flagMap, "-g");
            assertThrows(GenreDoesNotExistException.class, () -> {
                profileCommands.removePreference(removeFlagMap, "-g");
            });
        } catch (IOException | InvalidFormatCommandException | InvalidGenreNameEnteredException
                | DuplicateGenreException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void removePreferenceTest_genre_restrict_returns_does_not_exists()
            throws InvalidFormatCommandException, IOException {
        profileCommands.clearGenrePreference();
        profileCommands.clearGenreRestrict();
        TreeMap<String, ArrayList<String>> flagMap = new TreeMap<>();
        ArrayList<String> genreOption = new ArrayList<>();
        genreOption.add("action");
        genreOption.add("horror");
        flagMap.put("-g", genreOption);
        TreeMap<String, ArrayList<String>> removeFlagMap = new TreeMap<>();
        ArrayList<String> removeGenreOption = new ArrayList<>();
        removeGenreOption.add("horror");
        removeFlagMap.put("-r", removeGenreOption);
        try {
            profileCommands.addPreference(flagMap, "-g");
            assertThrows(GenreDoesNotExistException.class, () -> {
                profileCommands.removePreference(removeFlagMap, "-r");
            });
        } catch (IOException | InvalidFormatCommandException | InvalidGenreNameEnteredException
                | DuplicateGenreException e) {
            e.printStackTrace();
        }
    }
}