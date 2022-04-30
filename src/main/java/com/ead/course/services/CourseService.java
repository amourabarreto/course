package com.ead.course.services;

import com.ead.course.models.CourseModel;
import com.ead.course.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseService {

    public void delete(CourseModel courseModel);

    CourseModel save(CourseModel courseModel);

    Optional<CourseModel> findById(UUID courseId);

    List<CourseModel> findAll();

    Page<CourseModel> findAll(Specification<CourseModel> courseSpec, Pageable pageable);

    boolean existsByCourseAndUser(UUID courseId, UUID userId);

    void saveSubscriptionInCourse(UUID courseId, UUID userId);

    void saveSubscriptionInCourseAndSendNotification(CourseModel courseModel, UserModel userModel);
}
