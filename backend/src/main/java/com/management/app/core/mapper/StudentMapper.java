package com.management.app.core.mapper;

import com.management.app.core.model.entity.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentMapper {

    Student selectStudent(Integer id);

    Integer selectStudentCount(Integer teamId, String name);

    List<Student> selectStudentPage(Integer startIndex, Integer pageSize, Integer teamId, String name);

    void insertStudent(Student student);

    void updateStudent(Student student);

    void deleteStudent(Integer id);

    void deleteStudents(Integer teamId);
}
