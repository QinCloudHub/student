package com.management.app.core.mapperwrapper;

import com.management.app.core.mapper.CourseMapper;
import com.management.app.infrastructure.utils.IocUtil;

public class CourseCommand {

    private static final CourseMapper mapper = IocUtil.getBean(CourseMapper.class);

    public static void deleteCoursesByTeamId(Integer teamId) {
        mapper.deleteCoursesByTeam(teamId);
    }

    public static void deleteCoursesByTeacherId(Integer teacherId) {
        mapper.deleteCoursesByTeacher(teacherId);
    }
}
