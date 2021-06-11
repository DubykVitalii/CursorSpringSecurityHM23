package com.cursor.library;


import com.cursor.library.models.enums.UserPermission;
import com.cursor.library.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Set;


@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
public class LibraryApplication {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public LibraryApplication(UserRepo userRepo, BCryptPasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

    @PostConstruct
    public void addUsers() {
        userRepo.saveUser("admin", encoder.encode("admin"), Set.of(UserPermission.ROLE_ADMIN));
        userRepo.saveUser("user", encoder.encode("user"), Set.of(UserPermission.ROLE_WRITE, UserPermission.ROLE_READ));
        userRepo.saveUser("user1", encoder.encode("user1"), Set.of(UserPermission.ROLE_WRITE));
    }
}
