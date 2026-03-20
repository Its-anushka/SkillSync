package com.mentor.mapper;

import com.mentor.dto.request.MentorRequest;
import com.mentor.dto.response.MentorResponse;
import com.mentor.entity.Mentor;
import com.mentor.entity.MentorSkill;
import com.mentor.enums.MentorStatus;

import java.util.List;
import java.util.stream.Collectors;

public class MentorMapper {

    // Convert Apply Request → Mentor Entity
    public static Mentor toEntity(MentorRequest request) {
        return Mentor.builder()
                .userId(request.getUserId())
                .bio(request.getBio())
                .experience(request.getExperience())
                .hourlyRate(request.getHourlyRate())
                .rating(0.0) // default
                .status(MentorStatus.PENDING)
                .build();
    }

    // Convert skillIds → MentorSkill entities
    public static List<MentorSkill> toMentorSkills(Long mentorId, List<Long> skillIds) {
        return skillIds.stream()
                .map(skillId -> MentorSkill.builder()
                        .mentorId(mentorId)
                        .skillId(skillId)
                        .build())
                .collect(Collectors.toList());
    }

    //Convert Entity → Response DTO
    public static MentorResponse toResponse(Mentor mentor, List<Long> skillIds) {
        return MentorResponse.builder()
                .id(mentor.getMentorId())
                .userId(mentor.getUserId())
                .bio(mentor.getBio())
                .experience(mentor.getExperience())
                .rating(mentor.getRating())
                .hourlyRate(mentor.getHourlyRate())
                .skills(skillIds)
                .build();
    }
}