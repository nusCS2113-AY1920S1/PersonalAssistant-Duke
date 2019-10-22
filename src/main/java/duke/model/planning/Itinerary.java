package duke.model.planning;

import duke.model.lists.DayList;
import duke.model.locations.Venue;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Itinerary extends DayList {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Venue hotelLocation;

    public Itinerary(LocalDateTime startDate, LocalDateTime endDate, Venue hotelLocation){
        super();
        this.startDate = startDate;
        this.endDate = endDate;
        this.hotelLocation = hotelLocation;
    }

    public LocalDateTime getStartDate(){
        return startDate;
    }

    public LocalDateTime getEndDate(){
        return endDate;
    }

    public Venue getHotelLocation(){
        return hotelLocation;
    }

    public int getNumberOfDays(){
        LocalDateTime tempDateTime = LocalDateTime.from( startDate );
        long days = tempDateTime.until( endDate, ChronoUnit.DAYS);
        return Integer.parseInt(String.valueOf(days));
    }

    public StringBuilder printItinerary(){

        int days = this.getNumberOfDays();

        StringBuilder result = new StringBuilder("Here are the list of Recommended Locations in "
                +  days + " days around " + this.getHotelLocation().getAddress() + ": \n");

        for (int i = 0; i<days; i++){
            Day list1 = this.getList().get(i);
            result.append("\n");
            result.append("Day ").append(list1.getNumber()).append(":").append("\n \n");
            result.append("Venues: ").append("\n");
            for(Venue venue : list1.getVenueList()){
                result.append(venue.getAddress()).append("\n");
            }
            result.append("\n");
            result.append("Todo List: ").append("\n");
            for(Todo todo : list1.getTodoList()){
                result.append(" - ").append(todo.getDescription()).append("\n");
            }
        }
        return result;
    }
}
