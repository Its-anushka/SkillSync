package com.review.service;

import com.review.dto.request.ReviewRequest;
import com.review.dto.response.ReviewResponse;

import java.util.List;

public interface ReviewService {

    ReviewResponse addReview(ReviewRequest request);

    List<ReviewResponse> getReviewsByMentor(Long mentorId);
}