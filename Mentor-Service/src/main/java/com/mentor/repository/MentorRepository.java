package com.mentor.repository;

import com.mentor.dto.response.MentorResponse;
import com.mentor.entity.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentorRepository extends JpaRepository<Mentor, Long> {

}
