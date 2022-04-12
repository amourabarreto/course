package com.ead.course.controllers;

import com.ead.course.dtos.CourseDto;
import com.ead.course.models.CourseModel;
import com.ead.course.services.CourseService;
import com.ead.course.specifications.SpecificationTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("/courses")
public class CourseController {

    private static final String CURSO_NAO_ENCONTRADO = "Curso não encontrado!";
    @Autowired
    CourseService courseService;

    @PostMapping
    public ResponseEntity<Object>  saveCourse(@RequestBody @Valid CourseDto courseDto){
        var courseModel = new CourseModel();
        BeanUtils.copyProperties(courseDto,courseModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(courseModel));
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Object> deleteCourse(@PathVariable(value = "courseId")UUID courseId){
        Optional<CourseModel> courseModelOptional = courseService.findById(courseId);
        if(courseModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CURSO_NAO_ENCONTRADO);
        }
        courseService.delete(courseModelOptional.get());
    return ResponseEntity.status(HttpStatus.OK).body("Curso deletado com sucesso!");
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Object> updateCourse(@PathVariable(value="courseId") UUID courseId,
                                               @RequestBody @Valid CourseDto courseDto){
        Optional<CourseModel> courseModelOptional = courseService.findById(courseId);
        if(courseModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CURSO_NAO_ENCONTRADO);
        }
        var courseModel = courseModelOptional.get();

        BeanUtils.copyProperties(courseDto,courseModel,"courseID");

        return ResponseEntity.status(HttpStatus.OK).body(courseService.save(courseModel));
    }


    @GetMapping("/{courseId}")
    public ResponseEntity<Object> getOneCourse(@PathVariable(value="courseId") UUID courseId){
        var courseModel = courseService.findById(courseId);
        if(courseModel.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CURSO_NAO_ENCONTRADO);
        }
        return ResponseEntity.status(HttpStatus.OK).body(courseModel.get());
    }

    @GetMapping
    public ResponseEntity<Page> getAllCourses(SpecificationTemplate.CourseSpec courseSpec,
                                              @PageableDefault(page = 0, size=10,sort ="courseId", direction = Sort.Direction.ASC) Pageable pageable,
                                              @RequestParam(required = false) UUID userId){
       if(userId!=null){
            return ResponseEntity.status(HttpStatus.OK).body(courseService.findAll(SpecificationTemplate.courseUserId(userId).and(courseSpec),pageable));
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(courseService.findAll(courseSpec,pageable));
        }

    }

}
