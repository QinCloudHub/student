package com.management.app.core.service.impl;

import com.management.app.core.mapper.TeacherMapper;
import com.management.app.core.mapperwrapper.CourseCommand;
import com.management.app.core.model.entity.Teacher;
import com.management.app.core.service.ITeacherService;
import com.management.app.support.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.management.app.infrastructure.consts.AppConst.DEFAULT_NUMBER;
import static com.management.app.infrastructure.consts.AppConst.DEFAULT_SIZE;

@Service
public class TeacherServiceImpl implements ITeacherService {

    private final TeacherMapper mapper;


    public TeacherServiceImpl(TeacherMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Teacher findById(Integer id) {
        return mapper.selectTeacher(id);
    }

    @Override
    public List<Teacher> findAll() {
        return mapper.selectAllTeacher();
    }

    @Override
    public PageInfo findPaging(Integer pageNumber, Integer pageSize, String name) {
        Integer totalCount = mapper.selectTeacherCount(name);
        if (pageNumber == null || pageSize == null) {
            // 默认返回分页8
            return new PageInfo(DEFAULT_NUMBER, DEFAULT_SIZE, totalCount, mapper.selectTeacherPage(DEFAULT_NUMBER, DEFAULT_SIZE, name));
        }
        List<Teacher> content = mapper.selectTeacherPage(pageNumber * pageSize, pageSize, name);
        return new PageInfo(pageNumber, pageSize, totalCount, content);
    }

    private void setBaseProperties(Teacher entity, Teacher model) {
        entity.setProperties(model);
    }

    @Override
    public void create(Teacher model) {
        Teacher entity = new Teacher();
        setBaseProperties(entity, model);
        mapper.insertTeacher(entity);
    }

    @Override
    public void update(Integer id, Teacher model) {
        Teacher entity = findById(id);
        setBaseProperties(entity, model);
        mapper.updateTeacher(entity);
    }

    @Override
    public void delete(Integer id) {
        CourseCommand.deleteCoursesByTeacherId(id);
        mapper.deleteTeacher(id);
    }
}
