package com.mentor.service;

import com.mentor.client.SkillClient;
import com.mentor.client.UserClient;
import com.mentor.dto.request.AvailabilityRequest;
import com.mentor.dto.request.MentorRequest;
import com.mentor.dto.response.MentorResponse;
import com.mentor.entity.Availability;
import com.mentor.entity.Mentor;
import com.mentor.entity.MentorSkill;
import com.mentor.exception.BadRequestException;
import com.mentor.exception.ResourceNotFoundException;
import com.mentor.mapper.MentorMapper;
import com.mentor.repository.AvailabilityRepository;
import com.mentor.repository.MentorRepository;
import com.mentor.repository.MentorSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MentorServiceImpl implements MentorService {

    private final MentorRepository mentorRepository;
    private final MentorSkillRepository mentorSkillRepository;
    private final AvailabilityRepository availabilityRepository;

    private final UserClient userClient;
    private final SkillClient skillClient;


    @Override
    @Transactional
    public MentorResponse applyForMentor(MentorRequest request) {

        // 1. Validate user exists
        Object user = userClient.getUserById(request.getUserId());
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        // 2. Validate skills exist
        for (Long skillId : request.getSkillIds()) {
            Object skill = skillClient.getSkillById(skillId);
            if (skill == null) {
                throw new ResourceNotFoundException("Skill not found: " + skillId);
            }
        }

        // 3. Convert DTO → Entity
        Mentor mentor = MentorMapper.toEntity(request);

        // 4. Save mentor
        Mentor savedMentor = mentorRepository.save(mentor);

        // 5. Save mentor skills
        List<MentorSkill> mentorSkills =
                MentorMapper.toMentorSkills(savedMentor.getMentorId(), request.getSkillIds());

        mentorSkillRepository.saveAll(mentorSkills);

        // 6. Prepare response
        List<Long> skillIds = mentorSkills.stream()
                .map(MentorSkill::getSkillId)
                .collect(Collectors.toList());

        return MentorMapper.toResponse(savedMentor, skillIds);
    }

    // GET ALL MENTORS
    @Override
    public List<MentorResponse> getAllMentors() {

        List<Mentor> mentors = mentorRepository.findAll();

        return mentors.stream().map(mentor -> {

            List<Long> skillIds = mentorSkillRepository.findByMentorId(mentor.getMentorId())
                    .stream()
                    .map(MentorSkill::getSkillId)
                    .collect(Collectors.toList());

            return MentorMapper.toResponse(mentor, skillIds);

        }).collect(Collectors.toList());
    }

    // GET MENTOR BY ID
    @Override
    public MentorResponse getMentorById(Long id) {

        Mentor mentor = mentorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mentor not found"));

        List<Long> skillIds = mentorSkillRepository.findByMentorId(id)
                .stream()
                .map(MentorSkill::getSkillId)
                .collect(Collectors.toList());

        return MentorMapper.toResponse(mentor, skillIds);
    }

    // ADD AVAILABILITY
    @Override
    public void addAvailability(AvailabilityRequest request) {

        Mentor mentor = mentorRepository.findById(request.getMentorId())
                .orElseThrow(() -> new ResourceNotFoundException("Mentor not found"));

        if (request.getStartTime().isAfter(request.getEndTime())) {
            throw new BadRequestException("Start time must be before end time");
        }

        Availability availability = Availability.builder()
                .mentorId(mentor.getMentorId())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .build();

        availabilityRepository.save(availability);
    }
}