package com.example.moviescharactersapi.entityDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class MovieDTO {
    private Long id;
    private String image;
    private String title;
    private Date creationDate;
    private Integer qualification;
}
