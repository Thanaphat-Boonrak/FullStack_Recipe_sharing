package com.thanaphat.Recipe.Sharing.Service.imple;

import com.thanaphat.Recipe.Sharing.Repository.UserRepo;
import com.thanaphat.Recipe.Sharing.Service.UserService;
import com.thanaphat.Recipe.Sharing.jwt.JwtProvider;
import com.thanaphat.Recipe.Sharing.model.Users;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class UserServiceImpl implements
        UserService {
    private JwtProvider jwtProvider;
    private UserRepo userRepo;

    @Override
    public Users findUserById(Long id)
            throws Exception {
        Optional<Users> user = userRepo.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw new Exception("User not found");
    }

    @Override
    public Users findUserByEmail(String email) throws Exception {
        Users user = userRepo.findUserByEmail(email);
        return user;
    }

    @Override
    public Users findUserByEmailWithJwt(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwt(jwt);

        if (email == null) {
            throw new Exception("Invalid Jwt");
        }

        Users user = userRepo.findUserByEmail(email);
        if (user != null) {
            return user;
        }
        throw new Exception("User not found");
    }


}
