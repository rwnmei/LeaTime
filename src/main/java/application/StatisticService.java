package application;

import domain.Direction;
import domain.TimeEntry;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class StatisticService {
    public Duration getTimeResultForDay(Direction direction, LocalDate date){
        Duration result = Duration.ZERO;
                for(TimeEntry entry: direction.getTimeEntries()){
                    if (entry.getStartTime().toLocalDate().equals(date)){
                        result = result.plus(entry.getDuration());
                    }
                }
        return result;
    }
    public int[] getTimeByHoursForDay(Direction direction,LocalDate date){
        int[] result =new int[24];
        for (TimeEntry entry: direction.getTimeEntries()){
            System.out.println(entry.getStartTime());
            System.out.println(date);
            if (entry.getStartTime().toLocalDate().equals(date)){
                LocalDateTime endTime = entry.getEndTime();
                LocalDateTime current = entry.getStartTime();
                while(current.isBefore(endTime)){
                    LocalDateTime nextHour = current
                            .plusHours(1)
                            .withMinute(0)
                            .withSecond(0)
                            .withNano(0);
                    if (endTime.isBefore(nextHour)){
                        nextHour = endTime;
                    }
                    Duration duration = Duration.between(current,nextHour);
                    int minutes = (int) duration.toMinutes();
                    result[current.getHour()] += minutes;
                    current = nextHour;
                }
            }
        }

        return result;
    }

}
