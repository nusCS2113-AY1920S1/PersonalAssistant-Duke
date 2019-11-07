package entertainment.pro.storage.utils;


import entertainment.pro.commons.exceptions.InvalidFormatCommandException;
import entertainment.pro.model.UserProfile;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

public class ProfileCommandsTest {

    static ProfileCommands profileCommands;
    static UserProfile userProfile;
    {
        try {
            userProfile = EditProfileJson.load();
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
    public void addPreferenceTest_valid_sort_input() throws InvalidFormatCommandException, IOException {
        TreeMap<String, ArrayList<String>> flagMap = new TreeMap<>();
        ArrayList<String> sortOption = new ArrayList<>();
        sortOption.add("1");
        flagMap.put("-s", sortOption);
        try {
            profileCommands.addPreference(flagMap, "-s");
        } catch (IOException | InvalidFormatCommandException e) {
            e.printStackTrace();
        }
        assertTrue(userProfile.isSortByAlphabetical(), "Test failed");
        assertFalse(userProfile.isSortByHighestRating(), "Test failed");
        assertFalse(userProfile.isSortByLatestRelease(), "Test failed");
        profileCommands.clearSortPreference();
    }

    @Test
    public void addPreferenceTest_invalid_sort_input() throws IOException, InvalidFormatCommandException {
        TreeMap<String, ArrayList<String>> testFlagMap1 = new TreeMap<>();
        TreeMap<String, ArrayList<String>> testFlagMap2 = new TreeMap<>();
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
        testFlagMap2.put("-s", sortOption);
        assertThrows(InvalidFormatCommandException.class, () -> {
            profileCommands.addPreference(testFlagMap2, "-s");
        });
    }

    @Test
    public void addPreferenceTest_valid_adult_input() throws InvalidFormatCommandException, IOException {
        TreeMap<String, ArrayList<String>> flagMap = new TreeMap<>();
        ArrayList<String> adultOption = new ArrayList<>();
        adultOption.add("true");
        flagMap.put("-a", adultOption);
        try {
            profileCommands.addPreference(flagMap, "-a");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(userProfile.isAdult(), "Test has failed");
        profileCommands.clearSortPreference();
    }

    @Test
    public void addPreferenceTest_invalid_adult_input() throws IOException, InvalidFormatCommandException {
        TreeMap<String, ArrayList<String>> testFlagMap1 = new TreeMap<>();
        TreeMap<String, ArrayList<String>> testFlagMap2 = new TreeMap<>();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertFalse(userProfile.isSortByAlphabetical(), "Test failed");
        assertFalse(userProfile.isSortByHighestRating(), "Test failed");
        assertFalse(userProfile.isSortByLatestRelease(), "Test failed");
        profileCommands.clearSortPreference();
    }

    @Test
    public void clearPreferenceTest_invalid_sort_input() throws IOException, InvalidFormatCommandException {
        TreeMap<String, ArrayList<String>> testFlagMap1 = new TreeMap<>();
        TreeMap<String, ArrayList<String>> testFlagMap2 = new TreeMap<>();
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
        testFlagMap2.put("-s", sortOption);
        assertThrows(InvalidFormatCommandException.class, () -> {
            profileCommands.clearPreference(testFlagMap2, "-s");
        });
    }


}
