package com.skill.service.impl;

import com.skill.dto.request.SkillRequest;
import com.skill.dto.response.SkillResponse;
import com.skill.entity.Skill;
import com.skill.exceptions.BadRequestException;
import com.skill.exceptions.ResourceNotFoundException;
import com.skill.mapper.SkillMapper;
import com.skill.repository.SkillRepository;
import com.skill.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    //Create Skill
    @Override
    public SkillResponse createSkill(SkillRequest request) {

        //Check duplicate
        skillRepository.findByName(request.getName())
                .ifPresent(skill -> {
                    throw new BadRequestException("Skill already exists");
                });

        //Convert to entity
        Skill skill = SkillMapper.toEntity(request);

        //Save
        Skill savedSkill = skillRepository.save(skill);

        //Convert to response
        return SkillMapper.toResponse(savedSkill);
    }

    //Get all skills
    @Override
    public List<SkillResponse> getAllSkills() {

        return skillRepository.findAll()
                .stream()
                .map(SkillMapper::toResponse)
                .collect(Collectors.toList());
    }

    //Get skill by ID
    @Override
    public SkillResponse getSkillById(Long id) {

        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found"));

        return SkillMapper.toResponse(skill);
    }
}