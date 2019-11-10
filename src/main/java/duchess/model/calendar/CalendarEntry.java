package duchess.model.calendar;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import duchess.model.task.Task;

import java.time.LocalDate;
import java.util.List;

public class CalendarEntry {

    private LocalDate date;
    private List<Task> dateTasks;

    @JsonCreator
    public CalendarEntry(@JsonProperty("date") LocalDate date, @JsonProperty("dateTasks") List<Task> dateTasks) {
        this.date = date;
        this.dateTasks = dateTasks;
    }

    public boolean isRequestedEntry(LocalDate enquiry) {
        return this.date.equals(enquiry);
    }

    @JsonSetter("date")
    @JsonSerialize(using = LocalDateSerializer.class)
    public LocalDate getDate() {
        return this.date;
    }

    @JsonGetter("date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    public void setDate(LocalDate date) {
        this.date = date;
    }

    @JsonSetter("dateTasks")
    public void setDateTasks(List<Task> dateTasks) {
        this.dateTasks = dateTasks;
    }

    @JsonGetter("dateTasks")
    public List<Task> getDateTasks() {
        return this.dateTasks;
    }
}
