package org.kamsystem.callscheduling.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CallSchedule {

    private long id;
    @NonNull
    private Long leadId;

    private RecurrenceType recurrenceType;
    private LocalTime preferredTime;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Set<DayOfWeek> weeklyDays;  // For weekly recurrence like Monday, Wednesday, Friday etc.
    private Integer dayOfMonth;
    private Integer customDayInterval;
    private LocalDateTime lastCallDate;
    private LocalDateTime nextCallDate;
    private String timeZone;
    private boolean active;
    private Date createdDate;
    private Date updatedDate;

    public CallSchedule(long l, Long leadId, RecurrenceType recurrenceType, LocalTime parse, LocalDateTime parse1,
        LocalDateTime parse2, Set<DayOfWeek> weeklyDays, String s, boolean b, Date date,
        Date date1) {
    }

    public enum RecurrenceType {
        DAILY,
        WEEKLY,
        MONTHLY,
        CUSTOM_DAYS
    }
}
