package com.example.moviescharactersapi.controllers;

import com.example.moviescharactersapi.entity.User;
import com.example.moviescharactersapi.repository.UserRepository;
import com.example.moviescharactersapi.security.payload.AuthRequest;
import com.example.moviescharactersapi.security.payload.AuthResponse;
import com.example.moviescharactersapi.security.payload.RegisterRequest;
import com.example.moviescharactersapi.security.services.AuthService;
import com.example.moviescharactersapi.service.SendGridEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping( "/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService services;
    private final UserRepository userRepository;
    private final SendGridEmailService sendGridEmailService;

    @PostMapping( "/register")
    public ResponseEntity<AuthResponse> register( @RequestBody RegisterRequest request ) throws Exception {
        Optional<User> requestOp = userRepository.findByEmail( request.getEmail() );

        if ( requestOp.isPresent() ) {
            log.warn( "Error: Email is already registered!" );
            return ResponseEntity.notFound().build();
        }

        sendGridEmailService.sendEmail( request.getEmail(), "Registration Successful!" );

        return ResponseEntity.ok( services.register( request ) );
    }

    @PostMapping( "/login")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody AuthRequest request
    ) {

        Optional<User> requestOp = userRepository.findByEmail( request.getEmail() );
        if ( requestOp.isEmpty() ) {
            log.warn( "Error: Email does not exists!" );
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok( services.authenticate( request ) );
    }
}