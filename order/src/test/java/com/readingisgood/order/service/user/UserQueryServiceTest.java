package com.readingisgood.order.service.user;

import com.readingisgood.order.domain.entity.User;
import com.readingisgood.order.domain.enums.Role;
import com.readingisgood.order.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserQueryServiceTest {

    @Mock private UserRepository mockUserRepository;

    private UserQueryService userQueryServiceUnderTest;

    @BeforeEach
    void setUp() {
        userQueryServiceUnderTest = new UserQueryService(mockUserRepository);
    }

    @Test
    void testLoadUserByUsername() {
        // given
        final Optional<User> user = Optional.of(new User("username", "password", Role.ADMIN));
        when(mockUserRepository.findByUsername("username")).thenReturn(user);

        // when
        final UserDetails result = userQueryServiceUnderTest.loadUserByUsername("username");

        // then
        assertNotNull(result, "Result is null.");
        assertEquals("Username is incorrect.", user.get().getUsername(), result.getUsername());
        assertEquals("Password is incorrect.", user.get().getPassword(), result.getPassword());
    }

    @Test
    void testLoadUserByUsername_ThrowsUsernameNotFoundException() {
        // given
        when(mockUserRepository.findByUsername("username")).thenReturn(Optional.empty());

        // when - then
        assertThrows(
                UsernameNotFoundException.class,
                () -> userQueryServiceUnderTest.loadUserByUsername("username"));
    }
}
