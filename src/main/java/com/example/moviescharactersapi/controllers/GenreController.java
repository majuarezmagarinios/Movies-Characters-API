package com.example.moviescharactersapi.controllers;

import com.example.moviescharactersapi.entity.Genre;
import com.example.moviescharactersapi.entityDTO.GenreDTO;
import com.example.moviescharactersapi.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RequestMapping( value = "api/genres")
@RestController
public class GenreController {

    private final GenreRepository genreRepository;

    @PostMapping( value = "create")
    public ResponseEntity<Genre> create( @RequestBody GenreDTO genreDTO ) {
        Optional<Genre> genreOp = genreRepository.findById( genreDTO.getId() );

        if ( genreOp.isEmpty() ) {
            Genre newGenre = new Genre();

            BeanUtils.copyProperties( genreDTO, newGenre, "movies" );
            genreRepository.save( newGenre );

            return ResponseEntity.status( HttpStatus.CREATED ).body( newGenre );

        } else {
            log.warn( "Cannot create a genre with an existing id" );
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping
    public ResponseEntity<Genre> update( @RequestBody GenreDTO genreDTO ) {
        Optional<Genre> genreOp = genreRepository.findById( genreDTO.getId() );

        if ( genreOp.isPresent() ) {
            Genre genre = genreOp.get();

            BeanUtils.copyProperties( genreDTO, genre, "movies" );
            genreRepository.save( genre );

            return ResponseEntity.ok().body( genre );

        } else {
            log.warn( "You are trying to update a non existen genre" );
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping
    public ResponseEntity<List<Genre>> findAll() {

        List<Genre> genre = genreRepository.findAll();

        if ( genre.size() == 0 ) {
            log.warn( "There are no characters in the database" );
        }

        return ResponseEntity.ok().body( genre );
    }
}
