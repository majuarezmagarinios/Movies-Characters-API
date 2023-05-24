package com.example.moviescharactersapi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
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
@Table( name = "_movies")
@SQLDelete( sql = "UPDATE _movies SET _movies.deleted = true WHERE id=?")
@Where( clause = "deleted = false")
public class Movie {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( length = 200)
    private String image;

    private String title;

    @JsonFormat( pattern = "yyyy/MM/dd")
    private Date creationDate;

    private Integer qualification;
    private Boolean deleted = false;

    @JsonIgnoreProperties( value = {"deleted", "movies"})
    @ManyToMany( mappedBy = "movies", fetch = FetchType.LAZY)
    private Set<Characters> characters = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties( value = {"movies"})
    @JoinColumn( name = "genre_id")
    private Genre genre;

    @Override
    public int hashCode() {
        return Objects.hash( id, image, title, creationDate, qualification );
    }

}
