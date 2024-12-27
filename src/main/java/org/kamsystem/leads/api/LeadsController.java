package org.kamsystem.leads.api;

import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.kamsystem.authentication.annotation.UserAuth;
import org.kamsystem.common.enums.UserRole;
import org.kamsystem.common.model.ApiResponse;
import org.kamsystem.leads.model.Lead;
import org.kamsystem.leads.model.UpdateLeadStatusRequest;
import org.kamsystem.leads.service.ILeadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/leads")
@AllArgsConstructor
public class LeadsController {

    private final ILeadService leadService;

    @UserAuth(allowedFor = {UserRole.SUPER_ADMIN, UserRole.KEY_ACCOUNT_MANAGER})
    @PostMapping("/create")
    public ResponseEntity<?> createLead(@RequestBody @Valid Lead lead, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(new ApiResponse<>(false,
                result.getAllErrors()), HttpStatus.BAD_REQUEST);
        }
        leadService.createLead(lead);
        ApiResponse<String> body = new ApiResponse<>(true, "Lead created successfully");
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @UserAuth(allowedFor = {UserRole.SUPER_ADMIN, UserRole.KEY_ACCOUNT_MANAGER})
    @PostMapping("/update-status")
    public ResponseEntity<?> updateLeadStatus(@RequestBody @Valid UpdateLeadStatusRequest request,
        BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(new ApiResponse<>(false,
                result.getAllErrors()), HttpStatus.BAD_REQUEST);
        }
        leadService.updateLeadStatus(request);
        ApiResponse<String> body = new ApiResponse<>(true, "Lead status updated successfully");
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @UserAuth(allowedFor = {UserRole.KEY_ACCOUNT_MANAGER})
    @GetMapping("/get-by-creator")
    public ResponseEntity<?> getLeadsByCreator() {
        List<Lead> leads = leadService.getLeadsByCreator();
        return new ResponseEntity<>(new ApiResponse<>(true, leads), HttpStatus.OK);
    }

    @UserAuth(allowedFor = {UserRole.SUPER_ADMIN})
    @GetMapping("/get-all")
    public ResponseEntity<?> getAllLeads() {
        List<Lead> leads = leadService.getAllLeads();
        return new ResponseEntity<>(new ApiResponse<>(true, leads), HttpStatus.OK);
    }

    @UserAuth(allowedFor = {UserRole.SUPER_ADMIN, UserRole.KEY_ACCOUNT_MANAGER})
    @GetMapping("/get-by-id")
    public ResponseEntity<?> getLeadById(Long leadId) {
        Lead lead = leadService.getLeadById(leadId);
        return new ResponseEntity<>(new ApiResponse<>(true, lead), HttpStatus.OK);
    }
}
