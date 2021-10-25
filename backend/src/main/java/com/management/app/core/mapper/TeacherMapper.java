package com.management.app.core.mapper;

import com.management.app.core.model.entity.Teacher;
import com.management.app.core.model.entity.Teacher;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherMapper {

    Teacher selectTeacher(Integer id);

    List<Teacher> selectAllTeacher();

    Integer selectTeacherCount(String name);

    List<Teacher> selectTeacherPage(Integer startIndex, Integer pageSize, String name);

    void insertTeacher(Teacher teacher);

    void updateTeacher(Teacher teacher);

    void deleteTeacher(Integer id);
}
