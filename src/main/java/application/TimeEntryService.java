package application;

import domain.TimeEntry;
import java.time.Duration;

import java.time.LocalDateTime;
import java.util.UUID;

public class TimeEntryService {
    public TimeEntry createTimeEntry(Duration duration, LocalDateTime startTime, LocalDateTime endTime){
        TimeEntry timeEntry = new TimeEntry(
                UUID.randomUUID(),
                duration,
                startTime,
                endTime

        );
        return timeEntry;
    }

}
