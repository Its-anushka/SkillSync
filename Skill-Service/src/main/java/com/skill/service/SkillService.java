package com.skill.service;

import com.skill.dto.request.SkillRequest;
import com.skill.dto.response.SkillResponse;

import java.util.List;

public interface SkillService {

    SkillResponse createSkill(SkillRequest request);

    List<SkillResponse> getAllSkills();

    SkillResponse getSkillById(Long id);
}