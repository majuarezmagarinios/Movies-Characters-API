package com.example.moviescharactersapi.entityDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MoviesSearchDTO {

    @Column( length = 200)
    private String image;

    private String title;

    @JsonFormat( pattern = "yyyy/MM/dd")
    private Date creationDate;
}
