package com.ead.course.repositories;

import com.ead.course.models.LessonModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<LessonModel, UUID> {

    @Query(value = "SELECT lessons FROM LessonModel lessons WHERE lessons.module.moduleId= :moduleId")
    Optional<LessonModel> findAllLessonsIntoModule(@Param(value = "moduleId") UUID moduleId);

}
