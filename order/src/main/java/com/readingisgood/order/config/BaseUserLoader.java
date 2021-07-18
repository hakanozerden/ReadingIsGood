package com.readingisgood.order.config;

import com.readingisgood.order.domain.entity.User;
import com.readingisgood.order.domain.enums.Role;
import com.readingisgood.order.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

/** @author IS97550 */
@Component
@RequiredArgsConstructor
public class BaseUserLoader implements ApplicationRunner {

    private final UserRepository userRepository;

    /**
     * Insert dummy user.
     *
     * @param args
     */
    public void run(ApplicationArguments args) {
        Optional<User> dummyUser = userRepository.findByUsername("admin");

        if (dummyUser.isEmpty())
            userRepository.save(
                    User.builder()
                            .username("admin")
                            .password(
                                    "$2y$12$IcIQrVhbVxrCq8uo6iQ63u.3LlKG3SsWXJpphDWeR01UJG0APJOx6")
                            .role(Role.ADMIN)
                            .build());
    }
}
