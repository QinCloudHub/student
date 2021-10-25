package com.management.app.core.service.impl;

import com.management.app.core.mapper.CourseMapper;
import com.management.app.core.model.entity.Course;
import com.management.app.core.model.entity.Student;
import com.management.app.core.model.entity.Teacher;
import com.management.app.core.model.vo.CourseVO;
import com.management.app.core.model.vo.StudentVO;
import com.management.app.core.service.ICourseService;
import com.management.app.support.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.management.app.infrastructure.consts.AppConst.DEFAULT_NUMBER;
import static com.management.app.infrastructure.consts.AppConst.DEFAULT_SIZE;

@Service
public class CourseServiceImpl implements ICourseService {

    private final CourseMapper mapper;

    public CourseServiceImpl(CourseMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Course findById(Integer id) {
        return mapper.selectCourse(id);
    }

    @Override
    public List<String> findAllSemester() {
        List<Course> allCourse = mapper.selectAllCourse();
        // 使用distinct去重
        return allCourse.stream()
                .map(Course::getSemester)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public PageInfo findPaging(Integer pageNumber, Integer pageSize,
                               String semester, Integer teamId, Integer teacherId, String name) {
        Integer totalCount = mapper.selectCourseCount(semester, teamId, teacherId, name);
        if (pageNumber == null || pageSize == null) {
            return new PageInfo(DEFAULT_NUMBER,
                    DEFAULT_SIZE,
                    totalCount,
                    mapper.selectCoursePage(DEFAULT_NUMBER, DEFAULT_SIZE, semester, teamId, teacherId, name));
        }
        List<Course> content = mapper.selectCoursePage(pageNumber * pageSize, pageSize, semester, teamId, teacherId, name);
        List<CourseVO> vos = CourseVO.mapFrom(content);
        return new PageInfo(pageNumber, pageSize, totalCount, vos);
    }

    private void setBaseProperties(Course entity, Course model) {
        entity.setProperties(model);
    }

    @Override
    public void create(Course model) {
        Course entity = new Course();
        setBaseProperties(entity, model);
        mapper.insertCourse(entity);
    }

    @Override
    public void update(Integer id, Course model) {
        Course entity = findById(id);
        setBaseProperties(entity, model);
        mapper.updateCourse(entity);
    }

    @Override
    public void delete(Integer id) {
        mapper.deleteCourse(id);
    }
}
