package com.ead.course.repositories;

import com.ead.course.models.LessonModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<LessonModel, UUID>, JpaSpecificationExecutor<LessonModel> {

    @Query(value = "SELECT lessons FROM LessonModel lessons WHERE lessons.module.moduleId= :moduleId")
    List<LessonModel> findAllLessonsIntoModule(@Param(value = "moduleId") UUID moduleId);

    @Query(value = "SELECT lesson FROM LessonModel lesson WHERE lesson.lessonId= :lessonId AND lesson.module.moduleId= :moduleId")
    Optional<LessonModel> findLessonIntoModule(@Param(value = "moduleId") UUID moduleId, @Param(value = "lessonId") UUID lessonId);
}
