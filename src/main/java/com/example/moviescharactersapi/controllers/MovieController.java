package com.example.moviescharactersapi.controllers;

import com.example.moviescharactersapi.entity.Characters;
import com.example.moviescharactersapi.entity.Genre;
import com.example.moviescharactersapi.entity.Movie;
import com.example.moviescharactersapi.entityDTO.MovieDTO;
import com.example.moviescharactersapi.entityDTO.MoviesSearchDTO;
import com.example.moviescharactersapi.repository.CharactersRepository;
import com.example.moviescharactersapi.repository.GenreRepository;
import com.example.moviescharactersapi.repository.MovieRepository;
import com.example.moviescharactersapi.service.MovieControllerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RequestMapping( "/api/movies")
@RestController
public class MovieController {

    private final MovieRepository movieRepository;
    private final CharactersRepository charactersRepository;
    private final GenreRepository genreRepository;
    private final MovieControllerService movieControllerService;

    @PostMapping( value = "create")
    @PreAuthorize( "hasAnyRole('ADMIN', 'MANAGER')" )
    public ResponseEntity<Movie> create( @RequestBody MovieDTO moviesDTO ) {
        Optional<Movie> movieOp = movieRepository.findById( moviesDTO.getId() );

        if ( movieOp.isEmpty() ) {

            Movie newMovies = new Movie();

            List<String> ignoreFields = Arrays.asList( "charactersId", "genre" );
            BeanUtils.copyProperties( moviesDTO, newMovies, ignoreFields.toArray( new String[0] ) );

            movieRepository.save( newMovies );
            return ResponseEntity.status( HttpStatus.CREATED ).body( newMovies );

        } else {
            log.warn( "Cannot create a movie with an existing id" );
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    @PreAuthorize( "hasAnyRole('ADMIN', 'MANAGER')" )
    public ResponseEntity<Movie> update( @RequestBody MovieDTO movieDTO ) {

        Optional<Movie> movieOp = movieRepository.findById( movieDTO.getId() );

        if ( movieOp.isPresent() ) {
            Movie movies = movieOp.get();

            List<String> ignoreFields = Arrays.asList( "characters", "genre" );
            BeanUtils.copyProperties( movieDTO, movies, ignoreFields.toArray( new String[0] ) );

            movieRepository.save( movies );
            return ResponseEntity.ok().body( movies );
        } else {
            log.warn( "You are trying to update a non exist Movie" );
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Movie>> findAll() {
        List<Movie> mo = movieRepository.findAll();

        if ( mo.size() == 0 ) {
            log.warn( "There are no movies in the database" );
        }

        return ResponseEntity.ok().body( mo );
    }


    @GetMapping( value = "{id}")
    public ResponseEntity<Movie> findById( @PathVariable Long id ) {

        Optional<Movie> moviesOp = movieRepository.findById( id );

        if ( moviesOp.isPresent() ) {
            return ResponseEntity.ok( moviesOp.get() );
        } else {
            log.warn( "Id was not entered" );
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping( value = "findbytitle/{title}")
    public ResponseEntity<Set<Movie>> findMovieByName( @PathVariable String title ) {

        Set<Movie> movies = movieRepository.findByTitleContainingIgnoreCase( title );

        if ( movies.size() > 0 ) {
            return ResponseEntity.ok().body( movies );

        } else {
            log.warn( "The name does not exist in the database" );
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping( value = "/{movieId}/characters/{characterId}")
    @PreAuthorize( "hasAnyRole('ADMIN', 'MANAGER')" )
    public ResponseEntity<Movie> addCharacterToMovie( @PathVariable Long movieId, @PathVariable( "characterId") Long characterId ) {

        Optional<Characters> characterOp = charactersRepository.findById( characterId );
        Optional<Movie> movieOp = movieRepository.findById( movieId );

        if ( characterOp.isEmpty() ) {
            log.warn( "The character does not exist" );
            return ResponseEntity.notFound().build();
        }

        if ( movieOp.isEmpty() ) {
            log.warn( "The movie does not exist" );
            return ResponseEntity.notFound().build();
        }

        movieRepository.addCharacterToMovie( movieId, characterId );

        return ResponseEntity.ok().body( movieOp.get() );
    }

    @PutMapping( "/{movieId}/characters/{characterId}")
    @PreAuthorize( "hasAnyRole('ADMIN', 'MANAGER')" )
    public ResponseEntity<Void> disassociateCharacterMovies( @PathVariable Long movieId, @PathVariable Long characterId ) {

        Optional<Movie> movieOp = movieRepository.findById( movieId );
        Optional<Characters> chOp = charactersRepository.findById( characterId );

        if ( movieOp.isPresent() && chOp.isPresent() ) {

            movieRepository.disassociateCharacterMovie( movieId, characterId );
            return ResponseEntity.noContent().build();

        } else {

            log.warn( "The movie or character does not exist" );
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping( value = "findbygenre/{id}")
    public ResponseEntity<Set<Movie>> findByGenre( @PathVariable Long id ) {

        Optional<Genre> genreOp = genreRepository.findById( id );

        if ( genreOp.isEmpty() ) {
            log.warn( "The genre does not exist" );
            return ResponseEntity.notFound().build();
        }

        Set<Movie> movies = movieRepository.findMovieByGenreId( id );

        if ( movies.size() > 0 ) {
            return ResponseEntity.ok().body( movies );
        } else {
            log.warn( "The genre does not exist in the database" );
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping( value = "findbydate/{order}")
    public ResponseEntity<Set<MoviesSearchDTO>> findAllAndOrder( @PathVariable String order ) {

        if ( movieRepository.findAll().size() < 1 ) {
            log.warn( "There are no movies in the database" );
            return ResponseEntity.notFound().build();
        }

        return movieControllerService.orderMovies( order );

    }


    @DeleteMapping( value = "/{id}")
    @PreAuthorize( "hasAnyRole('ADMIN', 'MANAGER')" )
    public ResponseEntity<Void> deleteById( @PathVariable Long id ) {

        if ( movieRepository.existsById( id ) ) {

            movieRepository.deleteById( id );
            log.info( "The movie was deleted" );
            return ResponseEntity.noContent().build();

        } else {
            log.warn( "You are trying to delete a non existen movie" );
            return ResponseEntity.notFound().build();
        }
    }
}
