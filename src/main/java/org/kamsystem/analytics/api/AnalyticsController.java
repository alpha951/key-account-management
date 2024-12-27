package org.kamsystem.analytics.api;

import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.kamsystem.analytics.dao.KamMetric;
import org.kamsystem.analytics.model.MetricRequest;
import org.kamsystem.analytics.service.MetricService;
import org.kamsystem.common.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics")
@AllArgsConstructor
public class AnalyticsController {

    private final MetricService metricService;

    @PostMapping("/get-metrics")
    public ResponseEntity<?> getMetric(@RequestBody @Valid MetricRequest request, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(new ApiResponse<>(false, result.getAllErrors()),
                HttpStatus.BAD_REQUEST);
        }
        List<KamMetric> metrics = metricService.getMetrics(request.getMetric(), request.getTimeframe());
        return new ResponseEntity<>(new ApiResponse<>(true, metrics), HttpStatus.OK);
    }
}
