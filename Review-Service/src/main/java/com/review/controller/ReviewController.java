package com.review.controller;

import com.review.dto.request.ReviewRequest;
import com.review.dto.response.ApiResponse;
import com.review.dto.response.ReviewResponse;
import com.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    //Add Review
    @PostMapping
    public ApiResponse<ReviewResponse> addReview(
            @Valid @RequestBody ReviewRequest request) {

        ReviewResponse response = reviewService.addReview(request);

        return ApiResponse.<ReviewResponse>builder()
                .success(true)
                .message("Review added successfully")
                .data(response)
                .build();
    }

    //Get Reviews by Mentor
    @GetMapping("/mentor/{mentorId}")
    public ApiResponse<List<ReviewResponse>> getReviewsByMentor(
            @PathVariable Long mentorId) {

        List<ReviewResponse> reviews =
                reviewService.getReviewsByMentor(mentorId);

        return ApiResponse.<List<ReviewResponse>>builder()
                .success(true)
                .message("Reviews fetched successfully")
                .data(reviews)
                .build();
    }
}