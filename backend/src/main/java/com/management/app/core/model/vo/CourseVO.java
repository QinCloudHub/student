package com.management.app.core.model.vo;

import com.management.app.core.mapperwrapper.TeacherQuery;
import com.management.app.core.mapperwrapper.TeamQuery;
import com.management.app.core.model.entity.Course;
import com.management.app.core.model.entity.Student;
import com.management.app.core.model.entity.Teacher;
import com.management.app.core.model.entity.Team;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CourseVO {

    /**
     * id
     */
    private Integer id;

    /**
     * 学期 eg.2020年上学期
     */
    private String semester;

    /**
     * 学期开始时间
     */
    private LocalDateTime startAt;

    /**
     * 课程名
     */
    private String name;

    /**
     * 课程描述
     */
    private String description;

    /**
     * 班级
     */
    private Team team;

    /**
     * 教师
     */
    private Teacher teacher;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setProperties(Course course) {
        this.setId(course.getId());
        this.startAt = course.getStartAt();
        int year = startAt.getYear();
        int month = startAt.getMonthValue();
        if (month > 6) {
            this.semester = String.format("%s年下学期", year);
        } else {
            this.semester = String.format("%s年上学期", year);
        }
        this.setName(course.getName());
        this.setDescription(course.getDescription());
        this.setTeacher(TeacherQuery.getBy(course.getTeacherId()));
        this.setTeam(TeamQuery.getBy(course.getTeamId()));
    }

    public static CourseVO mapFrom(Course course) {
        CourseVO vo = new CourseVO();
        vo.setProperties(course);
        return vo;
    }

    public static List<CourseVO> mapFrom(List<Course> courses) {
        return courses.stream().map(CourseVO::mapFrom).collect(Collectors.toList());
    }
}
