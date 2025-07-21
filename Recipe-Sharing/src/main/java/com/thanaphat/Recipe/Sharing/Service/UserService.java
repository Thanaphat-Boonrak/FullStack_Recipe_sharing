package com.thanaphat.Recipe.Sharing.Service;

import com.thanaphat.Recipe.Sharing.model.Users;

public interface UserService {

    public Users findUserById(Long id) throws Exception;


    public Users findUserByEmailWithJwt(String jwt) throws Exception;

    public Users findUserByEmail(String email) throws Exception;
}
