package com.management.app.core.mapper;

import com.management.app.core.model.entity.Course;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseMapper {

    Course selectCourse(Integer id);

    List<Course> selectAllCourse();

    Integer selectCourseCount(String semester, Integer teamId, Integer teacherId, String name);

    List<Course> selectCoursePage(Integer startIndex, Integer pageSize,
                                  String semester, Integer teamId, Integer teacherId,  String name);

    void insertCourse(Course course);

    void updateCourse(Course course);

    void deleteCourse(Integer id);

    void deleteCoursesByTeam(Integer teamId);

    void deleteCoursesByTeacher(Integer teacherId);
}
