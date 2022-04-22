package com.ead.course.validations;

import com.ead.course.dtos.CourseDto;
import com.ead.course.enums.UserType;
import com.ead.course.models.UserModel;
import com.ead.course.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;
import java.util.UUID;


@Component
public class CourseValidator implements Validator {

    @Autowired
    @Qualifier("defaultValidator")
    Validator validator;

    @Autowired
    UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        CourseDto courseDto = (CourseDto) target;
        validator.validate(courseDto, errors);
        if (!errors.hasErrors()) {
            validateUserInstructor(courseDto.getUserInstructor(), errors);
        }
    }

    private void validateUserInstructor(UUID userInstructorId, Errors errors) {
        Optional<UserModel> userModelOptional = userService.findById(userInstructorId);
        if(userModelOptional.isEmpty()){
            errors.rejectValue("userInstructor", "UserInstructorError", "Instrutor não encontrado!");
        }else if(userModelOptional.get().getUserType().equals(UserType.STUDENT.toString())){
            errors.rejectValue("userInstructor", "UserInstructorError", "Usuário precisa ser INSTRUTOR ou ADMIN!");
        }

    }
}
