package com.management.app.api;

import com.management.app.core.service.addtional.LoginService;
import com.management.app.support.Rest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class LoginApi {

    private final LoginService service;

    public LoginApi(LoginService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public Rest<?> login(@RequestBody Map<String, String> params) {
        return Rest.ok(service.login(params.get("username"), params.get("password")));
    }
}
