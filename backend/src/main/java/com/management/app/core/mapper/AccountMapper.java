package com.management.app.core.mapper;

import com.management.app.core.model.entity.Account;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountMapper {

    Account selectAccount(String username);
}
