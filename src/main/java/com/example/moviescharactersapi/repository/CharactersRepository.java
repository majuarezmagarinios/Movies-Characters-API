package com.example.moviescharactersapi.repository;

import com.example.moviescharactersapi.entity.Characters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CharactersRepository extends JpaRepository<Characters, Long> {

    Set<Characters> findByNameContainsIgnoreCase( String name );

    Set<Characters> findByMoviesId( @Param( "movieId") Long movieId );

    Set<Characters> findByAgeIsBetween( Integer ageMin, Integer ageMax );

    Set<Characters> findByWeightIsBetween( Integer minWeight, Integer maxWeight );
}
