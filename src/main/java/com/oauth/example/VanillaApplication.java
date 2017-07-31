package com.oauth.example;

import com.oauth.example.config.CustomUserDetails;
import com.oauth.example.entities.Role;
import com.oauth.example.entities.User;
import com.oauth.example.repositories.UserRepository;
import com.oauth.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;


@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableWebMvc
@SpringBootApplication
public class VanillaApplication {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(VanillaApplication.class, args);
    }

    @Autowired
    public void authenticationManager(AuthenticationManagerBuilder builder,
                                      UserRepository repository, UserService service) throws Exception {
        //Setup a default user if db is empty
        if (repository.count() == 0)
            service.save(new User("user", "user", Arrays.asList(new Role("USER"), new Role("ACTUATOR"))));
        builder.userDetailsService(userDetailsService(repository)).passwordEncoder(passwordEncoder);
    }

    private UserDetailsService userDetailsService(final UserRepository repository) {
        return username -> new CustomUserDetails(repository.findByName(username).get());
    }

}
