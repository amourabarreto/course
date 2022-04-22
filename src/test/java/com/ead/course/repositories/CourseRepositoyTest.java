package com.ead.course.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseRepositoyTest {

    @Autowired
    CourseRepositoy repositoy;

    @Test
    void existsByCourseAndUser() {
        UUID courseId = UUID.fromString("1838af3b-6907-4f71-aafd-f07a98073eca");
        UUID userId = UUID.fromString("5e580812-c038-4b08-a80d-b66e044b9f4a");
        assertTrue(repositoy.existsByCourseAndUser(courseId,userId));
    }
}