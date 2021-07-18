package com.readingisgood.order.service.user;

import com.readingisgood.order.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/** @author hakan.ozerden */
@Service
@RequiredArgsConstructor
public class UserQueryService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(
                        () ->
                                new UsernameNotFoundException(
                                        "User Not Found with username: " + username));
    }
}
