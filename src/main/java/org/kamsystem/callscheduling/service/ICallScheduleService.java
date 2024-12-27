package org.kamsystem.callscheduling.service;

import java.time.LocalDate;
import java.util.List;
import org.kamsystem.callscheduling.model.CallSchedule;

public interface ICallScheduleService {

    void createCallSchedule(CallSchedule callSchedule);

    CallSchedule getCallScheduleById(Long callScheduleId);

    List<CallSchedule> getCallScheduleByLeadId(Long leadId);

    List<CallSchedule> getAllCallSchedulesByDate(LocalDate date);
}
