package Tasks;

import java.util.ArrayList;
import java.util.Date;

public class TentativeEvent extends Task {
    public ArrayList<Date> tentativeoptions ;
    public ArrayList<String> tentativetimes;

    public TentativeEvent(String description, ArrayList<Date> tentativeoptions,ArrayList<String> tentativetimes) {
        super(description);
        this.tentativeoptions = tentativeoptions;
        this.tentativetimes = tentativetimes;
    }

    //    @Override
//    public String toString() {
//        return "E"+ "|" + super.getStatusIcon() + "| " + super.description + "|" + "at: "+at;
//    }
    public StringBuilder showTentative(){
        StringBuilder showlist = new StringBuilder();
        showlist.append("Tentative time slots: \n");
        for (int i = 0; i < tentativeoptions.size(); i++) {
            showlist.append((i+1)+ ". "+tentativeoptions.get(i)+"\n");
        }
        return showlist;
    }

    @Override
    public String toString() {
        String timeslots = "P"+ " | " + super.getStatusIcon() + " | " + super.description + " | ";

        return timeslots;
    }

    @Override
    public String listFormat(){
        String timeslots = "[TE]"+ "[" + super.getStatusIcon() + "] "+description+"\n" ;
        for (int i = 0; i < tentativetimes.size(); i++) {
            if(i==0){
                timeslots += "at "+tentativetimes.get(i)+"\n";
            }else{
                timeslots += "or "+tentativetimes.get(i)+"\n";
            }
        }
        return timeslots;
    }
}