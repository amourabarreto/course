package com.ead.course.clients;

import com.ead.course.dtos.CourseUserDto;
import com.ead.course.dtos.ResponsePageDto;
import com.ead.course.dtos.UserDto;
import com.ead.course.services.UtilsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Log4j2
@Component
public class AuthUserClient {

    @Value("${ead.api.url.authuser}")
    private String RESQUEST_URI;


    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UtilsService utilsService;


    public Page<UserDto> getAllUsersByCourse(UUID courseId, Pageable pageable) {
        List<UserDto> searchResult;
        String url = utilsService.createUrl(courseId, pageable);
        log.debug(" Request URL: {}", url);
        log.info(" Request URL: {}", url);
        ResponseEntity<ResponsePageDto<UserDto>> result = null;
        try {
            ParameterizedTypeReference<ResponsePageDto<UserDto>> responseType = new ParameterizedTypeReference<ResponsePageDto<UserDto>>() {
            };
            result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            searchResult = result.getBody().getContent();
            log.debug(" Response Number of Elements: {}", searchResult.size());
        } catch (HttpStatusCodeException e) {
            log.error(" Request URL: {}", e);
        }
        log.info(" Ending request /User: courseId {}", courseId);
        return result.getBody();
    }

    public ResponseEntity<UserDto> getOneUserById(UUID userId) {
        List<UserDto> searchResult = null;
        String url = RESQUEST_URI + "/users/" + userId;
        log.debug(" Request URL: {}", url);
        log.info(" Request URL: {}", url);
        ResponseEntity<ResponsePageDto<UserDto>> result = null;
        return restTemplate.exchange(url, HttpMethod.GET, null, UserDto.class);
    }

    public void postSubscriptionUserInCourse(UUID courseId, UUID userId) {
        String url = RESQUEST_URI + "/users/" + userId + "/courses/subscription";
        var courseUserDto = new CourseUserDto(userId, courseId);
        String retorno = restTemplate.postForObject(url, courseUserDto, String.class);
    }
}
