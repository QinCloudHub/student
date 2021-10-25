package com.management.app.core.mapperwrapper;

import com.management.app.core.mapper.StudentMapper;
import com.management.app.infrastructure.utils.IocUtil;

public class StudentCommand {

    private static final StudentMapper mapper = IocUtil.getBean(StudentMapper.class);

    public static void deleteStudents(Integer teamId) {
        mapper.deleteStudents(teamId);
    }
}
