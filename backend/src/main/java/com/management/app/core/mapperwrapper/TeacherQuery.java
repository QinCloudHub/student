package com.management.app.core.mapperwrapper;

import com.management.app.core.mapper.TeacherMapper;
import com.management.app.core.model.entity.Teacher;
import com.management.app.infrastructure.exception.EntityNotFoundException;
import com.management.app.infrastructure.utils.IocUtil;

import java.util.Optional;

public class TeacherQuery {

    private static final TeacherMapper mapper = IocUtil.getBean(TeacherMapper.class);

    public static Teacher getBy(Integer id) {
        return Optional.ofNullable(mapper.selectTeacher(id)).orElseThrow(() ->
                new EntityNotFoundException(String.format("未找到id为%d的教师", id)));
    }
}
