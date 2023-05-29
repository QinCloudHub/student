package com.management.app.core.model.vo;

import com.management.app.core.mapperwrapper.TeamQuery;
import com.management.app.core.model.entity.Student;
import com.management.app.core.model.entity.Team;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class StudentVO {

    private Integer id;
    private String studentNumber;
    private String name;
    private LocalDateTime birthDate;
    private Boolean gender;
    private String national;
    private String phoneNumber;
    private String nativePlace;
    private Integer teamId;

    /**
     * 班级全名
     */
    private String teamFullName;

    public String getTeamFullName() {
        return teamFullName;
    }

    public void setTeamFullName(String teamFullName) {
        this.teamFullName = teamFullName;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
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

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public void setProperties(Student student) {
        this.setId(student.getId());
        this.setName(student.getName());
        this.setBirthDate(student.getBirthDate());
        this.setGender(student.getGender());
        this.setNational(student.getNational());
        this.setPhoneNumber(student.getPhoneNumber());
        this.setStudentNumber(student.getStudentNumber());
        this.setNativePlace(student.getNativePlace());
        Team team = TeamQuery.getBy(student.getTeamId());
        if (team != null) {
            String teamFullName = String.format("%s",
                    team.getClassNumber());
            this.setTeamFullName(teamFullName);
            this.setTeamId(team.getId());
        }
    }

    public static StudentVO mapFrom(Student student) {
        StudentVO vo = new StudentVO();
        vo.setProperties(student);
        return vo;
    }

    public static List<StudentVO> mapFrom(List<Student> students) {
        return students.stream()
                .map(StudentVO::mapFrom)
                .collect(Collectors.toList());
    }

}
