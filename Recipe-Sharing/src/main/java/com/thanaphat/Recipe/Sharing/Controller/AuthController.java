package com.thanaphat.Recipe.Sharing.Controller;


import com.thanaphat.Recipe.Sharing.DTO.LoginDTO;
import com.thanaphat.Recipe.Sharing.DTO.RegisterDTo;
import com.thanaphat.Recipe.Sharing.Repository.UserRepo;
import com.thanaphat.Recipe.Sharing.Utilty.Role;
import com.thanaphat.Recipe.Sharing.jwt.JwtProvider;
import com.thanaphat.Recipe.Sharing.model.Users;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final JwtProvider jwtProvider;

    private final UserRepo userRepo;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> Register(@RequestBody RegisterDTo registerDTo) throws Exception {

        if (userRepo.findUserByEmail(registerDTo.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Email already used"));
        }

        Users createUser = new Users();
        createUser.setEmail(registerDTo.getEmail());
        createUser.setPassword(passwordEncoder.encode(registerDTo.getPassword()));
        createUser.setName(registerDTo.getName());
        createUser.setSurname(registerDTo.getSurname());
        if (registerDTo.getEmail().equals("thanaphat.boon@kmutt.ac.th")) {
            createUser.setRole(Role.ADMIN);
        } else {
            createUser.setRole(Role.USER);
        }

        userRepo.save(createUser);

        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(registerDTo.getEmail(), registerDTo.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auth);


        String token = jwtProvider.generateJwt(auth);


        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("token", token));

    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> Login(@RequestBody LoginDTO loginDTO) throws Exception {
        Users user = userRepo.findUserByEmail(loginDTO.getEmail());
        if (user == null) {
            throw new Exception("User not found with email: " + loginDTO.getEmail());
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateJwt(authentication);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("token", token));
    }


}
