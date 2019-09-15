package Tasks;

import java.util.ArrayList;

public class EventsList extends TaskList{
    private static ArrayList<Events> eventsList;
    public EventsList(){
        eventsList = new ArrayList<>();
    }

    public Task getTask(int index){
        return eventsList.get(index);
    }

    public ArrayList<Events> getEventsList(){ return eventsList; }

    public void addEvent(Events e){ eventsList.add(e);}

    public void removeEvent(Events e){
        eventsList.remove(e);
    }

    @Override
    public int lengthOfList(){return super.lengthOfList();}

    public Boolean ScheduleClashes(Events e) {
        return eventsList.contains(e);
    }
}
