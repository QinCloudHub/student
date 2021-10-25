package com.management.app.core.model.entity;

import com.management.app.core.mapperwrapper.TeamQuery;
import com.management.app.infrastructure.exception.EntityNotFoundException;

import java.time.LocalDateTime;

public class Student {

    private Integer id;

    /**
     * 学号
     */
    private String studentNumber;

    /**
     * 名字
     */
    private String name;

    /**
     * 出生日期
     */
    private LocalDateTime birthDate;

    /**
     * 性别
     */
    private Boolean gender;

    /**
     * 民族
     */
    private String national;

    /**
     * 电话号码
     */
    private String phoneNumber;

    /**
     * 籍贯
     */
    private String nativePlace;

    /**
     * 对应班级id
     */
    private Integer teamId;

    public void setProperties(Student student) {
        this.setName(student.getName());
        this.setBirthDate(student.getBirthDate());
        this.setGender(student.getGender());
        this.setNational(student.getNational());
        this.setPhoneNumber(student.getPhoneNumber());
        this.setStudentNumber(student.getStudentNumber());
        this.setNativePlace(student.getNativePlace());
        // 判断数据是否存在
        TeamQuery.getBy(student.getTeamId());
        this.setTeamId(student.getTeamId());
    }

    public Student() {
    }

    public Student(Integer id, String studentNumber, String name, LocalDateTime birthDate,
                   Boolean gender, String national, String phoneNumber, String nativePlace, Integer teamId) {
        this.id = id;
        this.studentNumber = studentNumber;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.national = national;
        this.phoneNumber = phoneNumber;
        this.nativePlace = nativePlace;
        this.teamId = teamId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getNational() {
        return national;
    }

    public void setNational(String national) {
        this.national = national;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
