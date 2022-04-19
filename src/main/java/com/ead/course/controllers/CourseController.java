package com.ead.course.controllers;

import com.ead.course.dtos.CourseDto;
import com.ead.course.models.CourseModel;
import com.ead.course.services.CourseService;
import com.ead.course.specifications.SpecificationTemplate;
import com.ead.course.validations.CourseValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/courses")
public class CourseController {

    private static final String CURSO_NAO_ENCONTRADO = "Curso não encontrado!";
    @Autowired
    CourseService courseService;

    @Autowired
    CourseValidator courseValidator;

    @PostMapping
    public ResponseEntity<Object> saveCourse(@RequestBody CourseDto courseDto, Errors errors) {
        log.debug("POST saveCourse CourseDTO RECEIVED {} ", courseDto.toString());
        courseValidator.validate(courseDto, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());
        }
        var courseModel = new CourseModel();
        BeanUtils.copyProperties(courseDto, courseModel);
        courseModel = courseService.save(courseModel);
        log.debug("POST saveCourse CourseDTO SAVED {} ", courseModel.toString());
        log.info("Course saved successfully courseId {} ", courseModel.getCourseId());
        return ResponseEntity.status(HttpStatus.CREATED).body(courseModel);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Object> deleteCourse(@PathVariable(value = "courseId") UUID courseId) {
        Optional<CourseModel> courseModelOptional = courseService.findById(courseId);
        if (courseModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CURSO_NAO_ENCONTRADO);
        }
        courseService.delete(courseModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Curso deletado com sucesso!");
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Object> updateCourse(@PathVariable(value = "courseId") UUID courseId,
                                               @RequestBody @Valid CourseDto courseDto) {
        Optional<CourseModel> courseModelOptional = courseService.findById(courseId);
        if (courseModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CURSO_NAO_ENCONTRADO);
        }
        var courseModel = courseModelOptional.get();

        BeanUtils.copyProperties(courseDto, courseModel, "courseID");

        return ResponseEntity.status(HttpStatus.OK).body(courseService.save(courseModel));
    }


    @GetMapping("/{courseId}")
    public ResponseEntity<Object> getOneCourse(@PathVariable(value = "courseId") UUID courseId) {
        var courseModel = courseService.findById(courseId);
        if (courseModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CURSO_NAO_ENCONTRADO);
        }
        return ResponseEntity.status(HttpStatus.OK).body(courseModel.get());
    }

    @GetMapping
    public ResponseEntity<Page> getAllCourses(SpecificationTemplate.CourseSpec courseSpec,
                                              @PageableDefault(page = 0, size = 10, sort = "courseId", direction = Sort.Direction.ASC) Pageable pageable,
                                              @RequestParam(required = false) UUID userId) {

       return ResponseEntity.status(HttpStatus.OK).body(courseService.findAll(courseSpec, pageable));
    }

}
