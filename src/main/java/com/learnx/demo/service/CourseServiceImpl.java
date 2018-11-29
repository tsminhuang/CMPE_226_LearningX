package com.learnx.demo.service;

import com.learnx.demo.entity.Course;
import com.learnx.demo.model.CourseDto;
import com.learnx.demo.repository.CourseRepository;
import com.learnx.demo.repository.RepositoryUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repository;

    public CourseServiceImpl(CourseRepository repository) {
        this.repository = repository;
    }

    private static CourseDto convertToDto(Course entity) {
        CourseDto dto = new CourseDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setInstructorId(entity.getInstructorId());

        return dto;
    }

    private static Course converToEntity(CourseDto dto) {
        Course entity = new Course();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setInstructorId(dto.getInstructorId());

        return entity;
    }

    @Override
    public List<CourseDto> searchCourses(String keyword) {
        List<Course> results = repository.search(keyword);
        return results.stream()
                .map(CourseServiceImpl::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> listCourses() {
        return RepositoryUtil.mapAll(repository.findAll(), CourseServiceImpl::convertToDto);
    }

    @Override
    public List<CourseDto> listCoursesSortedByRating(boolean ascending) {
        return null;
    }

    @Override
    public List<CourseDto> listCoursesByUserId(int userId) {
        return null;
    }

    @Override
    public List<CourseDto> listOnGoingCoursesByUserId(int userId) {
        return null;
    }

    @Override
    public List<CourseDto> listFinishedCoursesByUserId(int userId) {
        return null;
    }

    @Override
    public List<CourseDto> listCoursesByInstructorId(int instructorId) {
        return null;
    }

    @Override
    public List<CourseDto> listCoursesByInstituteId(int instructorId) {
        return null;
    }

    @Override
    public CourseDto getCourseById(int courseId) {
        return null;
    }

    @Override
    public CourseDto create(CourseDto course) {
        return null;
    }

    @Override
    public CourseDto update(CourseDto newCourse) {
        return null;
    }
}
