package com.ead.course.repositories;

import com.ead.course.models.CourseModel;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CourseRepositoy extends JpaRepository<CourseModel, UUID>, JpaSpecificationExecutor<CourseModel> {

    @Query(value = "select case WHEN count(course)>0 THEN true ELSE false END from CourseModel course join course.users users Where " +
            "course.courseId= :courseId AND users.userId= :userId")
    boolean existsByCourseAndUser(@Param("courseId") UUID courseId,@Param("userId") UUID userId);
}
