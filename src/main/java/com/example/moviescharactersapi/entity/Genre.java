package com.example.moviescharactersapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table( name = "_genre")
public class Genre {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( length = 200)
    private String image;

    private String name;

    @JsonIgnoreProperties( value = {"characters", "genre"})
    @OneToMany( fetch = FetchType.LAZY, mappedBy = "genre")
    private Set<Movie> movies = new HashSet<>();

}
