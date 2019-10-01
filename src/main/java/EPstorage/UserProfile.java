package EPstorage;

import java.util.ArrayList;

public class UserProfile {
    private String userName;
    private int userAge;
    private ArrayList<Integer> genreId;

//    public UserProfile(String userName, int userAge, ArrayList<String> userGenre){
//        this.userName = userName;
//        this.userAge = userAge;
//        this.userGenre = userGenre;
//    }

    public String getUserName(){
        return userName;
    }

    public int getUserAge(){
        return userAge;
    }

    public ArrayList<Integer> getGenreId() {
        return genreId;
    }

    public void setUserName(String inputName) {
        userName = inputName;
    }

    public void setUserAge(int inputAge) {
        userAge = inputAge;
    }

    public void setGenreId(ArrayList<Integer> inputGenre) {
        genreId = inputGenre;
    }
}
