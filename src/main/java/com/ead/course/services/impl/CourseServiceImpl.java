package com.ead.course.services.impl;

import com.ead.course.models.CourseModel;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.CourseRepositoy;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepositoy courseRepositoy;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    LessonRepository lessonRepository;

    @Transactional
    @Override
    public void delete(CourseModel courseModel) {
        List<ModuleModel> moduleModelList = moduleRepository.findAllModuleIntoCourse(courseModel.getCourseId());
        if(!moduleModelList.isEmpty()){
            moduleModelList.stream().forEach(module -> {
                    List<LessonModel> lessonModelList = lessonRepository.findAllLessonsIntoModule(module.getModuleId());
                    if(!lessonModelList.isEmpty()){
                        lessonRepository.deleteAll(lessonModelList);
                    }
                });
                moduleRepository.deleteAll(moduleModelList.stream().collect(Collectors.toList()));
            }
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
        return courseRepositoy.findAll(courseSpec,pageable);
    }
}
