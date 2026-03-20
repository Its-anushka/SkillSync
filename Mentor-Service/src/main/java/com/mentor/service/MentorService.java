package com.mentor.service;

import com.mentor.dto.request.AvailabilityRequest;
import com.mentor.dto.request.MentorRequest;
import com.mentor.dto.response.MentorResponse;

import java.util.List;

public interface MentorService {

    MentorResponse applyForMentor(MentorRequest request);

    List<MentorResponse> getAllMentors();

    MentorResponse getMentorById(Long id);

    void addAvailability(AvailabilityRequest request);
}