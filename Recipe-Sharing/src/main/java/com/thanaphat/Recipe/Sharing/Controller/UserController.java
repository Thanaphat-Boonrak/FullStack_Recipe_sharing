package com.thanaphat.Recipe.Sharing.Controller;

import com.thanaphat.Recipe.Sharing.Service.UserService;
import com.thanaphat.Recipe.Sharing.model.Users;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/api/user/profile")
    public ResponseEntity<?> findUserByjwt(Authentication authentication) throws Exception {
        Users user = userService.findUserByEmail(authentication.getName());

        return ResponseEntity.ok(user);
    }


}
