package com.ead.course.repositories;

import com.ead.course.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserRespository extends JpaRepository<UserModel, UUID>, JpaSpecificationExecutor<UserModel> {

    @Modifying
    @Query(value = "delete from tb_courses_users where user_id= :userId",nativeQuery = true)
    void deleteCoursesUsersByUserId(@Param("userId") UUID userId);
}
