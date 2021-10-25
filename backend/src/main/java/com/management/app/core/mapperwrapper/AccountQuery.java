package com.management.app.core.mapperwrapper;

import com.management.app.core.mapper.AccountMapper;
import com.management.app.core.model.entity.Account;
import com.management.app.infrastructure.utils.IocUtil;

import java.util.Optional;

public class AccountQuery {

    private static final AccountMapper mapper = IocUtil.getBean(AccountMapper.class);

    public static Account getBy(String username) {
        return Optional.ofNullable(mapper.selectAccount(username)).orElse(null);
    }
}
