package domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Catalog {
    private UUID id;
    private String name;
    private LocalDateTime time;
    private List<Direction> directions;


    public void rename(String name) {
        this.name = name;
    }

    public void removeDirection(Direction direction){
        directions.remove(direction);
    }

    public void addDirection(Direction direction){
        directions.add(direction);
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



    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public List<Direction> getDirections() {
        return directions;
    }

    public void setDirections(List<Direction> directions) {
        this.directions = directions;
    }

    public Catalog(UUID id, String name, LocalDateTime time, List<Direction> directions) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.directions = directions;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Catalog catalog = (Catalog) o;
        return Objects.equals(id, catalog.id) && Objects.equals(name, catalog.name) && Objects.equals(time, catalog.time) && Objects.equals(directions, catalog.directions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, time, directions);
    }
}
