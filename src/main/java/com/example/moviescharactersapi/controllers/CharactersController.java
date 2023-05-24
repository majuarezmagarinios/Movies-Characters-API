package com.example.moviescharactersapi.controllers;

import com.example.moviescharactersapi.entity.Characters;
import com.example.moviescharactersapi.entity.Movie;
import com.example.moviescharactersapi.entityDTO.CharactersDTO;
import com.example.moviescharactersapi.repository.CharactersRepository;
import com.example.moviescharactersapi.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping( "/api/characters")
public class CharactersController {

    private final CharactersRepository charactersRepository;
    private final MovieRepository movieRepository;

    @PostMapping( value = "create")
    public ResponseEntity<Characters> create( @RequestBody CharactersDTO charactersDTO ) {

        Optional<Characters> chOp = charactersRepository.findById( charactersDTO.getId() );

        if ( chOp.isEmpty() ) {
            Characters newCharacters = new Characters();

            BeanUtils.copyProperties( charactersDTO, newCharacters, "movies" );
            charactersRepository.save( newCharacters );

            return ResponseEntity.status( HttpStatus.CREATED ).body( newCharacters );

        } else {
            log.warn( "Cannot create a character with an existing id" );
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<Characters> update( @RequestBody CharactersDTO characterDTO ) {

        Optional<Characters> chOp = charactersRepository.findById( characterDTO.getId() );

        if ( chOp.isPresent() ) {
            Characters character = chOp.get();
            BeanUtils.copyProperties( characterDTO, character, "movies" );

            charactersRepository.save( character );

            return ResponseEntity.ok().body( character );

        } else {
            log.warn( "You are trying to update a non existen character" );
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Characters>> findAll() {
        List<Characters> ch = charactersRepository.findAll();

        if ( ch.size() == 0 ) {
            log.warn( "There are no characters in the database" );
        }

        return ResponseEntity.ok().body( ch );
    }

    @GetMapping( value = "{id}")
    public ResponseEntity<Characters> findById( @PathVariable( "id") Long id ) {
        Optional<Characters> chOp = charactersRepository.findById( id );

        if ( chOp.isPresent() ) {
            return ResponseEntity.ok().body( chOp.get() );
        } else {
            log.warn( "Id was not entered" );
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping( value = "findbyname/{name}")
    public ResponseEntity<Set<Characters>> findByName( @PathVariable( "name") String name ) {

        Set<Characters> chOp = charactersRepository.findByNameContainsIgnoreCase( name );

        if ( chOp.size() > 0 ) {
            return ResponseEntity.ok().body( chOp );

        } else {
            log.warn( "The name does not exist in the database" );
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping( value = "/findbymovie/{movieId}")
    public ResponseEntity<Set<Characters>> findCharacterByMovieId( @PathVariable( "movieId") Long movieId ) {
        Optional<Movie> movieOp = movieRepository.findById( movieId );

        if ( movieOp.isPresent() && movieOp.get().getCharacters().size() > 0 ) {
            Set<Characters> charact = charactersRepository.findByMoviesId( movieId );
            return ResponseEntity.ok().body( charact );
        } else {
            log.warn( "The movie id non exist or the movie has no associated characters" );
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping( value = "/findByRangeAge/{minAge}/{maxAge}")
    public ResponseEntity<Set<Characters>> findByRangeAge( @PathVariable( "minAge") Integer minAge, @PathVariable( "maxAge") Integer maxAge ) {

        if ( minAge <= 0 && maxAge > 150 || minAge > maxAge ) {
            log.warn( "The age must be between 0 and 150" +
                              "and the first value entered must be greater than the second the second" );
            return ResponseEntity.notFound().build();

        }

        Set<Characters> charact = charactersRepository.findByAgeIsBetween( minAge, maxAge );

        if ( charact.size() > 0 ) {
            return ResponseEntity.ok().body( charact );
        } else {
            log.info( "Character not found" );
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping( value = "/findByWeight/{minWeight}/{maxWeight}")
    public ResponseEntity<Set<Characters>> findByWeight( @PathVariable( "minWeight") Integer minWeight, @PathVariable( "maxWeight") Integer maxWeight ) {

        if ( minWeight <= 0 && maxWeight > 150 || minWeight > maxWeight ) {
            log.info( "The weight must be between 0 and 150" +
                              "and the first value entered must be greater than the second the second" );
            return ResponseEntity.notFound().build();
        }

        Set<Characters> charact = charactersRepository.findByWeightIsBetween( minWeight, maxWeight );

        if ( charact.size() > 0 ) {
            return ResponseEntity.ok().body( charact );
        } else {
            log.info( "Character not found" );
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping( value = "/{id}")
    public ResponseEntity<Characters> deleteById( @PathVariable( "id") Long id ) {

        if ( charactersRepository.existsById( id ) ) {

            charactersRepository.deleteById( id );
            log.info( "The character was deleted" );
            return ResponseEntity.noContent().build();

        } else {
            log.warn( "You are trying to delete a non existen character" );
            return ResponseEntity.notFound().build();
        }
    }
}
