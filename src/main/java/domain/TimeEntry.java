package domain;


import java.time.Duration;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class TimeEntry {
    private UUID id;
    private Duration duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public TimeEntry() {
    }

    public TimeEntry(UUID id, Duration duration, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.duration = duration;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TimeEntry timeEntry = (TimeEntry) o;
        return Objects.equals(id, timeEntry.id) && Objects.equals(duration, timeEntry.duration) && Objects.equals(startTime, timeEntry.startTime) && Objects.equals(endTime, timeEntry.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, duration, startTime, endTime);
    }
}
