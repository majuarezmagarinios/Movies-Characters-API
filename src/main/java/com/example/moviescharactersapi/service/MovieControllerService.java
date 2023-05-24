package com.example.moviescharactersapi.service;

import com.example.moviescharactersapi.entity.Movie;
import com.example.moviescharactersapi.entityDTO.MoviesSearchDTO;
import com.example.moviescharactersapi.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieControllerService {

    private final MovieRepository movieRepository;

    public ResponseEntity<Set<MoviesSearchDTO>> getSetResponseEntity( Set<Movie> movies, Set<MoviesSearchDTO> moviesSearchDTO ) {
        for (Movie movie : movies) {
            MoviesSearchDTO moviesSearch = new MoviesSearchDTO();
            moviesSearch.setTitle( movie.getTitle() );
            moviesSearch.setImage( movie.getImage() );
            moviesSearch.setCreationDate( movie.getCreationDate() );
            moviesSearchDTO.add( moviesSearch );
        }
        return ResponseEntity.ok().body( moviesSearchDTO );
    }

    public ResponseEntity<Set<MoviesSearchDTO>> orderMovies( String order ) {

        Set<MoviesSearchDTO> moviesSearchDTO = new LinkedHashSet<>();
        Set<Movie> movies;

        switch (order.toLowerCase()) {
            case "asc" -> {
                movies = movieRepository.findAllByOrderByCreationDateAsc();
                return getSetResponseEntity( movies, moviesSearchDTO );
            }
            case "desc" -> {
                movies = movieRepository.findAllByOrderByCreationDateDesc();
                return getSetResponseEntity( movies, moviesSearchDTO );
            }
            default -> {
                log.warn( "You must enter asc or desc to order" );
                return ResponseEntity.badRequest().build();
            }
        }
    }

}
