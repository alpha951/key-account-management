package org.kamsystem.callscheduling.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.kamsystem.callscheduling.model.CallSchedule;
import org.kamsystem.callscheduling.model.CallSchedule.RecurrenceType;
import org.kamsystem.common.utils.JsonUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CallScheduleRepository implements ICallScheduleRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String INSERT_CALL_SCHEDULE_QUERY = """
        INSERT INTO call_schedule (
            lead_id, recurrence_type, preferred_time, start_date, end_date,
            weekly_days, day_of_month, custom_day_interval, last_call_date,
            next_call_date, time_zone, is_active
        ) VALUES (
            :leadId, :recurrenceType, :preferredTime, :startDate, :endDate,
            :weeklyDays::jsonb, :dayOfMonth, :customDayInterval, :lastCallDate,
            :nextCallDate, :timeZone, :isActive
        ) RETURNING id
        """;

    private static final String GET_CALL_SCHEDULE_BY_ID = """
        SELECT id, lead_id, recurrence_type, preferred_time, start_date, end_date,
               weekly_days, day_of_month, custom_day_interval, last_call_date,
               next_call_date, time_zone, created_at, updated_at, is_active
        FROM call_schedule
        WHERE id = :id
        """;

    private static final String GET_CALL_SCHEDULES_BY_DATE = """
        SELECT id, lead_id, recurrence_type, preferred_time, start_date, end_date,
               weekly_days, day_of_month, custom_day_interval, last_call_date,
               next_call_date, time_zone, created_at, updated_at, is_active
        FROM call_schedule
        WHERE is_active = true
        AND (
            (recurrence_type = 'WEEKLY' AND weekly_days ? :dayOfWeek)
            OR (recurrence_type = 'DAILY')
            OR (recurrence_type = 'MONTHLY' AND day_of_month = :dayOfMonth)
            OR (recurrence_type = 'CUSTOM_DAYS'
                AND (CURRENT_DATE - last_call_date::date) = custom_day_interval)
        )
        ORDER BY preferred_time
        """;

    private static final String GET_CALL_SCHEDULE_BY_LEAD_ID = """
        SELECT id, lead_id, recurrence_type, preferred_time, start_date, end_date,
               weekly_days, day_of_month, custom_day_interval, last_call_date,
               next_call_date, time_zone, created_at, updated_at, is_active
        FROM call_schedule
        WHERE lead_id = :leadId
        """;

    private static final String UPDATE_LAST_CALL_DATE = """
        UPDATE call_schedule
        SET last_call_date = :lastCallDate
        WHERE id = :id
        """;


    @Override
    public void createCallSchedule(CallSchedule schedule) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("leadId", schedule.getLeadId());
        params.addValue("recurrenceType", schedule.getRecurrenceType());
        params.addValue("preferredTime", Time.valueOf(schedule.getPreferredTime()));
        params.addValue("startDate", Timestamp.valueOf(schedule.getStartDate()));
        params.addValue("endDate", schedule.getEndDate() != null ? Timestamp.valueOf(schedule.getEndDate()) : null);
        params.addValue("weeklyDays",
            schedule.getWeeklyDays() != null ? JsonUtils.getJson(schedule.getWeeklyDays()) : null);
        params.addValue("dayOfMonth", schedule.getDayOfMonth());
        params.addValue("customDayInterval", schedule.getCustomDayInterval());
        params.addValue("lastCallDate",
            schedule.getLastCallDate() != null ? Timestamp.valueOf(schedule.getLastCallDate()) : null);
        params.addValue("nextCallDate",
            schedule.getNextCallDate() != null ? Timestamp.valueOf(schedule.getNextCallDate()) : null);
        params.addValue("timeZone", schedule.getTimeZone());
        params.addValue("isActive", schedule.isActive());

        namedParameterJdbcTemplate.update(INSERT_CALL_SCHEDULE_QUERY, params);
    }

    @Override
    public CallSchedule getCallScheduleById(long callScheduleId) {
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", callScheduleId);

        return namedParameterJdbcTemplate.queryForObject(GET_CALL_SCHEDULE_BY_ID, params, rowMapper);
    }

    @Override
    public List<CallSchedule> getCallSchedulesByLeadId(long leadId) {
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("leadId", leadId);
        return namedParameterJdbcTemplate.query(GET_CALL_SCHEDULE_BY_LEAD_ID, params, rowMapper);
    }

    @Override
    public List<CallSchedule> getCallSchedulesByDate(LocalDate date) {
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("dayOfWeek",
            String.valueOf(date.getDayOfWeek().getValue())).addValue("dayOfMonth", date.getDayOfMonth());
        return namedParameterJdbcTemplate.query(GET_CALL_SCHEDULES_BY_DATE, params, rowMapper);
    }

    @Override
    public void updateLastCallDate(long callScheduleId, LocalDate lastCallDate) {
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("id", callScheduleId)
            .addValue("lastCallDate", Timestamp.valueOf(lastCallDate.atStartOfDay()));
        namedParameterJdbcTemplate.update(UPDATE_LAST_CALL_DATE, params);
    }

    private final RowMapper<CallSchedule> rowMapper = (rs, rowNum) -> {
        CallSchedule schedule = new CallSchedule();
        schedule.setId(rs.getLong("id"));
        schedule.setLeadId(rs.getLong("lead_id"));
        schedule.setRecurrenceType(RecurrenceType.valueOf(rs.getString("recurrence_type")));
        schedule.setPreferredTime(rs.getTime("preferred_time").toLocalTime());
        schedule.setStartDate(rs.getTimestamp("start_date").toLocalDateTime());

        Timestamp endDate = rs.getTimestamp("end_date");
        if (endDate != null) {
            schedule.setEndDate(endDate.toLocalDateTime());
        }

        String weeklyDaysJson = rs.getString("weekly_days");
        if (weeklyDaysJson != null) {
            schedule.setWeeklyDays(getWeeklyDays(weeklyDaysJson));
        }

        schedule.setDayOfMonth(rs.getObject("day_of_month", Integer.class));
        schedule.setCustomDayInterval(rs.getObject("custom_day_interval", Integer.class));

        Timestamp lastCallDate = rs.getTimestamp("last_call_date");
        if (lastCallDate != null) {
            schedule.setLastCallDate(lastCallDate.toLocalDateTime());
        }

        Timestamp nextCallDate = rs.getTimestamp("next_call_date");
        if (nextCallDate != null) {
            schedule.setNextCallDate(nextCallDate.toLocalDateTime());
        }

        schedule.setTimeZone(rs.getString("time_zone"));
        schedule.setCreatedDate(rs.getTimestamp("created_at"));
        schedule.setUpdatedDate(rs.getTimestamp("updated_at"));
        schedule.setActive(rs.getBoolean("is_active"));

        return schedule;
    };

    private Set<DayOfWeek> getWeeklyDays(String weeklyDaysJson) {
        try {
            return JsonUtils.getObject(weeklyDaysJson, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new RuntimeException("Error parsing weekly_days JSON", e);
        }
    }
}
