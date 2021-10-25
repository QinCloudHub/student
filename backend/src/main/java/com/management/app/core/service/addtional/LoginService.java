package com.management.app.core.service.addtional;

import com.fasterxml.jackson.databind.JsonNode;
import com.management.app.core.mapperwrapper.AccountQuery;
import com.management.app.core.model.entity.Account;
import com.management.app.infrastructure.exception.EntityNotFoundException;
import com.management.app.infrastructure.exception.PermissionException;
import com.management.app.support.Rest;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class LoginService {

    /**
     * 登录
     */
    public String login(String username, String password) {
        if (AccountQuery.getBy(username) != null) {
            // 获取用户
            Account account = AccountQuery.getBy(username);
            if (!password.equals(account.getPassword())) {
                throw new PermissionException(String.format("账号%s的密码错误", username));
            }
        } else {
            throw new PermissionException(String.format("未找到用户名为%s的账号", username));
        }
        // 将username|password通过base64编码
        return new String(Base64.getEncoder().encode(String.format("%s|%s", username, password).getBytes()));
    }
}
