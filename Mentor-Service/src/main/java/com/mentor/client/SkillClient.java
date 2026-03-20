package com.mentor.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "skill-service")
public interface SkillClient {

    @GetMapping("/skills/{id}")
    Object getSkillById(@PathVariable Long id);
}