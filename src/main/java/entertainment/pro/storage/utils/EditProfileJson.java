package entertainment.pro.storage.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entertainment.pro.model.UserProfile;

import java.io.*;

/**
 * class that deals with editing the UserProfile.json file
 */
public class EditProfileJson {
    private ObjectMapper mapper = new ObjectMapper();
    private File file;
    private InputStream inputStream;
    private TypeReference<UserProfile> typeReference = new TypeReference<UserProfile>() {
    };


    public EditProfileJson() throws IOException {
        file = new File("EPdata/userProfile.json");
        if (file.exists()) {
            this.inputStream = new FileInputStream(file);
        } else {
//            if (!file.getParentFile().exists()) {
//                file.getParentFile().mkdirs();
//            }
            file.createNewFile();
            UserProfile userProfile = new UserProfile();
            mapper.writeValue(file, userProfile);
//            FileWriter fileWriter = new FileWriter(file);
//            fileWriter.write("{\"userName\":\"*undefined*\",\"userAge\":0,\"genreIdPreference\":[],\"genreIdRestriction\":[],\"adult\":true,\"playlistNames\":[],\"sortByAlphabetical\":false,\"sortByLatestRelease\":false,\"sortByHighestRAting\":false}");
            this.inputStream = new FileInputStream(file);
        }
    }

    public UserProfile load() throws IOException {
        return mapper.readValue(inputStream, typeReference);
    }

    /**
     * update json file with any changes made to user profile
     */
    public void updateProfile(UserProfile userProfile) throws IOException {
        mapper.writeValue(file, userProfile);
    }
}
