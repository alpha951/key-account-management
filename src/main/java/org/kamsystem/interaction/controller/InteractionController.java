package org.kamsystem.interaction.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
            return new ResponseEntity<>(new ApiResponse<>(false,
                result.getAllErrors()), HttpStatus.BAD_REQUEST);
        }

        interactionService.createInteraction(interaction);
        return new ResponseEntity<>(new ApiResponse<>(true,
            "Interaction created successfully"), HttpStatus.CREATED);
    }

    @GetMapping("/get-by-lead")
    public ResponseEntity<?> getInteractionByLeadId(@RequestParam Long leadId) {
        return new ResponseEntity<>(interactionService.getInteractionByLeadId(leadId), HttpStatus.OK);
    }

    @GetMapping("/get-by-restaurant")
    public ResponseEntity<?> getInteractionByRestaurantId(@RequestParam Long restaurantId) {
        return new ResponseEntity<>(interactionService.getInteractionByRestaurantId(restaurantId), HttpStatus.OK);
    }

    @GetMapping("/get-by-poc")
    public ResponseEntity<?> getInteractionByPocId(@RequestParam Long pocId) {
        return new ResponseEntity<>(interactionService.getInteractionByPocId(pocId), HttpStatus.OK);
    }

    @GetMapping("/get-by-call-schedule")
    public ResponseEntity<?> getInteractionByCallScheduleId(@RequestParam Long callScheduleId) {
        return new ResponseEntity<>(interactionService.getInteractionByCallScheduleId(callScheduleId), HttpStatus.OK);
    }

    @GetMapping("/get-by-kam")
    public ResponseEntity<?> getInteractionByKAMId(@RequestParam Long kamId) {
        return new ResponseEntity<>(interactionService.getInteractionByKAMId(kamId), HttpStatus.OK);
    }
}
