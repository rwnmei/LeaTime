package domain;

import application.Period;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Direction {
    private UUID id;
    private String name;
    private Catalog catalog;
    private Map<LocalDateTime, TimeEntry> timeEntry;

    public Direction(UUID id, String name, Catalog catalog, Map<LocalDateTime, TimeEntry> timeEntry) {
        this.id = id;
        this.name = name;
        this.catalog = catalog;
        this.timeEntry = timeEntry;
    }

    public void rename(String name) {
        this.name = name;
    }

    public void getResults(Period period) {

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


    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public Map<LocalDateTime, TimeEntry> getTimeEntry() {
        return timeEntry;
    }

    public void setTimeEntry(Map<LocalDateTime, TimeEntry> timeEntry) {
        this.timeEntry = timeEntry;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Direction direction = (Direction) o;
        return Objects.equals(id, direction.id) && Objects.equals(name, direction.name) && Objects.equals(catalog, direction.catalog) && Objects.equals(timeEntry, direction.timeEntry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, catalog, timeEntry);
    }
}
