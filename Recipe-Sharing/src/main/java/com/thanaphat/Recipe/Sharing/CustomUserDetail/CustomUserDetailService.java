package com.thanaphat.Recipe.Sharing.CustomUserDetail;


import com.thanaphat.Recipe.Sharing.Repository.UserRepo;
import com.thanaphat.Recipe.Sharing.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailService implements
        UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(
            String username)
            throws UsernameNotFoundException {
        Users user = userRepo.findUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found " + username);
        }

        List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
        return new User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }

}
