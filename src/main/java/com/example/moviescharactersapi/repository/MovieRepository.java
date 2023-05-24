package com.example.moviescharactersapi.repository;

import com.example.moviescharactersapi.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Set<Movie> findByTitleContainingIgnoreCase( String title );

    Set<Movie> findMovieByGenreId( Long genreId );

    Set<Movie> findAllByOrderByCreationDateAsc();

    Set<Movie> findAllByOrderByCreationDateDesc();

    @Modifying
    @Transactional
    @Query( value = "INSERT INTO characters_movie(movies_id, characters_id) VALUES (:movieId, :characterId)", nativeQuery = true)
    void addCharacterToMovie( @Param( "movieId") Long movieId, @Param( "characterId") Long characterId );

    @Modifying
    @Transactional
    @Query( value = "DELETE FROM characters_movie WHERE movies_id = :movieId AND characters_id = :characterId", nativeQuery = true)
    void disassociateCharacterMovie( @Param( "movieId") Long movieId, @Param( "characterId") Long characterId );

}
