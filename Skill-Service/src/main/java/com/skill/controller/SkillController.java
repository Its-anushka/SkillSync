package com.skill.controller;

import com.skill.dto.request.SkillRequest;
import com.skill.dto.response.ApiResponse;
import com.skill.dto.response.SkillResponse;
import com.skill.service.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    //Create Skill
    @PostMapping
    public ApiResponse<SkillResponse> createSkill(
            @Valid @RequestBody SkillRequest request) {

        SkillResponse response = skillService.createSkill(request);

        return ApiResponse.<SkillResponse>builder()
                .success(true)
                .message("Skill created successfully")
                .data(response)
                .build();
    }

    //Get all skills
    @GetMapping
    public ApiResponse<List<SkillResponse>> getAllSkills() {

        List<SkillResponse> skills = skillService.getAllSkills();

        return ApiResponse.<List<SkillResponse>>builder()
                .success(true)
                .message("Skills fetched successfully")
                .data(skills)
                .build();
    }

    //Get skill by ID
    @GetMapping("/{id}")
    public ApiResponse<SkillResponse> getSkillById(@PathVariable Long id) {

        SkillResponse skill = skillService.getSkillById(id);

        return ApiResponse.<SkillResponse>builder()
                .success(true)
                .message("Skill fetched successfully")
                .data(skill)
                .build();
    }
}