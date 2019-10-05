package EPstorage;

import java.util.ArrayList;

public class UserProfile {
    private String userName;
    private int userAge;
    private ArrayList<Integer> genreId;

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

    public void addGenreId(ArrayList<Integer> inputGenre) {
        genreId.addAll(inputGenre);
    }

    public void removeGenreId(ArrayList<Integer> inputGenre) {
        genreId.removeAll(inputGenre);
    }
}