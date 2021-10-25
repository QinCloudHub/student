package com.management.app.core.service.impl;

import com.management.app.core.mapper.StudentMapper;
import com.management.app.core.mapper.TeamMapper;
import com.management.app.core.mapperwrapper.CourseCommand;
import com.management.app.core.mapperwrapper.StudentCommand;
import com.management.app.core.model.entity.Team;
import com.management.app.core.service.ITeamService;
import com.management.app.support.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.management.app.infrastructure.consts.AppConst.DEFAULT_NUMBER;
import static com.management.app.infrastructure.consts.AppConst.DEFAULT_SIZE;

@Service
public class TeamServiceImpl implements ITeamService {

    private final TeamMapper mapper;

    public TeamServiceImpl(TeamMapper teamMapper) {
        this.mapper = teamMapper;
    }

    @Override
    public Team findById(Integer id) {
        return mapper.selectTeam(id);
    }

    @Override
    public List<Team> findAll() {
        return mapper.selectAllTeam();
    }

    @Override
    public PageInfo findPaging(Integer pageNumber, Integer pageSize, String professional) {
        Integer totalCount = mapper.selectTeamCount(professional);
        if (pageNumber == null || pageSize == null) {
            // 默认返回分页8
            return new PageInfo(DEFAULT_NUMBER, DEFAULT_SIZE, totalCount, mapper.selectTeamPage(DEFAULT_NUMBER, DEFAULT_SIZE, professional));
        }
        List<Team> content = mapper.selectTeamPage(pageNumber * pageSize, pageSize, professional);
        return new PageInfo(pageNumber, pageSize, totalCount, content);
    }

    @Override
    public void create(Team model) {
        Team entity = new Team();
        entity.setBaseProperties(model);
        mapper.insertTeam(entity);
    }

    @Override
    public void update(Integer id, Team model) {
        Team entity = findById(id);
        entity.setBaseProperties(model);
        mapper.updateTeam(entity);
    }

    @Override
    public void delete(Integer id) {
        StudentCommand.deleteStudents(id);
        CourseCommand.deleteCoursesByTeamId(id);
        mapper.deleteTeam(id);
    }
}
