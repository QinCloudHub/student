package com.management.app.core.mapperwrapper;

import com.management.app.core.mapper.TeamMapper;
import com.management.app.core.model.entity.Team;
import com.management.app.infrastructure.exception.EntityNotFoundException;
import com.management.app.infrastructure.utils.IocUtil;

import java.util.Optional;

public class TeamQuery {

    private static TeamMapper mapper = IocUtil.getBean(TeamMapper.class);

    public static Team getBy(Integer id) {
        return Optional.ofNullable(mapper.selectTeam(id)).orElseThrow(() ->
                new EntityNotFoundException(String.format("未找到id为%d的班级", id)));
    }
}
