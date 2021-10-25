package com.management.app.core.service;

import com.management.app.core.model.entity.Course;
import com.management.app.support.PageInfo;

import java.util.List;

public interface ICourseService {

    Course findById(Integer id);

    List<String> findAllSemester();

    PageInfo findPaging(Integer pageNumber, Integer pageSize, String semester, Integer teamId, Integer teacherId, String name);

    void create(Course model);

    void update(Integer id, Course model);

    void delete(Integer id);
}
