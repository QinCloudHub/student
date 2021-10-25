package com.management.app.core.model.entity;

import java.time.LocalDateTime;

public class Teacher {

    /**
     * id
     */
    private Integer id;

    /**
     * 学号
     */
    private String teacherNumber;

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

    public void setProperties(Teacher teacher) {
        this.setName(teacher.getName());
        this.setBirthDate(teacher.getBirthDate());
        this.setGender(teacher.getGender());
        this.setNational(teacher.getNational());
        this.setPhoneNumber(teacher.getPhoneNumber());
        this.setTeacherNumber(teacher.getTeacherNumber());
        this.setNativePlace(teacher.getNativePlace());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeacherNumber() {
        return teacherNumber;
    }

    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
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

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }
}
