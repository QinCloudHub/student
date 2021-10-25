package com.management.app.core.model.entity;

import com.management.app.core.mapperwrapper.TeacherQuery;
import com.management.app.core.mapperwrapper.TeamQuery;

import java.time.LocalDateTime;

public class Course {

    /**
     * id
     */
    private Integer id;

    /**
     * 学期 eg.2020年上学期 通过startTime自动配置
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
     * 课程绑定的班级id
     */
    private Integer teamId;

    /**
     * 课程的教师id
     */
    private Integer teacherId;

    public void setProperties(Course model) {
        this.startAt = model.getStartAt();
        int year = this.startAt.getYear();
        int month = this.startAt.getMonthValue();
        if (month > 6) {
            this.semester = String.format("%s年下学期", year);
        } else {
            this.semester = String.format("%s年上学期", year);
        }
        this.name = model.getName();
        this.description = model.getDescription();
        // 验证数据是否存在
        TeamQuery.getBy(model.getTeamId());
        this.teamId = model.getTeamId();
        TeacherQuery.getBy(model.getTeacherId());
        this.teacherId = model.getTeacherId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
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

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }
}
