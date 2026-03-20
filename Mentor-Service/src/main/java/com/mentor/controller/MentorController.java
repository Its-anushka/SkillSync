package com.mentor.controller;

import com.mentor.dto.request.AvailabilityRequest;
import com.mentor.dto.request.MentorRequest;
import com.mentor.dto.response.ApiResponse;
import com.mentor.dto.response.MentorResponse;
import com.mentor.service.MentorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mentors")
@RequiredArgsConstructor
public class MentorController {

    private final MentorService mentorService;

    //Apply for mentor
    @PostMapping("/apply")
    public ApiResponse<MentorResponse> applyForMentor(
            @Valid @RequestBody MentorRequest request) {

        MentorResponse response = mentorService.applyForMentor(request);

        return ApiResponse.<MentorResponse>builder()
                .success(true)
                .message("Mentor application submitted successfully")
                .data(response)
                .build();
    }

    //Get all mentors
    @GetMapping
    public ApiResponse<List<MentorResponse>> getAllMentors() {

        List<MentorResponse> mentors = mentorService.getAllMentors();

        return ApiResponse.<List<MentorResponse>>builder()
                .success(true)
                .message("Mentors fetched successfully")
                .data(mentors)
                .build();
    }

    //Get mentor by ID
    @GetMapping("/{id}")
    public ApiResponse<MentorResponse> getMentorById(@PathVariable Long id) {

        MentorResponse mentor = mentorService.getMentorById(id);

        return ApiResponse.<MentorResponse>builder()
                .success(true)
                .message("Mentor fetched successfully")
                .data(mentor)
                .build();
    }

    //Add availability
    @PutMapping("/{id}/availability")
    public ApiResponse<Void> addAvailability(
            @PathVariable Long id,
            @Valid @RequestBody AvailabilityRequest request) {

        request.setMentorId(id);

        mentorService.addAvailability(request);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Availability added successfully")
                .data(null)
                .build();
    }
}