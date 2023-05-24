package com.example.moviescharactersapi.repository;

import com.example.moviescharactersapi.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
