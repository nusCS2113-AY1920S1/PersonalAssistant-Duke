package duke.model.planning;

import duke.model.lists.DayList;
import duke.model.locations.Venue;

import java.time.LocalDateTime;

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
}
