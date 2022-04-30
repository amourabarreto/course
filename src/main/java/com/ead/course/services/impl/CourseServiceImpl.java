package com.ead.course.services.impl;

import com.ead.course.dtos.NotificationCommandDto;
import com.ead.course.models.CourseModel;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.models.UserModel;
import com.ead.course.publishers.NotificationCommandPublisher;
import com.ead.course.repositories.CourseRepositoy;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.CourseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepositoy courseRepositoy;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    NotificationCommandPublisher notificationCommandPublisher;


    @Transactional
    @Override
    public void delete(CourseModel courseModel) {
        boolean deleteCourseUserInAuthUser = false;
        List<ModuleModel> moduleModelList = moduleRepository.findAllModuleIntoCourse(courseModel.getCourseId());
        if (!moduleModelList.isEmpty()) {
            moduleModelList.stream().forEach(module -> {
                List<LessonModel> lessonModelList = lessonRepository.findAllLessonsIntoModule(module.getModuleId());
                if (!lessonModelList.isEmpty()) {
                    lessonRepository.deleteAll(lessonModelList);
                }
            });
            moduleRepository.deleteAll(moduleModelList);
        }
       courseRepositoy.deleteCoursesUsersByCourseId(courseModel.getCourseId());
       courseRepositoy.delete(courseModel);

    }


    @Override
    public CourseModel save(CourseModel courseModel) {
        return courseRepositoy.save(courseModel);
    }

    @Override
    public Optional<CourseModel> findById(UUID courseId) {
        return courseRepositoy.findById(courseId);
    }

    @Override
    public List<CourseModel> findAll() {
        return courseRepositoy.findAll();
    }

    @Override
    public Page<CourseModel> findAll(Specification<CourseModel> courseSpec, Pageable pageable) {
        return courseRepositoy.findAll(courseSpec, pageable);
    }

    @Override
    public boolean existsByCourseAndUser(UUID courseId, UUID userId) {
        return courseRepositoy.existsByCourseAndUser(courseId,userId);
    }
    @Transactional
    @Override
    public void saveSubscriptionInCourse(UUID courseId, UUID userId) {
        courseRepositoy.saveCourseUser(courseId,userId);
    }

    @Transactional
    @Override
    public void saveSubscriptionInCourseAndSendNotification(CourseModel courseModel, UserModel userModel) {
        courseRepositoy.saveCourseUser(courseModel.getCourseId(),userModel.userId);
        try{
            var notificationCommandDto = new NotificationCommandDto();
            notificationCommandDto.setTittle("Bem vindo(a) ao Curso: "+courseModel.getName());
            notificationCommandDto.setMessage(userModel.getFullName()+ " a sua inscrição foi efetuada com sucesso!");
            notificationCommandDto.setUserId(userModel.getUserId());
            notificationCommandPublisher.publish(notificationCommandDto);
        }catch(Exception e){
            log.warn("Error sending notification!");
        }
    }
}
