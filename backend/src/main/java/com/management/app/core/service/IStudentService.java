package com.management.app.core.service;

import com.management.app.core.model.entity.Student;
import com.management.app.support.PageInfo;

public interface IStudentService {

    Student findById(Integer id);

    PageInfo findPaging(Integer pageNumber, Integer pageSize, Integer teamId, String name);

    void create(Student model);

    void update(Integer id, Student model);

    void delete(Integer id);
}
