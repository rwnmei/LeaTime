package application;

import domain.Direction;
import domain.TimeEntry;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;

public class StatisticService {
    public int[] getTimeByHoursForDay(Direction direction, LocalDate date) {
        int[] result = new int[24];
        for (TimeEntry entry : direction.getTimeEntries()) {
            System.out.println(entry.getStartTime());
            System.out.println(date);
            if (entry.getStartTime().toLocalDate().equals(date)) {
                LocalDateTime endTime = entry.getEndTime();
                LocalDateTime current = entry.getStartTime();
                while (current.isBefore(endTime)) {
                    LocalDateTime nextHour = current
                            .plusHours(1)
                            .withMinute(0)
                            .withSecond(0)
                            .withNano(0);
                    if (endTime.isBefore(nextHour)) {
                        nextHour = endTime;
                    }
                    Duration duration = Duration.between(current, nextHour);
                    int minutes = (int) duration.toMinutes();
                    result[current.getHour()] += minutes;
                    current = nextHour;
                }
            }
        }

        return result;
    }

    public double[] getTimeResultForWeek(Direction direction, LocalDate date) {
        double[] result = new double[7];
        DayOfWeek weekDay = date.getDayOfWeek();
        int dayNumber = weekDay.getValue();
        LocalDate startOfWeek = date.minusDays(dayNumber - 1);
        for (int day = 0; day < 7; day++) {
            LocalDate currentDay = startOfWeek.plusDays(day);
            Duration duration = Duration.ZERO;
            for (TimeEntry entry : direction.getTimeEntries()) {
                if (entry.getStartTime().toLocalDate().equals(currentDay)) {
                    duration = duration.plus(entry.getDuration());
                }
            }
            double hours = duration.toMinutes() / 60.0;
            result[day] = hours;
        }
        return result;
    }

    public double[] getTimeResultForMonth(Direction direction,LocalDate date){
        int daysInMonth = date.lengthOfMonth();
        double[] result = new double[daysInMonth];
        LocalDate startOfMonth = date.withDayOfMonth(1);
        for (int day = 0; day < daysInMonth; day++) {
            LocalDate currentDay = startOfMonth.plusDays(day);
            Duration duration = Duration.ZERO;
            for (TimeEntry entry : direction.getTimeEntries()) {
                if (entry.getStartTime().toLocalDate().equals(currentDay)) {
                    duration = duration.plus(entry.getDuration());
                }
            }
            double hours = duration.toMinutes() / 60.0;
            result[day] = hours;
        }
        return result;
    }
     public double[] getTimeResultForYear(Direction direction,LocalDate date){
         double[] result = new double[12];
         int currentYear = date.getYear();
         LocalDate startOfYear = date.withDayOfYear(1);
         for (int i = 0; i < 12; i++) {
             LocalDate currentMonth = startOfYear.plusMonths(i);
             Duration duration = Duration.ZERO;
             for(TimeEntry entry: direction.getTimeEntries()){
                 if (entry.getStartTime().getYear() == currentYear
                         && entry.getStartTime().getMonth() == currentMonth.getMonth()) {
                        duration = duration.plus(entry.getDuration());
                 }
             }
             double hours = duration.toMinutes()/60.0;
             result[i] = hours;

         }
         return result;
     }
}




