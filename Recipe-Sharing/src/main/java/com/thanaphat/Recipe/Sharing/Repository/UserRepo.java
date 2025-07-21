package com.thanaphat.Recipe.Sharing.Repository;

import com.thanaphat.Recipe.Sharing.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Users,Long> {

    Users findUserByEmail(String email);
}
