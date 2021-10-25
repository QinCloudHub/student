package com.management.app.core.model.entity;

import java.time.LocalDateTime;

public class Team {

    private Integer id;

    /**
     * 学年
     */
    private LocalDateTime schoolYear;

    /**
     * 专业
     */
    private String professional;

    /**
     * 班号
     */
    private String classNumber;

    public Team() {
    }

    public Team(Integer id, LocalDateTime schoolYear, String professional, String classNumber) {
        this.id = id;
        this.schoolYear = schoolYear;
        this.professional = professional;
        this.classNumber = classNumber;
    }

    public void setBaseProperties(Team team) {
        this.schoolYear = team.getSchoolYear();
        this.professional = team.getProfessional();
        this.classNumber = team.getClassNumber();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(LocalDateTime schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }
}
