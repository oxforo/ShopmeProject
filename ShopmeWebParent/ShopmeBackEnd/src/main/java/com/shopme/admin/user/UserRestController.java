package com.shopme.admin.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @PostMapping("/users/check_email")
    public String checkDuplicateEmail(@Param("email") String email) {
        return userService.isEmailUnique(email) ? "OK" : "Duplicated";
    }
}
