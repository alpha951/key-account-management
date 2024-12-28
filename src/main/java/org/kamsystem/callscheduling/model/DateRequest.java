package org.kamsystem.callscheduling.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DateRequest {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
