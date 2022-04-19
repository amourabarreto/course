package com.ead.course.services.impl;

import com.ead.course.repositories.UserRespository;
import com.ead.course.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRespository userRespository;
}
