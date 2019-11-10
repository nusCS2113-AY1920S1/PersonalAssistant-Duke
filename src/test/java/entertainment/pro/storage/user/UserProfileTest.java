package entertainment.pro.storage.user;

import entertainment.pro.commons.exceptions.InvalidParameterException;
import entertainment.pro.commons.exceptions.NoPermissionException;
import entertainment.pro.commons.exceptions.logic.SetExceptions;
import entertainment.pro.model.UserProfile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserProfileTest {
    private UserProfile testUserProfile = new UserProfile();
    private ProfileCommands profileCommands = new ProfileCommands(testUserProfile);


    UserProfileTest() throws IOException {
    }

    @Test
    void setNameTest() {
        try {
            UserProfile actual = profileCommands.setName("test");
            UserProfile expected = new UserProfile("test", -1, new ArrayList<>(), new ArrayList<>(), true, new ArrayList<>(), false, false, false);
            assertEqualProfile(expected, actual);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void setAgeTest() {
        try {
            UserProfile actual = profileCommands.setAge("10");
            UserProfile expected = new UserProfile("", 10, new ArrayList<>(), new ArrayList<>(), false, new ArrayList<>(), false, false, false);
            assertEqualProfile(expected, actual);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void setGenrePreferenceTest() {
        try {
            TreeMap<String, ArrayList<String>> flagMapTest = new TreeMap<>();
            ArrayList<String> genreListTest = new ArrayList<>();
            genreListTest.add("comedy");
            flagMapTest.put("-g", genreListTest);
            UserProfile actual = profileCommands.setPreference(flagMapTest);
            ArrayList<Integer> genreIdListTest = new ArrayList<>();
            genreIdListTest.add(35);
            UserProfile expected = new UserProfile("", -1, genreIdListTest, new ArrayList<>(), true, new ArrayList<>(), false, false, false);
            assertEqualProfile(expected, actual);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void setGenreRestrictionTest() {
        try {
            TreeMap<String, ArrayList<String>> flagMapTest = new TreeMap<>();
            ArrayList<String> genreListTest = new ArrayList<>();
            genreListTest.add("comedy");
            flagMapTest.put("-g", genreListTest);
            UserProfile actual = profileCommands.setRestriction(flagMapTest);
            ArrayList<Integer> genreIdListTest = new ArrayList<>();
            genreIdListTest.add(35);
            UserProfile expected = new UserProfile("", -1, new ArrayList<>(), genreIdListTest, true, new ArrayList<>(), false, false, false);
            assertEqualProfile(expected, actual);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void setAdultTest() {
        try {
            TreeMap<String, ArrayList<String>> flagMapTest = new TreeMap<>();
            ArrayList<String> genreListTest = new ArrayList<>();
            genreListTest.add("no");
            flagMapTest.put("-a", genreListTest);
            UserProfile actual = profileCommands.setPreference(flagMapTest);
            UserProfile expected = new UserProfile("", -1, new ArrayList<>(), new ArrayList<>(), false, new ArrayList<>(), false, false, false);
            assertEqualProfile(expected, actual);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void setSortTest() {
        try {
            UserProfile actual = profileCommands.setSort(true, false, false);
            UserProfile expected = new UserProfile("", -1, new ArrayList<>(), new ArrayList<>(), true, new ArrayList<>(), true, false, false);
            assertEqualProfile(expected, actual);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void setRestrictionFailTest() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            TreeMap<String, ArrayList<String>> flagMapTest = new TreeMap<>();
            ArrayList<String> genreListTest = new ArrayList<>();
            genreListTest.add("comedy");
            flagMapTest.put("-g", genreListTest);
            ArrayList<Integer> genreIdListTest = new ArrayList<>();
            genreIdListTest.add(35);
            testUserProfile = testUserProfile.setGenreIdPreference(genreIdListTest);
            SetExceptions.checkRestrictionCommand(flagMapTest, testUserProfile);
        });
    }

    @Test
    void setInvalidRestrictionFailTest() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            TreeMap<String, ArrayList<String>> flagMapTest = new TreeMap<>();
            ArrayList<String> genreListTest = new ArrayList<>();
            genreListTest.add("fake genre");
            flagMapTest.put("-g", genreListTest);
            SetExceptions.checkRestrictionCommand(flagMapTest, testUserProfile);        });
    }

    @Test
    void setPreferenceFailTest() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            TreeMap<String, ArrayList<String>> flagMapTest = new TreeMap<>();
            ArrayList<String> genreListTest = new ArrayList<>();
            genreListTest.add("comedy");
            flagMapTest.put("-g", genreListTest);
            ArrayList<Integer> genreIdListTest = new ArrayList<>();
            genreIdListTest.add(35);
            testUserProfile = testUserProfile.setGenreIdRestriction(genreIdListTest);
            SetExceptions.checkPreferenceCommand(flagMapTest, testUserProfile);
        });
    }

    @Test
    void setInvalidPreferenceFailTest() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            TreeMap<String, ArrayList<String>> flagMapTest = new TreeMap<>();
            ArrayList<String> genreListTest = new ArrayList<>();
            genreListTest.add("fake genre");
            flagMapTest.put("-g", genreListTest);
            SetExceptions.checkPreferenceCommand(flagMapTest, testUserProfile);        });
    }

    @Test
    void setSortFailTest() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            SetExceptions.checkSortCommand("4");
        });
    }

    @Test
    void setAgeFailTest() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            SetExceptions.checkAgeCommand("four");
        });
    }

    @Test
    void setAdultFailTest() {
        Assertions.assertThrows(NoPermissionException.class, () -> {
           testUserProfile = profileCommands.setAge("10");
            TreeMap<String, ArrayList<String>> flagMapTest = new TreeMap<>();
            ArrayList<String> genreListTest = new ArrayList<>();
            genreListTest.add("yes");
            flagMapTest.put("-a", genreListTest);
            SetExceptions.checkPreferenceCommand(flagMapTest, testUserProfile);
        });
    }

    private void assertEqualProfile(UserProfile expected, UserProfile actual) {
        assertEquals(expected.getUserName(), actual.getUserName());
        assertEquals(expected.getUserAge(), actual.getUserAge());
        assertEquals(expected.getGenreIdPreference(), actual.getGenreIdPreference());
        assertEquals(expected.getGenreIdRestriction(), actual.getGenreIdRestriction());
        assertEquals(expected.getPlaylistNames(), actual.getPlaylistNames());
        assertEquals(expected.isAdult(), actual.isAdult());
        assertEquals(expected.isSortByAlphabetical(), actual.isSortByAlphabetical());
        assertEquals(expected.isSortByHighestRating(), actual.isSortByHighestRating());
        assertEquals(expected.isSortByLatestRelease(), actual.isSortByLatestRelease());
    }

}
