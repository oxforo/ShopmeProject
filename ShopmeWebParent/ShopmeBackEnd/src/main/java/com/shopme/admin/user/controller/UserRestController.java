package com.shopme.admin.user.controller;

import com.shopme.admin.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @PostMapping("/users/check_email")
    public String checkDuplicateEmail(Integer id, String email) {
        return userService.isEmailUnique(id, email) ? "OK" : "Duplicated";
    }
}
