package org.kamsystem.callschedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kamsystem.callscheduling.model.CallSchedule;
import org.kamsystem.callscheduling.repository.ICallScheduleRepository;
import org.kamsystem.callscheduling.service.CallScheduleService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class CallScheduleServiceTest {

    @Mock
    private ICallScheduleRepository callScheduleRepository;

    @InjectMocks
    private CallScheduleService callScheduleService;

    @Test
    @DisplayName("Should create call schedule successfully")
    void createCallScheduleSuccessfully() {
        // Given
        Set<DayOfWeek> weeklyDays = EnumSet.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY);
        CallSchedule mockCallSchedule = getCallSchedule(weeklyDays);

        doNothing().when(callScheduleRepository).createCallSchedule(mockCallSchedule);

        // When
        callScheduleService.createCallSchedule(mockCallSchedule);

        // Then
        verify(callScheduleRepository).createCallSchedule(mockCallSchedule);
    }

    private static CallSchedule getCallSchedule(Set<DayOfWeek> weeklyDays) {
        CallSchedule mockCallSchedule = new CallSchedule();
        mockCallSchedule.setLeadId(1L);
        mockCallSchedule.setRecurrenceType(CallSchedule.RecurrenceType.WEEKLY);
        mockCallSchedule.setPreferredTime(LocalTime.parse("14:00:00"));
        mockCallSchedule.setStartDate(LocalDateTime.parse("2024-12-30T14:00:00"));
        mockCallSchedule.setEndDate(LocalDateTime.parse("2024-12-30T15:00:00"));
        mockCallSchedule.setWeeklyDays(weeklyDays);
        mockCallSchedule.setTimeZone("Asia/Kolkata");
        mockCallSchedule.setActive(true);
        return mockCallSchedule;
    }

    @Test
    @DisplayName("Should fetch call schedule by ID")
    void getCallScheduleByIdSuccessfully() {
        // Given
        Long callScheduleId = 1L;
        Set<DayOfWeek> weeklyDays = EnumSet.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY);
        CallSchedule mockCallSchedule = getCallSchedule(callScheduleId, weeklyDays);

        when(callScheduleRepository.getCallScheduleById(callScheduleId)).thenReturn(mockCallSchedule);

        // When
        CallSchedule fetchedCallSchedule = callScheduleService.getCallScheduleById(callScheduleId);

        // Then
        assertEquals(mockCallSchedule, fetchedCallSchedule);
        verify(callScheduleRepository).getCallScheduleById(callScheduleId);
    }

    private static CallSchedule getCallSchedule(Long callScheduleId, Set<DayOfWeek> weeklyDays) {
        CallSchedule mockCallSchedule = new CallSchedule();
        mockCallSchedule.setId(callScheduleId);
        mockCallSchedule.setLeadId(1L);
        mockCallSchedule.setRecurrenceType(CallSchedule.RecurrenceType.WEEKLY);
        mockCallSchedule.setPreferredTime(LocalTime.parse("14:00:00"));
        mockCallSchedule.setStartDate(LocalDateTime.parse("2024-12-30T14:00:00"));
        mockCallSchedule.setEndDate(LocalDateTime.parse("2024-12-30T15:00:00"));
        mockCallSchedule.setWeeklyDays(weeklyDays);
        mockCallSchedule.setTimeZone("Asia/Kolkata");
        mockCallSchedule.setActive(true);
        return mockCallSchedule;
    }

    @Test
    @DisplayName("Should fetch call schedules by lead ID")
    void getCallSchedulesByLeadIdSuccessfully() {
        // Given
        Long leadId = 1L;
        Set<DayOfWeek> weeklyDays = EnumSet.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY);
        List<CallSchedule> mockCallSchedules = List.of(
            new CallSchedule(1L, leadId, CallSchedule.RecurrenceType.WEEKLY, LocalTime.parse("14:00:00"),
                LocalDateTime.parse("2024-12-30T14:00:00"), LocalDateTime.parse("2024-12-30T15:00:00"),
                weeklyDays, "Asia/Kolkata", true, new Date(), new Date())
        );

        when(callScheduleRepository.getCallSchedulesByLeadId(leadId)).thenReturn(mockCallSchedules);

        // When
        List<CallSchedule> fetchedCallSchedules = callScheduleService.getCallScheduleByLeadId(leadId);

        // Then
        assertEquals(mockCallSchedules, fetchedCallSchedules);
        verify(callScheduleRepository).getCallSchedulesByLeadId(leadId);
    }

    @Test
    @DisplayName("Should fetch call schedules by date")
    void getCallSchedulesByDateSuccessfully() {
        // Given
        LocalDate date = LocalDate.parse("2024-12-30");
        Set<DayOfWeek> weeklyDays = EnumSet.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY);
        List<CallSchedule> mockCallSchedules = List.of(
            new CallSchedule(1L, 1L, CallSchedule.RecurrenceType.WEEKLY, LocalTime.parse("14:00:00"),
                LocalDateTime.parse("2024-12-30T14:00:00"), LocalDateTime.parse("2024-12-30T15:00:00"),
                weeklyDays, "Asia/Kolkata", true, new Date(), new Date())
        );

        when(callScheduleRepository.getCallSchedulesByDate(date)).thenReturn(mockCallSchedules);

        // When
        List<CallSchedule> fetchedCallSchedules = callScheduleService.getAllCallSchedulesByDate(date);

        // Then
        assertEquals(mockCallSchedules, fetchedCallSchedules);
        verify(callScheduleRepository).getCallSchedulesByDate(date);
    }

    @Test
    @DisplayName("Should update last call date")
    void updateLastCallDateSuccessfully() {
        // Given
        Long callScheduleId = 1L;
        LocalDate lastCallDate = LocalDate.now();

        doNothing().when(callScheduleRepository).updateLastCallDate(callScheduleId, lastCallDate);

        // When
        callScheduleService.updateLastCallDate(callScheduleId, lastCallDate);

        // Then
        verify(callScheduleRepository).updateLastCallDate(callScheduleId, lastCallDate);
    }
}
