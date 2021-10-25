package com.management.app.core.service.impl;

import com.management.app.core.model.entity.Student;
import com.management.app.core.mapper.StudentMapper;
import com.management.app.core.model.vo.StudentVO;
import com.management.app.core.service.IStudentService;
import com.management.app.support.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.management.app.infrastructure.consts.AppConst.DEFAULT_NUMBER;
import static com.management.app.infrastructure.consts.AppConst.DEFAULT_SIZE;

@Service
public class StudentServiceImpl implements IStudentService {

    private final StudentMapper mapper;

    public StudentServiceImpl(StudentMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Student findById(Integer id) {
        return mapper.selectStudent(id);
    }

    @Override
    public PageInfo findPaging(Integer pageNumber, Integer pageSize,Integer teamId, String name) {
        Integer totalCount = mapper.selectStudentCount(teamId, name);
        if (pageNumber == null || pageSize == null) {
            // 默认返回分页8
            return new PageInfo(DEFAULT_NUMBER,
                    DEFAULT_SIZE,
                    totalCount,
                    mapper.selectStudentPage(DEFAULT_NUMBER, DEFAULT_SIZE, teamId, name));
        }
        List<Student> content = mapper.selectStudentPage(pageNumber * pageSize, pageSize, teamId, name);
        List<StudentVO> vos = StudentVO.mapFrom(content);
        return new PageInfo(pageNumber, pageSize, totalCount, vos);
    }

    private void setBaseProperties(Student entity, Student model) {
        entity.setProperties(model);
    }

    @Override
    public void create(Student model) {
        Student entity = new Student();
        setBaseProperties(entity, model);
        mapper.insertStudent(entity);
    }

    @Override
    public void update(Integer id, Student model) {
        Student entity = findById(id);
        setBaseProperties(entity, model);
        mapper.updateStudent(entity);
    }

    @Override
    public void delete(Integer id) {
        mapper.deleteStudent(id);
    }

}
