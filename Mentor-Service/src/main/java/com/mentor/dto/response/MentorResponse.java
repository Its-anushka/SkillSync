package com.mentor.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentorResponse {
    private Long id;
    private Long userId;
    private String bio;
    private int experience;
    private double rating;
    private double hourlyRate;
    private boolean approved;
    private List<Long> skills;
}
