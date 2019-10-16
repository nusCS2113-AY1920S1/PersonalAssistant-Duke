package sort;

import EPstorage.UserProfile;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

public class EditSortProfileJson {
    private static ObjectMapper mapper = new ObjectMapper();
    private File file;
    private static InputStream inputStream;
    private TypeReference<SortProfile> typeReference = new TypeReference<SortProfile>() {
    };


    public EditSortProfileJson() throws FileNotFoundException {
        file = new File("data/userSortDetails.json");
        this.inputStream = new FileInputStream(file);
    }

    public static void update(SortProfile sortProfile) {
        File newFile = new File("data/userSortDetails.json");
        try {
            mapper.writeValue(newFile, sortProfile);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SortProfile load() throws IOException {
        return mapper.readValue(inputStream, typeReference);
    }


}
