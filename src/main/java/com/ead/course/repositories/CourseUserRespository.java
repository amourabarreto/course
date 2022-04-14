package com.ead.course.repositories;

import com.ead.course.models.CourseModel;
import com.ead.course.models.CourseUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CourseUserRespository extends JpaRepository<CourseUserModel, UUID> {

    boolean existsByCourseAndUserId(CourseModel courseModel,UUID userId);

    @Query(value = "SELECT courseUserModel from CourseUserModel courseUserModel where courseUserModel.course.courseId = :courseId ")
    List<CourseUserModel> findAllCourseUserIntoCourse(UUID courseId);
}