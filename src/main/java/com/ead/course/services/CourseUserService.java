package com.ead.course.services;

import com.ead.course.models.CourseModel;
import com.ead.course.models.CourseUserModel;

import java.util.UUID;

public interface CourseUserService {
    boolean existsByCourseAndUserId(CourseModel courseModel, UUID userId);

    CourseUserModel save(CourseUserModel converttoCourseUserModel);

    CourseUserModel saveAndSendSubscriptionUserInCourse(CourseUserModel convertToCourseUserModel);

    boolean existsByUserId(UUID userId);

    void deleteCourseUserByUser(UUID userId);
}
