package duke.components;

import java.util.ArrayList;

public class VerseList {

    private ArrayList<Group> verseList = new ArrayList<>();

    public void add(Group group){
        this.verseList.add(group);
    }

    public Group find(String name){
        int size = verseList.size();
        for(int i = 0; i < size; i++){
            if(verseList.get(i).getName().equals(name)){
                return verseList.get(i);
            }
        }
        return null;
    }

}
