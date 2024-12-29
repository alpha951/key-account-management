package org.kamsystem.restaurantpoc.api;

import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.kamsystem.common.exception.InvalidRequestBodyException;
import org.kamsystem.common.model.ApiResponse;
import org.kamsystem.restaurantpoc.model.Poc;
import org.kamsystem.restaurantpoc.model.PocUpdateRequest;
import org.kamsystem.restaurantpoc.service.IPocService;
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
@AllArgsConstructor
@RequestMapping("/api/poc")
public class PocController {

    private final IPocService pocService;

    @PostMapping("/create")
    public ResponseEntity<?> createPoc(@RequestBody @Valid Poc poc, BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidRequestBodyException(result);
        }

        pocService.createPoc(poc);
        return new ResponseEntity<>(new ApiResponse<>(true, "Poc created successfully"), HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updatePoc(@RequestBody @Valid PocUpdateRequest request, BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidRequestBodyException(result);
        }

        pocService.updatePoc(request);
        return new ResponseEntity<>(new ApiResponse<>(true, "Poc updated successfully"), HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getPocById(@RequestParam Long pocId) {
        Poc poc = pocService.getPocById(pocId);
        return new ResponseEntity<>(new ApiResponse<>(true, poc), HttpStatus.OK);
    }

    @GetMapping("/get/restaurant")
    public ResponseEntity<?> getPocsByRestaurant(@RequestParam Long restaurantId) {
        List<Poc> pocs = pocService.getPocByRestaurant(restaurantId);
        return new ResponseEntity<>(new ApiResponse<>(true, pocs),
            HttpStatus.OK);
    }
}
