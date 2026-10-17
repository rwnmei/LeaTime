package domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Direction {
    private UUID id;
    private String name;
    private List<TimeEntry> timeEntries;

    public Direction() {
    }

    public Direction(UUID id, String name,List<TimeEntry> timeEntry) {
        this.id = id;
        this.name = name;
        this.timeEntries = timeEntry;
    }

    public void rename(String name) {
        this.name = name;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TimeEntry> getTimeEntries() {
        return timeEntries;
    }
    @Override
    public String toString() {
        return name;
    }
    public void setTimeEntries(List<TimeEntry> timeEntries) {
        this.timeEntries = timeEntries;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Direction direction = (Direction) o;
        return Objects.equals(id, direction.id) && Objects.equals(name, direction.name) && Objects.equals(timeEntries, direction.timeEntries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, timeEntries);
    }
}
