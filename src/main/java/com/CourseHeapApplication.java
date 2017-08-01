package com;

import com.oauth.config.CustomUserDetails;
import com.oauth.entities.Role;
import com.oauth.entities.User;
import com.oauth.repositories.UserRepository;
import com.oauth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;


@Configuration
@EnableAutoConfiguration
@EnableWebMvc
@SpringBootApplication
@ComponentScan(basePackages = {"com"})
@EntityScan(basePackages = {"com.courseheap.entities", "com.oauth.example.entities"})
public class CourseHeapApplication {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(CourseHeapApplication.class, args);
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
