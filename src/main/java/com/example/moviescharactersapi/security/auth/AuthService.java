package com.example.moviescharactersapi.security.auth;

import com.example.moviescharactersapi.security.user.Role;
import com.example.moviescharactersapi.security.user.User;
import com.example.moviescharactersapi.security.user.UserRepository;
import com.example.moviescharactersapi.security.jwt.JwtServices;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServices jwtServices;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register( RegisterRequest request ) {
        User user = new User();
        user.setFirstname( request.getFirstname() );

        user.setLastname( request.getLastname() );
        user.setEmail( request.getEmail() );
        user.setPassword( passwordEncoder.encode( request.getPassword() ) );



        if ( request.getRole() == null ) {
            request.setRole( Role.USER );
        }

        user.setRole( request.getRole() );

        repository.save( user );

        var jwtToken = jwtServices.generateToken( user );

        return AuthResponse.builder()
                .token( jwtToken )
                .build();
    }

    public AuthResponse authenticate( AuthRequest request ) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = repository.findByEmail( request.getEmail() )
                .orElseThrow();
        var jwtToken = jwtServices.generateToken( user );

        return AuthResponse.builder()
                .token( jwtToken )
                .build();
    }
}
