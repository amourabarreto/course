package com.ead.course.services.impl;

import com.ead.course.repositories.CourseUserRespository;
import com.ead.course.services.CourseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseUserServiceImpl implements CourseUserService {
    @Autowired
    CourseUserRespository courseUserRespository;
}
