package com.management.app.core.service;

import com.management.app.core.model.entity.Student;
import com.management.app.core.model.entity.Teacher;
import com.management.app.support.PageInfo;

import java.util.List;

public interface ITeacherService {

    Teacher findById(Integer id);

    List<Teacher> findAll();

    PageInfo findPaging(Integer pageNumber, Integer pageSize, String name);

    void create(Teacher model);

    void update(Integer id, Teacher model);

    void delete(Integer id);
}
