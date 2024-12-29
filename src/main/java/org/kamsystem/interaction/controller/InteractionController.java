package org.kamsystem.interaction.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.kamsystem.common.exception.InvalidRequestBodyException;
import org.kamsystem.common.model.ApiResponse;
import org.kamsystem.interaction.model.Interaction;
import org.kamsystem.interaction.service.IInteractionService;
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
@RequestMapping("/api/interaction")
@AllArgsConstructor
public class InteractionController {

    private final IInteractionService interactionService;

    @PostMapping("/create")
    public ResponseEntity<?> createInteraction(@RequestBody @Valid
    Interaction interaction, BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidRequestBodyException(result);
        }

        interactionService.createInteraction(interaction);
        return new ResponseEntity<>(new ApiResponse<>(true,
            "Interaction created successfully"), HttpStatus.CREATED);
    }

    @GetMapping("/get-by-lead")
    public ResponseEntity<?> getInteractionByLeadId(@RequestParam Long leadId) {
        List<Interaction> interactions = interactionService.getInteractionByLeadId(leadId);
        return new ResponseEntity<>(new ApiResponse<>(true, interactions), HttpStatus.OK);
    }

    @GetMapping("/get-by-restaurant")
    public ResponseEntity<?> getInteractionByRestaurantId(@RequestParam Long restaurantId) {
        List<Interaction> interactions = interactionService.getInteractionByRestaurantId(restaurantId);
        return new ResponseEntity<>(new ApiResponse<>(true, interactions), HttpStatus.OK);
    }

    @GetMapping("/get-by-poc")
    public ResponseEntity<?> getInteractionByPocId(@RequestParam Long pocId) {
        List<Interaction> interactions = interactionService.getInteractionByPocId(pocId);
        return new ResponseEntity<>(new ApiResponse<>(true, interactions), HttpStatus.OK);
    }

    @GetMapping("/get-by-call-schedule")
    public ResponseEntity<?> getInteractionByCallScheduleId(@RequestParam Long callScheduleId) {
        List<Interaction> interactions = interactionService.getInteractionByCallScheduleId(callScheduleId);
        return new ResponseEntity<>(new ApiResponse<>(true, interactions), HttpStatus.OK);
    }

    @GetMapping("/get-by-kam")
    public ResponseEntity<?> getInteractionByKAMId(@RequestParam Long kamId) {
        List<Interaction> interactions = interactionService.getInteractionByKAMId(kamId);
        return new ResponseEntity<>(new ApiResponse<>(true, interactions), HttpStatus.OK);
    }
}
