package org.kamsystem.callscheduling.service;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import org.kamsystem.callscheduling.model.CallSchedule;
import org.kamsystem.callscheduling.repository.ICallScheduleRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CallScheduleService implements ICallScheduleService {

    private final ICallScheduleRepository callScheduleRepository;

    @Override
    public void createCallSchedule(CallSchedule callSchedule) {
        callScheduleRepository.createCallSchedule(callSchedule);
    }

    @Override
    public CallSchedule getCallScheduleById(Long callScheduleId) {
        return callScheduleRepository.getCallScheduleById(callScheduleId);
    }

    @Override
    public List<CallSchedule> getCallScheduleByLeadId(Long leadId) {
        return callScheduleRepository.getCallSchedulesByLeadId(leadId);
    }

    @Override
    public List<CallSchedule> getAllCallSchedulesByDate(LocalDate date) {
        return callScheduleRepository.getCallSchedulesByDate(date);
    }
}
