package duke.model.events;

import duke.model.locations.Venue;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;

public class BindableEvent {
    private SimpleDoubleProperty latitude = new SimpleDoubleProperty(this, "latitude");
    private SimpleDoubleProperty longitude = new SimpleDoubleProperty(this, "longitude");
    private SimpleStringProperty address = new SimpleStringProperty(this, "address");
    private SimpleObjectProperty<LocalDateTime> startDate = new SimpleObjectProperty<>(this, "startDate");
    private SimpleObjectProperty<LocalDateTime> endDate = new SimpleObjectProperty<>(this, "endDate");
    private SimpleBooleanProperty isDone = new SimpleBooleanProperty(this, "isDone");

    public BindableEvent(Event event) {
        latitude.setValue(event.getLocation().getLatitude());
        longitude.setValue(event.getLocation().getLongitude());
        address.setValue(event.getDescription());
        startDate.setValue(event.getStartDate());
        endDate.setValue(event.getEndingDate());
        isDone.setValue(event.isDone());
    }

    public Event getEvent() {
        Venue v = new Venue(getAddress(), getLatitude(), getLongitude(), 0, 0);
        Event e = new Event(getAddress(), getStartDate(), getEndDate(), v);
        e.setDone(isDone());
        return e;
    }

    public boolean isDone() {
        return isDone.get();
    }

    public SimpleBooleanProperty isDoneProperty() {
        return isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone.set(isDone);
    }

    public double getLatitude() {
        return latitude.get();
    }

    public SimpleDoubleProperty latitudeProperty() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude.set(latitude);
    }

    public double getLongitude() {
        return longitude.get();
    }

    public SimpleDoubleProperty longitudeProperty() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude.set(longitude);
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public LocalDateTime getStartDate() {
        return startDate.get();
    }

    public SimpleObjectProperty<LocalDateTime> startDateProperty() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate.set(startDate);
    }

    public LocalDateTime getEndDate() {
        return endDate.get();
    }

    public SimpleObjectProperty<LocalDateTime> endDateProperty() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate.set(endDate);
    }
}
