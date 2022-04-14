package com.ead.course.repositories;

import com.ead.course.models.ModuleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleRepository extends JpaRepository<ModuleModel, UUID>, JpaSpecificationExecutor<ModuleModel> {

    @Query(value = "SELECT modules FROM ModuleModel modules WHERE modules.course.courseId = :courseId")
    List<ModuleModel> findAllModuleIntoCourse(@Param("courseId") UUID courseId);

    @Query(value = "SELECT module FROM ModuleModel module WHERE module.moduleId= :moduleId AND module.course.courseId= :courseId")
    Optional<ModuleModel> findModelIntoCourse(UUID courseId, UUID moduleId);

}
