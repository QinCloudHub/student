package com.management.app.core.mapper;

import com.management.app.core.model.entity.Team;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMapper {

    Team selectTeam(Integer id);

    List<Team> selectAllTeam();

    Integer selectTeamCount(String professional);

    List<Team> selectTeamPage(Integer startIndex, Integer pageSize, String professional);

    void insertTeam(Team team);

    void updateTeam(Team team);

    void deleteTeam(Integer id);
}
