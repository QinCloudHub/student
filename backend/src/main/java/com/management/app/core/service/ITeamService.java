package com.management.app.core.service;

import com.management.app.core.model.entity.Team;
import com.management.app.support.PageInfo;

import java.util.List;

public interface ITeamService {

    Team findById(Integer id);

    List<Team> findAll();

    PageInfo findPaging(Integer pageNumber, Integer pageSize, String professional);

    void create(Team model);

    void update(Integer id, Team model);

    void delete(Integer id);
}
