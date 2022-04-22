package com.ead.course.services.impl;

import com.ead.course.models.UserModel;
import com.ead.course.repositories.UserRespository;
import com.ead.course.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRespository userRespository;

    @Override
    public Object findAll(Specification<UserModel> spec, Pageable pageable) {
        return userRespository.findAll(spec,pageable);
    }

    @Override
    public UserModel save(UserModel userModel) {
        return userRespository.save(userModel);
    }

    @Override
    public void delete(UUID userId) {
        userRespository.deleteById(userId);
    }

    @Override
    public Optional<UserModel> findById(UUID userInstructorId) {
        return userRespository.findById(userInstructorId);
    }

}
