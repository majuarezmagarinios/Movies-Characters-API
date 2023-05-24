package com.example.moviescharactersapi.security.services;

import com.example.moviescharactersapi.entity.User;
import com.example.moviescharactersapi.repository.UserRepository;
import com.example.moviescharactersapi.security.jwt.JwtServices;
import com.example.moviescharactersapi.security.payload.AuthRequest;
import com.example.moviescharactersapi.security.payload.AuthResponse;
import com.example.moviescharactersapi.security.payload.RegisterRequest;
import com.example.moviescharactersapi.util.AuthorityRol;
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
        user.setAuthorityRol( AuthorityRol.USER );

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
