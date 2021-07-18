package com.readingisgood.order.service.authentication;

import com.readingisgood.order.request.AuthenticateRequest;
import com.readingisgood.order.response.AuthenticateResponse;
import com.readingisgood.order.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock private AuthenticationManager mockAuthenticationManager;
    @Mock private JwtUtil mockJwtUtil;

    private AuthenticationService authenticationServiceUnderTest;

    @BeforeEach
    void setUp() {
        authenticationServiceUnderTest =
                new AuthenticationService(mockAuthenticationManager, mockJwtUtil);
    }

    @Test
    void shouldAuthenticateUserAndGenerateJWT() {
        // given
        final AuthenticateRequest request = new AuthenticateRequest("username", "password");
        when(mockAuthenticationManager.authenticate(Mockito.any(Authentication.class)))
                .thenReturn(null);
        when(mockJwtUtil.generateJwt(null)).thenReturn("result");

        // when
        final AuthenticateResponse result =
                authenticationServiceUnderTest.authenticateUser(request);

        // then
        assertEquals("Jwt incorrect", "result", result.getToken());
    }
}
