package Events.Storage;

public class EventDetails {

    protected String venue;
    protected String performers;
    protected String teachersInCharge;
    protected String piecesToPractice;

    public EventDetails(String details) {
        String[] splitDetails = details.split("/");
        this.venue = splitDetails[0];
        this.teachersInCharge = splitDetails[1];
        this.piecesToPractice = splitDetails[2];
        this.performers = splitDetails[3];
    }

    public String getEventDetails() {
        String details = "Venue: " + venue + "\n" + "Teachers/Assessors: " + teachersInCharge + "\n" + "Pieces to practice: " + piecesToPractice + "\n" + "Performers: " + performers;
        return details;
    }
}
