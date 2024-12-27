package org.kamsystem.callscheduling.repository;

import java.time.LocalDate;
import java.util.List;
import org.kamsystem.callscheduling.model.CallSchedule;

public interface ICallScheduleRepository {

    void createCallSchedule(CallSchedule callSchedule);

    CallSchedule getCallScheduleById(long callScheduleId);

    List<CallSchedule> getCallSchedulesByLeadId(long leadId);

    List<CallSchedule> getCallSchedulesByDate(LocalDate date);

    void updateLastCallDate(long callScheduleId, LocalDate lastCallDate);
}
