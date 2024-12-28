package org.kamsystem.callscheduling.api;

import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.kamsystem.callscheduling.model.CallSchedule;
import org.kamsystem.callscheduling.model.DateRequest;
import org.kamsystem.callscheduling.service.ICallScheduleService;
import org.kamsystem.common.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/call-schedule")
@AllArgsConstructor
public class CallScheduleController {

    private final ICallScheduleService callScheduleService;

    @PostMapping("/create")
    public ResponseEntity<?> createSchedule(@RequestBody @Valid CallSchedule schedule,
        BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(new ApiResponse<>(false,
                result.getAllErrors()), HttpStatus.BAD_REQUEST);
        }
        callScheduleService.createCallSchedule(schedule);
        return new ResponseEntity<>(new ApiResponse<>(true, "Call schedule created successfully"),
            HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getScheduleById(@RequestParam Long scheduleId) {
        CallSchedule schedule = callScheduleService.getCallScheduleById(scheduleId);
        return new ResponseEntity<>(new ApiResponse<>(true, schedule),
            HttpStatus.OK);
    }

    @GetMapping("get-by-lead")
    public ResponseEntity<?> getScheduleByLead(@RequestParam Long leadId) {
        List<CallSchedule> schedules = callScheduleService.getCallScheduleByLeadId(leadId);
        return new ResponseEntity<>(new ApiResponse<>(true, schedules),
            HttpStatus.OK);
    }

    @PostMapping("/get-by-day")
    public ResponseEntity<?> getScheduleByDay(@RequestBody DateRequest request) {
        List<CallSchedule> schedules = callScheduleService.getAllCallSchedulesByDate(request.getDate());
        return new ResponseEntity<>(new ApiResponse<>(true, schedules),
            HttpStatus.OK);
    }
}
