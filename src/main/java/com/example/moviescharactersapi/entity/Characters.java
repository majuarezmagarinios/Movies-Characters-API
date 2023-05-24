package com.example.moviescharactersapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.HashSet;
import java.util.Set;

/**
 * recordar que se omitió de la serializacion el atributo deleted
 */
@JsonIgnoreProperties( value = {"deleted"})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table( name = "_characters")
@SQLDelete( sql = "UPDATE _characters SET _characters.deleted = true WHERE id=?")
@Where( clause = "deleted = false")
public class Characters {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( length = 200)
    private String image;

    private String name;

    private Integer age;

    private Integer weight;

    private String history;

    private Boolean deleted = false;

    @JsonIgnoreProperties( value = {"characters"})
    @ManyToMany( fetch = FetchType.LAZY)
    @JoinTable( name = "characters_movie",
            joinColumns = @JoinColumn( name = "characters_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn( name = "movies_id", referencedColumnName = "id")
    )
    private Set<Movie> movies = new HashSet<>();
}
