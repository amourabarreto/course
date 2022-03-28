package com.ead.course.repositories;

import com.ead.course.models.ModuleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleRepository extends JpaRepository<ModuleModel, UUID> {

    @Query(value = "SELECT modules FROM ModuleModel modules WHERE modules.course.courseId = :courseId")
    Optional<ModuleModel> findAllModuleIntoCourse(@Param("courseId") UUID courseId);

}
